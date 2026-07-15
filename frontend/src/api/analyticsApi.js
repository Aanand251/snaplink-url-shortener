import api from "./apiClient";

export async function getAnalytics(shortCode) {
    const response = await api.get(
        `/analytics/${shortCode}`
    );

    return response.data;
}