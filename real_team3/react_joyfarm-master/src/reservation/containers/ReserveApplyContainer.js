import React, { useEffect, useState, useCallback, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { format } from 'date-fns';
import { produce } from 'immer';
import _useConfirm from '../../commons/hooks/useConfirm';
import { apiGet } from '../apis/apiInfo';
import ReservationForm from '../components/ReservationForm';
import Loading from '../../commons/components/Loading';
import UserInfoContext from '../../member/modules/UserInfoContext';
import apiApply from '../apis/apiApply';

const ReservationApplyContainer = ({ setPageTitle }) => {
  const { seq } = useParams();
  const {
    states: { userInfo },
  } = useContext(UserInfoContext);

  const [data, setData] = useState(null);
  const [errors, setErrors] = useState({});
  const [form, setForm] = useState({
    activitySeq: seq,
    name: userInfo?.userName,
    email: userInfo?.email,
    mobile: userInfo?.mobile,
    persons: 1, //기본값 1명
  });
  const { t } = useTranslation();
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const res = await apiGet(seq);
        console.log('Fetched data:', res); // 데이터 확인용 로그 추가
        setPageTitle(`${res.townName} ${t('예약하기')}`);

        /* 예약 가능일 문자열 -> Date 객체  */
        const availableDates = Object.keys(res.availableDates).sort();
        res.minDate = new Date(availableDates[0]);
        res.maxDate = new Date(availableDates.pop());
        res._availableDates = availableDates;

        setData(res);
      } catch (err) {
        console.error(err);
      }
    })();
  }, [seq, t, setPageTitle]);

  const onDateChange = useCallback(
    (date) => {
      const rDate = format(date, 'yyyy-MM-dd');
      const times = data.availableDates[rDate];
      setData((data) => ({ ...data, times }));
      setForm((form) => ({ ...form, rDate }));
    },
    [data, setForm],
  );

  const onTimeChange = useCallback((ampm) => {
    setForm((form) => ({ ...form, ampm }));
  }, []);

  const onChange = useCallback((e) => {
    setForm(
      produce((draft) => {
        draft[e.target.name] = e.target.value.trim();
      }),
    );
  }, []);

  const selectChange = useCallback(
    (selectedOption) => {
      setForm(
        produce((draft) => {
          draft.persons = selectedOption ? selectedOption.value : null;
        }),
      );
    },
    [setForm],
  );

  const onSubmit = useCallback(
    (e) => {
      e.preventDefault();

      let _errors = {};
      let hasErrors = false;

      setErrors({});

      /* 필수 항목 검증 S */
      const requiredFields = {
        rDate: t('예약일을_선택하세요'),
        ampm: t('시간대를_선택하세요'),
        name: t('예약자명을_입력하세요'),
        email: t('예약자_이메일을_입력하세요'),
        mobile: t('예약자_전화번호를_입력하세요'),
        persons: t('예약인원을_선택하세요'),
      };

      for (const [field, message] of Object.entries(requiredFields)) {
        if (
          (typeof form[field] === 'string' && !form[field].trim()) ||
          (typeof form[field] !== 'string' && !form[field])
        ) {
          _errors[field] = _errors[field] ?? [];
          _errors[field].push(message);
          hasErrors = true;
        }
      }
      /* 필수 항목 검증 E */

      if (hasErrors) {
        setErrors(_errors);
        return;
      }

      /* 예약 접수 처리 S */
      _useConfirm(t('정말_접수_하겠습니까?'), () => {
        (async () => {
          try {
            const res = await apiApply(form);
            // 예약 접수 성공시 예약 완료 페이지 이동
            navigate(`/reservation/complete/${res.seq}`, { replace: true });
          } catch (err) {
            console.error(err);
            setErrors({ global: [err.message] });
          }
        })();
      });
      /* 예약 접수 처리 E */
    },
    [t, form, navigate],
  );

  if (!data) {
    return <Loading />;
  }

  return (
    <ReservationForm
      data={data}
      form={form}
      errors={errors}
      onDateChange={onDateChange}
      onTimeChange={onTimeChange}
      onSubmit={onSubmit}
      onChange={onChange}
      selectChange={selectChange}
    />
  );
};

export default React.memo(ReservationApplyContainer);
