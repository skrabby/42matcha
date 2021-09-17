import axios from "axios";
import * as Constants from "./constants";

const httpRequest = async (
    url: string,
    method: ('post' | 'get' | 'patch' | 'put' | 'delete') = 'get',
    data: object | null,
    headers?: object
) => {
    let commonHeaders = {}

    const response = await axios({
        method: method,
        headers: headers ? {...commonHeaders, ...headers} : commonHeaders,
        //url: Constants.PROXY_CORS_URL + url,
        url: url,
        data: data
    })
        .catch((error: any) => {
            console.log(error);
        }
    )
    return response;
}

export default httpRequest;