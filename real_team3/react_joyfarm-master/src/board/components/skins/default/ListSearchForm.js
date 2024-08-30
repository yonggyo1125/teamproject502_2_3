import React from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { color } from '../../../../styles/color';
import { ImSearch } from 'react-icons/im';

const { white } = color;

const FormBox = styled.form`
  width: 100%;

  .search {
    display: flex;
    gap: 5px;
    justify-content: flex-end;
    align-items: center;
    height: 70px;
    border: 1px solid #ccc;
    border-top: 3px solid #444;
    padding: 5px 20px;
    margin-top: 20px;

    select {
    width: 120px;
    height: 40px;
    padding: 0 10px;
    border: 1px solid #ccc;
    }

    input {
    width: 250px;
    height: 40px;
    border: 1px solid #ccc;
    padding: 0 10px;
    }
  }
`;

const Button = styled.button`
  font-size: 1.3em;
  color: ${white};
  width: 80px;
  height: 40px;
  background: #384863;
  border: none;
  cursor: pointer;
  padding-top: 5px;
  border-radius: 3px;
`;

const ListSearchForm = ({ search, onChange, onSubmit }) => {
  const { t } = useTranslation();
  return (
    <FormBox onSubmit={onSubmit} autoComplete="off">
      <div className="search">
      <select name="sopt" value={search?.sopt} onChange={onChange}>
        <option value="ALL">{t('통합검색')}</option>
        <option value="SUBJECT">{t('제목')}</option>
        <option value="CONTENT">{t('내용')}</option>
        <option value="SUBJECT_CONTENT">{t('제목+내용')}</option>
        <option value="NAME">{t('이름')}</option>
      </select>
      <input
        type="text"
        name="skey"
        value={search?.skey}
        onChange={onChange}
        placeholder={t('검색어를_입력하세요')}
      />
      <Button><ImSearch /></Button>
      </div>
    </FormBox>
  );
};

export default React.memo(ListSearchForm);
