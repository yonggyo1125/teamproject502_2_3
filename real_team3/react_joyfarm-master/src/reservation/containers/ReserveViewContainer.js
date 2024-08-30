import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import { apiGet } from '../apis/apiInfo';
import Loading from '../../commons/components/Loading';
import KakaoMap from '../../map/KakaoMap';
import ItemImage from '../components/ItemImage';
import ItemDescription from '../components/ItemDescription';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import farmImg from '../../images/farm.jpg';

const Wrapper = styled.div`
  display: flex;
  margin-bottom: 20px;
  border-bottom: solid 1px #e6e6eb;
  padding-bottom: 20px;

  img {
    width: 100%;
  }
`;

const ReserveViewContainer = ({ setPageTitle }) => {
  const { t } = useTranslation();
  const [item, setItem] = useState(null);
  const [loading, setLoading] = useState(false);
  const [mapOptions, setMapOptions] = useState({ height: '600px', zoom: 3 });

  const { seq } = useParams();

  useEffect(() => {
    setLoading(true);

    apiGet(seq).then((item) => {
      setPageTitle(item.title);
      setItem(item);

      const position = { lat: item.latitude, lng: item.longitude };
      setMapOptions((opt) => {
        const options = item.latitude
          ? { ...opt, center: position, marker: position }
          : { ...opt, doroAddress: item.doroAddress };

        return options;
      });
    });

    setLoading(false);
  }, [seq, setPageTitle]);

  const onShowImage = useCallback((imageUrl) => {
    console.log('이미지 주소', imageUrl);
  }, []);

  if (loading || !item) {
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
        <ItemDescription item={item} />
      </Wrapper>
      <h1>{t('길찾기')}</h1>
      <KakaoMap {...mapOptions} />
    </>
  );
};

export default React.memo(ReserveViewContainer);
