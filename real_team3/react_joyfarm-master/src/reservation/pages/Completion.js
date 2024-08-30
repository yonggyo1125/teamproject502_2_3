import React, { useState } from 'react';
import MemberOnlyContainer from '../../member/containers/MemberOnlyContainer';
import ReserveCompletionContainer from '../containers/ReserveCompletionContainer';
import {
  OuterBox,
  PageNav,
  PageNavWrap,
  PageTitle,
  ContentBox2,
} from '../../commons/components/LayoutBox';
import { MainTitle } from '../../commons/components/TitleBox';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import SubTitleLink from '../../commons/SubTitleLink';

const Completion = () => {
  const { t } = useTranslation();
  const [pageTitle, setPageTitle] = useState('');
  const [mainTitle, setMainTitle] = useState('');
  const navigate = useNavigate(); //상세 정보 페이지로 이동

  return (
    <MemberOnlyContainer>
      <SubTitleLink text={t('예약_완료')} href="/reservation/complete" />
      <Helmet>
        <title>{pageTitle}</title>
      </Helmet>
      <OuterBox>
        <PageNavWrap>
          <PageNav>
            <NavLink
              onClick={() => {
                navigate(-2);
              }}
            >
              체험활동 상세정보
            </NavLink>
            <span> | </span>
            <Link to="">예약 확인</Link>
          </PageNav>
          <PageTitle>
            <MainTitle>{mainTitle}</MainTitle>
          </PageTitle>
        </PageNavWrap>
        <ContentBox2>
          <ReserveCompletionContainer
            setPageTitle={setPageTitle}
            setMainTitle={setMainTitle}
          />
        </ContentBox2>
      </OuterBox>
    </MemberOnlyContainer>
  );
};

export default React.memo(Completion);
