import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { ImSearch } from 'react-icons/im';
import { color } from '../../styles/color';

const { gray } = color;

const FormBox = styled.form`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 20px;
  padding: 5px 20px 20px;
  border-bottom: 1px solid #ccc;

  .select_box,
  .input_part {
    height: 45px;
    items-align: center;
    justify-content: center;
    display: flex;
    margin: 0 20px 0 10px;
  }

  input {
    width: 300px;
    height: 45px;
    border: 1px solid ${gray};
    padding: 0 10px;
  }

  select {
    width: 130px;
    height: 45px;
    padding: 0 10px;
    border: 1px solid ${gray};
    margin-right: 3px;
  }

  .rsv_searchBar {
    justify-content: center;
    align-content: center;
    display: flex;
  }

  .rsv_btn {
    width: 180px;
  }
`;

const Button = styled.button`
  font-size: 1.3em;
  color: white;
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
      <div className="input_part">
        <select name="sido" onChange={onChange} value={form.sido}>
          <option value="ALL">{t('시도_선택')}</option>
          {sidoOptions.map((option) => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
        <select name="sigungu" onChange={onChange}>
          <option>- {t('시군구_선택')} -</option>
        </select>
        <select name="sopt" onChange={onChange} defaultValue={form.sopt}>
          <option value="ALL">{t('통합검색')}</option>
          <option value="DIVISION">{t('프로그램구분')}</option>
          <option value="ACTIVITY">{t('체험프로그램명')}</option>
          <option value="FACILITYINFO">{t('보유시설정보')}</option>
          <option value="ADDRESS">{t('체험_마을_주소')}</option>
        </select>
        <input
          type="text"
          name="skey"
          value={form.skey}
          onChange={onChange}
          placeholder="검색어를 입력하세요"
        />
      </div>
      <div className="rsv_searchBar">
        <Button>
          <ImSearch />
        </Button>
      </div>
    </FormBox>
  );
};

export default React.memo(SearchBox);
