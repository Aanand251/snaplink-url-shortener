import {
    Globe2,
    MonitorSmartphone,
    MousePointerClick,
    TimerReset,
    Webhook,
} from "lucide-react";

import AnalyticsCard from "./AnalyticsCard";

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

    return (
        <section
            className="
                grid
                grid-cols-1
                gap-5
                sm:grid-cols-2
                xl:grid-cols-5
            "
        >
            <AnalyticsCard
                title="Total Clicks"
                value={analytics.clicks}
                icon={MousePointerClick}
                bgColor="bg-violet-500/15"
                iconColor="text-violet-400"
            />

            <AnalyticsCard
                title="Top Browser"
                value={analytics.topBrowser}
                icon={Webhook}
                bgColor="bg-sky-500/15"
                iconColor="text-sky-400"
            />

            <AnalyticsCard
                title="Top Device"
                value={analytics.topDevice}
                icon={MonitorSmartphone}
                bgColor="bg-emerald-500/15"
                iconColor="text-emerald-400"
            />

            <AnalyticsCard
                title="Top Country"
                value={analytics.topCountry}
                icon={Globe2}
                bgColor="bg-amber-500/15"
                iconColor="text-amber-400"
            />

            <AnalyticsCard
                title="Last Click"
                value={lastClick.date}
                secondaryValue={lastClick.time}
                icon={TimerReset}
                bgColor="bg-rose-500/15"
                iconColor="text-rose-400"
            />
        </section>
    );
}

export default AnalyticsOverview;