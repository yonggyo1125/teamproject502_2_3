import React from 'react';
import styled from 'styled-components';
import { color } from '../styles/color';
import FooterImage1 from '../images/KF_logo_black.png';
import FooterImage2 from '../images/KR_logo_black.png';
import FooterImage3 from '../images/logo.png';

const { dark_gray, mid_gray } = color;

const FooterBox = styled.footer`
  min-height: 270px;
  background: ${dark_gray};
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const FooterWrap = styled.div`
  width: 1440px;
  display: flex;
`;

const Address = styled.div`
  width: 100%;
  font-size: 16px;
  margin-left: 100px;
  margin-bottom: 30px;
  margin-top: 10px;
  color: ${mid_gray};
  font-weight: bold;
`;

const Information = styled.div`
  width: 100%;
  font-size: 13px;
  margin-left: 100px;
  margin-bottom: 20px;
  line-height: 1.5;
  color: ${mid_gray};
`;

const Information2 = styled.div`
  width: 100%;
  font-size: 13px;
  margin-left: 100px;
  line-height: 1.5;
  color: ${mid_gray};
`;

const FooterContentBox = styled.div`
  margin-bottom: 20px;
`;

const FooterImageBox = styled.div`
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-left: 500px;
  margin-top: 180px;
`;

const Image = styled.img`
  width: 150px;
  height: auto;
  object-fit: contain;
`;

const Footer = () => {
  return (
    <FooterBox>
      <FooterWrap>
        <FooterContentBox>
          <Address>서울 마포구 신촌로 176 중앙빌딩 | 502호 3조</Address>
          <Information>
            현재 농촌 지역은 인구 감소와 고령화, 사회적 단절, 기반 시설 부족
            등으로 많은 어려움을 겪고 있습니다.
            <br />
          </Information>
          <Information2>
            농촌 문제의 해결을 위해 변화하는 여행 트렌드와 접목하여 농촌 체험과
            관련된 다양한 정보를 제공하고, 농촌 체험 및 여행의 수요를 확대하여
            지역 활성화를 도모하고자 본 프로젝트를 진행하게 되었습니다.
            <br />
            <br />
            중앙정보기술인재개발원 3조 이소은 서정현 강태현 양예지
            <br />
            이진표 유준범 최시원 지도강사 이용교 강사님
          </Information2>
        </FooterContentBox>
        <FooterImageBox>
          <Image src={FooterImage1} alt="1" />
          <Image src={FooterImage2} alt="2" />
          <Image src={FooterImage3} alt="3" />
        </FooterImageBox>
      </FooterWrap>
    </FooterBox>
  );
};

export default React.memo(Footer);
