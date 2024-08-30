import React, { useContext, useCallback, useState } from 'react';
import styled from 'styled-components';
import { NavLink, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import cookies from 'react-cookies';
import MessageBox from '../../commons/components/MessageBox';
import UserInfoContext from '../../member/modules/UserInfoContext';
import InputBox from '../../commons/components/InputBox';
import fontSize from '../../styles/fontSize';
import { ButtonGroup, MidButton } from '../../commons/components/Buttons';
import { color } from '../../styles/color';
import { apiPatch, apiUpdate } from '../apis/apiMyPage';
import MyViewForm from '../components/ProfileForm';
import ProfileImage from '../components/ProfileImage';

const { small, big, medium } = fontSize;
const { midGreen, whiteGray } = color;

const StyledProfileImage = styled.div`
  margin: auto;
  width: 250px;
  border: 3px solid ${midGreen};
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const MyprofileContainer = () => {
  const {
    states: { isLogin, userInfo, isAdmin },
    actions: { setIsLogin, setIsAdmin, setUserInfo },
  } = useContext(UserInfoContext);
  // 'UserInfoContext'로 로그인 상태, 사용자 정보 가져옴

  const initialForm = userInfo;
  delete initialForm.password;
  const [form, setForm] = useState(initialForm);
  const [errors, setErrors] = useState({});

  const { t } = useTranslation();

  const _onChange = useCallback((e) => {
    // 현재 userInfo 상태를 복사한 후 변경된 값을 덮어씀
    setForm((form) => ({
      ...form,
      [e.target.name]: e.target.value,
    }));
  }, []);

  const onLogout = useCallback(() => {
    setIsLogin(false);
    setIsAdmin(false);
    setUserInfo(null);
    cookies.remove('token', { path: '/' });
  }, [setIsLogin, setIsAdmin, setUserInfo]);

  const navigate = useNavigate();

  const onSubmit = useCallback(
    (e) => {
      e.preventDefault();

      const _errors = {};
      let hasErrors = false;

      /**
       * 필수항목 검증
       * 1. 회원명(이름)
       * 2. 비밀번호(선택), 있는 경우 confirmPassword(필수), password, confirmPassword 일치여부
       */
      const requiredFields = {
        userName: t('회원명을_입력하세요'),
      };
      if (form?.password?.trim()) {
        requiredFields.confirmPassword = t('비밀번호를_확인하세요');
      }

      for (const [field, message] of Object.entries(requiredFields)) {
        if (!form[field] || !form[field].trim()) {
          _errors[field] = _errors[field] ?? [];

          _errors[field].push(message);
          hasErrors = true;
        }
      }

      // 비밀번호가 입력된 경우 비밀번호 확인 일치여부 체크
      if (!hasErrors && form?.password !== form?.confirmPassword) {
        _errors.confirmPassword = _errors.confirmPassword ?? [];
        _errors.confirmPassword.push(t('비밀번호가_일치하지_않습니다'));
        hasErrors = true;
      }

      setErrors(_errors);
      if (hasErrors) {
        return;
      }

      (async () => {
        try {
          const res = await apiUpdate(form);
          //회원 정보 수정 완료 후-> context api 쪽 정보 업데이트
          //form 초기화, 마이페이지 메인으로 이동
          //setUserInfo(res);
          const newForm = { ...form, ...res };
          delete newForm.password;

          setForm(newForm);
          setUserInfo(newForm);

          alert(t('회원정보가_수정되었습니다'));
          navigate('/mypage', { replace: true });
        } catch (err) {
          console.error(err);
          const messages = err.message.global
            ? err.message.global
            : { global: [err.message] };
          setErrors(messages);
        }
      })();
    },
    [t, form, navigate, setUserInfo],
  );

  /*
    if (window.confirm(t('회원정보를_수정하시겠습니까'))) {
      apiUpdate(form)
        .then(() => {
          setUserInfo(form);
          alert(t('회원정보가_수정되었습니다'));
          navigate('/mypage', { replace: false });
        })
        .catch((error) => {
          console.error(error);
          alert(t('회원정보_수정_중_오류가_발생했습니다'));
        });
    }

  };

  */
  /*
  const deleteUserInfo = () => {
    if (window.confirm(t('회원탈퇴를_진행하시겠습니까'))) {
      apiPatch(form)
        .then(() => {
          setUserInfo(null);
          alert(t('회원탈퇴완료'));
          navigate('/', { replace: true });
          onLogout();
        })
        .catch((error) => {
          console.error(error);
          alert(t('회원탈퇴실패'));
        });
    }
        */

  const deleteUserInfo = useCallback(() => {
    if (!window.confirm(t('회원탈퇴를_진행하시겠습니까'))) {
      return;
    }

    (async () => {
      try {
        await apiPatch(form);
        alert(t('회원탈퇴완료'));
        navigate('/', { replace: true });
        onLogout();
      } catch (err) {
        console.error(err);
        alert(t('회원탈퇴실패'));
      }
    })();
  }, [t, form, navigate, onLogout]);

  const fileUploadCallback = useCallback(
    (files) => {
      if (files.length === 0) {
        return;
      }

      setForm((form) => ({ ...form, profileImage: files[0] }));
      setUserInfo((userInfo) => ({ ...userInfo, profileImage: files[0] }));
    },
    [setUserInfo],
  );

  const profileImage = form?.profileImage?.fileUrl;

  return (
    <>
      <StyledProfileImage>
        <ProfileImage
          gid={form?.gid}
          fileUploadCallback={fileUploadCallback}
          profileImage={profileImage}
        />
      </StyledProfileImage>
      <MyViewForm
        form={form}
        _onChange={_onChange}
        onSubmit={onSubmit}
        onClick={deleteUserInfo}
        errors={errors}
      />
    </>
  );
};

export default React.memo(MyprofileContainer);
