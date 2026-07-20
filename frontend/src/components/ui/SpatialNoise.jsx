import { motion } from "framer-motion";

function SpatialNoise() {
    return (
        <>
            {/* Fine Grain Texture */}

            <motion.div
                animate={{
                    opacity: [0.025, 0.04, 0.025],
                }}
                transition={{
                    duration: 6,
                    repeat: Infinity,
                    ease: "easeInOut",
                }}
                className="
                    pointer-events-none
                    absolute
                    inset-0
                    overflow-hidden
                    rounded-[inherit]
                    mix-blend-soft-light
                "
            >
                <div
                    className="
                        h-full
                        w-full
                    "
                    style={{
                        backgroundImage: `
                            radial-gradient(circle at 20% 20%, rgba(255,255,255,0.08) 0.8px, transparent 1px),
                            radial-gradient(circle at 80% 40%, rgba(255,255,255,0.05) 0.8px, transparent 1px),
                            radial-gradient(circle at 40% 80%, rgba(255,255,255,0.06) 0.8px, transparent 1px),
                            radial-gradient(circle at 70% 70%, rgba(255,255,255,0.05) 0.8px, transparent 1px)
                        `,
                        backgroundSize: "18px 18px",
                    }}
                />
            </motion.div>

            {/* Soft Vignette */}

            <div
                className="
                    pointer-events-none
                    absolute
                    inset-0
                    rounded-[inherit]
                    bg-[radial-gradient(circle_at_center,transparent_55%,rgba(0,0,0,0.12)_100%)]
                "
            />
        </>
    );
}

export default SpatialNoise;