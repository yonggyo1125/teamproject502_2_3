import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import WishButton from '../../commons/components/WishButton';
import { ImageBgBox } from '../../commons/components/ImageBox';
import { useTranslation } from 'react-i18next';
import farmImg from '../../images/farm.jpg';
import fontSize from '../../styles/fontSize';
import { FaMapMarkerAlt } from 'react-icons/fa';
import { color } from '../../styles/color';
import { FcLandscape } from 'react-icons/fc';

const { darkGreen } = color;
const { medium, big, normal } = fontSize;

//농활 체험 목록 조회
const ItemBox = ({ item, className }) => {
  const { t } = useTranslation();
  const { seq, townImage, townName, activityName, doroAddress } = item;
  const url = `/reservation/info/${seq}`;
  return (
    <li className={className}>
      <div className="wishbtn">
        <WishButton seq={seq} type={'ACTIVITY'} />
      </div>
      <Link to={url}>
        {townImage ? (
          <ImageBgBox
            className="townImg"
            url={townImage}
            width="30%"
            height="230px"
            alt={t('마을사진')}
          />
        ) : (
          //이미지 없는 경우 대체
          <ImageBgBox
            className="basicImg"
            url={farmImg}
            width="30%"
            height="230px"
            alt={t('마을사진')}
          />
        )}
        <div className="item-content">
          <div className="townName">{townName}</div>
          <div className="act_content">
            <div className="actNameTitle">
              <FcLandscape className="t_icon" />
              <p className="act_title">{t('체험_프로그램_소개')}</p>
            </div>
            <div className="activityName">{activityName}</div>
            <div className="doroAddress">
              <FaMapMarkerAlt className="icon" />
              <p className="addr">{doroAddress}</p>
            </div>
          </div>
        </div>
      </Link>
    </li>
  );
};

const ItemStyledBox = styled(ItemBox)`
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 5px;
  border: 1px solid #ada493;
  width: 100%;
  position: relative;

  .wishbtn {
    position: absolute;
    top: 20px;
    right: 20px;
    z-index: 10;
  }

  a {
    height: 450px;
    display: flex;
    flex-direction: column;

    .townImg,
    .basicImg {
      width: 100%;
      height: 230px;
      border-radius: 5px 5px 0px 0px;
      position: relative;
    }

    .item-content {
      width: 100%;
      word-break: break-all;
      padding: 15px 10px 5px;
      height: 220px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      font-size: ${normal};

      .townName {
        font-size: ${big};
        font-weight: bold;
        text-align: center;
        line-height: 1;
        margin-bottom: 15px;
        display: flex;
        align-items: center;
        justify-content: center;
      }

      .act_content {
        background: #fffff0;
        border-radius: 40px;
        padding: 20px;
        margin-bottom: 10px;
        height: 160px;
        position: relative;
      }

      .actNameTitle {
        font-size: ${medium};
        font-weight: bold;
        margin-bottom: 10px;
        display: flex;
        align-items: center;

        .act_title {
          margin: 0;
        }

        .t_icon {
          margin-right: 10px;
        }
      }

      .activityName {
        font-size: ${medium};
        line-height: 170%;
        width: 100%;
        margin-bottom: 15px;
        overflow: hidden; //글자 넘치는 부분 감추기
        text-overflow: ellipsis; //숨겨지는 영역 끝에 말줄임표 생성
        white-space: normal; //줄바꿈
        text-align: left; //텍스트 윈쪽 정렬
        word-wrap: break-word; //단어 단위로 줄바꿈
        display: -webkit-box; //영역을 box형태로 지정
        -webkit-line-clamp: 2; //해당 영역 내 텍스트 최대 라인수
        -webkit-box-orient: vertical; //박스 방향 설정(가로)
      }

      .doroAddress {
        font-size: ${medium};
        color: #767676;
        height: 15%;
        display: flex;
        align-items: center;
        position: absolute;
        bottom: 20px;

        .addr {
          margin: 0;
          overflow: hidden; //글자 넘치는 부분 감추기
          text-overflow: ellipsis; //숨겨지는 영역 끝에 말줄임표 생성
          white-space: normal; //줄바꿈
          text-align: left; //텍스트 윈쪽 정렬
          word-wrap: break-word; //단어 단위로 줄바꿈
          display: -webkit-box; //영역을 box형태로 지정
          -webkit-line-clamp: 1; //해당 영역 내 텍스트 최대 라인수
          -webkit-box-orient: vertical; //박스 방향 설정(가로)
        }

        .icon {
          color: ${darkGreen};
          top: 3px;
          margin-right: 10px;
        }
      }
    }
  }
`;

const ItemsBox = ({ items }) => {
  return (
    items.length > 0 &&
    items.map((item) => <ItemStyledBox key={item.seq} item={item} />)
  );
};

export default React.memo(ItemsBox);
