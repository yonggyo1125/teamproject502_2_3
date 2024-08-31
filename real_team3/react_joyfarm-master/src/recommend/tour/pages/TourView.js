import React, { useContext } from 'react';
import { Helmet } from 'react-helmet-async';
import { Link } from 'react-router-dom';
import { OuterBox, ContentBox } from '../../../commons/components/LayoutBox';
import Header from '../../../layouts/Header';
import {
  DetailImgBox,
  DetailTitle,
} from '../../../commons/components/DetailBox';
import ViewContainer from '../containers/ViewContainer';
import { IoMdPricetags } from 'react-icons/io';
import CommonContext from '../../../commons/modules/CommonContext';

const TourView = () => {
  const {
    states: { linkText, linkHref },
  } = useContext(CommonContext);
  return (
    <>
      <Helmet>
        <title>{linkText}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <DetailImgBox>
            <DetailTitle>
              <h1>
                <IoMdPricetags className="icon" />
                <Link to={linkHref}>{linkText}</Link>
              </h1>
            </DetailTitle>
            <ViewContainer />
          </DetailImgBox>
        </ContentBox>
      </OuterBox>
    </>
  );
};

export default React.memo(TourView);
