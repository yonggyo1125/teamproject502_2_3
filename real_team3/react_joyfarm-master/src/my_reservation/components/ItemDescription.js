import React from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { MidButton } from '../../commons/components/Buttons';
import fontSize from '../../styles/fontSize';
import { BsPersonLinesFill } from 'react-icons/bs';
import { IoTicketOutline } from 'react-icons/io5';
import { color } from '../../styles/color';
import { format } from 'date-fns';

const { normedium, big } = fontSize;
const { primary, danger } = color;

const Wrapper = styled.div`
  width: 40%;
  height: 550px;
  word-break: break-all;

  .dscp_box {
    display: flex;
    flex-direction: column;
    position: relative;
    height: 500px;

    .cancel_button {
      position: absolute;
    }

    .person_info {
      height: 50%;
    }

    .rsv_info {
      height: 50%;
    }

    .title {
      font-size: ${big};
      font-weight: bold;
      display: flex;
      align-items: center;
      margin-bottom: 10px;

      .icon {
        margin-right: 10px;
        color: ${primary};
      }

      p {
        font-weight: bold;
        margin: 5px 0;
      }
    }
  }
  .cancelment {
    text-align: center;
    font-size: ${big};
    color: ${danger};
    height: 40px;
    line-height: 40px;
    border: solid 1px 
  }

  dl {
    display: flex;
    padding: 10px 15px;
    font-size: ${normedium};
    line-height: 170%;

    dt {
      width: 140px;
      font-weight: bold;
    }

    dd {
      width: calc(100% - 140px);
    }
  }

  dl + dl {
    border-top: 1px solid #a6a6a6;
  }
`;

const ItemDescription = ({ item, onClick }) => {
  const { t } = useTranslation();
  const {
    name,
    email,
    mobile,
    rdate,
    ampm,
    townName,
    persons,
    status,
    deletedAt,
  } = item;
  const formatDate = format(Date(rdate), 'yyyy-MM-dd');
  const formatMobile = (mobile) => {
    if (mobile.length === 11) {
      return mobile.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    }
    return mobile; // 만약 전화번호가 11자리가 아닌 경우, 원래 문자열을 그대로 반환
  };

  return (
    <Wrapper>
      <div className="dscp_box">
        <div className="person_info">
          <div className="title">
            <BsPersonLinesFill className="icon" />
            <p>{t('예약자_정보')}</p>
          </div>
          <dl>
            <dt>{t('예약자명')}</dt>
            <dd>{name}</dd>
          </dl>
          <dl>
            <dt>{t('이메일')}</dt>
            <dd>{email}</dd>
          </dl>
          <dl>
            <dt>{t('예약자_전화번호')}</dt>
            <dd>{formatMobile(mobile)}</dd>
          </dl>
          {deletedAt ? (
            <dl>
              <dt>{t('취소일')}</dt>
              <dd>{deletedAt}</dd>
            </dl>
          ) : null}
        </div>

        <div className="rsv_info">
          <div className="title">
            <IoTicketOutline className="icon" />
            <p>{t('예약한_체험_마을_정보')}</p>
          </div>
          <dl>
            <dt>{t('예약일')}</dt>
            <dd>{formatDate}</dd>
          </dl>
          <dl>
            <dt>{t('예약시간')}</dt>
            <dd>{ampm === 'AM' ? '오전' : '오후'}</dd>
          </dl>
          <dl>
            <dt>{t('예약인원')}</dt>
            <dd>
              {persons}
              {t('명')}
            </dd>
          </dl>
          <dl>
            <dt>{t('체험_마을명')}</dt>
            <dd>{townName}</dd>
          </dl>
        </div>
      </div>

      {status !== 'CANCEL' ? (
        <MidButton
          className="cancel_button"
          onClick={() => onClick(item.seq)}
          color="midGreen"
        >
          {t('예약_취소')}
        </MidButton>
      ) : (
        <div className="cancelment">{t('취소된 예약입니다.')}</div>
      )}
    </Wrapper>
  );
};

export default React.memo(ItemDescription);
