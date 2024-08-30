import React from 'react';
import styled from 'styled-components';

const InputBox = styled.input`
  border: 1px solid #d5d5d5;
  height: 50px;
  width: 100%;
  padding: 0 10px;
  border-radius: 5px;
  margin-bottom: 15px;
  font-size: 1.5rem;
`;
export default React.memo(InputBox);
