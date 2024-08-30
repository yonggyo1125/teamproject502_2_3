import React, { useContext } from 'react';
import styled from 'styled-components';
import { PageNavWrap, PageTitle } from '../commons/components/LayoutBox';
import { IoMdHome } from 'react-icons/io';
import RecommendContext from '../commons/modules/CommonContext';

const HeaderBox = styled.header`
  /* 헤더 스타일 */
`;

const StyledLink = styled.a`
  font-size: 1.3em;
  color: #000; /* 링크 색상 설정 */
  text-decoration: none; /* 밑줄 제거 */
  &:hover {
    color: #007bff; /* 호버 시 색상 변경 */
  }
`;

const Header = () => {
  const {
    states: { linkHref, linkText },
  } = useContext(RecommendContext);
  /*
  const location = useLocation();

  // 현재 페이지 URL경로
  const currentPath = location.pathname;

  let linkText = '';
  let linkHref = '#';
  let pattern = new RegExp('/d+$');

  if (currentPath === '/recommend/tour' || currentPath.concat(pattern)) {
    linkText = '추천 여행지';
    linkHref = '/recommend/tour';
  } else if (
    currentPath === '/recommend/festival' ||
    currentPath.concat(pattern)
  ) {
    linkText = '지역별 축제 정보';
    linkHref = '/recommend/festival';
  } else if (currentPath === '/recommend/mylocation') {
    linkText = '내 위치 주변 농촌 체험';
    linkHref = '/recommend/mylocation';
  } else {
    linkText = 'HOME';
    linkHref = '/';
  }
  */

  return (
    <HeaderBox>
      <PageNavWrap>
        <StyledLink href="/">
          <IoMdHome /> HOME
        </StyledLink>
        &nbsp;&nbsp; &gt; &nbsp;&nbsp;
        <StyledLink href={linkHref}>{linkText}</StyledLink>
        <PageTitle>
          <h1>{linkText}</h1>
        </PageTitle>
      </PageNavWrap>
    </HeaderBox>
  );
};

export default Header;
