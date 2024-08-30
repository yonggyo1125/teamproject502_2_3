import React from 'react';
import { Route, Routes } from 'react-router-dom';
import loadable from '@loadable/component';

const MainLayout = loadable(() => import('../layouts/MainLayout'));

const WritePage = loadable(() => import('../board/pages/Write'));

const UpdatePage = loadable(() => import('../board/pages/Update'));

const ListPage = loadable(() => import('../board/pages/List'));

const ViewPage = loadable(() => import('../board/pages/View'));

const board = () => {
  return (
    <Routes>
      <Route path="/board" element={<MainLayout />}>
        {/* <Route path="qna" element={<QnAPage />} />
        <Route path='notice' element={<NoticePage />} />
        <Route path='review' element={<ReviewPage />} /> */}
        <Route path="write/:bid" element={<WritePage />} />
        <Route path="update/:seq" element={<UpdatePage />} />
        <Route path="list/:bid" element={<ListPage />} />
        <Route path="view/:seq" element={<ViewPage />} />
      </Route>
    </Routes>
  );
};

export default React.memo(board);
