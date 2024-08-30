import React from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import ListContainer from '../containers/ListContainer';
import { OuterBox, ContentBox2 } from '../../../commons/components/LayoutBox';
import Header from '../../../layouts/Header';
import SubTitleLink from '../../../commons/SubTitleLink';

const Tour = () => {
  const { t } = useTranslation();
  return (
    <>
      <SubTitleLink text={t('추천_여행지')} href="/recommend/tour" />
      <Helmet>
        <title>{t('추천_여행지')}</title>
      </Helmet>
      <OuterBox>
        <Header />
        <ContentBox2>
          <ListContainer />
        </ContentBox2>
      </OuterBox>
    </>
  );
};

export default React.memo(Tour);
