import { motion, useMotionValue } from "framer-motion";

function DashboardShell({ children }) {
    const rotateX = useMotionValue(0);
    const rotateY = useMotionValue(0);

    function handleMove(e) {
        const rect = e.currentTarget.getBoundingClientRect();

        const x = e.clientX - rect.left - rect.width / 2;
        const y = e.clientY - rect.top - rect.height / 2;

        rotateY.set(x / 40);
        rotateX.set(-y / 40);
    }

    return (
        <motion.div
            whileHover={{
                scale: 1.01,
            }}
            transition={{
                type: "spring",
                stiffness: 120,
                damping: 20,
            }}
            style={{
                rotateX,
                rotateY,
            }}
            onMouseMove={handleMove}
            onMouseLeave={() => {
                rotateX.set(0);
                rotateY.set(0);
            }}
            className="
                relative
                overflow-hidden
                rounded-[42px]
                border
                border-white/10
                bg-white/[0.05]
                backdrop-blur-[45px]
                shadow-[0_40px_120px_rgba(0,0,0,.55)]
            "
        >
            <div className="absolute inset-0 bg-gradient-to-br from-white/10 via-white/[0.02] to-transparent"/>

            <div className="absolute inset-x-0 top-0 h-px bg-white/30"/>

            {children}
        </motion.div>
    );
}

export default DashboardShell;