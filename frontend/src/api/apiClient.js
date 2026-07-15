import axios from "axios";

import { isTokenUsable } from "../utils/jwtUtils";

const TOKEN_KEY = "snaplink_token";

const apiClient = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
    timeout: 10000,
});

function clearSession() {
    localStorage.removeItem(TOKEN_KEY);
}

function redirectToLogin() {
    const currentPath = window.location.pathname;

    if (
        currentPath !== "/login" &&
        currentPath !== "/register"
    ) {
        window.location.replace("/login");
    }
}

apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem(TOKEN_KEY);

        if (token && !isTokenUsable(token)) {
            clearSession();
            redirectToLogin();

            return Promise.reject(
                new axios.Cancel(
                    "Authentication token expired.",
                ),
            );
        }

        if (token) {
            config.headers.Authorization =
                `Bearer ${token}`;
        }

        return config;
    },
    (error) => Promise.reject(error),
);

apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        const status = error.response?.status;

        if (status === 401) {
            clearSession();
            redirectToLogin();
        }

        return Promise.reject(error);
    },
);

export default apiClient;