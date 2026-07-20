import { motion } from "framer-motion";
import {
    Globe2,
    Link2,
    MousePointerClick,
    TimerReset,
} from "lucide-react";

const stats = [
    {
        icon: Link2,
        value: "1.2M+",
        title: "Links Created",
    },
    {
        icon: MousePointerClick,
        value: "52M+",
        title: "Total Clicks",
    },
    {
        icon: Globe2,
        value: "180+",
        title: "Countries Reached",
    },
    {
        icon: TimerReset,
        value: "99.99%",
        title: "Server Uptime",
    },
];

function StatsSection() {
    return (
        <section
            className="
                relative
                overflow-hidden
                bg-[#050505]
                py-28
            "
        >

            {/* Background Glow */}

            <div
                className="
                    absolute
                    left-1/2
                    top-0
                    h-[450px]
                    w-[450px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-400/10
                    blur-[180px]
                "
            />

            <div className="relative z-10 mx-auto max-w-7xl px-6">

                <div className="grid gap-8 md:grid-cols-2 xl:grid-cols-4">

                    {stats.map(({ icon: Icon, value, title }, index) => (

                        <motion.div
                            key={title}

                            initial={{
                                opacity: 0,
                                y: 40,
                            }}

                            whileInView={{
                                opacity: 1,
                                y: 0,
                            }}

                            transition={{
                                delay: index * 0.08,
                                duration: 0.5,
                            }}

                            viewport={{
                                once: true,
                            }}

                            whileHover={{
                                y: -10,
                            }}

                            className="
                                group
                                relative
                                overflow-hidden
                                rounded-[32px]
                                border
                                border-white/10
                                bg-white/5
                                p-8
                                text-center
                                backdrop-blur-3xl
                                transition-all
                                duration-300
                                hover:border-cyan-400/30
                                hover:bg-white/10
                            "
                        >

                            {/* Hover Glow */}

                            <div
                                className="
                                    absolute
                                    left-1/2
                                    top-1/2
                                    h-44
                                    w-44
                                    -translate-x-1/2
                                    -translate-y-1/2
                                    rounded-full
                                    bg-cyan-400/0
                                    blur-3xl
                                    transition-all
                                    duration-500
                                    group-hover:bg-cyan-400/10
                                "
                            />

                            <div className="relative">

                                <div
                                    className="
                                        mx-auto
                                        flex
                                        h-16
                                        w-16
                                        items-center
                                        justify-center
                                        rounded-2xl
                                        border
                                        border-cyan-400/20
                                        bg-cyan-400/10
                                    "
                                >

                                    <Icon
                                        size={28}
                                        className="text-cyan-300"
                                    />

                                </div>

                                <h2
                                    className="
                                        mt-8
                                        text-5xl
                                        font-black
                                        tracking-tight
                                        text-white
                                    "
                                >
                                    {value}
                                </h2>

                                <p
                                    className="
                                        mt-4
                                        text-base
                                        text-zinc-400
                                    "
                                >
                                    {title}
                                </p>

                            </div>

                        </motion.div>

                    ))}

                </div>

            </div>

        </section>
    );
}

export default StatsSection;