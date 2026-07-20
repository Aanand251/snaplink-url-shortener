import { motion } from "framer-motion";

function AuroraBackground() {
    return (
        <div className="absolute inset-0 overflow-hidden pointer-events-none">

            {/* Base */}

            <div className="absolute inset-0 bg-[#050505]" />

            {/* Top Environment Light */}

            <motion.div
                animate={{
                    x: [-60, 60, -60],
                    y: [-30, 30, -30],
                    scale: [1, 1.06, 1],
                }}
                transition={{
                    duration: 22,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    absolute
                    left-1/2
                    top-[-28%]
                    h-[900px]
                    w-[1400px]
                    -translate-x-1/2
                    rounded-full
                    bg-white/[0.08]
                    blur-[190px]
                "
            />

            {/* Left Ambient */}

            <motion.div
                animate={{
                    x: [-30, 30, -30],
                    opacity: [0.08, 0.12, 0.08],
                }}
                transition={{
                    duration: 18,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    absolute
                    -left-56
                    top-24
                    h-[700px]
                    w-[700px]
                    rounded-full
                    bg-slate-100/[0.06]
                    blur-[170px]
                "
            />

            {/* Right Ambient */}

            <motion.div
                animate={{
                    x: [30, -30, 30],
                    opacity: [0.05, 0.09, 0.05],
                }}
                transition={{
                    duration: 20,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    absolute
                    -right-48
                    top-16
                    h-[650px]
                    w-[650px]
                    rounded-full
                    bg-white/[0.05]
                    blur-[180px]
                "
            />

            {/* Cyan Accent */}

            <motion.div
                animate={{
                    scale: [1, 1.08, 1],
                    opacity: [0.10, 0.18, 0.10],
                }}
                transition={{
                    duration: 16,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    absolute
                    left-1/2
                    top-[22%]
                    h-[600px]
                    w-[600px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-300/8
                    blur-[170px]
                "
            />

            {/* Bottom Fog */}

            <div
                className="
                    absolute
                    bottom-[-280px]
                    left-1/2
                    h-[900px]
                    w-[1300px]
                    -translate-x-1/2
                    rounded-full
                    bg-white/[0.035]
                    blur-[220px]
                "
            />

            {/* Environment Gradient */}

            <div
                className="
                    absolute
                    inset-0
                    bg-[radial-gradient(circle_at_50%_18%,rgba(255,255,255,0.06),transparent_42%)]
                "
            />

            <div
                className="
                    absolute
                    inset-0
                    bg-[radial-gradient(circle_at_center,transparent_45%,rgba(5,5,5,0.82)_100%)]
                "
            />

            {/* Soft Light Beams */}

            <div
                className="
                    absolute
                    inset-0
                    opacity-[0.05]
                "
                style={{
                    backgroundImage: `
                        linear-gradient(
                            115deg,
                            transparent 0%,
                            rgba(255,255,255,.35) 50%,
                            transparent 100%
                        )
                    `,
                    backgroundSize: "1200px 100%",
                }}
            />

            {/* Fine Noise */}

            <div
                className="
                    absolute
                    inset-0
                    opacity-[0.018]
                    mix-blend-soft-light
                "
                style={{
                    backgroundImage:
                        "radial-gradient(circle, rgba(255,255,255,.6) 1px, transparent 1px)",
                    backgroundSize: "22px 22px",
                }}
            />

        </div>
    );
}

export default AuroraBackground;