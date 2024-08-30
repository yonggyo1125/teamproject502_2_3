import React from 'react';
import styled, { keyframes } from 'styled-components';
import { color } from '../../../styles/color';
import ReviewImage1 from '../../../images/ReviewImage1.jpg';
import ReviewImage2 from '../../../images/ReviewImage2.jpg';
import ReviewImage3 from '../../../images/ReviewImage3.jpg';
import ReviewImage4 from '../../../images/ReviewImage4.jpg';
import ReviewImage5 from '../../../images/ReviewImage5.jpg';
import ReviewImage6 from '../../../images/ReviewImage6.jpg';
import ReviewImage7 from '../../../images/ReviewImage7.jpg';
import ReviewImage8 from '../../../images/ReviewImage8.jpg';
import ReviewImage9 from '../../../images/ReviewImage9.jpg';
import ReviewImage10 from '../../../images/ReviewImage10.jpg';
import logoImage from '../../../images/logo.png';

const { darkGreen, white, dark, midGreen, whiteGreen, lightGreen,mid_gray } = color;

const LogoImage = styled.img`
  width: 900px;
  height: auto;
  opacity: 0.3;
  position: absolute;
  top: 655px; 
  left: 1200px;
  transform: translateX(-25%);
`;

const MainReviewWrapper = styled.div`
  background: ${whiteGreen};
  display: flex;
  justify-content: center; 
  position: relative;
  height: 700px;
  margin-bottom: 150px;
`;

const WhiteSection = styled.div`
  width: 100%;
  height: 300px;
  background: ${white};
`;

const InnerContentWrapper = styled.div`
  padding: 0 20px;
  max-width: 1440px; 
  width: 100%; 
  display: flex;
  justify-content: center;
`;

const ContentWrapper = styled.div`
  margin-left: 100px;
  display: flex;
  flex-direction: column;
  flex: 1;
  margin-top: 230px;
  padding: 0 40px;
`;

const scrollUp = keyframes`
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-100%);
  }
`;

const scrollDown = keyframes`
  0% {
    transform: translateY(-100%);
  }
  100% {
    transform: translateY(0%);
  }
`;

const LeftSection = styled.div`
  display: flex;
  flex-direction: column;
  overflow: hidden;
  height: 1000px; 
  width: 270px;
  margin-right: 40px;
`;

const RightSection = styled.div`
  display: flex;
  flex-direction: column;
  overflow: hidden;
  height: 1000px; 
  width: 270px;
  margin-right: 100px;
`;

const ImageBoxContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const ImageBox = styled.div`
  width: 270px;
  height: 190px;
  background: ${dark};
  margin-bottom: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 15px; 
`;

const ImageBox1 = styled(ImageBoxContainer)`
  animation: ${scrollUp} 25s linear infinite;
`;

const ImageBox2 = styled(ImageBoxContainer)`
  animation: ${scrollUp} 25s linear infinite;
    animation-delay: 0.3s;
`;

const ImageBox3 = styled(ImageBoxContainer)`
  animation: ${scrollDown} 25s linear infinite;
`;

const ImageBox4 = styled(ImageBoxContainer)`
  animation: ${scrollDown} 25s linear infinite;
  animation-delay: -1.7s;
`;

const ReviewImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 15px; 
`;

const Title = styled.h2`
  font-size: 3em;
  margin-bottom: 10px;
  position: relative;
`;

const Underline = styled.span`
  display: block;
  width: 500px;
  height: 10px;
  background-color: ${lightGreen};
  margin-top: -5px; 
  margin-left: 20px;
  border-radius: 5px;
`;

const Subtitle = styled.p`
  font-size: 1.5em;
  font-weight:bold;
  color: ${mid_gray};
  margin-bottom: 70px;
  line-height: 1.7;

`;

const Button = styled.button`
  width: 300px;
  padding: 20px 20px;
  font-size: 1.3em;
  font-weight: bold;
  color: ${midGreen};
  background: ${white};
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  &:hover {
    background: ${darkGreen};
    color: ${white};
  }
`;

const MainReview = ({ onButtonClick }) => {
  return (
    <>
      <MainReviewWrapper>
        <InnerContentWrapper>
          <LeftSection>
            <ImageBox1>
              <ImageBox>
                <ReviewImage src={ReviewImage1} alt="Review 1" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage2} alt="Review 2" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage3} alt="Review 3" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage4} alt="Review 4" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage5} alt="Review 5" />
              </ImageBox>
            </ImageBox1>
            <ImageBox2>
              <ImageBox>
                <ReviewImage src={ReviewImage1} alt="Review 1" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage2} alt="Review 2" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage3} alt="Review 3" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage4} alt="Review 4" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage5} alt="Review 5" />
              </ImageBox>
            </ImageBox2>
          </LeftSection>
          <RightSection>
            <ImageBox3>
              <ImageBox>
                <ReviewImage src={ReviewImage6} alt="Review 1" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage7} alt="Review 2" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage8} alt="Review 3" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage9} alt="Review 4" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage10} alt="Review 5" />
              </ImageBox>
            </ImageBox3>
            <ImageBox4>
              <ImageBox>
                <ReviewImage src={ReviewImage6} alt="Review 1" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage7} alt="Review 2" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage8} alt="Review 3" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage9} alt="Review 4" />
              </ImageBox>
              <ImageBox>
                <ReviewImage src={ReviewImage10} alt="Review 5" />
              </ImageBox>
            </ImageBox4>
          </RightSection>
          <ContentWrapper>
            <Title>
              특별했던 순간을 공유하세요!
              <Underline />
            </Title>
            <Subtitle>
              짜릿하고 즐거웠던 체험의 순간들을<br />
              사람들과 공유하며 행복을 나눠보세요!
            </Subtitle>
            <Button onClick={onButtonClick}>체험후기 바로가기</Button>
          </ContentWrapper>
        </InnerContentWrapper>
        <LogoImage src={logoImage} alt="Logo" />
      </MainReviewWrapper>
      <WhiteSection />
    </>
  );
};

export default React.memo(MainReview);