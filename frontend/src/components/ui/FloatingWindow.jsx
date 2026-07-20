import { motion } from "framer-motion";

import GlassBorder from "./GlassBorder";
import GlassReflection from "./GlassReflection";
import SpatialShadow from "./SpatialShadow";
import SpatialNoise from "./SpatialNoise";

function FloatingWindow({
                            children,
                            className = "",
                            delay = 0,
                            duration = 8,
                        }) {
    return (
        <div className="relative">

            <SpatialShadow />

            <motion.div
                initial={{
                    opacity: 0,
                    y: 20,
                    scale: 0.96,
                }}
                animate={{
                    opacity: 1,
                    y: [0, -10, 0],
                    rotate: [-0.4, 0.4, -0.4],
                    scale: 1,
                }}
                transition={{
                    opacity: {
                        duration: 0.6,
                        delay,
                    },
                    y: {
                        duration,
                        repeat: Infinity,
                        ease: "easeInOut",
                    },
                    rotate: {
                        duration: duration + 2,
                        repeat: Infinity,
                        ease: "easeInOut",
                    },
                    scale: {
                        duration: 0.6,
                        delay,
                    },
                }}
                whileHover={{
                    y: -8,
                    scale: 1.03,
                    transition: {
                        type: "spring",
                        stiffness: 260,
                        damping: 18,
                    },
                }}
                className={`
                    group
                    relative
                    overflow-hidden
                    rounded-[30px]
                    bg-white/[0.06]
                    backdrop-blur-[30px]
                    ${className}
                `}
            >

                {/* Ambient Glow */}

                <div
                    className="
                        absolute
                        inset-0
                        bg-gradient-to-br
                        from-cyan-300/10
                        via-white/[0.03]
                        to-transparent
                    "
                />

                <GlassReflection />

                <SpatialNoise />

                <GlassBorder />

                {/* Hover Glow */}

                <motion.div
                    initial={{
                        opacity: 0,
                    }}
                    whileHover={{
                        opacity: 1,
                    }}
                    transition={{
                        duration: 0.35,
                    }}
                    className="
                        pointer-events-none
                        absolute
                        inset-0
                        bg-gradient-to-br
                        from-cyan-300/10
                        via-transparent
                        to-white/5
                    "
                />

                <div className="relative z-10">
                    {children}
                </div>

            </motion.div>

        </div>
    );
}

export default FloatingWindow;