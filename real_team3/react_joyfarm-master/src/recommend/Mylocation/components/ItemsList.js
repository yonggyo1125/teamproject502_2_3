// src/components/ItemsList.js
import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { FaMapMarkerAlt } from 'react-icons/fa';
import { color } from '../../../styles/color';

const { lightGreen } = color;

const ItemListStyled = styled.div`
  margin-left: 5px;

  .icon {
    color: #ff5e00;
  }

  .item {
    padding: 4px 0;
    margin: 2px 2px 2px 2px;
    border-bottom: 1px solid #e6e6e6;

    .title {
      font-size: 18px;
      font-weight: bold;
    }

    .address {
      font-size: 14px;
      color: #666;
      margin-top: 5px;
    }

    .map-link {
      margin-top: 10px;
      color: blue;
      text-decoration: underline;
    }
  }

  .item:hover {
    background: ${lightGreen};
  }
`;

const ItemsList = ({ items }) => {
  return (
    <ItemListStyled>
      {items.map((item) => (
        <div key={item.seq} className="item">
          <Link to={`/recommend/tour/${item.seq}`}>
            <div className="title">{item.title}</div>
            <div className="address">
              <FaMapMarkerAlt className="icon" /> {item.address}
            </div>
          </Link>
        </div>
      ))}
    </ItemListStyled>
  );
};

export default ItemsList;
