import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { color } from '../../../styles/color';
import MainReviewImage from '../../../images/QnAImage.png';
import ListItems from '../../../board/components/skins/default/ListItems';
import { getList } from '../../../board/apis/apiBoard';
import moment from 'moment';

const { darkGreen, white, dark, midGreen, lightGreen, mid_gray, line_gray } =
  color;

const MainBoardWrapper = styled.div`
  padding: 50px 20px;
  background: ${white};
  display: flex;
  justify-content: center;
`;

const InnerContentWrapper = styled.div`
  max-width: 1440px;
  width: 100%;
  margin-bottom: 50px;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
`;

const TitleWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const QnATitle = styled.h2`
  font-size: 3em;
  margin-bottom: 10px;
  position: relative;
  text-align: center;
  margin-left: 150px;
`;

const NoticeTitle = styled.h2`
  font-size: 3em;
  margin-bottom: 10px;
  position: relative;
  text-align: center;
  margin-left: 320px;
`;

const Underline = styled.span`
  display: block;
  width: 150px;
  height: 10px;
  background-color: ${lightGreen};
  margin: 0 auto;
  border-radius: 5px;
`;

const MoreLink = styled.a`
  font-size: 1.5em;
  color: ${darkGreen};
  font-weight: bold;
  margin-right: 170px;
  margin-top: 70px;
  cursor: pointer;
  &:hover {
    text-decoration-line: underline;
    text-decoration-thickness: 2px;
    text-underline-offset: 10px;
  }
`;

const ContentWrapper = styled.div`
  display: flex;
  gap: 70px;
  margin: 0 auto;
`;

const LeftSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  background: ${white};
  border-radius: 15px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.3);
  padding-bottom: 30px;
  margin-left: 150px;
`;

const ImageBox = styled.div`
  width: 100%;
  max-width: 450px;
  height: 230px;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  background: ${dark};
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
`;

const Image = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const TextContainer = styled.div`
  width: 100%;
  color: ${dark};
  cursor: pointer;
`;

const DateText = styled.p`
  font-size: 1.5em;
  font-weight: bold;
  color: ${midGreen};
  font-weight: bold;
  margin-bottom: 20px;
`;

const NoticeText = styled.p`
  font-size: 1.2em;
  line-height: 1.5;
  color: ${dark};
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
`;

const RightSection = styled.div`
  width: 700px;
  display: flex;
  flex-direction: column;
  text-align: center;
  background: ${white};
  padding-top: 40px;
  border-radius: 15px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.3);
`;

const NoticeHeader = styled.div`
  font-size: 1.6em;
  font-weight: bold;
  color: ${dark};
  margin-bottom: 25px;
  width: 70%;
  margin-left: 80px;
  text-align: left;
  cursor: pointer;
`;

const NoticeItem = styled.div`
  width: 80%;
  display: flex;
  cursor: pointer;
  align-items: center;
  margin-left: 60px;
  border-bottom: 1px solid ${line_gray};
  padding: 5px 0;
  &:nth-child(5) {
    border-bottom: none;
  }
`;
const NoticeItems = styled.p`
  font-size: 1.4em;
  font-weight: bold;
  flex: 1;
  color: ${mid_gray};
  margin-left: 20px;
  margin-right: 30px;
`;

const NoticeSubject = styled.p`
  font-size: 1.4em;
  flex: 2;
  color: black;
  font-weight: bold;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 70%;
`;

const MainBoard = ({ onButtonClick, bid = 'notice' }) => {
  const [notices, setNotices] = useState([]); // 상태 추가

  useEffect(() => {
    (async () => {
      try {
        const { items } = await getList(bid, { page: 1, limit: 5 });
        console.log('Fetched items: ', items);
        setNotices(items); // 상태 설정
      } catch (err) {
        console.error(err);
      }
    })();
  }, [bid]);

  const handleNoticeClick = (seq) => {
    window.location.href = `/board/view/${seq}`; //클릭시 해당 게시글로 이동
  };

  const qnaClick = () => {
    window.location.href = '/board/list/qna';
  };

  return (
    <MainBoardWrapper>
      <InnerContentWrapper>
        <Header>
          <TitleWrapper>
            <QnATitle>
              QnA
              <Underline />
            </QnATitle>
            <NoticeTitle>
              공지사항
              <Underline />
            </NoticeTitle>
          </TitleWrapper>
          <MoreLink onClick={onButtonClick}>더보기</MoreLink>
        </Header>
        <ContentWrapper>
          <LeftSection>
            <ImageBox>
              <Image src={MainReviewImage} alt="QnA" onClick={qnaClick} />
            </ImageBox>
            <TextContainer onClick={qnaClick}>
              <DateText>문의 사항이 있으시다면 알려주세요!</DateText>
              <NoticeText onClick={qnaClick}>
                당신의 소중한 의견을 받아,
                <br />더 나은 서비스가 되도록 노력하겠습니다.
              </NoticeText>
            </TextContainer>
          </LeftSection>

          <RightSection>
            <NoticeHeader onClick={onButtonClick}>공지사항</NoticeHeader>
            <div className="noticeParent">
              {notices && notices.length > 0 ? (
                notices.map((notice) => (
                  <NoticeItem
                    key={notice.seq}
                    onClick={() => handleNoticeClick(notice.seq)}
                  >
                    <NoticeSubject>{notice.subject}</NoticeSubject>
                    <NoticeItems>{notice.poster}</NoticeItems>
                    <NoticeItems>
                      {moment(notice.createdAt).format('YYYY/MM/DD')}
                    </NoticeItems>
                  </NoticeItem>
                ))
              ) : (
                <p>공지사항이 없습니다.</p>
              )}
            </div>
          </RightSection>
        </ContentWrapper>
      </InnerContentWrapper>
    </MainBoardWrapper>
  );
};

export default React.memo(MainBoard);
