import { motion } from "framer-motion";
import { Sparkles } from "lucide-react";

function HeroBadge() {
    return (
        <motion.div
            initial={{
                opacity: 0,
                y: -25,
            }}
            animate={{
                opacity: 1,
                y: 0,
            }}
            transition={{
                duration: 0.8,
                ease: "easeOut",
            }}
            whileHover={{
                scale: 1.03,
            }}
            className="relative inline-flex overflow-hidden rounded-full"
        >
            {/* Glass Capsule */}

            <div
                className="
                    relative
                    flex
                    items-center
                    gap-3
                    rounded-full
                    border
                    border-white/15
                    bg-white/5
                    px-6
                    py-3
                    backdrop-blur-3xl
                    shadow-[0_10px_40px_rgba(0,0,0,0.25)]
                "
            >

                {/* Shimmer */}

                <motion.div
                    animate={{
                        x: ["-150%", "220%"],
                    }}
                    transition={{
                        duration: 3,
                        repeat: Infinity,
                        ease: "linear",
                    }}
                    className="
                        absolute
                        inset-y-0
                        w-16
                        rotate-12
                        bg-white/20
                        blur-xl
                    "
                />

                {/* Status Dot */}

                <motion.div
                    animate={{
                        scale: [1, 1.4, 1],
                        opacity: [1, .5, 1],
                    }}
                    transition={{
                        duration: 2,
                        repeat: Infinity,
                    }}
                    className="
                        h-2.5
                        w-2.5
                        rounded-full
                        bg-emerald-400
                    "
                />

                <Sparkles
                    size={16}
                    className="text-cyan-300"
                />

                <span
                    className="
                        text-sm
                        font-medium
                        tracking-wide
                        text-white
                    "
                >
                    Beautifully crafted with React & Spring Boot
                </span>

            </div>

        </motion.div>
    );
}

export default HeroBadge;