import apiRequest from '../../commons/libs/apiRequest';

export default function apiCancel(seq) {
  return new Promise((resolve, reject) => {
    (async () => {
      try {
        const res = await apiRequest(`/myreservation/cancel/${seq}`, 'POST');
        if (res.status >= 200 || res.status < 300) {
          resolve(res.data.data);
          return;
        }

        reject(res.data);
      } catch (err) {
        reject(err);
      }
    })();
  });
}
