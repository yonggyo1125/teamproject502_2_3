import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { apiGet } from '../apis/apiInfo'; // 예약된 상세조회로 끌고옴
import Loading from '../../commons/components/Loading';
import { useTranslation } from 'react-i18next';
import CancelForm from '../components/CancelForm';

const ReserveCanCelContainer = ({ setPageTitle }) => {
  const [data, setData] = useState(null);
  const { seq } = useParams();
  const { t } = useTranslation();

  useEffect(() => {
    (async () => {
      try {
        const res = await apiGet(seq);
        setData(res);
        setPageTitle(`${res.townName} ${t('예약_취소')}`);
      } catch (err) {
        console.error(err);
      }
    })();
  }, [seq, setPageTitle, t]); //변화감지 값 넣어주기

  if (!data) {
    return <Loading />;
  }
  console.log(data);

  return (
    <>
      <CancelForm data={data} />
    </>
  );
};

export default React.memo(ReserveCanCelContainer);
