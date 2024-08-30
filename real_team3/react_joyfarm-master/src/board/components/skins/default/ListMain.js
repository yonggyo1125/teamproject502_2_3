import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { color } from '../../../../styles/color';

const { white, midGreen } = color;

const ListMain = (props) => {
  const navigate = useNavigate();
  const { bid } = useParams();

  const handleButtonClick = () => {
    navigate(`/board/write/${bid}`);
  };

  const Wrapper = styled.div`
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
  `;

  const Button = styled.button`
    font-size: 1.3em;
    width: 110px;
    height: 35px;
    background-color: ${midGreen};
    color: ${white};
    border-radius: 5px;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;

    &:hover {
      transform: scale(1.05);
      box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
    }
  `;

  return (
    <Wrapper>
      <Button onClick={handleButtonClick}>글쓰기</Button>
    </Wrapper>
  );
};

export default React.memo(ListMain);
