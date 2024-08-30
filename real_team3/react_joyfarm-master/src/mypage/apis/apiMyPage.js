import apiRequest from '../../commons/libs/apiRequest';

export const apiUpdate = (form) =>
  new Promise((resolve, reject) => {
    console.log(form);

    apiRequest('/account/update', 'PUT', form)
      .then((res) => {
        if (![201, 204, 200].includes(res.status)) {
          // 검증 실패

          reject(res.data);
          return;
        }

        resolve(res.data); // 성공
      })
      .catch((err) => {
        console.error(err);
        reject(err);
      });
  });

export const apiPatch = (form) =>
  new Promise((resolve, reject) => {
    console.log(form);

    apiRequest('/account/withdraw', 'PATCH')
      .then((res) => {
        if (![201, 204, 200].includes(res.status)) {
          // 검증 실패

          reject(res.data);
          return;
        }

        resolve(res.data); // 성공
      })
      .catch((err) => {
        console.error(err);
        reject(err);
      });
  });
