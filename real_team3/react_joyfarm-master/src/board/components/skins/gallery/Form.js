import React, { useState, useEffect, useContext, useCallback } from 'react';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import {
  ClassicEditor,
  Image,
  ImageInsert,
  Bold,
  Essentials,
  Italic,
  Paragraph,
} from 'ckeditor5';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import MessageBox from '../../../../commons/components/MessageBox';
import InputBox2 from '../../../../commons/components/InputBox2';
import UserInfoContext from '../../../../member/modules/UserInfoContext';
import { FaCheckSquare, FaSquare } from 'react-icons/fa';
import { MidButton } from '../../../../commons/components/Buttons';
import FileUpload from '../../../../commons/components/FileUpload';
import FileItems from '../../../../commons/components/FileItems';

import 'ckeditor5/ckeditor5.css';

const Wrapper = styled.form`

  flex-direction: column;
  align-items: center;
  max-width: 1100px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 

  .ck-editor__editable {
    height: 350px;
    width: 100%;
    margin-bottom:15px;
  }

  textarea {
    width: 100%;
    height: 200px;
    border: 1px solid #d5d5d5;
    resize: none;
    padding: 15px;
    border-radius: 5px;
  }

  .input-box {
    width: 180px;
    padding: 10px;
    margin-bottom: 15px;
    border-radius: 5px;
  }

  .sub{
    font-size: 20px;
    font-weight: bold;
    padding-top: 10px;
    padding-bottom: 5px;
    text-align: left;
  }

  .sub2{
    font-size: 16px;
    margin-bottom: 10px;
    text-align: left;
    width: 100%;
  }

  .uploadButton{
    padding-bottom: 10px;
  }

  .submitButton {
    width: 100%;
    padding: 12px;
    margin-top: 20px;
    background-color: #2d8f2d;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 18px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    
    &:hover {
      background-color: #276d27;
    }
`;

const Form = ({
  board,
  form,
  onSubmit,
  onToggleNotice,
  errors,
  fileUploadCallback,
  fileDeleteCallback,
  onChange,
}) => {
  const [mounted, setMounted] = useState(false);
  const [editor, setEditor] = useState(null);
  const { useEditor, useUploadImage, useUploadFile } = board;
  const { t } = useTranslation();
  const {
    states: { isLogin, isAdmin },
  } = useContext(UserInfoContext);

  useEffect(() => {
    setMounted(true);

    return () => {
      setMounted(false);
    };
  }, []);

  // 이미지 에디터 첨부
  const insertImageCallback = useCallback(
    (url) => {
      editor.execute('insertImage', { source: url });
    },
    [editor],
  );

  return (
    <Wrapper onSubmit={onSubmit} autoComplete="off">
      <dl>
        <dt className="sub">{t('작성자')}</dt>
        <dd>
          <InputBox2
            type="text"
            name="poster"
            value={form?.poster}
            onChange={onChange}
            className="input-box"
            placeholder="이름을 입력하세요."
          />
          {errors?.poster && (
            <MessageBox color="danger" messages={errors.poster} />
          )}
        </dd>
      </dl>
      {((form.mode === 'write' && !isLogin) ||
        (form.mode === 'update' && !form?.member)) && (
        <dl>
          <dt className="sub">{t('비밀번호')}</dt>
          <dd>
            <InputBox2
              type="password"
              name="guestPw"
              value={form?.guestPw}
              onChange={onChange}
              className="input-box"
              placeholder="비회원 비밀번호"
            />
            {errors?.guestPw && (
              <MessageBox color="danger" messages={errors.guestPw} />
            )}
          </dd>
        </dl>
      )}
      {isAdmin && (
        <dl>
          <dt>{t('공지글')}</dt>
          <dd>
            <label onClick={onToggleNotice}>
              {form?.notice ? <FaCheckSquare /> : <FaSquare />}
              {t('공지글로_등록하기')}
            </label>
          </dd>
        </dl>
      )}
      <dl>
        <dt className="sub">{t('제목')}</dt>
        <dd>
          <InputBox2
            type="text"
            name="subject"
            value={form?.subject}
            onChange={onChange}
            placeholder="제목을 입력하세요."
          />
          {errors?.subject && (
            <MessageBox color="danger" messages={errors.subject} />
          )}
        </dd>
      </dl>
      <dl>
        <dt className="sub">{t('내용')}</dt>
        <dd>
          {useEditor ? (
            mounted && (
              <>
                <CKEditor
                  editor={ClassicEditor}
                  config={{
                    plugins: [
                      Bold,
                      Essentials,
                      Italic,
                      Paragraph,
                      Image,
                      ImageInsert,
                    ],
                    toolbar: [
                      'bold',
                      'italic',
                      '|',
                      'undo',
                      'redo',
                      '|',
                      'numberedList',
                      'bulletedList',
                    ],
                  }}
                  data={form?.content}
                  onReady={(editor) => setEditor(editor)}
                  onChange={(_, editor) => {
                    onChange({
                      target: { name: 'content', value: editor.getData() },
                    });
                  }}
                />
                {editor && useUploadImage && (
                  <dl>
                    <dt className="sub2">{t('이미지 첨부')}</dt>
                    <div className="uploadButton">
                      <FileUpload
                        gid={form.gid}
                        location="editor"
                        imageOnly
                        color="dark"
                        width="120"
                        callback={(files) => fileUploadCallback(files, editor)}
                      >
                        {t('이미지_업로드')}
                      </FileUpload>
                    </div>

                    <FileItems
                      files={form?.editorImages}
                      mode="editor"
                      insertImageCallback={insertImageCallback}
                      fileDeleteCallback={fileDeleteCallback}
                    />
                  </dl>
                )}
              </>
            )
          ) : (
            <textarea
              name="content"
              value={form?.content}
              onChange={onChange}
            ></textarea>
          )}
          {errors?.content && (
            <MessageBox color="danger" messages={errors.content} />
          )}
        </dd>
      </dl>
      {useUploadFile && (
        <dl>
          <dt className="sub2">{t('파일첨부')}</dt>
          <dd>
            <FileUpload
              gid={form.gid}
              location="attach"
              width="120"
              color="dark"
              callback={fileUploadCallback}
            >
              {t('파일선택')}
            </FileUpload>
            <FileItems
              files={form?.attachFiles}
              mode="attach"
              fileDeleteCallback={fileDeleteCallback}
            />
          </dd>
        </dl>
      )}
      <MidButton type="submit" color="darkGreen" className="submitButton">
        {t(form.mode === 'update' ? '수정하기' : '작성하기')}
      </MidButton>
    </Wrapper>
  );
};

export default React.memo(Form);
