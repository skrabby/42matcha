export const  getUserInfo = (id: string) => {
    return new Promise((resolve, reject) => {
        fetch(`/api/id${id}`, {
            method: 'get',
        })
            .then((rs) => {
                if (rs.ok || rs.status === 401) {
                    resolve(rs.json());
                } else {
                    reject();
                }
            })
            .catch((err) => {
                reject(err);
            })
    });
}
