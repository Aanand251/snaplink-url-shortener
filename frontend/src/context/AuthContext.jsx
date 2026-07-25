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
        localStorage.removeItem("snaplink_user");
        return null;
    }

    return storedToken;
}

function getStoredUser() {
    const storedToken = localStorage.getItem("snaplink_token");

    if (!isTokenUsable(storedToken)) {
        localStorage.removeItem("snaplink_user");
        return null;
    }

    const storedUser = localStorage.getItem("snaplink_user");

    if (!storedUser) {
        return null;
    }

    try {
        return JSON.parse(storedUser);
    } catch {
        localStorage.removeItem("snaplink_user");
        return null;
    }
}

export function AuthProvider({ children }) {
    const [token, setToken] = useState(getStoredToken);

    const [user, setUser] = useState(getStoredUser);

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

        const loggedInUser = {
            id: response.data.userId,
            name: response.data.name,
            email: response.data.email,
        };

        localStorage.setItem(
            "snaplink_token",
            authToken,
        );

        localStorage.setItem(
            "snaplink_user",
            JSON.stringify(loggedInUser),
        );

        setToken(authToken);
        setUser(loggedInUser);

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
        localStorage.removeItem("snaplink_user");

        setToken(null);
        setUser(null);
    }, []);

    const value = useMemo(
        () => ({
            token,
            user,
            isAuthenticated: isTokenUsable(token),
            login,
            register,
            logout,
        }),
        [token, user, login, register, logout],
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