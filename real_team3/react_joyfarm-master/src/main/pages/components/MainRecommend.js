import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { color } from '../../../styles/color';
import MainRecommendImage from '../../../images/ReviewImage1.jpg';
import ItemsBox4 from '../../../recommend/tour/components/ItemsBox4';
import { ImageListBox3 } from '../../../commons/components/ImageListBox';
import { apiList } from '../../../recommend/tour/apis/apiInfo';

const { darkGreen, white, dark, mid_gray, midGreen, lightGreen } = color;

const MainRecommendWrapper = styled.div`
  padding: 100px 20px;
  background: ${white};
  display: flex;
  justify-content: center;
`;

const ContentWrapper = styled.div`
  max-width: 1440px;
  width: 100%;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
`;

const Title = styled.h2`
  font-size: 3em;
  margin-bottom: 10px;
  position: relative;
`;

const Underline = styled.span`
  display: block;
  width: 400px;
  height: 10px;
  background-color: ${lightGreen};
  margin-top: -5px;
  margin-left: 20px;
  border-radius: 5px;
`;

const MoreLink = styled.a`
  font-size: 1.5em;
  margin-right: 50px;
  color: ${darkGreen};
  font-weight: bold;
  cursor: pointer;
  &:hover {
    text-decoration-line: underline;
    text-decoration-thickness: 2px;
    text-underline-offset: 10px;
  }
`;

const MainRecommend = ({ onButtonClick }) => {
  const [items, setItems] = useState([]);

  useEffect(() => {
    apiList().then((res) => {
      setItems(res.items);
    });
  }, []);

  return (
    <MainRecommendWrapper>
      <ContentWrapper>
        <Header>
          <Title>
            이런 여행지는 어떠세요?
            <Underline />
          </Title>
          <MoreLink onClick={onButtonClick}>더보기</MoreLink>
        </Header>
        <ImageListBox3>
          <ItemsBox4 items={items} />
        </ImageListBox3>
      </ContentWrapper>
    </MainRecommendWrapper>
  );
};

export default React.memo(MainRecommend);
