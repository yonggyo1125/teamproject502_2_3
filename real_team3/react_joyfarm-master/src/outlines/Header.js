import React, { useContext, useCallback } from 'react';
import cookies from 'react-cookies';
import styled from 'styled-components';
import { Link, NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import classNames from 'classnames';
import fontSize from '../styles/fontSize';
import { color } from '../styles/color';
import MainMenu from './MainMenu';
import UserInfoContext from '../member/modules/UserInfoContext';
import WishListContext from '../commons/contexts/WishListContext';
import { GrUserManager } from 'react-icons/gr';
import { BiLock, BiLockOpen, BiUserPlus, BiWinkSmile } from 'react-icons/bi';

const { midGreen } = color;

const HeaderBox = styled.header`
  .icon {
    position: relative;
    top: 3px;
  }
  .site-top {
    background: #fff;
    height: 45px;
    display: flex;
    align-items: center;
    justify-content: space-between; 

    div {
      text-align: right;

      a {
        display: inline-block;
        line-height: 34px;
        margin: 0 10px;
        font-size: ${fontSize.medium};

        &.on {
          color: ${midGreen};
        }
      }
    }
  }
`;

const Header = () => {
  const { t } = useTranslation();
  const {
    states: { isLogin, userInfo, isAdmin },
    actions: { setIsLogin, setIsAdmin, setUserInfo },
  } = useContext(UserInfoContext);

  const {
    actions: { setBoardWish, setTourWish, setFestivalWish, setActivityWish },
  } = useContext(WishListContext);

  const onLogout = useCallback(() => {
    setIsLogin(false);
    setIsAdmin(false);
    setUserInfo(null);
    cookies.remove('token', { path: '/' });
    setBoardWish([]);
    setTourWish([]);
    setFestivalWish([]);
    setActivityWish([]);
  }, [
    setIsLogin,
    setIsAdmin,
    setUserInfo,
    setBoardWish,
    setTourWish,
    setFestivalWish,
    setActivityWish,
  ]);

  //관리자 url, 환경 변수로 추가함
  const adminUrl =
    process.env.REACT_APP_ADMIN_URL + '?token=' + cookies.load('token');
  // console.log(adminUrl);

  return (
    <HeaderBox>
      <section className="site-top">
        <div className="layout-width">
          {isLogin ? (
            <>
              {/* 로그인 상태 */}
              {/* <span>
                {userInfo?.userName}({userInfo?.email}){t('님_로그인')}
              </span> */}
              {userInfo?.profileImage?.fileUrl && (
                <Link to="/mypage">
                  <img
                    src={userInfo.profileImage.fileUrl}
                    alt="profile"
                    width={40}
                  />
                </Link>
              )}
              {isAdmin && (
                <a href={adminUrl} target="_blank">
                  <GrUserManager className="icon" />
                  {t('사이트_관리')}
                </a>
                //컴포넌트를 교체하는 방식인데 a태그로 새 창 이동해서 페이지 교체
              )}
              <NavLink
                to="/mypage"
                className={({ isActive }) => classNames({ on: isActive })}
              >
                <BiWinkSmile className="icon" />
                {t('마이페이지')}
              </NavLink>
              <NavLink onClick={onLogout}>
                <BiLockOpen className="icon" />
                {t('로그아웃')}
              </NavLink>
              {/*
              <NavLink
                to="/member/logout"
                className={({ isActive }) => classNames({ on: isActive })}
              >
                {t('로그아웃')}
              </NavLink> */}
            </>
          ) : (
            <>
              {/* 미로그인 상태 */}
              <NavLink
                to="/member/join"
                className={({ isActive }) => classNames({ on: isActive })}
              >
                <BiUserPlus className="icon" />
                {t('회원가입')}
              </NavLink>
              <NavLink
                to="/member/login"
                className={({ isActive }) => classNames({ on: isActive })}
              >
                <BiLock className="icon" />
                {t('로그인')}
              </NavLink>
            </>
          )}
        </div>
      </section>
      <MainMenu />
    </HeaderBox>
  );
};

export default React.memo(Header);
