import React from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';
import LoginContainer from '../containers/LoginContainer';
import fontSize from '../../styles/fontSize';
import GuestOnlyContainer from '../containers/GuestOnlyContainer';
import { MainTitle3 } from '../../commons/components/TitleBox';
import { ContentBox3, OuterBox } from '../../commons/components/LayoutBox';
import logo from '../../images/logo3.png';

const { medium, big } = fontSize;

const StyledBox = styled.div`
  .logo {
    width: 300px;
    height: 150px;
  }
`;

const Login = () => {
  const { t } = useTranslation();
  return (
    <GuestOnlyContainer>
      <Helmet>
        <title>{t('로그인')}</title>
      </Helmet>
      <OuterBox>
        <ContentBox3>
          <StyledBox>
            <MainTitle3 style={{ border: 'none' }}>
             <h2>
                <img src={logo} className="logo" alt="로고" />
              </h2>
            </MainTitle3>
            <LoginContainer />
          </StyledBox>
        </ContentBox3>
      </OuterBox>
    </GuestOnlyContainer>
  );
};

export default React.memo(Login);
