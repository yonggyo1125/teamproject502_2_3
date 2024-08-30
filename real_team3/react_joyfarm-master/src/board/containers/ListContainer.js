import React, { useEffect, useState, useCallback } from 'react';
import { useParams, useSearchParams } from 'react-router-dom';
import Loading from '../../commons/components/Loading';
import apiConfig from '../apis/apiConfig';
import { getList } from '../apis/apiBoard';
import Pagination from '../../commons/components/Pagination';
import DefaultList from '../components/skins/default/List'; // 기본 스킨
import GalleryList from '../components/skins/gallery/List'; // 갤러리 스킨
import ListMain from '../components/skins/default/ListMain';

function getQueryString(searchParams) {
  const qs = { limit: 10 };
  if (searchParams.size > 0) {
    for (const [k, v] of searchParams) {
      qs[k] = v;
    }
  }
  return qs;

}

function skinRoute(skin) {
  switch (skin) {
    case 'gallery':
      return GalleryList;
    default:
      return DefaultList;
  }
}

const ListContainer = ({ setPageTitle, bid }) => {
  const [searchParams] = useSearchParams();
  const { bid: _bid } = useParams();
  const mode = bid ? 'view' : 'list';
  bid = bid ?? _bid;

  const [board, setBoard] = useState(null);
  const [items, setItems] = useState(null);
  const [pagination, setPagination] = useState(null);
  const [search, setSearch] = useState(() => getQueryString(searchParams));

  useEffect(() => {
    (async () => {
      try {
        // 게시판 설정
        const res1 = await apiConfig(bid);
        setBoard(res1);

        setPageTitle && setPageTitle(res1.bname);

        // 게시글 목록
        const { items, pagination } = await getList(bid, search);
        console.log('Pagination Data:', pagination);
        setItems(items);
        setPagination(pagination);
      } catch (err) {
        console.error(err);
      }
    })();
  }, [bid, search, setPageTitle]);

  const onChange = useCallback((e) => {
    setSearch((search) => ({ ...search, [e.target.name]: [e.target.value] }));
  }, []);

  const onSubmitSearch = useCallback(
    (e) => {
      e.preventDefault();
      setSearch({ ...search, page: 1 });
    },
    [search],
  );

  const onChangePage = useCallback((p) => {
    setSearch((search) => ({ ...search, page: p })); //상태 업데이트
    window.location.hash = '#root'; //페이지 스크롤 이동 처리
    console.log(`Page Changed to ${p}`); //페이지 번호 확인
  }, []);

  if (!board || !items) {
    return <Loading />;
  }

  const { skin } = board;
  const List = skinRoute(skin);

  return (
    <>
      <List items={items} search={search} onChange={onChange} onSubmit={onSubmitSearch} />
      {mode === 'list' && <ListMain />}
      <Pagination pagination={pagination} onClick={onChangePage} />
    </>
  );
};

export default React.memo(ListContainer);
