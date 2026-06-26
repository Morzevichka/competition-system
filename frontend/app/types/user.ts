export interface Me {
    email: string
    login: string
    first_name: string
    last_name: string
    role: string
}

export interface UpdateMe {
    login: string
    first_name: string
    last_name: string
}

export interface User {
    id: string
    email: string
    login: string
    first_name: string
    last_name: string
    role: string
}

export interface CreateUserRequest {
    email: string
    login: string
    first_name: string
    last_name: string
    role: string
    password: string
}

export interface UpdateUserRequest {
    email: string
    login: string
    first_name: string
    last_name: string
    role: string
}