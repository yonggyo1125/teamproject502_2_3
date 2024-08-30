import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { HelmetProvider } from 'react-helmet-async';
import { BrowserRouter } from 'react-router-dom';
import ErrorPage from './commons/pages/Error';
import { UserInfoProvider } from './member/modules/UserInfoContext';
import { CommonProvider } from './commons/modules/CommonContext';
import { WishListProvider } from './commons/contexts/WishListContext';
import 'react-calendar/dist/Calendar.css'; //calendar 기본 스타일 시트 전역 적용
import 'react-datepicker/dist/react-datepicker.css'; //datepicker 스타일 시트

import './i18n';
import ScrollTop from './commons/components/ScrollTop';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <WishListProvider>
      <UserInfoProvider>
        <CommonProvider>
          <ErrorPage>
            <HelmetProvider>
              <BrowserRouter>
                <ScrollTop>
                  <App />
                </ScrollTop>
              </BrowserRouter>
            </HelmetProvider>
          </ErrorPage>
        </CommonProvider>
      </UserInfoProvider>
    </WishListProvider>
  </React.StrictMode>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
