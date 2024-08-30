/* eslint-disable no-undef */
import requestData from '../../commons/libs/requestData';

export const apiList = (search) => {
  search = search ?? {};

  const qs = [];

  for (const [k, v] of Object.entries(search)) {
    qs.push(`${k}=${v}`);
  }

  let url = '/myreservation/list';
  if (qs.length > 0) url += `?${qs.join('&')}`; //검색 조건이 있을 때

  return requestData(url);
};

// 상세 조회
export const apiGet = (seq) => requestData(`/myreservation/info/${seq}`);

// 예약된 상세조회
//export const myApiGet = (seq) => requestData(`/myreservation/info/${seq}`);
