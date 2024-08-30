import styled from 'styled-components';
import fontSize from '../../styles/fontSize';

const { extraBig, big, medium } = fontSize;

export const MainTitle = styled.h1`
  font-size: ${extraBig};
  text-align: center;
  border-bottom: 2px solid #000;
  padding: 0 15px 15px;
  margin: 0 0 20px;
  line-height: 1;
`;

export const MainTitle2 = styled.h1`
  font-size: ${big};
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  border-bottom: 2px solid #000;
  padding: 0 15px 15px;
  margin: 30px 0 20px;
  line-height: 1;

  svg{
    color: green;
  }
`;

export const MainTitle3 = styled.h1`
  font-size: ${extraBig};
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  border-bottom: 2px solid #000;
  margin-bottom: -10px;
  line-height: 1;

  svg{
    color: green;
  }
`;

export const SubTitle = styled.h1`
  font-size: ${big};
  border-bottom: 2px solid #000;
  padding: 0 15px 15px;
  margin: 0 0 20px;
  line-height: 1;
  text-align: center;
`;

export const MidTitle = styled.h1`
  font-size: ${medium};
  border-bottom: 2px solid #000;
  padding: 0 15px 15px;
  margin: 0 0 20px;
  line-height: 1;
  text-align: center;
`;


