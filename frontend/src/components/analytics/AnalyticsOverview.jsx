import {
    Globe2,
    MonitorSmartphone,
    MousePointerClick,
    TimerReset,
    Webhook,
} from "lucide-react";

import AnalyticsCard from "./AnalyticsCard";

// Future Components
// import TrafficChartCard from "./TrafficChartCard";
// import PieChartCard from "./PieChartCard";
// import BrowserStatsCard from "./BrowserStatsCard";
// import RecentActivityCard from "./RecentActivityCard";
// import TopLinksCard from "./TopLinksCard";

function formatLastClick(lastClickedAt) {

    if (!lastClickedAt) {

        return {
            date: "--",
            time: null,
        };

    }

    const lastClickDate = new Date(lastClickedAt);

    if (Number.isNaN(lastClickDate.getTime())) {

        return {
            date: "--",
            time: null,
        };

    }

    return {

        date: lastClickDate.toLocaleDateString("en-IN", {

            day: "2-digit",
            month: "short",
            year: "numeric",

        }),

        time: lastClickDate.toLocaleTimeString("en-IN", {

            hour: "2-digit",
            minute: "2-digit",

        }),

    };

}

function AnalyticsOverview({ analytics }) {

    const lastClick = formatLastClick(
        analytics.lastClickedAt
    );

    const statistics = [

        {
            title: "Total Clicks",
            value: analytics.clicks,
            icon: MousePointerClick,
        },

        {
            title: "Top Browser",
            value: analytics.topBrowser,
            icon: Webhook,
        },

        {
            title: "Top Device",
            value: analytics.topDevice,
            icon: MonitorSmartphone,
        },

        {
            title: "Top Country",
            value: analytics.topCountry,
            icon: Globe2,
        },

        {
            title: "Last Click",
            value: lastClick.date,
            secondaryValue: lastClick.time,
            icon: TimerReset,
        },

    ];

    return (

        <section className="space-y-10">

            {/* ======================================
                    KPI SECTION
            ======================================= */}

            <section
                className="
                    grid
                    grid-cols-1
                    gap-7
                    md:grid-cols-2
                    xl:grid-cols-5
                "
            >

                {statistics.map((item) => (

                    <AnalyticsCard

                        key={item.title}

                        title={item.title}

                        value={item.value}

                        secondaryValue={item.secondaryValue}

                        icon={item.icon}

                    />

                ))}

            </section>

            {/* ======================================
                    CHARTS
            ======================================= */}

            {/*
            <section className="grid gap-8 xl:grid-cols-5">

                <div className="xl:col-span-3">
                    <TrafficChartCard />
                </div>

                <div className="xl:col-span-2">
                    <PieChartCard />
                </div>

            </section>
            */}

            {/* ======================================
                    INSIGHTS
            ======================================= */}

            {/*
            <section className="grid gap-8 xl:grid-cols-5">

                <div className="xl:col-span-2">
                    <BrowserStatsCard />
                </div>

                <div className="xl:col-span-3">
                    <RecentActivityCard />
                </div>

            </section>
            */}

            {/* ======================================
                    TOP LINKS
            ======================================= */}

            {/*
            <TopLinksCard />
            */}

        </section>

    );

}

export default AnalyticsOverview;