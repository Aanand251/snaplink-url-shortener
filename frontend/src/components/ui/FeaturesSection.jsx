import { motion } from "framer-motion";
import {
    BarChart3,
    Link2,
    ShieldCheck,
    Globe,
    MousePointerClick,
    Sparkles,
} from "lucide-react";

const features = [
    {
        icon: Link2,
        title: "Smart URL Shortening",
        description:
            "Generate short, memorable links instantly with support for custom aliases and expiration dates.",
    },
    {
        icon: MousePointerClick,
        title: "Real-Time Click Tracking",
        description:
            "Track every visitor with detailed click history, timestamps and performance insights.",
    },
    {
        icon: BarChart3,
        title: "Powerful Analytics",
        description:
            "Visualize traffic with charts, browser statistics, device information and total engagement.",
    },
    {
        icon: Globe,
        title: "Global Accessibility",
        description:
            "Share links anywhere with lightning-fast redirects and optimized performance.",
    },
    {
        icon: ShieldCheck,
        title: "Secure Authentication",
        description:
            "JWT authentication keeps every link protected and accessible only by its owner.",
    },
    {
        icon: Sparkles,
        title: "Modern Dashboard",
        description:
            "Manage links from one elegant dashboard designed for speed and productivity.",
    },
];

function FeaturesSection() {
    return (
        <section
            id="features"
            className="
                relative
                overflow-hidden
                border-t
                border-white/10
                bg-[#050505]
                py-32
            "
        >
            {/* Background Glow */}

            <div
                className="
                    absolute
                    left-1/2
                    top-24
                    h-[500px]
                    w-[500px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-400/10
                    blur-[180px]
                "
            />

            <div className="relative z-10 mx-auto max-w-7xl px-6">

                {/* Heading */}

                <motion.div
                    initial={{ opacity: 0, y: 40 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    transition={{ duration: .6 }}
                    viewport={{ once: true }}
                    className="mx-auto max-w-3xl text-center"
                >

                    <span
                        className="
                            inline-flex
                            rounded-full
                            border
                            border-cyan-400/20
                            bg-cyan-400/10
                            px-5
                            py-2
                            text-sm
                            font-medium
                            tracking-wide
                            text-cyan-300
                        "
                    >
                        WHY SNAPLINK
                    </span>

                    <h2
                        className="
                            mt-8
                            text-4xl
                            font-black
                            leading-tight
                            tracking-tight
                            text-white
                            md:text-6xl
                        "
                    >
                        Everything you need.

                        <br />

                        <span className="text-cyan-300">
                            Nothing you don't.
                        </span>

                    </h2>

                    <p
                        className="
                            mx-auto
                            mt-8
                            max-w-2xl
                            text-lg
                            leading-8
                            text-zinc-400
                        "
                    >
                        A modern URL shortening platform built for speed,
                        analytics, security and a beautiful developer
                        experience.
                    </p>

                </motion.div>

                {/* Cards */}

                <div className="mt-24 grid gap-8 md:grid-cols-2 xl:grid-cols-3">

                    {features.map(({ icon: Icon, title, description }, index) => (

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
                                delay: index * .08,
                                duration: .5,
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
                                backdrop-blur-3xl
                                transition-all
                                duration-300
                                hover:border-cyan-400/30
                                hover:bg-white/10
                            "
                        >

                            {/* Glow */}

                            <div
                                className="
                                    absolute
                                    left-1/2
                                    top-1/2
                                    h-48
                                    w-48
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

                                <h3
                                    className="
                                        mt-8
                                        text-2xl
                                        font-semibold
                                        text-white
                                    "
                                >
                                    {title}
                                </h3>

                                <p
                                    className="
                                        mt-5
                                        leading-8
                                        text-zinc-400
                                    "
                                >
                                    {description}
                                </p>

                            </div>

                        </motion.div>

                    ))}

                </div>

            </div>

        </section>
    );
}

export default FeaturesSection;