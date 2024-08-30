import React from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';

const Wrapper = styled.div`
  display: flex;
  padding-top: 10px;
  padding-left: 10px;
  font-weight: bold;

  .sort-item {
    margin-right: 10px;

    cursor: pointer;
    border-radius: 4px;
    font-size: 1.2rem;
    background-color: #e0fbe2;
    color: #333;
    transition: background-color 0.3s, color 0.3s;
  }

  .sort-item:hover {
    background-color: #b0ebb4;
    color: #333;
  }

  .sort-item.active {
    background-color: #b0ebb4;
    color: #333;
  }
`;

const ListSort = ({ search, onChange }) => {
  const { t } = useTranslation();

  const handleSortChange = (sortValue) => {
    onChange({ target: { name: 'sort', value: sortValue } });
  };

  return (
    <Wrapper>
      <div
        className={`sort-item ${
          search?.sort === 'viewCount_DESC' ? 'active' : ''
        }`}
        onClick={() => handleSortChange('viewCount_DESC')}
      >
        {t('인기순')}
      </div>
      <div
        className={`sort-item ${
          search?.sort === 'createdAt_DESC' ? 'active' : ''
        }`}
        onClick={() => handleSortChange('createdAt_DESC')}
      >
        {t('최신순')}
      </div>
      <div
        className={`sort-item ${
          search?.sort === 'createdAt_ASC' ? 'active' : ''
        }`}
        onClick={() => handleSortChange('createdAt_ASC')}
      >
        {t('오래된순')}
      </div>
    </Wrapper>
  );
};

export default React.memo(ListSort);
