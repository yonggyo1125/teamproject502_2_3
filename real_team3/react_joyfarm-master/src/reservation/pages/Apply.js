import React, { useState } from 'react';
import MemberOnlyContainer from '../../member/containers/MemberOnlyContainer';
import { OuterBox, ContentBox2 } from '../../commons/components/LayoutBox';
import { MainTitle } from '../../commons/components/TitleBox';
import { Helmet } from 'react-helmet-async';
import ReservationApplyContainer from '../containers/ReserveApplyContainer';
import SubTitleLink from '../../commons/SubTitleLink';
import { useTranslation } from 'react-i18next';
import Header from '../../layouts/Header';

const Apply = () => {
  const { t } = useTranslation();
  const [pageTitle, setPageTitle] = useState('');
  //컨테이너에서 데이터 불러와서 사용

  return (
    <MemberOnlyContainer>
      <SubTitleLink text={t('농촌체험_예약')} href="/reservation/apply" />
      <Helmet>
        <title>{pageTitle}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox2>
          <MainTitle>{pageTitle}</MainTitle>
          <ReservationApplyContainer setPageTitle={setPageTitle} />
        </ContentBox2>
      </OuterBox>
    </MemberOnlyContainer>
  );
};

export default React.memo(Apply);
