import React, { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { OuterBox, ContentBox } from '../../../commons/components/LayoutBox';
import { IoMdPricetags } from "react-icons/io";

import {
  DetailImgBox,
  DetailTitle,
} from '../../../commons/components/DetailBox';
import ViewContainer from '../containers/ViewContainer';
import Header from '../../../layouts/Header';

const FestivalView = () => {
  const [SubPageTitle, setSubPageTitle] = useState('');
  return (
    <>
      <Helmet>
        <title>{SubPageTitle}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <DetailImgBox>
            <DetailTitle>
              <h1>
              <IoMdPricetags  className='icon' /> {SubPageTitle}</h1>
            </DetailTitle>
            <ViewContainer setSubPageTitle={setSubPageTitle} />
          </DetailImgBox>
        </ContentBox>
      </OuterBox>
    </>
  );
};

export default React.memo(FestivalView);
