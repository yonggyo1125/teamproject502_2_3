import React, { useState, useCallback, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { apiJoin, apiEmailAuth, apiEmailAuthCheck } from '../apis/apiJoin';
import JoinForm from '../components/JoinForm';
import apiRequest from '../../commons/libs/apiRequest';

const basicForm = {
  gid: '' + Date.now(),
  agree: false,
  authNum: '',
  emailVerified: false,
  authCount: 180,
  authCountMin: '03:00',
};
const JoinContainer = () => {
  const authCountInterval = useRef();

  // 양식 데이터
  const [form, setForm] = useState(basicForm);

  // 양식 항목별 에러 메세지
  const [errors, setErrors] = useState({});

  const { t } = useTranslation();

  const navigate = useNavigate();

  // 이메일 인증 코드 전송
  const onSendAuthCode = useCallback(() => {
    // 이메일을 입력하지 않은 경우
    if (!form?.email?.trim()) {
      setErrors((errors) => ({
        ...errors,
        email: [t('이메일을_입력하세요')],
      }));
      return;
    } else {
      delete errors.email;
      const _errors = errors;
      setErrors(_errors);
    }

    form.authCount = 180;
    // 3분 카운트 시작
    authCountInterval.current = setInterval(() => {
      form.authCount--;
      const minutes = Math.floor(form.authCount / 60);
      const seconds = form.authCount - minutes * 60;

      const authCountMin =
        ('' + minutes).padStart(2, '0') + ':' + ('' + seconds).padStart(2, '0');

      if (form.authCount < 0) {
        form.authCount = 0;
        clearInterval(authCountInterval.current);
      }

      setForm((form) => ({
        ...form,
        authCount: form.authCount,
        authCountMin,
      }));
    }, 1000);

    // 인증 이메일 보내기
    apiEmailAuth(form.email, form.gid);
  }, [form, errors, t]);

  // 인증 코드 재전송
  const onReSendAuthCode = useCallback(() => {
    clearTimeout(authCountInterval.current);
    onSendAuthCode();
  }, [onSendAuthCode]);

  const onVerifyAuthCode = useCallback(() => {
    if (!form.authNum?.trim()) {
      setErrors((errors) => ({
        ...errors,
        email: [t('인증코드를_입력하세요')],
      }));
      return;
    }

    (async () => {
      try {
        await apiEmailAuthCheck(form.authNum, form.gid);

        setForm((form) => ({ ...form, emailVerified: true })); // 이메일 인증 처리

        delete errors.email;
        const _errors = errors;
        setErrors(_errors);
        // 인증 완료 시 타이머 멈춤
        clearInterval(authCountInterval.current);
      } catch (err) {
        setErrors((errors) => ({
          ...errors,
          email: [t('이메일_인증에_실패하였습니다.')],
          
        }));
      }
    })();
  }, [t, form, errors]);

  /**
   * 회원 가입 처리
   *
   * 1. 데이터 검증
   *    1) 필수 항목 체크 - 이메일, 비밀번호, 비밀번호 확인, 회원명, 약관동의
   *    2) 이메일 중복 여부, 이메일 형식 체크
   *    3) 비밀번호 복잡성 체크
   *    4) 비밀번호와 비밀번호 확인 일치 여부
   *
   * 2. 가입 처리 - 영구 저장
   * 3. 로그인 페이지 이동
   */
  const onSubmit = useCallback(
    (e) => {
      e.preventDefault();

      const _errors = {};
      let hasErrors = false; // 에러 유무

      /* 데이터 검증 - 필수 항목 체크 S */
      const requiredFields = {
        email: t('이메일을_입력하세요'),
        password: t('비밀번호를_입력하세요'),
        confirmPassword: t('비밀번호를_확인하세요'),
        userName: t('회원명을_입력하세요'),
        agree: t('회원가입_약관에_동의하세요'),
      };

      for (const [field, msg] of Object.entries(requiredFields)) {
        // !form[field] - null, undefined, '' 체크, !form[field].trim() //  - '    '
        if (
          !form[field] ||
          (typeof form[field] === 'string' && !form[field].trim())
        ) {
          _errors[field] = _errors[field] || [];
          _errors[field].push(msg);
          hasErrors = true;
        }
      }

      /* 데이터 검증 - 필수 항목 체크 E */

      /* 데이터 검증 - 비밀번호와 비밀번호 확인 일치 여부 */
      if (
        form.password &&
        form.confirmPassword &&
        form.password !== form.confirmPassword
      ) {
        _errors.confirmPassword = _errors.confirmPassword || [];
        _errors.confirmPassword.push(t('비밀번호가_정확하지_않습니다'));
        hasErrors = true;
      }

      /* 이메일 인증 여부 체크 S */
      if (!form.emailVerified) {
        _errors.email = _errors.email ?? [];
        _errors.email.push(t('이메일을_인증하세요'));
        hasErrors = true;
      }

      /* 이메일 인증 여부 체크 E */

      if (hasErrors) {
        setErrors(_errors);
        return;
      }

      /* 가입처리 S */
      apiJoin(form)
        .then(() => {
          /* 가입완료 후 로그인 페이지 이동 */
          navigate('/member/login', { replace: true }); // replace: true -> 방문기록 X
        })
        .catch((err) => {
          console.log(err);
          // 검증 실패, 가입 실패
          const messages =
            typeof err.message === 'string'
              ? { global: [err.message] }
              : err.message;

          for (const [field, _messages] of Object.entries(messages)) {
            _errors[field] = _errors[field] ?? [];
            _errors[field].push(_messages);
          }
          setErrors({ ..._errors });
        });

      /* 가입처리 E */
    },
    [t, form, navigate],
  );

  const onChange = useCallback((e) => {
    const name = e.target.name;
    const value = e.target.value.trim();
    setForm((form) => ({ ...form, [name]: value }));
  }, []);

  const onToggle = useCallback(() => {
    setForm((form) => ({ ...form, agree: !form.agree }));
  }, []);

  const onReset = useCallback(() => setForm({ ...basicForm }), []);

  //파일 업로드 콜백 처리
  const fileUploadCallback = useCallback((files) => {
    // 프로필 파일 정보 업데이트
    if (files.length === 0) return;
    setForm((form) => ({ ...form, profile: files[0] }));
  }, []);

  const fileDeleteCallback = useCallback(
    (seq) => {
      if (!window.confirm(t('정말_삭제_하시겠습니까'))) {
        return;
      }

      (async () => {
        try {
          const res = await apiRequest(`/file/delete/${seq}`, 'DELETE');
          if (res.status === 200 && res.data.success) {
            setForm((form) => ({ ...form, profile: null }));
            return;
          }

          if (res.data.message) {
            setErrors({ global: [res.data.message] });
          }
        } catch (err) {
          setErrors({ global: [err.message] });
          console.error(err);
        }
      })();
    },
    [t],
  );

  return (
    <JoinForm
      form={form}
      errors={errors}
      onSubmit={onSubmit}
      onChange={onChange}
      onToggle={onToggle}
      onReset={onReset}
      onSendAuthCode={onSendAuthCode}
      onReSendAuthCode={onReSendAuthCode}
      onVerifyAuthCode={onVerifyAuthCode}
      fileUploadCallback={fileUploadCallback}
      fileDeleteCallback={fileDeleteCallback}
    />
  );
};

export default React.memo(JoinContainer);
