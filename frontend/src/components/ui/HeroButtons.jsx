import { motion } from "framer-motion";
import { ArrowRight, Play } from "lucide-react";

function HeroButtons() {
    return (
        <motion.div
            initial={{
                opacity: 0,
                y: 25,
            }}
            animate={{
                opacity: 1,
                y: 0,
            }}
            transition={{
                delay: 0.6,
                duration: 0.8,
            }}
            className="
                mt-12
                flex
                flex-wrap
                items-center
                justify-center
                gap-5
            "
        >

            {/* Primary Button */}

            <motion.button
                whileHover={{
                    scale: 1.04,
                    y: -2,
                }}
                whileTap={{
                    scale: 0.97,
                }}
                transition={{
                    type: "spring",
                    stiffness: 300,
                    damping: 20,
                }}
                className="
                    group
                    relative
                    overflow-hidden
                    rounded-2xl
                    border
                    border-cyan-300/30
                    bg-cyan-300
                    px-8
                    py-4
                    font-semibold
                    text-black
                    shadow-[0_20px_60px_rgba(103,232,249,0.25)]
                "
            >

                {/* Hover Glow */}

                <div
                    className="
                        absolute
                        inset-0
                        bg-gradient-to-r
                        from-white/30
                        via-transparent
                        to-white/20
                        opacity-0
                        transition-opacity
                        duration-300
                        group-hover:opacity-100
                    "
                />

                <span className="relative flex items-center gap-2">

                    Start Free

                    <ArrowRight
                        size={18}
                        className="
                            transition-transform
                            duration-300
                            group-hover:translate-x-1
                        "
                    />

                </span>

            </motion.button>

            {/* Secondary Button */}

            <motion.button
                whileHover={{
                    scale: 1.03,
                    y: -2,
                }}
                whileTap={{
                    scale: 0.97,
                }}
                transition={{
                    type: "spring",
                    stiffness: 300,
                    damping: 20,
                }}
                className="
                    group
                    flex
                    items-center
                    gap-3
                    rounded-2xl
                    border
                    border-white/15
                    bg-white/5
                    px-8
                    py-4
                    font-semibold
                    text-white
                    backdrop-blur-3xl
                    transition-all
                    duration-300
                    hover:border-cyan-300/30
                    hover:bg-white/10
                "
            >

                <Play
                    size={16}
                    className="
                        transition-transform
                        duration-300
                        group-hover:scale-110
                    "
                />

                Explore Dashboard

            </motion.button>

        </motion.div>
    );
}

export default HeroButtons;