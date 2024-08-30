import React from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import { SubTitle } from '../../commons/components/TitleBox';
import { OuterBox, ContentBox } from '../../commons/components/LayoutBox';
import ReserveListContainer from '../containers/ReserveListContainer';
import Header from '../../layouts/Header';
import SubTitleLink from '../../commons/SubTitleLink';

const ReservationList = () => {
  const { t } = useTranslation();

  return (
    <>
      <SubTitleLink text={t('체험활동_조회')} href="/reservation/list" />
      <Helmet>
        <title>{t('농촌체험_예약')}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <SubTitle>{t('체험활동_리스트')}</SubTitle>
          <ReserveListContainer />
        </ContentBox>
      </OuterBox>
    </>
  );
};
export default React.memo(ReservationList);
