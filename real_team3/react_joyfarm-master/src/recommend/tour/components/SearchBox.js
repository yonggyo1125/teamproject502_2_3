import React from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { useState, useEffect } from 'react';
import { color } from '../../../styles/color';
import { ImSearch } from 'react-icons/im';

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

  const [sidoOptions, setSidoOptions] = useState([]);

  useEffect(() => {
    const fetchSidoOptions = async () => {
      const fetchedSidoOptions = [
        { value: '전라북도', label: '전라북도' },
        { value: '대전광역시', label: '대전광역시' },
        { value: '강원특별자치도', label: '강원특별자치도' },
        { value: '전라남도', label: '전라남도' },
        { value: '제주특별자치도', label: '제주특별자치도' },
        { value: '충청북도', label: '충청북도' },
        { value: '인천광역시', label: '인천광역시' },
        { value: '충청남도', label: '충청남도' },
        { value: '경상북도', label: '경상북도' },
        { value: '경상남도', label: '경상남도' },
        { value: '경기도', label: '경기도' },
        { value: '울산광역시', label: '울산광역시' },
      ];
      setSidoOptions(fetchedSidoOptions);
    };

    fetchSidoOptions();
  }, []);

  return (
    <FormBox onSubmit={onSubmit} autoComplete="off">
      <div className="tour-search">
        <select name="sido" onChange={onChange} value={form.sido}>
          <option value="ALL">{t('시도_선택')}</option>
          {sidoOptions.map((option) => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
        <select name="sigungu" onChange={onChange} value={form.sigungu}>
          <option value="ALL">{t('시군구_선택')}</option>
        </select>
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
