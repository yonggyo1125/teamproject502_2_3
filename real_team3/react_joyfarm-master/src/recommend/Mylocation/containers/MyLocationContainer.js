/* eslint-disable no-undef */
import React, { useEffect, useState } from 'react';
import { apiList } from '../apis/apiInfo';
import Loading from '../../../commons/components/Loading';
import KakaoMap from '../../../map/KakaoMap';

const MyLocationContainer = ({ onLocationsUpdate }) => {
  const [search, setSearch] = useState({
    sido: '',
    sigungu: '',
    limit: 100000,
  });
  const [center, setCenter] = useState([]); // 지도 중심 좌표(현재 위치의 위도, 경도) - 사용자의 현재 위치 기반으로 설정
  const [locations, setLocations] = useState([]); // 검색된 위치들의 위도, 경도를 저장하는 배열 -> 마커 표기할 위도, 경도 정보

  /* 현재 위치의 시도, 시군구 찾기 S */
  useEffect(() => {
    const geocoder = new kakao.maps.services.Geocoder();
    //Geocoder - 특정 좌표를 시도, 시군구 등의 행정구역 정보로 변환하는 작업

    navigator.geolocation.getCurrentPosition((pos) => {
      /* 현재 위치 설정
      const { latitude, longitude } = pos.coords; 
      setCenter({ lat: latitude, lng: longitude }); */

      const [latitude, longitude] = [37.7271753, 126.5122578];
      setCenter({ lat: 37.7271753, lng: 126.5122578 });

      geocoder.coord2RegionCode(longitude, latitude, (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
          for (const r of result) {
            if (r.region_type === 'H') {
              setSearch((search) => ({
                ...search,
                //sido: r.region_1depth_name,
                //sigungu: r.region_2depth_name,
                //sido: r.region_1depth_name, // 현재 시도로 데이터 검색
                sido: '인천',
                //sigungu: r.region_2depth_name, // 현재 시군구로 데이터 검색
                sigungu: '강화군',
              }));
              break;
            }
          }
        }
      });
    });
  }, [setSearch, setCenter]);
  /* 현재 위치의 시도, 시군구 찾기 E */

  useEffect(() => {
    (async () => {
      try {
        if (!search.sido?.trim()) {
          return;
        }

        const res = await apiList(search);

        /* 마커 표기 좌표 가공 처리 S */
        if (!res?.items || res?.items?.length === 0) {
          return;
        }

        const _locations = res.items
          .filter((d) => d.latitude && d.longitude)
          .map((d) => ({
            lat: d.latitude,
            lng: d.longitude,
          }));

        setLocations(_locations);

        /* 마커 표기 좌표 가공 처리 E */
      } catch (err) {
        console.err(err);
      }
    })();
  }, [search]);

  //마커 표기
  useEffect(() => {
    (async () => {
      try {
        if (!search.sido?.trim()) {
          return;
        }
        const res = await apiList(search);

        /* 마커 표기 좌표 가공 처리 S */
        if (!res?.items || res?.items?.length === 0) {
          return;
        }

        //const url = `https://map.kakao.com/link/map/${d.latitude}, ${d.longitude}`

        const _locations = res.items
          .filter((d) => d.latitude && d.longitude)
          .map((d) => ({
            lat: d.latitude,
            lng: d.longitude,
            seq: d.seq,
            title: d.title,
            address: d.address,
            info: {
              content: `<div style="padding:8px; font-size: 1.4rem; font-weight: bold;">${d.title}<br><a href="https://map.kakao.com/link/map/${d.title}, ${d.latitude}, ${d.longitude}" target="_blank" style="color:blue">카카오맵 길찾기</a></div>`, // 표시할 정보
              clickable: true, // 클릭 시 인포윈도우 표시
              removable: true, // 인포윈도우 닫기 버튼 표시
            },
          }));

        if (onLocationsUpdate) {
          onLocationsUpdate(_locations); // 부모 컴포넌트에 위치 정보 전달
        }

        setLocations(_locations);
        /* 마커 표기 좌표 가공 처리 E */
      } catch (err) {
        console.err(err);
      }
    })();
  }, [search, onLocationsUpdate]);

  if (center?.length === 0 || locations?.length === 0) {
    return <Loading />;
  }

  return (
    <KakaoMap center={center} marker={locations} zoom={5} height={'1000px'} />
  );
};

export default React.memo(MyLocationContainer);
