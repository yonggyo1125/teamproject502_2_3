import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Line } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

// Chart.js 구성 요소 등록
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

const VisitorInfo = () => {
  const [visitorData, setVisitorData] = useState(null); // GA4 데이터를 저장할 상태
  const [chartData, setChartData] = useState(null); // 차트 데이터를 저장할 상태

  useEffect(() => {
    // 'client_id', 'client_secret', 'refresh_token'을 사용하여 갱신된 'access_token'을 요청한다.
    axios.post('https://accounts.google.com/o/oauth2/token', {
      "client_id": `${process.env.REACT_APP_OAUTH_CLIENT_ID}`,
      "client_secret": `${process.env.REACT_APP_OAUTH_CLIENT_SECRET}`,
      "refresh_token": `${process.env.REACT_APP_OAUTH_REFRESH_TOKEN}`,
      "grant_type": "refresh_token"
    })
    .then((response) => {
      // 정상적으로 'access_token'을 받았다면, 기본 보고서(runReport)를 호출하는 요청을 보낸다.
      axios.post(`https://analyticsdata.googleapis.com/v1beta/properties/${process.env.REACT_APP_GA4_PROPERTY_ID}:runReport`, {
        "dimensions": [{ "name": "date" }],
        "metrics": [
          { "name": "activeUsers" },
          { "name": "screenPageViews" },
          { "name": "sessions" }
        ],
        "dateRanges": [{ "startDate": "2024-08-01", "endDate": "today" }],
        "keepEmptyRows": true,
      },
      {
        headers: {
          'Authorization': `Bearer ${response.data.access_token}`
        }
      })
      .then((response) => {
        const result = response.data;

        // 차트에 필요한 데이터를 구성합니다.
        const labels = result.rows.map(row => row.dimensionValues[0].value); // 날짜 값
        const activeUsersData = result.rows.map(row => parseInt(row.metricValues[0].value, 10)); // activeUsers 데이터
        const pageViewsData = result.rows.map(row => parseInt(row.metricValues[1].value, 10)); // screenPageViews 데이터
        const sessionsData = result.rows.map(row => parseInt(row.metricValues[2].value, 10)); // sessions 데이터

        // 차트 데이터를 설정합니다.
        setChartData({
          labels,
          datasets: [
            {
              label: 'Active Users',
              data: activeUsersData,
              borderColor: 'rgba(75, 192, 192, 1)',
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              fill: true,
            },
            {
              label: 'Page Views',
              data: pageViewsData,
              borderColor: 'rgba(153, 102, 255, 1)',
              backgroundColor: 'rgba(153, 102, 255, 0.2)',
              fill: true,
            },
            {
              label: 'Sessions',
              data: sessionsData,
              borderColor: 'rgba(255, 159, 64, 1)',
              backgroundColor: 'rgba(255, 159, 64, 0.2)',
              fill: true,
            },
          ],
        });
      })
      .catch((error) => {
        console.error('[REPORT ERROR] ', error);
      });
    })
    .catch((error) => {
      console.error('[TOKEN ERROR] ', error);
    });
  }, []);

  return (
    <div>
      <h1>Visitor Information</h1>
      {chartData ? (
        // 차트를 렌더링합니다.
        <Line
          data={chartData}
          options={{
            responsive: true,
            plugins: {
              legend: {
                position: 'top',
              },
              title: {
                display: true,
                text: 'Visitor Metrics Over Time',
              },
            },
          }}
        />
      ) : (
        <p>Loading chart...</p> // 차트 데이터가 로드되기 전에는 로딩 메시지를 표시합니다.
      )}
    </div>
  );
};

export default VisitorInfo;
