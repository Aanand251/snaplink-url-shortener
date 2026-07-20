import { motion } from "framer-motion";

function GlassReflection() {
    return (
        <>
            {/* Main Reflection */}

            <motion.div
                animate={{
                    x: [-40, 40, -40],
                    opacity: [0.12, 0.2, 0.12],
                }}
                transition={{
                    duration: 10,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    pointer-events-none
                    absolute
                    -top-10
                    left-1/2
                    h-40
                    w-44
                    -translate-x-1/2
                    rounded-full
                    bg-white
                    blur-[70px]
                "
                style={{
                    opacity: 0.12,
                }}
            />

            {/* Secondary Reflection */}

            <motion.div
                animate={{
                    x: [30, -30, 30],
                    opacity: [0.04, 0.08, 0.04],
                }}
                transition={{
                    duration: 14,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    pointer-events-none
                    absolute
                    right-6
                    top-8
                    h-24
                    w-20
                    rounded-full
                    bg-cyan-300
                    blur-3xl
                "
            />
        </>
    );
}

export default GlassReflection;