import React from 'react';
import styled from 'styled-components';
import { color } from '../../../styles/color';
import mapImage from '../../../images/Main_Map.png';
import logoImage from '../../../images/logo.png';

const { dark, light, midGreen, white, lightGreen, darkGreen } = color;

const MainLocationWrapper = styled.div`
  display: flex;
  justify-content: center;
  background: linear-gradient(to bottom, ${lightGreen}, ${white});
  padding: 50px 0;
  min-height: 950px;
`;

const ContentWrapper = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: space-between
  max-width: 1440px; 
  width: 100%; 
  padding: 0 20px;
  
`;

const MapImage = styled.img`
  margin-left: 200px;
  margin-top: 50px;
  width: 40%;
  height: auto;
  object-fit: cover;
  margin-bottom: 20px;
`;

const InnerContentWrapper = styled.div`
  margin-top: 100px;
  display: flex;
  flex-direction: column;
  width: 40%;
  padding-left: 50px;
`;

const LogoImage = styled.img`
  width: 500px;
  margin-bottom: 30px;
`;

const HighlightBox = styled.div`
  background-color: ${darkGreen};
  color: ${white};
  padding: 25px 40px;
  width: 85%;
  margin-bottom: 30px;
  display: flex;
  font-size: 2em;
  font-weight: bold;
  // border-top-left-radius: 40px;
  // border-bottom-left-radius: 40px;
  border-radius: 20px;
  margin-right: 0;
`;

const ButtonContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 40px;
  width: 100%;
`;

const Button = styled.button`
  background-color: ${white};
  color: ${dark};
  border-radius: 15px;
  width: 170px;
  height: 70px;
  padding-top: 10px;
  padding-bottom: 10px;
  font-size: 1.5em;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  box-shadow: 5px 5px 8px rgba(0, 0, 0, 0.5);
  border: none;
  &:hover {
    border: none;
    text-decoration-line: underline;
    text-decoration-thickness: 2px;
    text-underline-offset: 10px;
    color: ${midGreen};
    transform: scale(1.07);
  }
`;

const handleItemClick = (url) => {
  window.location.href = url;
};

const MainLocation = ({ onButtonClick }) => {
  return (
    <MainLocationWrapper>
      <ContentWrapper>
        <MapImage src={mapImage} alt="Map" />
        <InnerContentWrapper>
          <LogoImage src={logoImage} alt="Logo" />
          <HighlightBox>어떤지역으로 가시나요?</HighlightBox>
          <ButtonContainer>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=서울특별시')
              }
            >
              서 울
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=경기도')
              }
            >
              경기•인천
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=강원도')
              }
            >
              강원도
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=제주도')
              }
            >
              제주도
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=충청북도')
              }
            >
              충청북도
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=충청남도')
              }
            >
              충청남도
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=전라북도')
              }
            >
              전라북도
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=전라남도')
              }
            >
              전라남도
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=경상북도')
              }
            >
              경상북도
            </Button>
            <Button
              onClick={() =>
                handleItemClick('recommend/festival?address=경상남도')
              }
            >
              경상남도
            </Button>
          </ButtonContainer>
        </InnerContentWrapper>
      </ContentWrapper>
    </MainLocationWrapper>
  );
};

export default React.memo(MainLocation);
