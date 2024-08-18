# 회원 가입 이메일 인증

## 백앤드 
### 이메일 서버 소스를 이전 먼저 할 것
- 패키지명은 조별로 적절하게 변경
- 서버 실행 시 환경 변수 설정

```
configServerUrl=http://localhost:3100;db.host=localhost;db.password=DB 비밀번호;db.port=1521;db.username=DB 사용자;ddl.auto=update;eurekaHost=http://localhost:3101;hostname=localhost;mail.password=메일 앱 비밀번호;mail.username=이메일 사용자;redis.host=localhost;redis.port=6379
```

## 프론트 앤드 - 리액트

### 환경 변수에 EMAIL_URL을 다음과 같이 추가

- 주소는 조별로 달라질 수 있음

> .env.development.local

```
REACT_APP_API_URL=http://localhost:3000/api/v1
REACT_APP_EMAIL_URL=http://localhost:3000/email
REACT_APP_ADMIN_URL=http://localhost:3000/admin
```

### apiRequest 공통 소스 변경
> src/commons/libs/apiRequest.js

```javascript
...

export default function apiRequest(url, method = 'GET', data, headers) {
  /**
   * url - http://jsonplaceholder.. https://
   */
  if (!/^http[s]?/i.test(url)) {
    // 외부 URL이 아닌 경우 - http://localhost:4000/api/v1/account
    if (url.indexOf('/email') !== 0) {
      url = process.env.REACT_APP_API_URL + url;
    } else {
      url = process.env.REACT_APP_EMAIL_URL + url.replace('/email', '');
    }
  }
  
  ...
    
}
```

### 이메일 인증코드 및 인증코드 확인 API 추가

> src/member/apis/apiJoin.js

```javascript
...

// 이메일 인증 메일 보내기
export const apiEmailAuth = (email, uid) =>
  requestData(`/email/verify?email=${email}&uid=${uid}`);

// 인증 메일 코드 검증 처리
export const apiEmailAuthCheck = (authNum, uid) =>
  new Promise((resolve, reject) => {
    (async () => {
      try {
        const res = await apiRequest(
          `/email/auth_check?authNum=${authNum}&uid=${uid}`,
        );

        if (res.status === 200 && res.data.success) {
          reject(res.data);
          return;
        }

        resolve(res.data.data);
      } catch (err) {
        reject(err);
      }
    })();
  });
```

### 이메일 인증 컴포넌트 구성 및 처리 

> src/member/containers/JoinContainer.js

```jsx
import React, { useState, useCallback, useRef } from 'react';

...

import { apiJoin, apiEmailAuth, apiEmailAuthCheck } from '../apis/apiJoin';
import JoinForm from '../components/JoinForm';
import apiRequest from '../../commons/libs/apiRequest';
...

const JoinContainer = () => {
  const authCountInterval = useRef();

  // 양식 데이터
  const [form, setForm] = useState({
    gid: '' + Date.now(),
    agree: false,
    authNum: '',
    emailVerified: false,
    authCount: 180,
    authCountMin: '03:00',
  });
    
  ...
  
  // 이메일 인증 코드 전송
  const onSendAuthCode = useCallback(() => {
    // 이메일을 입력하지 않은 경우
    if (!form?.email?.trim()) {
      setErrors((errors) => ({
        ...errors,
        email: [t('이메일을_입력하세요.')],
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
        email: [t('인증코드를_입력하세요.')],
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
    
    ...
    
    /* 이메일 인증 여부 체크 S */
    if (!form.emailVerified) {
        _errors.email = _errors.email ?? [];
        _errors.email.push(t('이메일을_인증하세요.'));
        hasErrors = true;
    }
    /* 이메일 인증 여부 체크 E */

    if (hasErrors) {
      setErrors(_errors);
      return;
    }

    /* 가입처리 S */
    apiJoin(form)
    ...
    
    /* 가입처리 E */
    
    
    },
    [t, form, navigate],
  );
  
  ...
  
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
}
```

> src/member/containers/JoinForm.js

```jsx

...

const EmailVerificationBox = styled.div`
  .rows {
    display: flex;
    align-items: center;
    button {
      width: 160px;
      height: 40px;
    }
  }

  .rows:last-of-type {
    span {
      width: 100px;
      text-align: center;
    }
    button {
      width: 80px;
    }

    button + button {
      margin-left: 5px;
    }
  }
`;

const JoinForm = ({
  form,
  onSubmit,
  onChange,
  onToggle,
  onReset,
  onSendAuthCode,
  onReSendAuthCode,
  onVerifyAuthCode,
  errors,
  fileUploadCallback,
  fileDeleteCallback,
}) => {
    const { t } = useTranslation();
  return (
    <FormBox autoComplete="off" onSubmit={onSubmit}>
      <dl>
        <dt>{t('이메일')}</dt>
        <dd>
          <EmailVerificationBox>
            <div className="rows">
              <InputBox
                type="text"
                name="email"
                value={form.email ?? ''}
                onChange={onChange}
                readOnly={
                  form.emailVerified ||
                  (form.authCount > 0 && form.authCount < 180)
                }
              />
              {!form.emailVerified && form.authCount > 0 && (
                <button
                  type="button"
                  onClick={onSendAuthCode}
                  disabled={form.authCount < 180 && form.authCount > 0}
                >
                  {t('인증코드_전송')}
                </button>
              )}
            </div>
            {form.emailVerified ? (
              <MessageBox color="primary">
                {t('확인된_이메일_입니다.')}
              </MessageBox>
            ) : (
              <div className="rows">
                {form.authCount > 0 && (
                  <InputBox
                    type="text"
                    name="authNum"
                    placeholder={t('인증코드_입력')}
                    onChange={onChange}
                  />
                )}
                <span>{form.authCountMin}</span>
                <button type="button" onClick={onVerifyAuthCode}>
                  {t('확인')}
                </button>
                <button type="button" onClick={onReSendAuthCode}>
                  {t('재전송')}
                </button>
              </div>
            )}
          </EmailVerificationBox>
          <MessageBox messages={errors.email} color="danger" />
        </dd>
      </dl>
      ...
      
    </FormBox>    
};

export default React.memo(JoinForm);
```