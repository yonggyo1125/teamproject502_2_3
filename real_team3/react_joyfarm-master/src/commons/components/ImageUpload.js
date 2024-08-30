import React, { useState, useCallback } from 'react';
import { SmallButton } from './Buttons';
import Modal from 'react-modal';

const customStyles = {
  content: {
    top: 'calc(50% - 200px)',
    left: 'calc(50% - 150px)',
    width: '300px',
    height: '400px',
  },
};

const ImageUpload = ({ children, gid, color }) => {
  Modal.setAppElement('#root');
  color = color ?? 'blue';

  color = color ?? 'midGreen';

  const [open, setOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState(null); // 업로드한 이미지 저장
  const [previewImage, setPreviewImage] = useState(null);  // 미리보기 이미지 저장

  const onClick = useCallback(() => {
    setOpen((open) => !open);
  }, []);

  return (
    <>
      <SmallButton type="button" color={color} onClick={onClick}>
        {children}
      </SmallButton>
      {open && (
        <Modal isOpen={open} style={customStyles}>

          <h1>오류발생!</h1>

          <button type="button" onClick={() => setOpen(false)}>
            닫기
          </button>
        </Modal>
      )}
    </>
  );
};

export default React.memo(ImageUpload);
