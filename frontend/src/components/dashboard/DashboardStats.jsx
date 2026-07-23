import {
    Activity,
    Link2,
    MousePointerClick,
    TrendingUp,
} from "lucide-react";

import StatCard from "./StatCard";

function DashboardStats({
                            totalLinks = 0,
                            totalClicks = 0,
                            topLink = null,
                        }) {
    const topLinkValue =
        typeof topLink === "object" && topLink !== null
            ? topLink.shortCode || topLink.originalUrl || "-"
            : topLink || "-";

    const topLinkSubtitle =
        typeof topLink === "object" && topLink !== null
            ? `${topLink.totalClicks ?? 0} Clicks`
            : "Most clicked URL";

    return (
        <section
            className="
                grid
                gap-7

                grid-cols-1

                md:grid-cols-2

                xl:grid-cols-4
            "
        >
            <StatCard
                title="Total Links"
                value={totalLinks}
                subtitle="Short URLs Created"
                icon={Link2}
                iconColor="text-sky-500"
                iconBackground="bg-sky-100"
            />

            <StatCard
                title="Total Clicks"
                value={totalClicks}
                subtitle="Across all links"
                icon={MousePointerClick}
                iconColor="text-violet-500"
                iconBackground="bg-violet-100"
            />

            <StatCard
                title="Top Performing"
                value={topLinkValue}
                subtitle={topLinkSubtitle}
                icon={TrendingUp}
                iconColor="text-emerald-500"
                iconBackground="bg-emerald-100"
            />

            <StatCard
                title="Status"
                value="Active"
                subtitle="System Running"
                icon={Activity}
                iconColor="text-orange-500"
                iconBackground="bg-orange-100"
            />
        </section>
    );
}

export default DashboardStats;