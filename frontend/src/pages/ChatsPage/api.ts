export const  getUserChats = () => {
    return new Promise((resolve, reject) => {
        fetch(`/api/findAllChats`, {
            method: 'get',
            headers: {
                'token': localStorage.getItem("token") || ''
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
