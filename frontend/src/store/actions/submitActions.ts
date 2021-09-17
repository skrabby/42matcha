import * as Interfaces from "./interfaces";
import httpRequest from "../../utils/httpRequest";
import { URL } from "../../utils/constants";

export const submitLogin = async (data: Interfaces.ILoginSubmit) => {
    const response = await httpRequest(`${URL.auth}/login`, 'post', data);
    return response;
}

export const submitRegister = (data: Interfaces.IRegisterSubmit) => {

}

export const submitForgotPassword = (data: Interfaces.ILoginSubmit) => {

}
