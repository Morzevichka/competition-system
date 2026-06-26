import type { CreatePilotRequest, Pilot, UpdatePilotRequest } from "~/types/pilot";
import { usePublicApi } from "./client/usePublicApi"
import { usePrivateApi } from "./client/usePrivateApi";
import type { Page } from "~/types/page";

export const usePilotApi = () => {
    const usePublic = usePublicApi();
    const usePrivate = usePrivateApi();

    return {
        get: (page: number) => usePublic.get<Page<Pilot>>(`/api/pilots?page=${page}`),
        getById: (id: string) => usePublic.get<Pilot>(`/api/pilots/${id}`),

        post: (body: CreatePilotRequest) => usePrivate.post<Pilot>("/api/pilots", body),
        put: (id: string, body: UpdatePilotRequest) => usePrivate.put<Pilot>(`/api/pilots/${id}`, body),
        delete: (id: string) => usePrivate.delete<void>(`/api/pilots/${id}`)
    }
}