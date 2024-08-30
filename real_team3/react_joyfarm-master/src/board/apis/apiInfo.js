import requestData from '../../commons/libs/requestData';

// 찜한 목록 조회
export const apiWishlist = (page = 1, limit = 8) => {
  return requestData(`/board/wish?page=${page}&limit=${limit}`);
};
