import React from 'react';
import { Link } from 'react-router-dom';
import { useTransition } from 'react';
import ListItems from './ListItems';
import ListSearchForm from './ListSearchForm';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import Listsort from './Listsort';

const List = ({items, search, onChange, onSubmitSearch, onSubmit }) => {
  const { t } = useTranslation();
  // 검색 항목 제출
  return (
    <>
      <ListSearchForm search={search} onChange={onChange} onSubmit={onSubmitSearch} />
      <Listsort search={search} onChange={onChange} onSubmit={onSubmit} />
      <ListItems items={items} />
    </>
  );
};

export default React.memo(List);
