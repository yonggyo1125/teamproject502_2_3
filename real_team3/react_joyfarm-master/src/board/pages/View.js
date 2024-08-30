import React, { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { ContentBox, OuterBox } from '../../commons/components/LayoutBox';
import SubTitleLink from '../../commons/SubTitleLink';
import Header from '../../layouts/Header';
import ViewContainer from '../containers/ViewContainer';
import { useParams } from 'react-router-dom';

const View = (bid) => {
  const [SubPageTitle, setSubPageTitle] = useState('');
  const [pageTitle, setPageTitle] = useState('');

  return (
    <>
      <Helmet>
        <title>{SubPageTitle}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <ViewContainer setPageTitle={setPageTitle} />
        </ContentBox>
      </OuterBox>
    </>
  );
}
export default React.memo(View);