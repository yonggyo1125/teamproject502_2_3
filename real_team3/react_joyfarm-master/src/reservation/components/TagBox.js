import React from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { color } from '../../../src/styles/color';

const { white, dark, midGreen } = color;

const Wrapper = styled.div`
  display: flex;
  height: 150px;
  align-items: center;
`;

const TagSection = styled.div`
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 10px 20px;
  align-items: center;
`;

const Tag = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  padding: 5px 30px 5px 20px;
`;

const TagTitle = styled.div`
  .tagtitle {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 200px;
    height: 100%;
    font-size: 1.3em;
    font-weight: bold;
    background-color: ${midGreen};
    color: #fff;
    font-size: 28px;
    border: none;
    cursor: pointer;
    text-align: center;
    box-sizing: border-box;
    border-radius: 3px 40px 0 60px;
    transition: background-color 0.3s ease, color 0.3s ease;
  }
`;

const Button = styled.button`
  font-size: 1.3em;
  width: 150px;
  height: 60px;
  background-color: ${white};
  color: ${dark};
  border: 2px solid ${({ borderColor }) => borderColor || 'midGreen'};
  border-radius: 30px;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s ease, color 0.3s ease;
  &:hover {
    color: ${white};
    font-weight: bold;
    transform: scale(1.07);
    border: none;
    background-color: ${({ hoverColor }) => hoverColor || 'transparent'};
  }
`;

const TagBox = () => {
  const { t } = useTranslation();
  return (
    <Wrapper>
      <Tag>
        <TagTitle>
          <div className="tagtitle">{t('인기_테마')}</div>
        </TagTitle>
        <TagSection>
          <Button borderColor="#fbbd20" hoverColor="#fbbd20">
            <span>{t('반갑습니다')}</span>
          </Button>
          <Button borderColor="#87c9f7" hoverColor="#87c9f7">
            <span>{t('저는 1998년')}</span>
          </Button>
          <Button borderColor="#9ce89a" hoverColor="#9ce89a">
            <span>{t('2월')}</span>
          </Button>
          <Button borderColor="#ffbc80" hoverColor="#ffbc80">
            <span>{t('4일')}</span>
          </Button>
          <Button borderColor="#feacb6" hoverColor="#feacb6">
            <span>{t('에 태어난')}</span>
          </Button>
          <Button borderColor="#dbbbff" hoverColor="#dbbbff">
            <span>{t('이진표')}</span>
          </Button>
          <Button borderColor="#dbbbff" hoverColor="#dbbbff">
            <span>{t('입니다.')}</span>
          </Button>
          <Button borderColor="#feacb6" hoverColor="#feacb6">
            <span>{t('캠핑')}</span>
          </Button>
          <Button borderColor="#ffbc80" hoverColor="#ffbc80">
            <span>{t('은 좋아하지')}</span>
          </Button>
          <Button borderColor="#9ce89a" hoverColor="#9ce89a">
            <span>{t('않구요')}</span>
          </Button>
          <Button borderColor="#87c9f7" hoverColor="#87c9f7">
            <span>{t('저는 주로')}</span>
          </Button>
          <Button borderColor="#fbbd20" hoverColor="#fbbd20">
            <span>{t('노래를 듣거나')}</span>
          </Button>
        </TagSection>
      </Tag>
    </Wrapper>
  );
};

export default React.memo(TagBox);
