import React from 'react';
import { Helmet } from 'react-helmet-async';
import { useTranslation } from 'react-i18next';
import JoinContainer from '../containers/JoinContainer';
import { ContentBox3 } from '../../commons/components/LayoutBox';
import { MainTitle2 } from '../../commons/components/TitleBox';
import { color } from '../../styles/color';
import GuestOnlyContainer from '../containers/GuestOnlyContainer';
import { RiPlantLine } from 'react-icons/ri';

const { whiteGray } = color;

const Join = () => {
  const { t } = useTranslation();

  return (
    <GuestOnlyContainer>
      <Helmet>
        <title>{t('회원가입')}</title>
      </Helmet>
      <MainTitle2 style={{ borderBottom: `1px solid ${whiteGray}` }}>
        <h1><RiPlantLine />
        &nbsp;
        {t('회원가입')}
        &nbsp;
        <RiPlantLine /></h1>
      </MainTitle2>
      <ContentBox3>
        <JoinContainer />
      </ContentBox3>
    </GuestOnlyContainer>
  );
};

export default React.memo(Join);
