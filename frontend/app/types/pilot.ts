export interface Pilot {
    id: string;
    nickname: string;
    first_name: string;
    last_name: string;
    tg_username: string;
}

export interface CreatePilotRequest {
    nickname: string;
    first_name: string;
    last_name: string;
    tg_username: string;
}

export interface UpdatePilotRequest {
    id: string;
    nickname: string;
    first_name: string;
    last_name: string;
    tg_username: string;
}