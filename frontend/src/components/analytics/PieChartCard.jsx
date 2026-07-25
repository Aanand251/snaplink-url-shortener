import {
    Globe2,
    MoreHorizontal,
} from "lucide-react";

import {
    ResponsiveContainer,
    PieChart,
    Pie,
    Cell,
    Tooltip,
    Legend,
} from "recharts";

import "./neumorphism.css";

const COLORS = [
    "#3B82F6",
    "#10B981",
    "#F59E0B",
    "#EF4444",
    "#8B5CF6",
    "#06B6D4",
];

function PieChartCard({

                          browserStats = [],

                          totalVisitors = 0,

                      }) {

    return (

        <section
            className="
                neo-card
                neo-hover
                neo-highlight
                relative
                overflow-hidden
                rounded-[36px]
                p-8
                min-h-[420px]
            "
        >

            {/* Header */}

            <div className="relative z-10 flex items-center justify-between">

                <div className="flex items-center gap-4">

                    <div className="neo-icon">

                        <Globe2
                            size={20}
                            className="text-blue-600"
                        />

                    </div>

                    <div>

                        <p className="neo-title">

                            Visitors

                        </p>

                        <p className="neo-subtitle">

                            Browser Distribution

                        </p>

                    </div>

                </div>

                <button className="neo-button h-12 w-12">

                    <MoreHorizontal size={18} />

                </button>

            </div>

            {/* Chart */}

            <div
                className="
                    neo-inset
                    mt-10
                    h-[240px]
                    rounded-[28px]
                    p-4
                "
            >

                {

                    browserStats.length === 0

                        ? (

                            <div
                                className="
                                    flex
                                    h-full
                                    items-center
                                    justify-center
                                    text-lg
                                    font-medium
                                    text-[#64707C]
                                "
                            >

                                No Browser Data

                            </div>

                        )

                        : (

                            <ResponsiveContainer
                                width="100%"
                                height="100%"
                            >

                                <PieChart>

                                    <Pie

                                        data={browserStats}

                                        dataKey="clicks"

                                        nameKey="browser"

                                        outerRadius={80}

                                        label

                                    >

                                        {

                                            browserStats.map((entry, index) => (

                                                <Cell

                                                    key={entry.browser}

                                                    fill={COLORS[index % COLORS.length]}

                                                />

                                            ))

                                        }

                                    </Pie>

                                    <Tooltip />

                                    <Legend />

                                </PieChart>

                            </ResponsiveContainer>

                        )

                }

            </div>

            <div className="neo-divider mt-8" />

            {/* Footer */}

            <div className="mt-6 flex items-center justify-between">

                <div>

                    <p className="neo-secondary">

                        Total Visitors

                    </p>

                    <h3
                        className="
                            mt-2
                            text-3xl
                            font-bold
                            text-[#2F343C]
                        "
                    >

                        {totalVisitors.toLocaleString()}

                    </h3>

                </div>

            </div>

        </section>

    );

}

export default PieChartCard;