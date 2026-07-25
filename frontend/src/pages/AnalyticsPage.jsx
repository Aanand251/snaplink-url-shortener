import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import {
    ResponsiveContainer,
    AreaChart,
    Area,
    XAxis,
    Tooltip,
} from "recharts";

import { getAnalytics } from "../api/analyticsApi.js";

import AnalyticsWorkspace from "../components/analytics/AnalyticsWorkspace";
import AnalyticsHeader from "../components/analytics/AnalyticsHeader";
import TrafficChartCard from "../components/analytics/TrafficChartCard";
import PieChartCard from "../components/analytics/PieChartCard";
import BrowserStatsCard from "../components/analytics/BrowserStatsCard";
import RecentActivityCard from "../components/analytics/RecentActivityCard";

function AnalyticsPage() {

    const { shortCode } = useParams();

    const [analytics, setAnalytics] = useState(null);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    useEffect(() => {

        async function loadAnalytics() {

            try {

                setLoading(true);

                const response = await getAnalytics(shortCode);

                setAnalytics(response);

                setError("");

            } catch (err) {

                console.error(err);

                setError("Unable to load analytics.");

            } finally {

                setLoading(false);

            }

        }

        loadAnalytics();

    }, [shortCode]);

    if (loading) {

        return (

            <main
                className="
                    min-h-screen
                    flex
                    items-center
                    justify-center
                    bg-[#ECEEEF]
                "
            >

                <h2
                    className="
                        text-2xl
                        font-semibold
                        text-[#64707C]
                    "
                >
                    Loading Analytics...
                </h2>

            </main>

        );

    }

    if (error) {

        return (

            <main
                className="
                    min-h-screen
                    flex
                    items-center
                    justify-center
                    bg-[#ECEEEF]
                "
            >

                <h2
                    className="
                        text-xl
                        font-semibold
                        text-red-500
                    "
                >
                    {error}
                </h2>

            </main>

        );

    }

    return (

        <main
            className="
                min-h-screen
                bg-[#ECEEEF]
                px-8
                py-10
            "
        >

            <AnalyticsWorkspace>

                <AnalyticsHeader />

                <div
                    className="
                        mt-10
                        grid
                        gap-8
                        xl:grid-cols-[2fr_1fr]
                    "
                >

                    <TrafficChartCard
                        totalClicks={analytics?.totalClicks ?? 0}
                        todayClicks={analytics?.todayClicks ?? 0}
                        growth="+0%"
                    >

                        <ResponsiveContainer
                            width="100%"
                            height="100%"
                        >

                            <AreaChart
                                data={analytics?.dailyClicks ?? []}
                            >

                                <XAxis
                                    dataKey="date"
                                    axisLine={false}
                                    tickLine={false}
                                />

                                <Tooltip />

                                <Area
                                    type="monotone"
                                    dataKey="clicks"
                                    stroke="#64748B"
                                    fill="#DCE4EB"
                                    strokeWidth={3}
                                />

                            </AreaChart>

                        </ResponsiveContainer>

                    </TrafficChartCard>

                    <PieChartCard
                        totalVisitors={analytics?.totalClicks ?? 0}
                        browserStats={analytics?.browserStats ?? []}
                    />

                </div>

                <div
                    className="
                        mt-8
                        grid
                        gap-8
                        lg:grid-cols-2
                    "
                >

                    <BrowserStatsCard
                        browserStats={
                            analytics?.browserStats ?? []
                        }
                    />

                    <RecentActivityCard
                        activities={
                            analytics?.recentActivities ?? []
                        }
                    />

                </div>

            </AnalyticsWorkspace>

        </main>

    );

}

export default AnalyticsPage;