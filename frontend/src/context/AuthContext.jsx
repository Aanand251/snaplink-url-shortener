import {
    createContext,
    useCallback,
    useContext,
    useMemo,
    useState,
} from "react";

import apiClient from "../api/apiClient";
import { isTokenUsable } from "../utils/jwtUtils";

const AuthContext = createContext(null);

function getStoredToken() {
    const storedToken = localStorage.getItem("snaplink_token");

    if (!isTokenUsable(storedToken)) {
        localStorage.removeItem("snaplink_token");
        return null;
    }

    return storedToken;
}

export function AuthProvider({ children }) {
    const [token, setToken] = useState(getStoredToken);

    const login = useCallback(async (email, password) => {
        const response = await apiClient.post(
            "/api/auth/login",
            {
                email,
                password,
            },
        );

        const authToken = response.data.token;

        if (!isTokenUsable(authToken)) {
            throw new Error(
                "Authentication server returned an invalid token.",
            );
        }

        localStorage.setItem(
            "snaplink_token",
            authToken,
        );

        setToken(authToken);

        return response.data;
    }, []);

    const register = useCallback(
        async (name, email, password) => {
            const response = await apiClient.post(
                "/api/auth/register",
                {
                    name,
                    email,
                    password,
                },
            );

            return response.data;
        },
        [],
    );

    const logout = useCallback(() => {
        localStorage.removeItem("snaplink_token");
        setToken(null);
    }, []);

    const value = useMemo(
        () => ({
            token,
            isAuthenticated: isTokenUsable(token),
            login,
            register,
            logout,
        }),
        [token, login, register, logout],
    );

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error(
            "useAuth must be used inside AuthProvider",
        );
    }

    return context;
}