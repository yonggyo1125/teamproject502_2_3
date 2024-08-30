import React from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { color } from '../../styles/color';

const { whiteGray } = color;

const WriteHeader = styled.div`
  width: 1200px;
  height: 70px;
  background: #e2f7dd;
  border: 1px solid ${whiteGray};
  border-top: 3px solid ${whiteGray};
  border-bottom: none;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
  border-radius: 5px 5px 0 0;
`;

export default React.memo(WriteHeader);
