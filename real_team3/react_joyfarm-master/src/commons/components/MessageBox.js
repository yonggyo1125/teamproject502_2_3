import React from 'react';
import styled, { css } from 'styled-components';
import { color } from '../../styles/color';
import fontSize from '../../styles/fontSize';

const { normedium } = fontSize;
const { dark, danger } = color;

const Box = styled.div`
  text-align: left;
  padding: 10px;
  font-size: ${normedium};
  border-radius: 3px;
  color: ${dark};

  ${({ color: c }) =>
    c &&
    css`
      color: ${danger};
    `}
`;

const MessageBox = ({ messages, color, children }) => {
  messages = messages || [];

  if (children) messages.push(children);

  return (
    <>
      {messages.map((message, i) => (
        <Box key={i} color={color}>
          {message}
        </Box>
      ))}
    </>
  );
};

export default React.memo(MessageBox);
