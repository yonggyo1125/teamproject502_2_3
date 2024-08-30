import React, { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { ContentBox, OuterBox } from '../../commons/components/LayoutBox';
import FormContainer from '../containers/FormContainer';
import SubTitleLink from '../../commons/SubTitleLink';
import Header from '../../layouts/Header';
import { useParams } from 'react-router-dom';

const Update = () => {
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
          <FormContainer setPageTitle={setPageTitle} />
        </ContentBox>
      </OuterBox>
    </>
  );
};

export default React.memo(Update);