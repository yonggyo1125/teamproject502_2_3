import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { ImageBgBox } from '../../../commons/components/ImageBox';
import { FaMapMarkerAlt } from 'react-icons/fa';
import { color } from '../../../styles/color';

const { midGreen, mid_gray, darkGreen } = color;

const ItemBox = ({ item, className }) => {
  const { seq, title, photoUrl, address, description } = item;
  const url = `/recommend/tour/${seq}`;
  return (
    <li className={className}>
      <Link to={url}>
        {photoUrl && (
          <ImageBgBox className="photo" url={photoUrl} width="100%" />
        )}
        <div className="item-content">
          <div className="title">{title}</div>
          <div className="description">{description}</div>
          <div className="address">
            <FaMapMarkerAlt className="icon" />
            {address}
          </div>
        </div>
      </Link>
    </li>
  );
};

const ItemStyledBox = styled(ItemBox)`
  width: 250px;
  height: 400px;
  border-radius: 30px;
  overflow: hidden;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.5);
  border: 2px solid ${midGreen};

  a {
    display: flex;
    flex-direction: column;

    .photo {
      width: 100%;
      height: 200px;
      border-radius: 5px 5px 0px 0px;
      filter: brightness(40%);

      &:hover {
        filter: none;
      }
    }

    .item-content {
      width: 100%;
      word-break: break-all;
      padding: 5px 20px;
      height: 200px;
      display: flex;
      flex-direction: column;
      justify-content: center;

      .title {
        font-size: 20px;
        width: 200px;
        font-weight: bold;
        text-align: center;
        height: 30%;
        margin-bottom: 5px;
        display: flex;
        align-items: center;
        justify-content: center;
        overflow-wrap: break-word;
        color: ${darkGreen};
      }

      .description {
        font-size: 15px;
        width: 100%;
        height: 32%;
        display: flex;
        align-items: center;
        text-align: justify;
        line-height: 20px;
        -webkit-line-clamp: 3;
        display: -webkit-box;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: normal;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
      }

      .address {
        font-size: 1.2em;
        font-weight: bold;
        margin-top: 10px;
        margin-left: 20px;
        color: ${mid_gray};

        .icon {
          color: #ff5e00;
          position: relative;
          top: 3px;
          margin-right: 5px;
        }
      }
    }
   &:hover {
    .photo {
      filter: none;
    }
  }
`;

const shuffleArray = (array) => {
  let shuffled = array.slice(); // 원본 배열을 변경하지 않기 위해 복사
  for (let i = shuffled.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]]; // 요소 교환
  }
  return shuffled;
};

const ItemsBox = ({ items = [] }) => {
  const shuffledItems = shuffleArray(items);
  const limitedItems = shuffledItems.slice(0, 5);

  return (
    <ItemList>
      {limitedItems.map((item) => (
        <ItemStyledBox key={item.seq} item={item} />
      ))}
    </ItemList>
  );
};

const ItemList = styled.ul`
  display: flex;
  justify-content: center;
  gap: 20px;
`;

export default React.memo(ItemsBox);
