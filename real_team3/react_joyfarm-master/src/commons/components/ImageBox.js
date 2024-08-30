import React from 'react';
import styled from 'styled-components';

export const ImageBgBox = styled.div`
  background: url('${({ url }) => url}') no-repeat center center;
  background-size: 100% 100%;
  width: ${({ width }) => width ?? '200px'};
  height: ${({ height }) => height ?? '200px'};
  object-fit: cover;
`;
