import React, { useCallback } from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { color } from '../../../styles/color';
import { ImSearch } from 'react-icons/im';
import SelectLocation from '../../../commons/components/SelectLocation';

const { white } = color;

const FormBox = styled.form`
  width: 1300px;

  .tour-search {
    display: flex;
    gap: 5px;
    justify-content: flex-end;
    align-items: center;
    height: 80px;
    border: 1px solid #ccc;
    border-top: 3px solid #444;
    padding: 5px 20px;
    margin-top: 20px;

    select {
      width: 120px;
      height: 45px;
      padding: 0 10px;
      border: 1px solid #ccc;
    }

    input {
      width: 250px;
      height: 45px;
      border: 1px solid #ccc;
      padding: 0 10px;
    }
  }
`;

const Button = styled.button`
  font-size: 1.3em;
  color: ${white};
  width: 80px;
  height: 45px;
  background: #384863;
  border: none;
  cursor: pointer;
  padding-top: 5px;
  font-size: 2rem;
`;

const SearchBox = ({ form, onChange, onSubmit }) => {
  const { t } = useTranslation();

  const onSelectedLocation = useCallback(
    ({ sido, sigungu }) => {
      onChange({ target: { name: 'sido', value: sido } });
      onChange({ target: { name: 'sigungu', value: sigungu } });
    },
    [onChange],
  );

  return (
    <FormBox onSubmit={onSubmit} autoComplete="off">
      <div className="tour-search">
        <SelectLocation selected={form} callback={onSelectedLocation} />
        <select name="sopt" onChange={onChange} defaultValue={form.sopt}>
          <option value="ALL">{t('통합검색')}</option>
          <option value="TITLE">{t('여행지_명')}</option>
          <option value="TEL">{t('문의처')}</option>
          <option value="ADDRESS">{t('여행장소')}</option>
          <option value="DESCRIPTION">{t('여행지_설명')}</option>
        </select>
        <input
          type="text"
          name="skey"
          value={form.skey}
          onChange={onChange}
          placeholder="검색어를 입력하세요"
        />
        <Button>
          <ImSearch />
        </Button>
      </div>
    </FormBox>
  );
};

export default React.memo(SearchBox);
