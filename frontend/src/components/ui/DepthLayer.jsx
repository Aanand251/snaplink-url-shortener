import { motion } from "framer-motion";
import clsx from "clsx";

function DepthLayer({
                        children,
                        className = "",
                        depth = 40,
                        hoverDepth = 12,
                        delay = 0,
                    }) {
    return (
        <motion.div
            initial={{
                opacity: 0,
                z: 0,
            }}
            animate={{
                opacity: 1,
                z: depth,
            }}
            transition={{
                duration: 0.8,
                delay,
            }}
            whileHover={{
                z: depth + hoverDepth,
                scale: 1.015,
            }}
            style={{
                transformStyle: "preserve-3d",
                willChange: "transform",
            }}
            className={clsx(
                "relative",
                className
            )}
        >
            {children}
        </motion.div>
    );
}

export default DepthLayer;