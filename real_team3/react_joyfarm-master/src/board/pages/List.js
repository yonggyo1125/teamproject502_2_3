import React, { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { OuterBox } from '../../commons/components/LayoutBox';
import ListContainer from '../containers/ListContainer';
import { ContentBox } from '../../commons/components/LayoutBox';
import SubTitleLink from '../../commons/SubTitleLink';
import Header from '../../layouts/Header';
import { useParams } from 'react-router-dom';

const List = () => {
  const [pageTitle, setPageTitle] = useState('');
  const { bid } = useParams();

  return (
    <>
      <SubTitleLink text={pageTitle} href={`/board/list/${bid}`} />
      <Helmet>
        <title>{pageTitle}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <ListContainer setPageTitle={setPageTitle} />
        </ContentBox>
      </OuterBox>
    </>
  );
};

export default React.memo(List);
