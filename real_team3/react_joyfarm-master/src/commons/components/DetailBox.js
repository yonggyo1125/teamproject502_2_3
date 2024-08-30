import styled from 'styled-components';
import { color } from '../../styles/color';

const {darkGreen} = color;

export const DetailImgBox = styled.div`
  padding: 30px 30px;
  border: 1px solid #d9d9d9;
  margin: 30px auto;
  max-width: 1200px;
  border-radius: 8px;
  box-shadow: 2px 2px 2px 1px #f5f5f5;
`;

export const DetailImgBox2 = styled.form`
  border: 2.5px solid #d9d9d9;
  margin: auto;
  max-width: 1300px;
  border-radius: 8px;
  box-shadow: 2px 2px 2px 1px #f5f5f5;
`;

export const DetailTitle = styled.div`
  overflow: hidden;
  width: calc(100% - 170px);
  font-size: 1.5rem;
  padding-top: 20px;

  h1 {
    display: flex;
    align-items: center;
    margin: 0;
    line-height: 1;
  }

  .icon {
    color: ${darkGreen};
  }

  svg {
    margin-right: 6px;
  }
`;
