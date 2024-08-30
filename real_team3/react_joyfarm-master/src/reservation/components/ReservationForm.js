import React, { useCallback } from 'react';
import styled from 'styled-components';
import Calendar from 'react-calendar';
import { useTranslation } from 'react-i18next';
import { IoIosRadioButtonOff, IoIosRadioButtonOn } from 'react-icons/io';
import { BigButton } from '../../commons/components/Buttons';
import InputBox from '../../commons/components/InputBox';
import MessageBox from '../../commons/components/MessageBox';
import { color } from '../../styles/color';
import fontSize from '../../styles/fontSize';
import moment from 'moment/moment';
import Select from 'react-select';
import { produce } from 'immer';
import { FcConferenceCall, FcAlarmClock, FcCalendar } from 'react-icons/fc';
import { BsFillPersonLinesFill } from 'react-icons/bs';

const { gray, primary, lightGreen, darkGreen, white, midGreen, dark } = color;
const { normal, medium, normedium, big, extraBig } = fontSize;

const FormBox = styled.form`
  .infoBox {
    display: flex;
    height: 780px;
  }

  .box {
    flex-grow: 1;
    width: 0;
  }

  .box + .box {
    margin-left: 60px;
  }

  dl {
    padding: 10px 15px;
    font-size: ${medium};
    line-height: 170%;

    dt {
      font-weight: bold;
      margin-bottom: 10px;
      font-size: ${big};
    }

    dd {
      width: calc(100% - 140px);
      font-size: ${normal};
    }
  }

  .react-calendar {
    width: 95%;
    height: 500px;
    padding: 15px;
    border-radius: 20px;
    align-content: center;
    display: flex;
    flex-direction: column;
  }

  /* 네비게이션 가운데 정렬 */
  .react-calendar__navigation {
    justify-content: center;
    align-content: center;
  }

  // 연도 옮기는 버튼 없애기
  .react-calendar__navigation__next2-button,
  .react-calendar__navigation__prev2-button {
    display: none;
  }

  /* 네비게이션 비활성화 됐을때 스타일 */
  .react-calendar__navigation button:disabled {
    background-color: ${white};
    color: ${gray};
  }

  /* 년/월 상단 네비게이션 칸 크기 줄이기 */
  .react-calendar__navigation__label {
    flex-grow: 0 !important;
  }

  //hover 했을 때, 선택한 날짜 색상 변경
  .react-calendar__navigation__label:hover,
  .react-calendar__navigation button:enabled:hover {
    background: ${lightGreen};
    border-radius: 40px;
  }

  .react-calendar__viewContainer {
    height: 100%;
    display: flex;
    flex-direction: column;
  }

  /* 요일 밑줄 제거 */
  .react-calendar__month-view__weekdays abbr {
    text-decoration: none;
    font-weight: bold;
  }

  .react-calendar__navigation button:focus {
    background-color: ${white};
  }

  .react-calendar__tile:disabled {
    background-color: ${gray};
  }

  .react-calendar__navigation__label > span {
    // 달력 상단 년/월 글씨 커스텀
    font-size: ${normedium};
    font-weight: bold;
    line-height: 140%;
  }

  /* 주말에만 빨간 폰트 */
  .react-calendar__month-view__weekdays__weekday--weekend {
    color: #red;
  }

  .react-calendar__month-view__weekdays__weekday {
    padding: 15px;
    font-size: ${medium};
    font-weight: bold;
    border-bottom: solid 1px ${dark};
    margin-bottom: 5px;
  }

  //hover 했을 때, 선택한 날짜 색상 변경
  .react-calendar__tile:enabled:hover {
    background: ${lightGreen};
    border-radius: 40px;
  }
  .react-calendar__tile:enabled:focus,
  .react-calendar__tile--active {
    background: ${darkGreen};
    color: ${white};
    border-radius: 40px;
  }

  .react-calendar__tile--now {
    // 오늘 날짜 하이라이트 커스텀
    background: ${white};
    color: ${midGreen};
  }

  /* 네비게이션 현재 선택한 월 스타일 적용 */
  .react-calendar__tile--hasActive {
    abbr {
      color: ${primary};
    }
  }

  /* 네비게이션 월, 연도 스타일 적용 */
  .react-calendar__year-view__months__month,
  .react-calendar__decade-view__years__year,
  .react-calendar__century-view__decades__decade {
    flex: 0 0 calc(33.3333% - 10px) !important;
    margin-inline-start: 5px !important;
    margin-inline-end: 5px !important;
    margin-block-end: 10px;
    font-weight: bold;
    border-radius: 10px;
    background-color: ${lightGreen};
  }

  /* 일 날짜 간격 */
  .react-calendar__tile {
    position: relative;
    text-align: center;
    height: 60px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: ${normedium};
  }

  .select {
    width: 230px;

    height: 40px;
    padding: 5px 10px;
    border-radius: 4px;
    outline: 0 none;
    position: relative;
    font-size: ${medium};
    line-height: 170%;
    margin-left: 10px;

    //Option 태그에 대한 스타일은 직접적으로 적용할 수 없다.(os 영역)
    .options {
      font-size: ${medium};
      margin-bottom: 5px;
      background: ${white};
    }
  }

  h2 {
    font-size: ${extraBig};
  }

  li {
    display: flex;
    align-items: center;
    font-size: ${big};
    margin: 0 0 10px 10px;

    svg {
      margin-right: 5px;
    }
  }

  .btn_box {
    display: flex;
    justify-content: center;
    margin-top: 20px;

    .rsv_btn {
      width: 350px;
      height: 50px;
    }
  }

  .select_date {
    padding-left: 50px;
    height: 100%;
  }

  input {
    font-size: ${medium};
  }

  .select {
    height: 50px;
    width: 300px;
    padding: 0;
    font-size: ${medium};

    .css-13cymwt-control {
      height: 50px;
    }

    .css-1nmdiq5-menu {
    }
  }

  .people {
    margin-bottom: 40px;
  }

  .title {
    display: flex;
    align-items: center;

    svg {
      font-size: ${extraBig};
      margin-right: 10px;
      color: ${primary};
    }
  }
`;

const options = [...new Array(30).keys()].map((i) => ({
  value: i + 1,
  label: `${i + 1}명`,
}));

const ReservationForm = ({
  data,
  form,
  onSubmit,
  onDateChange,
  onTimeChange,
  onChange,
  errors,
  selectChange,
}) => {
  const { t } = useTranslation();
  const { minDate, maxDate, times } = data;

  return (
    <FormBox onSubmit={onSubmit} autoComplete="off">
      <div className="infoBox">
        <div className="select_date box">
          <div className="title">
            <FcCalendar />
            <h2>{t('예약일_선택')}</h2>
          </div>
          <h3>{t('예약은_당일로부터_한달_이내만_가능합니다')}</h3>
          <Calendar
            minDate={minDate}
            maxDate={maxDate}
            onChange={onDateChange}
            formatDay={(locale, date) => moment(date).format('DD')}
            calendarType="gregory" //일요일부터 시작
          />
          {errors?.rDate && (
            <MessageBox color="danger" messages={errors.rDate} />
          )}
        </div>
        <div className="select-time box">
          <div className="userInfo">
            <div className="title">
              <BsFillPersonLinesFill />
              <h2>{t('예약자_정보_입력')}</h2>
            </div>
            <dl>
              <dt>{t('예약자명')}</dt>
              <dd>
                <InputBox
                  type="text"
                  name="name"
                  value={form?.name}
                  onChange={onChange}
                />
                {errors?.name && (
                  <MessageBox color="danger" messages={errors.name} />
                )}
              </dd>
            </dl>
            <dl>
              <dt>{t('이메일')}</dt>
              <dd>
                <InputBox
                  type="text"
                  name="email"
                  value={form?.email}
                  onChange={onChange}
                />
                {errors?.email && (
                  <MessageBox color="danger" messages={errors.email} />
                )}
              </dd>
            </dl>
            <dl>
              <dt>{t('전화번호')}</dt>
              <dd>
                <InputBox
                  type="text"
                  name="mobile"
                  value={form?.mobile}
                  onChange={onChange}
                />
                {errors?.mobile && (
                  <MessageBox color="danger" messages={errors.mobile} />
                )}
              </dd>
            </dl>
          </div>
          <div className="people">
            <div className="title">
              <FcConferenceCall />
              <h2>{t('인원수_선택')}</h2>
            </div>
            <Select
              value={options.find((option) => option.value === form?.persons)}
              onChange={selectChange}
              className="select"
              options={options}
            />
            {/* // 드롭 다운 */}
            {errors?.persons && (
              <MessageBox color="danger" messages={errors.persons} />
            )}
          </div>
          <div className="time_box">
            {times && (
              <div>
                <div className="title">
                  <FcAlarmClock />
                  <h2>{t('예약시간_선택')}</h2>
                </div>
                <ul>
                  {times[0] && (
                    <li onClick={() => onTimeChange('AM')}>
                      {form.ampm === 'AM' ? (
                        <IoIosRadioButtonOn />
                      ) : (
                        <IoIosRadioButtonOff />
                      )}
                      {t('오전')}
                    </li>
                  )}
                  {times[1] && (
                    <li onClick={() => onTimeChange('PM')}>
                      {form.ampm === 'PM' ? (
                        <IoIosRadioButtonOn />
                      ) : (
                        <IoIosRadioButtonOff />
                      )}
                      {t('오후')}
                    </li>
                  )}
                </ul>
                {errors?.ampm && (
                  <MessageBox color="danger" messages={errors.ampm} />
                )}
              </div>
            )}
          </div>
        </div>
      </div>
      <div className="btn_box">
        <BigButton type="submit" color="midGreen" className="rsv_btn">
          {t('예약하기')}
        </BigButton>
        {errors?.global && (
          <MessageBox color="danger" messages={errors.global} />
        )}
      </div>
    </FormBox>
  );
};

export default React.memo(ReservationForm);
