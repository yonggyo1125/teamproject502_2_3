import React, { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import {
  OuterBox,
  PageNav,
  PageNavWrap,
  PageTitle,
  ContentBox,
} from '../../commons/components/LayoutBox';
import { MainTitle } from '../../commons/components/TitleBox';
import MyReserveViewContainer from '../containers/MyReserveViewContainer';
import { Link, NavLink } from 'react-router-dom';
import Header from '../../layouts/Header';
import SubTitleLink from '../../commons/SubTitleLink';

const MyReserveView = () => {
  const { t } = useTranslation();
  const [pageTitle, setPageTitle] = useState('');

  return (
    <>
      <SubTitleLink text={t('예약내역_상세')} href="" />
      <Helmet>
        <title>{pageTitle}</title>
      </Helmet>
      <OuterBox>
      <Header />
        <ContentBox>
          <MainTitle>{t('예약내역_상세')}</MainTitle>
          <MyReserveViewContainer setPageTitle={setPageTitle} />
        </ContentBox>
      </OuterBox>
    </>
  );
};

export default React.memo(MyReserveView);
