import React from 'react';
import { Routes, Route } from 'react-router-dom';
import loadable from '@loadable/component';

const MainLayout = loadable(() => import('../layouts/MainLayout'));

/* 예약현황 관련 페이지 S */
const MyReserveList = loadable(() =>
  import('../my_reservation/pages/MyReserveList'),
);
const MyReserveView = loadable(() =>
  import('../my_reservation/pages/MyReserveView'),
);
const Cancel = loadable(() => import('../my_reservation/pages/Cancel'));
/* 예약현황 관련 페이지 E */

const Reservation = () => {
  return (
    <Routes>
      <Route path="/myreservation" element={<MainLayout />}>
        <Route path="list" element={<MyReserveList />} />
        <Route path="info/:seq" element={<MyReserveView />} />
        <Route path="cancel/:seq" element={<Cancel />} />
      </Route>
    </Routes>
  );
};

export default React.memo(Reservation);
