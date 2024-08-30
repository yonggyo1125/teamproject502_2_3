import React, { useState, useCallback } from 'react';
import Itemslist from '../components/ItemsList';
import MyLocationContainer from './MyLocationContainer';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { GrMapLocation } from 'react-icons/gr';
import { color } from '../../../styles/color';

const { midGreen, whiteGreen, whiteGray, mid_gray } = color;

const Container = styled.div`
  position: relative;
`;

const StyledMyLoc = styled.div`
  position: relative;
  z-index: 100;
`;

const StyledList = styled.div`
  position: absolute;
  top: 20px;
  left: 20px;
  z-index: 500;
  background-color: white;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3);
  border-radius: 8px;
  padding: 10px;
  width: 300px;
  max-height: 950px;
  overflow-y: auto; // 스크롤바

  .icon {
    color: ${mid_gray};
  }

  .sub_title {
    background: ${whiteGreen};
    margin-left: 5px;
    font-size: 1.25rem;

    h2 {
      color: ${midGreen};
      margin-bottom: 4px;
    }
  }
`;

const MyLocListContainer = () => {
  const [locations, setLocations] = useState([]);
  const { t } = useTranslation();

  const handleLocationsUpdate = useCallback((newLocations) => {
    setLocations(newLocations);
  }, []);

  return (
    <>
      <Container>
        <StyledMyLoc>
          <MyLocationContainer onLocationsUpdate={handleLocationsUpdate} />
        </StyledMyLoc>
        <StyledList>
          <div className="sub_title">
            <h2>
              <GrMapLocation className="icon" />
              &nbsp;
              {t('주변_여행지_리스트')}
            </h2>
            클릭 시, 상세페이지로 이동합니다.
          </div>
          <Itemslist
            items={locations.map((loc) => ({
              seq: loc.seq,
              title: loc.title,
              address: loc.address,
            }))}
          />
        </StyledList>
      </Container>
    </>
  );
};

export default React.memo(MyLocListContainer);
