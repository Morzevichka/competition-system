import { useApiClient } from "./useApiClient";
import { useHttpClient } from "./useHttpClient";

export const useAuthApi = () => {
  const apiClient = useApiClient();
  const httpClient = useHttpClient();

  return {
    login: (body: any) => httpClient("/api/auth/login", { method: "POST", body }),
    register: (body: any) => httpClient("/api/auth/register", { method: "POST", body }),
    me: () => apiClient.request("/api/auth/me"),
    logout: () => httpClient("/api/auth/logout", { method: "POST" }),
    refresh: () => httpClient("/api/auth/refresh"),
  };
};