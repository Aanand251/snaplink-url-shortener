import api from "./apiClient";

export async function getMyUrls() {
    const response = await api.get("/api/url/my-urls");

    return response.data;
}

export async function getUrlDashboard(shortCode) {
    const response = await api.get(
        `/api/url/dashboard/${shortCode}`
    );

    return response.data;
}

export async function createShortUrl(payload) {
    const response = await api.post(
        "/api/url/shorten",
        payload
    );

    return response.data;
}

export async function updateUrl(id, payload) {
    const response = await api.put(
        `/api/url/${id}`,
        payload
    );

    return response.data;
}

export async function deleteUrl(id) {
    const response = await api.delete(
        `/api/url/${id}`
    );

    return response.data;
}