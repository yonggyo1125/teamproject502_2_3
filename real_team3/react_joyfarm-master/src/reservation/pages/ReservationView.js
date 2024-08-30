import React, { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import {
  OuterBox,
  ContentBox,
} from '../../commons/components/LayoutBox';
import { MainTitle } from '../../commons/components/TitleBox';
import ReserveViewContainer from '../containers/ReserveViewContainer';
import Header from '../../layouts/Header';
import SubTitleLink from '../../commons/SubTitleLink';

const ReservationView = () => {
  const { t } = useTranslation();
  const [pageTitle, setPageTitle] = useState('');

  return (
    <>
      <SubTitleLink text={t('체험활동_상세_정보')} href="" />
      <Helmet>
        <title>{pageTitle}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <MainTitle>{t('체험활동_상세_정보')}</MainTitle>
          <ReserveViewContainer setPageTitle={setPageTitle} />
        </ContentBox>
      </OuterBox>
    </>
  );
};

export default React.memo(ReservationView);
