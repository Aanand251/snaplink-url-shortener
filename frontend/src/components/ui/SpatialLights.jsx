import { motion } from "framer-motion";

function SpatialLights() {
    return (
        <div className="absolute inset-0 overflow-hidden pointer-events-none">

            {/* Top Cyan Light */}

            <motion.div
                animate={{
                    x: [0, 60, -40, 0],
                    y: [0, 40, -20, 0],
                    scale: [1, 1.15, 0.95, 1],
                }}
                transition={{
                    duration: 18,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    absolute
                    left-1/2
                    top-[-160px]
                    h-[520px]
                    w-[520px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-400/12
                    blur-[170px]
                "
            />

            {/* Left White Light */}

            <motion.div
                animate={{
                    x: [0, 35, 0],
                    y: [0, -40, 0],
                    scale: [1, 1.08, 1],
                }}
                transition={{
                    duration: 16,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    absolute
                    left-[-180px]
                    top-1/2
                    h-[420px]
                    w-[420px]
                    -translate-y-1/2
                    rounded-full
                    bg-white/8
                    blur-[150px]
                "
            />

            {/* Right Cyan Orb */}

            <motion.div
                animate={{
                    x: [0, -50, 20, 0],
                    y: [0, 50, 0],
                    scale: [1, 1.12, 1],
                }}
                transition={{
                    duration: 20,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    absolute
                    right-[-180px]
                    bottom-[-120px]
                    h-[460px]
                    w-[460px]
                    rounded-full
                    bg-cyan-300/10
                    blur-[170px]
                "
            />

        </div>
    );
}

export default SpatialLights;