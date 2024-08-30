import styled, { css } from 'styled-components';
import { buttonColor } from '../../styles/color';
import fontSize from '../../styles/fontSize';
import { color } from '../../styles/color';

const { big, medium, normal } = fontSize;

const commonStyle = css`
  width: 100%;
  border-radius: 3px;
  cursor: pointer;
`;

const { midGreen, white } = color;

export const SmallButton = styled.button`
  font-size: ${normal};
  height: 30px;
  ${commonStyle}

  ${({ color }) =>
    buttonColor[color] &&
    css`
      background: ${buttonColor[color][0]};
      color: ${buttonColor[color][1]};
      border: 1px solid ${buttonColor[color][2]};
    `}

  ${({ width }) => css`
    width: ${width && '100px'};
  `}
`;

export const MidButton = styled.button`
  font-size: ${medium};
  height: 40px;
  ${commonStyle}

  ${({ color }) =>
    buttonColor[color] &&
    css`
      background: ${buttonColor[color][0]};
      color: ${buttonColor[color][1]};
      border: 1px solid ${buttonColor[color][2]};
    `}
`;

export const BigButton = styled.button`
  font-size: ${big};
  height: 45px;
  ${commonStyle}

  ${({ color }) =>
    buttonColor[color] &&
    css`
      background: ${buttonColor[color][0]};
      color: ${buttonColor[color][1]};
      border: 1px solid ${buttonColor[color][2]};
    `}
`;

export const ButtonGroup = styled.div`
  display: flex;
  width: ${({ width }) => (width ? `${width}px` : '100%')};
  margin: 20px auto;

  button {
    width: 0;
    flex-grow: 1;
  }

  button + button {
    margin-left: 5px;
  }
`;

export const ZzimButton = styled.div`
  font-size: 1.3em;
  font-weight: 700;
  width: 200px;
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
    transform: scale(1.05);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
  }
`;

export const BoardButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: ${medium};
  font-weight: 600px;
  cursor: pointer;
  width: 100px;
  height: 35px;
  background-color: ${midGreen};
  border-radius: 5px;
  color: white;
  a {
    color: white;
  }
`;
