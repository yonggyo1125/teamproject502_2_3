import React from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { color } from '../../styles/color';
import { useTranslation } from 'react-i18next';

const { white, midGreen } = color;

const ListButton = () => {
  const navigate = useNavigate();

  const { t } = useTranslation();

  const handleButtonClick = () => {
    navigate(-1);
  };

  const Wrapper = styled.div`
    display: flex;
    justify-content: center;
    margin-top: 30px;
  `;

  const Button = styled.button`
    font-size: 1.4em;
    width: 120px;
    height: 50px;
    background-color: ${midGreen};
    color: ${white};
    border-radius: 5px;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;

    &:hover {
      transform: scale(1.02);
      box-shadow: 0 3px 3px rgba(0, 0, 0, 0.3);
    }
  `;

  return (
    <Wrapper>
      <Button onClick={handleButtonClick}>{t('목록')}</Button>
    </Wrapper>
  );
};

export default React.memo(ListButton);
