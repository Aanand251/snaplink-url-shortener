import axios from "axios";

const apiClient = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
    timeout: 10000,
});

apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("snaplink_token");

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => Promise.reject(error),
);

apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            localStorage.removeItem("snaplink_token");

            if (
                window.location.pathname !== "/login" &&
                window.location.pathname !== "/register"
            ) {
                window.location.href = "/login";
            }
        }

        return Promise.reject(error);
    },
);

export default apiClient;