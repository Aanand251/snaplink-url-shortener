import {
    lazy,
    Suspense,
} from "react";

import {
    Navigate,
    Route,
    Routes,
} from "react-router-dom";

import LoadingSpinner from "./components/ui/LoadingSpinner";
import AuthLayout from "./layouts/AuthLayout";
import ProtectedRoute from "./routes/ProtectedRoute";

const LandingPage = lazy(
    () => import("./pages/LandingPage"),
);

const LoginPage = lazy(
    () => import("./pages/LoginPage"),
);

const RegisterPage = lazy(
    () => import("./pages/RegisterPage"),
);

const DashboardPage = lazy(
    () => import("./pages/DashboardPage"),
);

const AnalyticsPage = lazy(
    () => import("./pages/AnalyticsPage"),
);

function RouteLoader() {
    return (
        <main
            className="
                flex
                min-h-screen
                items-center
                justify-center
                bg-[#09090B]
            "
        >
            <LoadingSpinner />
        </main>
    );
}

function App() {
    return (
        <Suspense fallback={<RouteLoader />}>
            <Routes>
                <Route
                    path="/"
                    element={<LandingPage />}
                />

                <Route element={<AuthLayout />}>
                    <Route
                        path="/login"
                        element={<LoginPage />}
                    />

                    <Route
                        path="/register"
                        element={<RegisterPage />}
                    />
                </Route>

                <Route element={<ProtectedRoute />}>
                    <Route
                        path="/dashboard"
                        element={<DashboardPage />}
                    />

                    <Route
                        path="/analytics/:shortCode"
                        element={<AnalyticsPage />}
                    />
                </Route>

                <Route
                    path="*"
                    element={
                        <Navigate
                            to="/"
                            replace
                        />
                    }
                />
            </Routes>
        </Suspense>
    );
}

export default App;