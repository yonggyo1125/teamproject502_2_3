import React from 'react';
import { Routes, Route } from 'react-router-dom';
import loadable from '@loadable/component';

const MainLayout = loadable(() => import('../layouts/MainLayout'));

const QnaPage = loadable(() => import('../board/qna/pages/QnaMain'));

const QnA = () => {
    return (
        <Routes>
            <Route path='/' element={<MainLayout />}>
                <Route path="/qna" element={<QnaPage /> } />
            </Route>
        </Routes>
    );
};

export default React.memo(QnA);