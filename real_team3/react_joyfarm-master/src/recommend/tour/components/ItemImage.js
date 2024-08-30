import React from 'react';
import styled from 'styled-components';

const Wrapper = styled.div`
  flex-grow: 1;
  margin-right: 10px;
  border-radius: 5px;
  height: 500px;
  overflow: hidden;
  display:flex; 
  align-items:center;
`;

const ImageBox = styled.div`
  background:url('${({image}) => image}') no-repeat center center;
  background-size: cover;
  background-size: 100% 100%;
  width: 100%;
  height: 450px;
  border-radius: 8px;
  isolation: isolate;
  cursor: pointer;
`;

const ItemImage = ({ images, onClick }) => {
  images = Array.isArray(images) ? images : [images];

  return (
    images.length > 0 && (
      <Wrapper className="item-images">
        {images.map((image) => (
          <ImageBox
            image={image}
            key={image}
            className="item-image"
            onClick={() => onClick(image)}
          />
        ))}
      </Wrapper>
    )
  );
};

export default React.memo(ItemImage);
