import styled from 'styled-components';
import { color } from '../../styles/color';
export const OuterBox = styled.div`
  margin-bottom: 150px;
`;

export const PageNavWrap = styled.div`
  // box-sizing: border-box;
  // padding: 48px 20px 20px 48px;
  // border-bottom: solid 1.5px rgb(221, 221, 221);
  // max-width: 1300px;
  // position: relative;
  // margin: 0 auto 20px;

  //이 부분은 정렬을 위해 추가
  box-sizing: border-box;
  padding: 48px 20px 20px;
  border-bottom: solid 1.5px rgb(221, 221, 221);
  max-width: 1300px;
  position: relative;
  margin: 0 auto 20px;
`;

export const PageNav = styled.div`
  line-height: normal;

  padding-left: 20px; //이 부분은 정렬을 위해 추가
`;

export const PageTitle = styled.div`
  margin-top: 20px;
  font-size: 20px;

  padding-left: 20px;
`;

export const ContentBox = styled.div`
  box-sizing: border-box;
  width: 1300px;
  min-height: 700px;
  padding: 0 20px 50px 20px;
  margin: 0 auto;
`;

export const ContentBox2 = styled.div`
  box-sizing: border-box;
  width: 1300px;
  min-height: 700px;
  padding: 0 0 50px 0;
  margin: 0 auto;
`;

export const ContentBox3 = styled.div`
  box-sizing: border-box;
  width: 800px;
  min-height: 700px;
  padding: 10px 0 30px 0;
  margin: 0 auto;
`;
