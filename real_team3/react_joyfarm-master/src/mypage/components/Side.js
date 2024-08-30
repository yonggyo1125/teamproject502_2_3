import React from 'react';
import styled from 'styled-components';
import { NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import classNames from 'classnames';
import { color } from '../../styles/color';
import fontSize from '../../styles/fontSize';
import { useParams } from 'react-router-dom';
const { medium } = fontSize;
const { midGreen, lightGreen } = color;

const Wrapper = styled.aside`
  align-content: center;
  position: fixed;
  top: 25%;
  margin-left: -150px;

  a {
    display: flex;
    align-items: center;
    padding: 25px 25px;
    justify-content: center;
    text-align: center;
    vertical-align: middle;
    font-size: 1.25rem;
    font-weight: bold;
    background: ${lightGreen};
    &.on {
      background: ${midGreen};
      color: white;
    }
  }
  a:hover {
    background: ${midGreen};
    color: white;
  }

  a:first-of-type {
    border-top-left-radius: 15px; /* 좌측 상단 모서리 반경 */
    border-top-right-radius: 15px; /* 우측 상단 모서리 반경 */
  }

  a:last-of-type {
    border-bottom-left-radius: 15px; /* 좌측 상단 모서리 반경 */
    border-bottom-right-radius: 15px; /* 우측 상단 모서리 반경 */
  }
`;

const Side = () => {
  const { t } = useTranslation();
  const { tab } = useParams();
  const wishlistTab = tab || 'reservation';

  return (
    <Wrapper>
      <NavLink
        to="/mypage/info"
        className={({ isActive }) => classNames({ on: isActive })}
      >
        {t('회원정보_수정')}
      </NavLink>
      {/*
      <NavLink
        to="/mypage/reservation"
        className={({ isActive }) => classNames({ on: isActive })}
      >
        {t('예약관리')}
      </NavLink>*/}
      <NavLink
        to="/mypage/board"
        className={({ isActive }) => classNames({ on: isActive })}
      >
        {t('게시글_관리')}
      </NavLink>
      <NavLink
        to={`/mypage/wishlist/${wishlistTab}`}
        className={({ isActive }) => classNames({ on: isActive })}
      >
        {t('찜하기_리스트')}
      </NavLink>
    </Wrapper>
  );
};

export default React.memo(Side);
