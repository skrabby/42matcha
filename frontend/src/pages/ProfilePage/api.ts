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

export const setLike = (id: string) => {
    return new Promise((resolve, reject) => {
        fetch(`/api/setLike/${id}`, {
            method: 'post',
            headers: {
                token: localStorage.getItem("token") || ''
            }
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
