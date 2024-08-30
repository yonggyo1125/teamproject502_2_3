import React, { useContext } from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import UserInfoContext from '../../../../member/modules/UserInfoContext';
import MessageBox from '../../../../commons/components/MessageBox';
import fontSize from '../../../../styles/fontSize';
import { color } from '../../../../styles/color';
const { medium, big } = fontSize;
const { darkGreen, midGreen, white } = color;

//댓글 목록
const FormBox = styled.form`
  display: block;

  .comment-form {
    font-size: ${medium};
    margin: 5px;
    position: relative;

    .field {
      overflow: hidden;
      margin-bottom: -1px;

      .idpwFied {
        display: flex;
      }

      input {
        float: left;
        width: 20%;
        padding: 12px 16px;
        border: 1px solid #cccccc;
        color: #777;
        box-sizing: border-box;
        border-radius: 5px;
        margin-right: 5px;
        margin-bottom: 5px;
        font-size: 1.2rem;
      }

      textarea {
        padding-right: 32px;
        display: block;
        width: 100%;
        height: 100px;
        padding: 12px 16px;
        border: 1px solid #cccccc;
        color: #777;
        box-sizing: border-box;
        resize: none;
        border-radius: 5px;
        font-size: 1.2rem;
      }

      button {
        display: flex;
        justify-content: center;
        align-items: center;
        color: ${white};
        font-size: ${medium};
        font-weight: 600px;
        width: 100px;
        height: 35px;
        background-color: ${midGreen};
        border: none;
        margin-left: auto;
        cursor: pointer;
        border-radius: 5px;
        margin-top: 10px;
      }
    }
  }
`;

const CommentForm = ({ form, onChange, onSubmit, errors }) => {
  console.log(errors);
  const { t } = useTranslation();
  const {
    states: { isLogin },
  } = useContext(UserInfoContext);

  return (
    <FormBox onSubmit={onSubmit} autoComplete="off">
      <div className="comment-form">
        <div className="field">
          <div className="idpwFied">
            <input
              type="text"
              name="commenter"
              placeholder={t('작성자')}
              value={form?.commenter}
              onChange={onChange}
            />
            {!isLogin && (
              <input
                type="password"
                name="guestPw"
                className="guest"
                placeholder={t('비밀번호')}
                value={form?.guestPw}
                onChange={onChange}
              />
            )}
            {errors?.commenter && (
              <MessageBox color="danger" messages={errors.commenter} />
            )}
            {errors?.guestPw && (
              <MessageBox color="danger" messages={errors.guestPw} />
            )}
          </div>
          <textarea
            name="content"
            placeholder={t('여러분의_소중한_의견을_남겨주세요')}
            value={form?.content}
            onChange={onChange}
          ></textarea>
          {errors?.content && (
            <MessageBox color="danger" messages={errors.content} />
          )}

          <button type="submit">{t('작성하기')}</button>
        </div>
      </div>
    </FormBox>
  );
};

export default React.memo(CommentForm);
