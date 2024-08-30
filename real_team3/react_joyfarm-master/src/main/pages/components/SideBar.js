

import React from 'react';
import styled from 'styled-components';
import { color } from '../../../styles/color';

const { darkGreen, white, dark, mid_gray, midGreen } = color;

const SidebarContainer = styled.div`
  position: fixed;
  top: 30%;
  right: 50px;
  width: 130px;
  background: ${white};
  padding: 20px;
  box-shadow: -2px 2px 12px rgba(0, 0, 0, 0.3);
  z-index: 1000;
  text-align: center;
  border-radius: 20px;
  font-size: 1.2em;
  font-weight: bold;
`;

const SidebarLink = styled.a`
  display: block;
  margin: 10px 0;
  padding: 10px;
  background: ${white};
  border-radius: 10px;
  text-decoration: none;
  color: #333;
  cursor: pointer;
  &:hover {
    background: #e1e1e1;
  }
`;

const Sidebar = ({ onLinkClick }) => (
  <SidebarContainer>
    <SidebarLink onClick={() => onLinkClick('top')}>메 뉴</SidebarLink>
    <SidebarLink onClick={() => onLinkClick('location')}>지 역</SidebarLink>
    <SidebarLink onClick={() => onLinkClick('recommend')}>추 천</SidebarLink>
    <SidebarLink onClick={() => onLinkClick('review')}>후 기</SidebarLink>
    <SidebarLink onClick={() => onLinkClick('board')}>공 지</SidebarLink>
  </SidebarContainer>
);

export default Sidebar;