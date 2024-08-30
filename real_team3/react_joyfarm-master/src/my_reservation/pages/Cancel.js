import React from 'react';
import MemberOnlyContainer from '../../member/containers/MemberOnlyContainer';
import ReserveCancelContainer from '../containers/ReserveCancelContainer';
import { OuterBox, ContentBox } from '../../commons/components/LayoutBox';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import SubTitleLink from '../../commons/SubTitleLink';
import Header from '../../layouts/Header';

const Cancel = () => {
  const { t } = useTranslation();

  return (
    <MemberOnlyContainer>
      <SubTitleLink text={t('나의 예약 현황')} href="/reservation/list" />
      <Helmet></Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <ReserveCancelContainer />
        </ContentBox>
      </OuterBox>
    </MemberOnlyContainer>
  );
};

export default React.memo(Cancel);
