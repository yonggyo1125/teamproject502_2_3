import React, { useState } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { MidButton } from '../../commons/components/Buttons';
import { format } from 'date-fns';
import fontSize from '../../styles/fontSize';

const { large } = fontSize;

const FlatWrapper = styled.div`
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  margin-top: 50px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 20px;

  // background: pink;
  // color: pink;

  .completion_message {
    font-size: ${large};
    color: #4caf50;
    margin-bottom: 20px;
  }
  .btn_group {
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-top: 20px;

    .rsv_but {
      display: flex;
      justify-content: center;
      align-items: center;
      border: 1px solid #e0e0e0;
      border-radius: 6px;
      width: 30%;
    }
    .btn_right {
      display: flex;
      justify-content: center;
      align-items: center;
      border: 1px solid #e0e0e0;
      border-radius: 6px;
      width: 30%;
    }
  }

  h2 {
    color: #4caf50;
    margin-bottom: 20px;
  }

  .info_section {
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #e0e0e0;
    font-weight: bolder;
  }

  .toggle_btn button {
    background: #f0f0f0;
    border: 1px solid #ccc;
    padding: 5px 10px;
    cursor: pointer;
  }
`;

const CancelForm = ({ data }) => {
  const { t } = useTranslation();
  const [isOpen, setIsOpen] = useState(false);
  const formatMobile = (mobile) => {
    if (mobile.length === 11) {
      return mobile.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    }
    return mobile; // 만약 전화번호가 11자리가 아닌 경우, 원래 문자열을 그대로 반환
  };

  const toggleInfo = () => {
    setIsOpen(!isOpen);
  };

  const {
    name,
    email,
    mobile,
    rdate,
    ampm,
    townName,
    persons,
    activityName,
    ownerName,
    doroAddress,
    ownerTel,
  } = data;

  const formatDate = format(Date(rdate), 'yyyy-MM-dd');

  return (
    <FlatWrapper>
      <div className="completion_message">
        {t('예약이 성공적으로 취소되었습니다!')}
      </div>
      <h2>{t('취소 완료')}</h2>
      <div className="info_section">
        <p>
          {t('예약자명')} : {name}
        </p>
        <p>
          {t('이메일')} : {email}
        </p>
        <p>
          {t('전화번호')} : {formatMobile(mobile)}
        </p>
      </div>

      <div className="info_section">
        <p>
          {t('예약일')} : {formatDate}
        </p>
        <p>
          {t('예약시간')} : {ampm === 'AM' ? t('오전') : t('오후')}
        </p>
        <p>
          {t('인원수')} : {persons} {t('명')}
        </p>
        <p>
          {t('체험 마을')} : {townName}
        </p>
      </div>

      <div className="toggle_btn">
        <button onClick={toggleInfo}>
          {isOpen ? t('체험_마을_세부정보_닫기') : t('체험_마을_세부정보_열기')}
        </button>
        {isOpen && (
          <div className="info_section">
            <dl>
              <dt>{t('체험_활동_소개')}</dt>
              <dd>{activityName}</dd>
            </dl>
            <dl>
              <dt>{t('체험_마을_대표자명')}</dt>
              <dd>{ownerName}</dd>
            </dl>
            <dl>
              <dt>{t('체험_마을_주소')}</dt>
              <dd>{doroAddress}</dd>
            </dl>
            <dl>
              <dt>{t('체험_마을_전화번호')}</dt>
              <dd>{ownerTel}</dd>
            </dl>
          </div>
        )}
      </div>
      <div className="btn_group">
        <MidButton
          className="rsv_but"
          color="primary"
          as={Link}
          to="/myreservation/list"
        >
          {t('나의 예약 현황')}
        </MidButton>
        <MidButton
          className="rsv_but"
          color="midGreen"
          as={Link}
          to="/reservation/list"
        >
          {t('농촌체험 예약')}
        </MidButton>
      </div>
    </FlatWrapper>
  );
};

export default React.memo(CancelForm);
