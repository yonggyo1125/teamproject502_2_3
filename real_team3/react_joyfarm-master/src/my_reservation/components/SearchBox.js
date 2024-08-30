import React, { useState } from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { MidButton } from '../../commons/components/Buttons';
import { ImSearch } from 'react-icons/im';
import { color } from '../../styles/color';
import DatePicker from 'react-datepicker';
import fontSize from '../../styles/fontSize';
import { format } from 'date-fns';
import { registerLocale, setDefaultLocale } from 'react-datepicker';
import { ko } from 'date-fns/locale/ko';
registerLocale('ko', ko);

const { white, midGreen, gray, lightGreen, dark, darkGreen } = color;
const { medium, normal, normedium } = fontSize;

const FormBox = styled.form`
  display: flex;
  border-bottom: 1px solid #ccc;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 20px;
  padding: 5px 20px 20px;

  .search_box {
    height: 45px;
    display: flex;

    .pick_sdate,
    .pick_edate {
      border: 1px solid ${gray};
      display: flex;
      align-items: center;
      width: 120px;
      height: 45px;
      text-align: center;
      margin-right: 3px;
      box-sizing: border-box;
      font-size: ${normal};
      padding: 8px 20px;
      border-radius: 4px;
    }

    input {
      width: 430px;
      height: 45px;
      padding: 0 10px;
      border: 1px solid ${gray};
    }

    select {
      width: 130px;
      margin-right: 3px;
      height: 45px;
      padding: 0 10px;
      border: 1px solid ${gray};
    }

    .rsv_searchBar {
      justify-content: center;
      align-content: center;
      display: flex;
    }

    .rsv_btn {
      width: 180px;
    }

    .react-datepicker {
      border-radius: 10px;
      border: 1px solid ${gray};
      width: 250px;
      height: 240px;
    }

    .react-datepicker__month-container {
      width: 100%;
      height: 100%;
    }

    .react-datepicker__header {
      background-color: #e2f7dd;
      width: 100%;
      padding: 10px;
      border-radius: 10px 10px 0 0;
    }

    //요일
    .react-datepicker__day-names {
      display: flex;
      justify-content: space-around;
      align-items: center;
      margin-top: 10px;
      width: 100%;
      font-weight: bold;
    }

    .react-datepicker__week {
      justify-content: space-between;
      display: flex;
      padding: 5px 15px;
    }

    //날짜
    .react-datepicker__month {
      display: flex;
      flex-direction: column;
      margin: 5px 0 0 0;
    }

    .react-datepicker__day {
      color: ${dark};
      margin: 0;
    }

    .react-datepicker__current-month {
      font-size: ${normal};
      margin-top: 3px;
    }

    .react-datepicker__day--today {
      // 오늘 날짜 하이라이트 커스텀
      color: ${darkGreen};
      border: 1px solid ${dark};
      border-radius: 50%;
    }
    .react-datepicker__day--selected {
      background: ${midGreen};
      color: ${white};
      border-radius: 50%;
    }
    .react-datepicker__day:hover {
      background-color: ${lightGreen}; /* 마우스 오버 시 배경색 변경 */
      color: ${dark}; /* 마우스 오버 시 텍스트 색상 변경 */
      border-radius: 50%; /* 원형 테두리 적용 */
    }

    .react-datepicker__day--outside-month {
      color: ${gray};
    }

    .react-datepicker__day--keyboard-selected {
      border-radius: 50%;
      background-color: ${midGreen};
    }
  }
`;

const Button = styled.button`
  font-size: 1.3em;
  color: ${white};
  width: 80px;
  height: 45px;
  background: #384863;
  border: none;
  cursor: pointer;
  padding-top: 5px;
  font-size: 2rem;
`;

const SearchBox = ({ form, onChange, onSubmit }) => {
  const { t } = useTranslation();

  return (
    <FormBox onSubmit={onSubmit} autoComplete="off">
      <div className="search_box">
        <div className="sdate">
          <DatePicker
            className="pick_sdate"
            locale="ko"
            selected={form?.sDate ? new Date(form.sDate) : null}
            onChange={(date) =>
              onChange({
                target: { name: 'sDate', value: format(date, 'yyyy-MM-dd') },
              })
            }
            dateFormat="yyyy-MM-dd" // 날짜 포맷 설정
            placeholderText={t('예약시작일')}
          />
        </div>
        <div className="edate">
          <DatePicker
            className="pick_edate"
            locale="ko"
            selected={form?.eDate ? new Date(form.eDate) : null}
            onChange={(date) =>
              onChange({
                target: { name: 'eDate', value: format(date, 'yyyy-MM-dd') },
              })
            }
            dateFormat="yyyy-MM-dd" // 날짜 포맷 설정
            placeholderText={t('예약종료일')}
          />
        </div>
        <select name="sopt" onChange={onChange} defaultValue={form.sopt}>
          <option value="ALL">{t('통합검색')}</option>
          <option value="ACTIVITY">{t('체험프로그램명')}</option>
          <option value="ADDRESS">{t('체험_마을_주소')}</option>
        </select>
        <input
          type="text"
          name="skey"
          value={form.skey}
          onChange={onChange}
          placeholder="검색어를 입력하세요"
        />
      </div>
      <div className="rsv_searchBar">
        <Button>
          <ImSearch />
        </Button>
      </div>
    </FormBox>
  );
};

export default React.memo(SearchBox);
