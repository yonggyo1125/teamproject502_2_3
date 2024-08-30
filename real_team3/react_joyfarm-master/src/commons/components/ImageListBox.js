import React from 'react';
import styled from 'styled-components';

export const ImageListBox = styled.div`
  box-sizing: border-box;
  width: 1300px;
  min-height: 700px;
  margin: 0 auto;
  display: grid;
  place-items: center;
  gap: 60px;
  grid-template-columns: repeat(3, 1fr);
  margin-top: 60px;
  justify-content: center;
`;

export const ImageListBox2 = styled.div`
  box-sizing: border-box;
  width: 100%;
  min-height: 700px;
  margin: 0 auto;
  display: grid;
  place-items: center;
  gap: 30px;
  grid-template-columns: repeat(3, 1fr);
  margin-top: 60px;
  justify-content: center;
  `;

  export const ImageListBox3 = styled.div`
  box-sizing: border-box;
  width: 1300px;
  min-height: 700px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
`;

export const WishListBox = styled.div`
  box-sizing: border-box;
  width: 950px;
  margin: 0 auto;
  display: grid;
  place-items: center;
  grid-template-columns: repeat(2, 1fr);
`;
