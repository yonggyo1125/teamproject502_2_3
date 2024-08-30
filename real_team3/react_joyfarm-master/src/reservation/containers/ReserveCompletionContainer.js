import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { myApiGet } from '../apis/apiInfo'; // 예약된 상세조회로 끌고옴
import Loading from '../../commons/components/Loading';
import { useTranslation } from 'react-i18next';
import CompletionForm from '../components/CompletionForm';

const ReserveCompletionContainer = ({ setPageTitle, setMainTitle }) => {
  const [data, setData] = useState(null);
  const { seq } = useParams();
  const { t } = useTranslation();

  useEffect(() => {
    (async () => {
      try {
        const res = await myApiGet(seq);
        setData(res);
        setMainTitle(
          `${res.name} ${t('님의_')} ${res.townName} ${t('예약_확인')}`,
        );
        setPageTitle(`${res.townName} ${t('예약_확인')}`);
      } catch (err) {
        console.error(err);
      }
    })();
  }, [seq, setMainTitle, setPageTitle, t]); //변화감지 값 넣어주기

  if (!data) {
    return <Loading />;
  }
  console.log(data);

  return (
    <>
      <CompletionForm data={data} />
    </>
  );
};

export default React.memo(ReserveCompletionContainer);
