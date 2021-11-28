export const login = (data: object) => {
    return new Promise((resolve, reject) => {
        fetch('/api/auth', {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
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
