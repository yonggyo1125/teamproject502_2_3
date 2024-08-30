import React from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import { MainTitle, SubTitle } from '../../commons/components/TitleBox';
import {
  OuterBox,
  PageNav,
  PageNavWrap,
  PageTitle,
  ContentBox,
} from '../../commons/components/LayoutBox';
import MyReserveListContainer from '../containers/MyReserveListContainer';
import { Link } from 'react-router-dom';
import MemberOnlyContainer from '../../member/containers/MemberOnlyContainer';
import Header from '../../layouts/Header';
import SubTitleLink from '../../commons/SubTitleLink';

const MyReservList = () => {
  const { t } = useTranslation();

  return (
    <MemberOnlyContainer>
      <SubTitleLink text={t('나의_예약_현황')} href="/myreservation/list" />
      <Helmet>
        <title>{t('나의_예약_현황')}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <PageTitle>
            <SubTitle>{t('예약_현황_리스트')}</SubTitle>
          </PageTitle>
          <MyReserveListContainer />
        </ContentBox>
      </OuterBox>
    </MemberOnlyContainer>
  );
};

export default React.memo(MyReservList);
