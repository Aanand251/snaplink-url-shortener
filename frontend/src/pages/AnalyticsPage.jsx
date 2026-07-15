import { useEffect, useState } from "react";
import { AlertCircle } from "lucide-react";
import { useParams } from "react-router-dom";

import { getAnalytics } from "../api/analyticsApi";
import AnalyticsHeader from "../components/analytics/AnalyticsHeader";
import AnalyticsOverview from "../components/analytics/AnalyticsOverview";
import LoadingSpinner from "../components/ui/LoadingSpinner";

function AnalyticsPage() {
    const { shortCode } = useParams();

    const [analytics, setAnalytics] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        let active = true;

        async function loadAnalytics() {
            try {
                setLoading(true);
                setError("");

                const data = await getAnalytics(shortCode);

                if (active) {
                    setAnalytics(data);
                }
            } catch (err) {
                console.error(
                    "Failed to load link analytics:",
                    err
                );

                if (active) {
                    setError(
                        err.response?.data?.message ||
                        "Unable to load analytics."
                    );
                }
            } finally {
                if (active) {
                    setLoading(false);
                }
            }
        }

        loadAnalytics();

        return () => {
            active = false;
        };
    }, [shortCode]);

    if (loading) {
        return (
            <main className="flex min-h-screen items-center justify-center bg-[#09090B]">
                <LoadingSpinner />
            </main>
        );
    }

    if (error || !analytics) {
        return (
            <main
                className="
                    flex
                    min-h-screen
                    items-center
                    justify-center
                    bg-[#09090B]
                    px-6
                "
            >
                <div
                    className="
                        max-w-md
                        rounded-3xl
                        border
                        border-red-500/20
                        bg-red-500/10
                        p-8
                        text-center
                        backdrop-blur-xl
                    "
                >
                    <AlertCircle
                        size={32}
                        className="mx-auto text-red-400"
                    />

                    <h1 className="mt-5 text-xl font-semibold text-white">
                        Analytics unavailable
                    </h1>

                    <p className="mt-2 text-sm leading-6 text-zinc-400">
                        {error ||
                            "Analytics data could not be loaded."}
                    </p>
                </div>
            </main>
        );
    }

    return (
        <main
            className="
                relative
                min-h-screen
                overflow-hidden
                bg-[#09090B]
                text-white
            "
        >
            <div
                className="
                    pointer-events-none
                    absolute
                    left-1/2
                    top-0
                    h-[500px]
                    w-[900px]
                    -translate-x-1/2
                    rounded-full
                    bg-violet-700/10
                    blur-[140px]
                "
            />

            <div
                className="
                    pointer-events-none
                    absolute
                    -right-40
                    top-1/3
                    h-[450px]
                    w-[450px]
                    rounded-full
                    bg-purple-900/10
                    blur-[130px]
                "
            />

            <div className="relative z-10 mx-auto max-w-[1500px] px-5 py-10 sm:px-8 lg:px-12">
                <AnalyticsHeader
                    shortCode={analytics.shortCode}
                />

                <AnalyticsOverview
                    analytics={analytics}
                />
            </div>
        </main>
    );
}

export default AnalyticsPage;