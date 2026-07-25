import {
    Activity,
    TrendingUp,
} from "lucide-react";

import NeumorphicCard from "./NeumorphicCard";
import "./neumorphism.css";

function TrafficChartCard({

                              totalClicks = 0,

                              todayClicks = 0,

                              growth = "+0%",

                              children,

                          }) {

    return (

        <NeumorphicCard
            className="
                neo-card
                neo-hover
                neo-highlight
                relative
                overflow-hidden
                rounded-[40px]
                p-10
                min-h-[430px]
            "
        >

            {/* Ambient Light */}

            <div
                className="
                    pointer-events-none
                    absolute
                    inset-x-0
                    top-0
                    h-40
                    bg-gradient-to-b
                    from-white/70
                    via-white/25
                    to-transparent
                "
            />

            {/* Right Glow */}

            <div
                className="
                    pointer-events-none
                    absolute
                    -right-36
                    top-20
                    h-72
                    w-72
                    rounded-full
                    opacity-50
                    blur-[90px]
                "
                style={{
                    background:
                        "radial-gradient(circle,rgba(255,255,255,.65),transparent 70%)",
                }}
            />

            {/* Header */}

            <div className="relative z-10 flex items-start justify-between">

                <div>

                    <div className="flex items-center gap-3">

                        <div className="neo-icon">

                            <Activity
                                size={20}
                                className="text-blue-600"
                            />

                        </div>

                        <div>

                            <p className="neo-title">

                                Traffic Overview

                            </p>

                            <p className="neo-subtitle">

                                Real-time click analytics

                            </p>

                        </div>

                    </div>

                </div>

                <div className="neo-pill">

                    <TrendingUp
                        size={18}
                        className="text-emerald-600"
                    />

                    <span
                        className="
                            font-semibold
                            text-emerald-600
                        "
                    >
                        {growth}
                    </span>

                </div>

            </div>

            {/* Total Clicks */}

            <div className="relative z-10 mt-12">

                <h2 className="neo-value">

                    {totalClicks.toLocaleString()}

                </h2>

                <p className="neo-subtitle mt-3">

                    Total Clicks

                </p>

            </div>
            {/* Chart Section */}

            <div
                className="
                    neo-inset
                    relative
                    mt-12
                    h-[180px]
                    overflow-hidden
                    rounded-[30px]
                    p-5
                "
            >

                <div
                    className="
                        absolute
                        inset-0
                        bg-gradient-to-b
                        from-white/40
                        to-transparent
                        pointer-events-none
                    "
                />

                <div
                    className="
                        relative
                        z-10
                        flex
                        h-full
                        items-center
                        justify-center
                        text-[#94A0AE]
                    "
                >

                    {

                        children ??

                        (

                            <span
                                className="
                                    text-base
                                    font-medium
                                "
                            >
                                Timeline Chart
                            </span>

                        )

                    }

                </div>

            </div>

            {/* Divider */}

            <div className="neo-divider mt-8" />

            {/* Bottom */}

            <div
                className="
                    mt-8
                    flex
                    flex-wrap
                    items-center
                    justify-between
                    gap-6
                "
            >

                {/* Today's Clicks */}

                <div>

                    <p className="neo-secondary">

                        Today's Clicks

                    </p>

                    <h3
                        className="
                            mt-2
                            text-4xl
                            font-bold
                            text-[#2F343C]
                        "
                    >

                        {todayClicks.toLocaleString()}

                    </h3>

                </div>

                {/* Action Button */}

                <button
                    className="
                        neo-button
                        px-8
                        py-4
                        font-semibold
                        text-[#46505E]
                    "
                >

                    View Details

                </button>

            </div>

        </NeumorphicCard>

    );

}

export default TrafficChartCard;