import React, { useEffect, useState, useCallback, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { apiGet } from '../apis/apiInfo';
import Loading from '../../commons/components/Loading';
import KakaoMap from '../../map/KakaoMap';
import ItemImage from '../components/ItemImage';
import ItemDescription from '../components/ItemDescription';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import farmImg from '../../images/farm.jpg';
import apiCancel from '../apis/apiCancel';

const Wrapper = styled.div`
  display: flex;
  margin-bottom: 20px;
  border-bottom: solid 1px #e6e6eb;
  padding-bottom: 20px;

  .img {
    width: 100%;
  }
`;

const MyReserveViewContainer = ({ setPageTitle }) => {
  const { t } = useTranslation();
  const [item, setItem] = useState(null);
  const [mapOptions, setMapOptions] = useState({ height: '600px', zoom: 3 });

  const { seq } = useParams();

  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const res = await apiGet(seq);
        console.log('Fetched data:', res); // 데이터 확인용 로그 추가
        setPageTitle(`${res.townName} ${t('예약정보')}`);
        setItem(res);

        const position = { lat: res.latitude, lng: res.longitude };
        setMapOptions((opt) => {
          const options = res.latitude
            ? { ...opt, center: position, marker: position }
            : { ...opt, doroAddress: res.doroAddress };

          return options;
        });
      } catch (err) {
        console.error(err);
      }
    })();
  }, [seq, t, setPageTitle]);

  const onShowImage = useCallback((imageUrl) => {
    console.log('이미지 주소', imageUrl);
  }, []);

  const onClick = useCallback(
    (seq) => {
      /* 예약 취소 처리 S */
      (async () => {
        try {
          const res = await apiCancel(seq);
          // 예약 취소 성공시 예약 취소 페이지 이동
          navigate(`/myreservation/cancel/${res.seq}`, { replace: true });
        } catch (err) {
          console.error(err);
        }
      })();
      /* 예약 취소 처리 E */
    },
    [navigate],
  );

  if (!item) {
    return <Loading />;
  }

  return (
    <>
      <Wrapper>
        {item.townImage ? (
          <ItemImage images={item.townImage} onClick={onShowImage} />
        ) : (
          //이미지 없는 경우 대체
          <ItemImage className="img" images={farmImg} onClick={onShowImage} />
        )}
        <ItemDescription item={item} onClick={onClick} />
      </Wrapper>
      <h1>{t('길찾기')}</h1>
      <KakaoMap {...mapOptions} />
    </>
  );
};

export default React.memo(MyReserveViewContainer);
