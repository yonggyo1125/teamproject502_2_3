import React, { useState, useEffect, useCallback } from 'react';
import styled from 'styled-components';
import { color } from '../../../styles/color';
import bannerImage1 from '../../../images/MainBanner1.jpeg';
import bannerImage2 from '../../../images/MainBanner2.jpeg';
import bannerImage3 from '../../../images/MainBanner3.jpeg';

const { darkGreen, white, light, mid_gray } = color;

const MainBannerWrapper = styled.div`
  position: relative;
  overflow: hidden;
  background-color: ${darkGreen};
  height: 650px;
`;

const BannerImage = styled.img`
  width: 100%;
  height: 650px;
  object-fit: cover;
  position: absolute;
  top: 0;
  left: 0;
  transition: opacity 1s ease-in-out;
  opacity: ${props => (props.isActive ? 1 : 0)};
`;

const InfoBar = styled.nav`
  position: relative;
  width: 100%;
  background-color: ${darkGreen};
  color: ${white};
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 50px 40px;
  box-sizing: border-box;
  font-size: 1.2em;
`;

const InfoBox = styled.div`
  text-align: right;
`;

const InfoText = styled.div`
  margin-right:270px;
  color: ${white};
  font-size: ${props => (props.isHeader ? '1.5em' : '1em')};
  margin-top: ${props => (props.isHeader ? '0' : '5px')};
  margin-bottom: ${props => (props.isHeader ? '10px' : '0')};
`;

const Button = styled.button`
  background-color: ${darkGreen};
  color: ${white};
  border: 1px solid ${white};
  border-radius: 20px;
  margin-left:300px;
  padding: 10px 20px;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  &:hover {
    color: ${darkGreen};
    background-color: ${white};
    border: none;
  }
`;

const DotsWrapper = styled.div`
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
`;

const Dot = styled.div`
  width: 10px;
  height: 10px;
  background-color: ${props => (props.isActive ? white : mid_gray)};
  border-radius: 50%;
  cursor: pointer;
`;

const ArrowButton = styled.button`
  position: absolute;
  top: 50%;
  ${props => (props.isLeft ? 'left: 20px;' : 'right: 20px;')}
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5); 
  color: ${white};
  border: none;
  padding: 15px;
  cursor: pointer;
  border-radius: 50%;
  width: 55px; 
  z-index: 2;
  font-size: 2em;
  line-height: 1;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: rgba(0, 0, 0, 0.8); 
  }
`;

const MainBanner = ({ onButtonClick }) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const banners = [bannerImage1, bannerImage2, bannerImage3]; 

  const nextBanner = useCallback(() => {
    setActiveIndex(prevIndex => (prevIndex + 1) % banners.length);
  }, [banners.length]);

  const prevBanner = useCallback(() => {
    setActiveIndex(prevIndex =>
      prevIndex === 0 ? banners.length - 1 : prevIndex - 1
    );
  }, [banners.length]);

  useEffect(() => {
    const interval = setInterval(nextBanner, 7000);
    return () => clearInterval(interval);
  }, [nextBanner]);

  return (
    <>
      <MainBannerWrapper>
        {banners.map((banner, index) => (
          <BannerImage
            key={index}
            src={banner}
            alt={`Main Banner ${index + 1}`}
            isActive={index === activeIndex}
          />
        ))}
        <ArrowButton isLeft onClick={prevBanner}>❮</ArrowButton>
        <ArrowButton onClick={nextBanner}>❯</ArrowButton>
        <DotsWrapper>
          {banners.map((_, index) => (
            <Dot
              key={index}
              isActive={index === activeIndex}
              onClick={() => setActiveIndex(index)}
            />
          ))}
        </DotsWrapper>
      </MainBannerWrapper>
      <InfoBar>
        <Button onClick={onButtonClick}>지금 바로 예약하기</Button>
        <InfoBox>
          <InfoText isHeader>조이팜과 함께 떠나는 즐거운 농촌체험</InfoText>
          <InfoText>
            전국에 있는 체험가능한 농촌들을 방문하여, 좋은 경험과 추억을 쌓아보세요!
          </InfoText>
        </InfoBox>
      </InfoBar>
    </>
  );
};

export default React.memo(MainBanner);