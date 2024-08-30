/* eslint-disable no-undef */
import apiRequest from '../../commons/libs/apiRequest';
import cookies from 'react-cookies';
import requestData from '../../commons/libs/requestData';

export const apiJoin = (form) =>
  new Promise((resolve, reject) => {
    cookies.remove('token', { path: '/' }); //요청 보내기 전에 전체 경로의 토큰 제거
    apiRequest('/account', 'POST', form)
      .then((res) => {
        if (res.status !== 201) {
          // 검증 실패
          reject(res.data);
          return;
        }

        resolve(res.data); // 성공
      })
      .catch((err) => {
        reject(err);
      });
  });

// 이메일 인증 메일 보내기
export const apiEmailAuth = (email, uid) =>
  requestData(`/email-service/verify?email=${email}&uid=${uid}`);

// 인증 메일 코드 검증 처리
export const apiEmailAuthCheck = (authNum, uid) =>
  new Promise((resolve, reject) => {
    (async () => {
      try {
        const res = await apiRequest(
          `/email/auth_check?authNum=${authNum}&uid=${uid}`,
        );

        if (res.status === 200 && res.data.success) {
          resolve(res.data.data);
          return;
        }

        reject(res.data);
      } catch (err) {
        reject(err);
      }
    })();
  });
