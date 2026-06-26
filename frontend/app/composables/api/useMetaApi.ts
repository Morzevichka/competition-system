import type { Meta } from "~/types/meta";
import { usePublicApi } from "./client/usePublicApi";

export const useMetaApi = () => {
    const publicApi = usePublicApi();

    return {
        roles: () => publicApi.get<Meta>("/api/meta/roles")
    }
};