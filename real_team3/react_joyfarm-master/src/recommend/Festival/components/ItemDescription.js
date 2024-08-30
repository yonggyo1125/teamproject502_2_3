import React from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { color } from '../../../styles/color';
import moment from 'moment';
import {
  FcCalendar,
  FcAbout,
  FcBookmark,
  FcLibrary,
  FcContacts,
  FcHome,
  FcElectricity,
  FcPlus,
} from 'react-icons/fc';
import WishButton from '../../../commons/components/WishButton';

const { mid_gray } = color;

const Wrapper = styled.div`
  display: flex;
  flex: 1;
  padding-left: 20px;
  margin: auto;

  dl {
    padding-top: 10px;
    margin: 16px 0;
    display: flex;
    align-items: center;
    flex-wrap: wrap;

    dt {
      padding-left: 15px;
      font-size: 1.5rem;
      font-weight: 650;
      line-height: 29px;
    }

    dd {
      padding-left: 15px;
      font-size: 1.3rem;
      line-height: 1.8rem;
    }
  }

  dl dt svg {
    margin-right: 8px;
  }

  dl + dl {
    border-top: 1px solid #f8f9fa;
  }

  .button {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 15px;
    color: ${mid_gray};
    position: absolute;
    right: 20px;
    top: -40px;
  }
`;

const ItemDescription = ({ item }) => {
  const { t } = useTranslation();
  const {
    startDate,
    endDate,
    title,
    address,
    location,
    hostMain,
    hostSub,
    tel,
    pageLink,
    content,
    seq,
  } = item;
  const startformattedDate = moment(startDate).format('YYYY년 MM월 DD일');
  const endformattedDate = moment(endDate).format('YYYY년 MM월 DD일');
  return (
    <Wrapper>
      <div className="button">
        <WishButton seq={seq} type={'FESTIVAL'}></WishButton>
        {t('찜하기')}
      </div>
      <div className="description">
        {startDate && endDate && (
          <dl>
            <dt>
              <FcCalendar />
              {t('행사기간 ')}
            </dt>
            <dd>
              {startformattedDate}~{endformattedDate}
            </dd>
          </dl>
        )}
        <dl>
          <dt>
            <FcAbout />
            {t('행사명')}
          </dt>
          <dd>{title}</dd>
        </dl>
        {address && (
          <dl>
            <dt>
              <FcElectricity />
              {t('주소')}
            </dt>
            <dd>{address}</dd>
          </dl>
        )}
        {location && (
          <dl>
            <dt>
              <FcBookmark />
              {t('행사장소')}
            </dt>
            <dd>{location}</dd>
          </dl>
        )}
        {hostMain && (
          <dl>
            <dt>
              <FcLibrary />
              {t('주최기관')}
            </dt>
            <dd>{hostMain}</dd>
          </dl>
        )}
        <dl>
          <dt>
            <FcLibrary />
            {t('주관기관')}
          </dt>
          <dd>{hostSub}</dd>
        </dl>
        <dl>
          <dt>
            <FcContacts />
            {t('문의처')}
          </dt>
          <dd>{tel}</dd>
        </dl>
        <dl>
          <dt>
            <FcHome />
            {t('홈페이지_주소')}
          </dt>
          <dd>{pageLink}</dd>
        </dl>
        <dl>
          <dt>
            <FcPlus />
            {t('행사내용')}
          </dt>
          <dd>{content}</dd>
        </dl>
      </div>
    </Wrapper>
  );
};

export default React.memo(ItemDescription);
