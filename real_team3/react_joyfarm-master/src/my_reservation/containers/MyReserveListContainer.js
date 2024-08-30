import React, { useEffect, useState, useCallback } from 'react';
import { useSearchParams } from 'react-router-dom';
import { apiList } from '../apis/apiInfo';
import ItemsBox from '../components/ItemsBox';
import SearchBox from '../components/SearchBox';
import Pagination from '../../commons/components/Pagination';
import Loading from '../../commons/components/Loading';

function getQueryString(searchParams) {
  const qs = { limit: 5 };
  if (searchParams.size > 0) {
    for (const [k, v] of searchParams.entries()) {
      qs[k] = v;
    }
  }
  return qs;
}

const MyReserveListContainer = () => {
  const [searchParams] = useSearchParams();

  const [form, setForm] = useState(() => getQueryString(searchParams));
  const [search, setSearch] = useState(() => getQueryString(searchParams));
  const [items, setItems] = useState([]);
  const [pagination, setPagination] = useState({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    console.log(search);
    apiList(search).then((res) => {
      setItems(res.items);
      setPagination(res.pagination);
      setLoading(false);
    });
  }, [search]);

  /* 검색 관련 함수 */
  const onChangeSearch = useCallback((e) => {
    setForm((form) => ({ ...form, [e.target.name]: [e.target.value] }));
  }, []);

  const onSubmitSearch = useCallback(
    (e) => {
      e.preventDefault();
      setSearch({ ...form, page: 1 });
    },
    [form],
  );

  /* 페이지 변경 함수 */
  const onChangePage = useCallback((p) => {
    setSearch((search) => ({ ...search, page: p }));

    // 페이지 변경을 통해 새로운 데이터 로드할떄마다 페이지 위치를 이동 시킴
    // 없으면 새 데이터가 들어와도 위치 고정
    // window.location.hash = '#root';
  }, []);

  /* 로딩 처리 */
  if (loading) {
    return <Loading />;
  }

  return (
    <>
      <SearchBox
        form={form}
        onChange={onChangeSearch}
        onSubmit={onSubmitSearch}
      />
      <ItemsBox items={items} />
      {items.length > 0 && (
        <Pagination onClick={onChangePage} pagination={pagination} />
      )}
    </>
  );
};

export default React.memo(MyReserveListContainer);
