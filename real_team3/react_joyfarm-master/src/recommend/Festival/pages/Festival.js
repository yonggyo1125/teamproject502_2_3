import React, { useEffect } from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import { OuterBox, ContentBox } from '../../../commons/components/LayoutBox';

import ListContainer from '../containers/ListContainer';

import { PiLeafDuotone } from 'react-icons/pi';
import styled from 'styled-components';
import { color } from '../../../styles/color';
import SubTitleLink from '../../../commons/SubTitleLink';
import Header from '../../../layouts/Header';
const { midGreen } = color;

const Wrapper = styled.div`
  display: flex;
  font-size: 1.23rem;
  margin: 0 8px;

  .icon {
    color: ${midGreen};
  }
`;

const Festival = () => {
  const { t } = useTranslation();

  return (
    <>
      <SubTitleLink text={t('지역별_축제_정보')} href="/recommend/festival" />
      <Helmet>
        <title>{t('지역별_축제_정보')}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox>
          <Wrapper>
            <h2>
              <PiLeafDuotone className="icon" /> 지도를 클릭하면 지역별 축제를
              검색합니다.
            </h2>
          </Wrapper>
          <ListContainer />
        </ContentBox>
      </OuterBox>
    </>
  );
};

export default React.memo(Festival);
