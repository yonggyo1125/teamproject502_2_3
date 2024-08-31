import React, { useState, useCallback } from 'react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import areas from '../libs/areas';
const { sido, sigungu } = areas;

const Wrapper = styled.div``;

const SelectLocation = ({ selected }) => {
  const { t } = useTranslation();
  const [sigunguArea, setSigunguArea] = useState([]);
  const [_selected, setSelected] = useState({
    sido: selected?.sido ?? '',
    sigungu: selected?.sigungu ?? '',
  });

  const onChange = useCallback((e) => {
    const name = e.target.name;
    const value = e.target.value;
    setSelected((selected) => ({ ...selected, [name]: value }));
    if (name === 'sido') {
      setSigunguArea(sigungu[name]);
    }
  }, []);
  return (
    <Wrapper>
      <select name="sido" value={_selected?.sido} onChange={onChange}>
        <option value="">{t('시도_선택')}</option>
        {sido.map((s) => (
          <option key={s} value={s}>
            {s}
          </option>
        ))}
      </select>
      <select name="sigungu" value={_selected?.sigungu} onChange={onChange}>
        <option value="">{t('시군구_선택')}</option>
        {sigunguArea &&
          sigunguArea.length > 0 &&
          sigunguArea.map((area) => (
            <option key={area} value={area}>
              {area}
            </option>
          ))}
      </select>
    </Wrapper>
  );
};

export default React.memo(SelectLocation);
