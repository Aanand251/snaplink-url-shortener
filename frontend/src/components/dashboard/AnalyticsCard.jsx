import { BarChart3 } from "lucide-react";
import {
    ResponsiveContainer,
    BarChart,
    Bar,
    CartesianGrid,
    Tooltip,
    XAxis,
    YAxis,
} from "recharts";

import ClayCard from "./ClayCard";
import EmptyState from "./EmptyState";

function AnalyticsCard({ chartData }) {
    return (
        <ClayCard
            className="
                relative
                overflow-hidden

                p-8

                rounded-[38px]
            "
        >
            {/* Decorative Background */}

            <div
                className="
                    absolute
                    -right-20
                    -top-20

                    h-72
                    w-72

                    rounded-full

                    bg-sky-100/60

                    blur-3xl
                "
            />

            <div
                className="
                    absolute

                    bottom-0
                    left-0

                    h-48
                    w-48

                    rounded-full

                    bg-blue-50

                    blur-3xl
                "
            />

            <div className="relative z-10">
                {/* Header */}

                <div className="flex items-center justify-between">
                    <div>
                        <span
                            className="
                                inline-flex

                                rounded-full

                                bg-sky-100

                                px-4
                                py-2

                                text-xs
                                font-semibold

                                uppercase
                                tracking-[0.18em]

                                text-sky-600
                            "
                        >
                            Analytics
                        </span>

                        <h2
                            className="
                                mt-5

                                text-3xl
                                font-bold

                                text-slate-800
                            "
                        >
                            Click Performance
                        </h2>

                        <p
                            className="
                                mt-2

                                text-slate-500
                            "
                        >
                            Track how your shortened
                            links perform.
                        </p>
                    </div>

                    <div
                        className="
                            flex

                            h-16
                            w-16

                            items-center
                            justify-center

                            rounded-[22px]

                            bg-sky-100

                            shadow-[6px_6px_18px_rgba(163,177,198,.16),-6px_-6px_18px_rgba(255,255,255,.95)]
                        "
                    >
                        <BarChart3
                            className="text-sky-600"
                            size={30}
                        />
                    </div>
                </div>

                {/* Empty */}

                {chartData.length === 0 ? (
                    <div className="mt-10">
                        <EmptyState
                            icon={BarChart3}
                            title="No Analytics Yet"
                            description="Create your first shortened URL to start collecting analytics."
                        />
                    </div>
                ) : (
                    <div className="mt-10 h-[360px]">
                        <ResponsiveContainer
                            width="100%"
                            height="100%"
                        >
                            <BarChart
                                data={chartData}
                                margin={{
                                    top: 12,
                                    right: 12,
                                    left: -18,
                                    bottom: 0,
                                }}
                            >
                                <CartesianGrid
                                    vertical={false}
                                    stroke="#E7EEF8"
                                    strokeDasharray="5 5"
                                />

                                <XAxis
                                    dataKey="name"
                                    axisLine={false}
                                    tickLine={false}
                                    tick={{
                                        fill: "#64748b",
                                        fontSize: 12,
                                    }}
                                />

                                <YAxis
                                    allowDecimals={false}
                                    axisLine={false}
                                    tickLine={false}
                                    tick={{
                                        fill: "#64748b",
                                        fontSize: 12,
                                    }}
                                />

                                <Tooltip
                                    cursor={{
                                        fill:
                                            "rgba(14,165,233,.08)",
                                    }}
                                    contentStyle={{
                                        borderRadius:
                                            "20px",
                                        border:
                                            "1px solid #e2e8f0",
                                        background:
                                            "#ffffff",
                                        boxShadow:
                                            "0 12px 30px rgba(148,163,184,.20)",
                                    }}
                                />

                                <Bar
                                    dataKey="clicks"
                                    radius={[
                                        12,
                                        12,
                                        0,
                                        0,
                                    ]}
                                    fill="#38bdf8"
                                    maxBarSize={58}
                                />
                            </BarChart>
                        </ResponsiveContainer>
                    </div>
                )}
            </div>
        </ClayCard>
    );
}

export default AnalyticsCard;