import { useEffect } from "react";

import {
    Navigate,
    Outlet,
    useLocation,
} from "react-router-dom";

import { useAuth } from "../context/AuthContext";
import { isTokenUsable } from "../utils/jwtUtils";

function ProtectedRoute() {
    const {
        token,
        isAuthenticated,
        logout,
    } = useAuth();

    const location = useLocation();

    const hasValidSession =
        isAuthenticated && isTokenUsable(token);

    useEffect(() => {
        if (token && !hasValidSession) {
            logout();
        }
    }, [
        token,
        hasValidSession,
        logout,
    ]);

    if (!hasValidSession) {
        return (
            <Navigate
                to="/login"
                replace
                state={{
                    from: location.pathname,
                    sessionExpired: Boolean(token),
                }}
            />
        );
    }

    return <Outlet />;
}

export default ProtectedRoute;