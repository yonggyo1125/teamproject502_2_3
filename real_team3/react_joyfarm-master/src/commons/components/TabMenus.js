import React from 'react';
import { NavLink } from 'react-router-dom';
import classNames from 'classnames';
import styled from 'styled-components';
import { color } from '../../styles/color';
import fontSize from '../../styles/fontSize';

const { midGreen, line_gray, dark, white } = color;
const { medium } = fontSize;

const Wrapper = styled.nav`
  padding: 10px 0;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 55px;
  margin-bottom: 30px;

  a {
    background: ${white};
    width: 130px;
    height: 50px;
    color: ${dark};
    padding: 0 25px;
    border-radius: 5px;
    font-size: ${medium};
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
    border: 1px solid ${line_gray};
  }
  a:hover,
  a.on {
    background: ${midGreen};
    color: ${white};
    border: none;
    transform: scale(1.05);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
  }
  a + a {
    margin-left: 20px;
  }
`;

const TabMenus = ({ items }) => {
  return (
    items &&
    items.length > 0 && (
      <Wrapper>
        {items.map(({ name, link }) => (
          <NavLink
            to={link}
            key={link}
            className={({ isActive }) => classNames({ on: isActive })}
          >
            {name}
          </NavLink>
        ))}
      </Wrapper>
    )
  );
};

export default React.memo(TabMenus);
