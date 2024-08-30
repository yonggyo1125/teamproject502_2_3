import React, { useContext } from 'react';
import MemberOnlyContainer from '../../member/containers/MemberOnlyContainer';
import UserInfoContext from '../../member/modules/UserInfoContext';
import { useTranslation } from 'react-i18next';
import { Helmet } from 'react-helmet-async';
import styled from 'styled-components';
import { color } from '../../styles/color';
import ProfileImage from '../../commons/components/ProfileImage';

const { whiteGreen, mid_gray } = color;

const StyledMain = styled.div`
  width: 700px;
  height: 400px;
  margin: 50px auto 0;
  padding: 20px 0;
  background: ${whiteGreen};
  text-align: center;
  font-size: 1.8rem;
  border: 4px solid ${mid_gray};
  border-radius: 5px;
  box-shadow: 2px 2px 2px 3px #d6d6d6;

  .icon {
    margin-top: 20px;
    font-size: 5rem;
  }

  .proimg {
    width: 200px;
    margin-top: 20px;
  }
`;

const StyledProfileImage = styled(ProfileImage)`
  margin-top: 20px;
  pointer-events: none;
`;

const MyPageMain = () => {
  const {
    states: { userInfo },
  } = useContext(UserInfoContext);
  const { t } = useTranslation();
  const profileImageUrl = userInfo?.profileImage?.fileUrl;

  return (
    <MemberOnlyContainer>
      <Helmet>
        <title>{t('마이페이지')}</title>
      </Helmet>
      <StyledMain>
        <h1>{t('마이페이지')}</h1>
        {userInfo?.userName}({userInfo?.email}) 님 환영합니다.
        <StyledProfileImage
          gid={userInfo?.gid}
          profileImage={profileImageUrl}
          fileUploadCallback={null}
        />
      </StyledMain>
    </MemberOnlyContainer>
  );
};

export default React.memo(MyPageMain);
