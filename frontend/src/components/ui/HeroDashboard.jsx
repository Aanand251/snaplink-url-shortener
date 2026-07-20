import { motion } from "framer-motion";

import FloatingStats from "./FloatingStats";

import DashboardShell from "../hero/dashboard/DashboardShell";
import DashboardBackground from "../hero/dashboard/DashboardBackground";
import BrowserHeader from "../hero/dashboard/BrowserHeader";
import StatsCards from "../hero/dashboard/StatsCards";
import UrlPreviewCard from "../hero/dashboard/UrlPreviewCard";
import AnalyticsChart from "../hero/dashboard/AnalyticsChart";
import QrCard from "../hero/dashboard/QrCard";
import ActivityTimeline from "../hero/dashboard/ActivityTimeline";
import QuickActions from "../hero/dashboard/QuickActions";

function HeroDashboard() {
    return (
        <motion.div
            initial={{
                opacity: 0,
                y: 80,
                scale: 0.96,
            }}
            animate={{
                opacity: 1,
                y: 0,
                scale: 1,
            }}
            transition={{
                duration: 0.55,
                ease: "easeOut",
            }}
            className="
                relative
                mx-auto
                mt-24
                w-full
                max-w-7xl
                px-6
                [perspective:2200px]
            "
        >
            <FloatingStats />

            <DashboardShell>

                <DashboardBackground />

                {/* Glass Window */}

                <div
                    className="
                        relative
                        m-5
                        overflow-hidden
                        rounded-[34px]
                        border
                        border-white/5
                        bg-[#090909]/80
                        p-8
                        backdrop-blur-3xl
                        shadow-[0_40px_120px_rgba(0,0,0,0.45)]
                    "
                >

                    {/* Ambient Reflection */}

                    <div
                        className="
                            pointer-events-none
                            absolute
                            inset-0
                            bg-gradient-to-br
                            from-white/5
                            via-transparent
                            to-cyan-300/5
                        "
                    />

                    <BrowserHeader />

                    <div className="relative mt-8">

                        <StatsCards />

                    </div>

                    <div
                        className="
                            relative
                            mt-8
                            grid
                            grid-cols-12
                            gap-6
                        "
                    >

                        <div className="col-span-12 lg:col-span-7">

                            <AnalyticsChart />

                        </div>

                        <div
                            className="
                                col-span-12
                                space-y-6
                                lg:col-span-5
                            "
                        >

                            <UrlPreviewCard />

                            <QrCard />

                        </div>

                    </div>

                    <div
                        className="
                            relative
                            mt-6
                            grid
                            grid-cols-12
                            gap-6
                        "
                    >

                        <div className="col-span-12 lg:col-span-7">

                            <ActivityTimeline />

                        </div>

                        <div className="col-span-12 lg:col-span-5">

                            <QuickActions />

                        </div>

                    </div>

                </div>

            </DashboardShell>

        </motion.div>
    );
}

export default HeroDashboard;