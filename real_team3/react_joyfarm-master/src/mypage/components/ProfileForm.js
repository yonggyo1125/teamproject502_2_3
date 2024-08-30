import React from 'react';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';
import MessageBox from '../../commons/components/MessageBox';
import InputBox from '../../commons/components/InputBox';
import fontSize from '../../styles/fontSize';
import { color } from '../../styles/color';
import { ButtonGroup, MidButton } from '../../commons/components/Buttons';

const { medium } = fontSize;
const { midGreen } = color;

const FormBox = styled.form`
  width: 500px;
  margin: 30px auto;
  font-size: ${medium};

  input {
    margin-bottom: 10px;
  }

  dl + dl {
    padding-top: 20px;
  }

  button {
    border: none;
    color: white;
    background: ${midGreen};
  }

  button a {
    border: none;
    color: white;
    background: ${midGreen};
  }
`;
const ProfileForm = ({ form, _onChange, errors, onSubmit, onClick }) => {
  const { t } = useTranslation();

  return (
    <FormBox onSubmit={onSubmit}>
      <div className="mypage">
        <dl>
          <dt>{t('이메일')}</dt>
          <dd>
            <InputBox
              type="text"
              name="email"
              value={form?.email}
              disabled
              onChange={_onChange}
            />
          </dd>
        </dl>
        <dl>
          <dt>{t('비밀번호')}</dt>
          <dd>
            <InputBox
              name="password"
              type="password"
              value={form?.password}
              onChange={_onChange}
            />
            {errors?.password && (
              <MessageBox color="danger" messages={errors.password} />
            )}
          </dd>
        </dl>
        <dl>
          <dt>{t('비밀번호_확인')}</dt>
          <dd>
            <InputBox
              name="confirmPassword"
              type="password"
              value={form?.confirmPassword}
              onChange={_onChange}
            />
            {errors?.confirmPassword && (
              <MessageBox color="danger" messages={errors.confirmPassword} />
            )}
          </dd>
        </dl>
        <dl>
          <dt>{t('회원명')}</dt>
          <dd>
            <InputBox
              type="text"
              name="userName"
              value={form?.userName}
              onChange={_onChange}
            />
            {errors?.userName && (
              <MessageBox color="danger" messages={errors.userName} />
            )}
          </dd>
        </dl>
        <dl>
          <dt>{t('휴대전화번호')}</dt>
          <dd>
            <InputBox
              type="text"
              name="mobile"
              value={form?.mobile}
              onChange={_onChange}
            />
            {errors?.mobile && (
              <MessageBox color="danger" messages={errors.mobile} />
            )}
          </dd>
        </dl>
      </div>
      {errors?.global && <MessageBox color="danger" messages={errors.global} />}
      <ButtonGroup>
        <MidButton type="submit">{t('회원정보_수정하기')}</MidButton>
        <MidButton type="button" onClick={onClick}>
          {t('회원탈퇴하기')}
        </MidButton>
      </ButtonGroup>
    </FormBox>
  );
};

export default React.memo(ProfileForm);
