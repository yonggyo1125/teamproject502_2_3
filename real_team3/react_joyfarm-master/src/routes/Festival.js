import React from 'react';
import { Routes, Route } from 'react-router-dom';
import loadable from '@loadable/component';

const MainLayout = loadable(() => import('../layouts/MainLayout'));

const Festival = () => {
  return <Routes></Routes>;
};

export default React.memo(Festival);
