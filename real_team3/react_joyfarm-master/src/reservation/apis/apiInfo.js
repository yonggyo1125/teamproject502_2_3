import requestData from '../../commons/libs/requestData';

//목록 조회
export const apiList = (search) => {
  search = search ?? {};

  const qs = [];

  for (const [k, v] of Object.entries(search)) {
    qs.push(`${k}=${v}`);
  }

  let url = '/reservation/list';
  if (qs.length > 0) url += `?${qs.join('&')}`; //검색 조건이 있을 때

  return requestData(url);
};

// 상세 조회
export const apiGet = (seq) => requestData(`/reservation/info/${seq}`);

// 예약된 상세조회
export const myApiGet = (seq) => requestData(`/reservation/complete/${seq}`);

// 찜한 목록 조회
export const apiWishlist = (page = 1, limit = 8) => {
  return requestData(`/reservation/wish?page=${page}&limit=${limit}`);
};
