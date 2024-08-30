/* eslint-disable react/jsx-no-undef */
import React from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { MidButton } from '../components/Buttons';
import styled from 'styled-components';
import image from '../../images/loading.gif';
import fontSize from '../../styles/fontSize';

const { big, medium } = fontSize;

const Wrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 80%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: ${medium};

  .inner {
    text-align: center;

    * + * {
      margin-top: 10px;
    }
  }

  .back_main {
    width: 300px;
  }

  .back_main a {
    color: #fff;
  }
`;

const NotFound = () => {
  const { t } = useTranslation();
  return (
    <Wrapper>
      <div className="inner">
        <img src={image} alt="loading" />
        <div>{t('페이지가_없습니다')}</div>
        <MidButton color="midGreen" className="back_main">
          <Link to="/">{t('메인페이지로_돌아가기')}</Link>
        </MidButton>
      </div>
    </Wrapper>
  );
};

export default React.memo(NotFound);
