import React, { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import { OuterBox, ContentBox } from '../../../commons/components/LayoutBox';
import SubTitleLink from '../../../commons/SubTitleLink';
import { DetailImgBox2 } from '../../../commons/components/DetailBox';
import Header from '../../../layouts/Header';
import MyLocListContainer from '../containers/MyLocListContainer';

const MylocationView = () => {
  const [SubPageTitle, setSubPageTitle] = useState('');
  const { t } = useTranslation();
  return (
    <>
      <SubTitleLink
        text={t('내_위치_주변_여행지')}
        href="/recommend/mylocation"
      />
      <Helmet>
        <title>{SubPageTitle}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <DetailImgBox2>
          <MyLocListContainer />
        </DetailImgBox2>
      </OuterBox>
    </>
  );
};

export default React.memo(MylocationView);
