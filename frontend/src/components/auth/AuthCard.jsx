import { motion } from "framer-motion";
import clsx from "clsx";

function AuthCard({
                      children,
                      className,
                  }) {
    return (
        <motion.div
            initial={{ opacity: 0, y: 28, scale: 0.96 }}
            animate={{ opacity: 1, y: 0, scale: 1 }}
            transition={{
                duration: 0.55,
                ease: [0.22, 1, 0.36, 1],
            }}
            className={clsx(
                "relative w-full max-w-md overflow-hidden rounded-[32px]",
                "border border-white/10",
                "bg-white/[0.05]",
                "backdrop-blur-3xl",
                "shadow-[0_30px_80px_rgba(0,0,0,0.45)]",
                className,
            )}
        >
            {/* Top glow */}
            <div className="pointer-events-none absolute inset-x-0 top-0 h-px bg-gradient-to-r from-transparent via-cyan-300/70 to-transparent" />

            {/* Glass highlight */}
            <div className="pointer-events-none absolute inset-0 bg-gradient-to-br from-white/8 via-transparent to-transparent" />

            {/* Soft cyan glow */}
            <div className="pointer-events-none absolute -top-28 left-1/2 h-64 w-64 -translate-x-1/2 rounded-full bg-cyan-400/10 blur-[90px]" />

            {/* Content */}
            <div className="relative z-10 p-8 sm:p-10">
                {children}
            </div>
        </motion.div>
    );
}

export default AuthCard;