import React from 'react';
import { Route, Routes } from 'react-router-dom';
import loadable from '@loadable/component';

const MainLayout = loadable(() => import('../layouts/MainLayout'));

/* 여행 관련 페이지 S - 필요할 때만 로딩하도록 지연로딩 */
const TourPage = loadable(() => import('../recommend/tour/pages/Tour'));
const TourViewPage = loadable(() => import('../recommend/tour/pages/TourView'));
/* 여행 관련 페이지 E */

/* 축제 관련 페이지 S */
const FestivalPage = loadable(() => import('../recommend/Festival/pages/Festival'));
const FestivalViewPage = loadable(() => import('../recommend/Festival/pages/FestivalView'));
/* 축제 관련 페이지 E */

/* 내 위치 주변 여행지 페이지 */
const MylocationPage = loadable(() => import('../recommend/Mylocation/pages/Mylocation'));

const Recommend = () => {
  return (
    <Routes>
      <Route path="/recommend" element={<MainLayout />}>
        <Route path="tour" element={<TourPage />} />
        <Route path="tour/:seq" element={<TourViewPage />}/>
        <Route path="festival" element={<FestivalPage />} />
        <Route path="festival/:seq" element={<FestivalViewPage/>}/>
        <Route path="mylocation" element={<MylocationPage />} />{/* 임시페이지 */}
      </Route>
    </Routes>
  );
};

export default React.memo(Recommend);