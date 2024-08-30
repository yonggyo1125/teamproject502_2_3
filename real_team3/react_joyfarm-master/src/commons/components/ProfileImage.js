import React from 'react';
import styled from 'styled-components';
import FileUpload from '../../commons/components/FileUpload';
import NoProfile from '../../images/myprofile.png';

const Wrapper = styled.div`
  width: 200px;
  height: 200px;
  margin: auto;
`;

const ProfileImage = ({ gid, profileImage, fileUploadCallback, className }) => {
  return (
    <Wrapper className={className}>
      <FileUpload
        width={200}
        imageUrl={profileImage ?? NoProfile}
        gid={gid}
        imageOnly={true}
        single={true}
        done={true} // false 로 바꾸면 수정하기 눌러야 프로필이미지 수정됨!
        callback={fileUploadCallback}
      />
    </Wrapper>
  );
};

export default React.memo(ProfileImage);
