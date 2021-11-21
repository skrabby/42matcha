export const HOME: string = '/';
export const LOGIN: string = '/login';
export const PROFILE: string = '/id:profileId';
export const CHATS: string = '/id:profileId/chats';
export const SET_CHATS = (profileId: string) => `/id${profileId}/chats`;
export const CHAT: string = '/id:profileId/chats/:chatId';
export const SET_PROFILE_CHAT = (profileId: string, chatId: string) => `/id${profileId}/chats/${chatId}`;
