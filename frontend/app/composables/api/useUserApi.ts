import type { Page } from "~/types/page";
import type { User, Me, UpdateMe, CreateUserRequest, UpdateUserRequest } from "~/types/user";
import { usePrivateApi } from "./client/usePrivateApi";

export const useUserApi = () => {
    const usePrivate = usePrivateApi();

    return {
        getMe: () => usePrivate.get<Me>(`/api/users/me`),
        putMe: (body: UpdateMe) => usePrivate.put<Me>(`/api/users/me`, body),

        get: (page: number) => usePrivate.get<Page<User>>(`/api/users?page=${page}`),
        getById: (id: string) => usePrivate.get<User>(`/api/users/${id}`),
        post: (body: CreateUserRequest) => usePrivate.post<User>("/api/users", body),
        put: (id: string, body: UpdateUserRequest) => usePrivate.put<User>(`/api/users/${id}`, body),
        delete: (id: string) => usePrivate.delete<void>(`/api/users/${id}`)
    }
}