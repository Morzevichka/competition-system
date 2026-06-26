import { usePrivateApi } from "./client/usePrivateApi";
import type { Page } from "~/types/page";
import type { Car, CreateCarRequest, UpdateCarRequest } from "~/types/car";

export const useCarApi = () => {
    const usePrivate = usePrivateApi();

    return {
        get: (page: number) => usePrivate.get<Page<Car>>(`/api/cars?page=${page}`),
        getById: (id: string) => usePrivate.get<Car>(`/api/cars/${id}`),

        post: (body: CreateCarRequest) => usePrivate.post<Car>("/api/cars", body),
        put: (id: string, body: UpdateCarRequest) => usePrivate.put<Car>(`/api/cars/${id}`, body),
        delete: (id: string) => usePrivate.delete<void>(`/api/cars/${id}`)
    }
}