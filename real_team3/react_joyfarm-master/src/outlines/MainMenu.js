import React from 'react';
import styled from 'styled-components';
import { Link, NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { color } from '../styles/color';
import fontSize from '../styles/fontSize';
import logo from '../images/logo.png';

const { dark, light, midGreen, white, lightGreen } = color;

const MenuBox = styled.nav`
  background: ${white};
  width: 100%;
  max-width: 1440px;
  margin: 0 auto;
  padding: 0 20px;
  
`;

const MenuWrap = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const MenuItem = styled.div`
  position: relative;
  display: inline-block;
  margin: 0 10px;

  a {
    color: ${dark};
    line-height: 100px;
    width: 200px;
    padding: 0 20px;
    font-size: ${fontSize.medium};
    text-decoration: none;
    text-align: center;
    display: block;
    font-size: 1.5em;
    font-weight: bold;

    &:hover {
      background: ${white};
      text-decoration-line: underline;
      text-decoration-thickness: 2px;
      text-underline-offset: 10px;
      color: ${midGreen};
    }
  }

  &:hover .sub-menu {
    display: flex;
  }
`;

const SubMenu = styled.div`
  position: absolute;
  top: 80px;
  background: ${white};
  display: none;
  flex-direction: column;
  width: 200px;
  height: auto;
  z-index: 1000;

  a {
    padding: 0 20px;
    color: ${dark};
    font-size: 1.3em;
    text-decoration: none;

    &:hover {
      background: ${lightGreen};
    }
  }
`;

const Logo = styled.div`
  display: flex;
  align-items: center;
  margin: 0 10px;

  img {
    width: 180px;
    height: auto;
  }
`;

const MainMenu = () => {
  const { t } = useTranslation();

  return (
    <MenuBox>
      <MenuWrap>
        <MenuItem>
          <NavLink to="/reservation/list" className={({ isActive }) => isActive ? 'on' : ''}>
            {t('농촌체험 예약')}
          </NavLink>
        </MenuItem>
        <MenuItem>
          <NavLink
            to="/myreservation/list"
            className={({ isActive }) => (isActive ? 'on' : '')}
          >
            {t('나의_예약_현황')}
          </NavLink>
        </MenuItem>
        <MenuItem>
          <Logo>
            <Link to="/">
              <img src={logo} alt={t('로고')} />
            </Link>
          </Logo>
        </MenuItem>
        <MenuItem>
          <NavLink className={({ isActive }) => (isActive ? 'on' : '')}>
            {t('조이팜의_추천')}
          </NavLink>
          <SubMenu className="sub-menu">
            <NavLink to="/recommend/tour">{t('추천_여행지')}</NavLink>
            <NavLink to="/recommend/festival">{t('지역별_축제_정보')}</NavLink>
            <NavLink to="/recommend/mylocation">
              {t('내_위치_주변_여행지')}
            </NavLink>
          </SubMenu>
        </MenuItem>
        <MenuItem>
          <NavLink className={({ isActive }) => (isActive ? 'on' : '')}>
            {t('커뮤니티')}
          </NavLink>
          <SubMenu className="sub-menu">
            <NavLink to="/board/list/review">{t('체험_여행후기')}</NavLink>
            <NavLink to="/board/list/notice">{t('공지사항')}</NavLink>
            <NavLink to="/board/list/qna">{t('QnA')}</NavLink>
          </SubMenu>
        </MenuItem>
      </MenuWrap>
    </MenuBox>
  );
};

export default React.memo(MainMenu);