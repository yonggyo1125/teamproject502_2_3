import React, { useEffect, useState, useCallback, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { apiGet } from '../apis/apiInfo';
import Loading from '../../../commons/components/Loading';
import KakaoMap from '../../../map/KakaoMap';
import ItemImage from '../components/ItemImage';
import ItemDescription from '../components/ItemDescription';
import styled from 'styled-components';
import ListButton from '../../../commons/components/ListButton';
import CommonContext from '../../../commons/modules/CommonContext';

const Wrapper = styled.div`
  display: flex;
  position: relative;
  padding: 48px 0;
  margin-bottom: 20px;
  border-bottom: solid 1px #e6e6eb;
`;

const ViewContainer = () => {
  const { t } = useTranslation();
  const [item, setItem] = useState(null);
  const [loading, setLoading] = useState(false);
  const [mapOptions, setMapOptions] = useState({ height: '600px', zoom: 3 });

  const { seq } = useParams();

  const {
    actions: { setLinkText, setLinkHref, setSubTitle },
  } = useContext(CommonContext);

  useEffect(() => {
    setLoading(true);
    apiGet(seq).then((item) => {
      setLinkText(t('추천여행지'));
      setLinkHref('/recommend/tour');
      setSubTitle(item.title);
      setItem(item);
      const position = { lat: item.latitude, lng: item.longitude };
      setMapOptions((opt) => {
        const options = item.latitude
          ? { ...opt, center: position, marker: position }
          : { ...opt, address: item.address };

        return options;
      });
    });

    setLoading(false);
  }, [seq, setLinkHref, setLinkText]);

  const onShowImage = useCallback((imageUrl) => {
    console.log('이미지 주소', imageUrl);
  }, []);

  if (loading || !item) {
    return <Loading />;
  }

  return (
    <>
      <Wrapper>
        {item.photoUrl && (
          <ItemImage images={item.photoUrl} onClick={onShowImage} />
        )}
        <ItemDescription item={item} />
      </Wrapper>
      <h1>{t('길찾기')}</h1>
      <KakaoMap {...mapOptions} />
      <ListButton />
    </>
  );
};

export default React.memo(ViewContainer);
