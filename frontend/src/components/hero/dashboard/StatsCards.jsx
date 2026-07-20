import {
    Activity,
    Link2,
    MousePointerClick,
} from "lucide-react";

import StatCard from "./StatCard";

function StatsCards() {

    return (

        <div className="grid gap-6 md:grid-cols-3">

            <StatCard
                title="Total Links"
                value="1,248"
                icon={Link2}
                color="text-cyan-300"
            />

            <StatCard
                title="Total Clicks"
                value="82.4K"
                icon={MousePointerClick}
                color="text-white"
            />

            <StatCard
                title="Active Links"
                value="973"
                icon={Activity}
                color="text-emerald-300"
            />

        </div>

    );

}

export default StatsCards;