import { motion, useMotionValue, useSpring } from "framer-motion";
import { useEffect } from "react";

function DynamicLight() {
    const mouseX = useMotionValue(window.innerWidth / 2);
    const mouseY = useMotionValue(window.innerHeight / 2);

    const x = useSpring(mouseX, {
        stiffness: 80,
        damping: 30,
        mass: 0.8,
    });

    const y = useSpring(mouseY, {
        stiffness: 80,
        damping: 30,
        mass: 0.8,
    });

    useEffect(() => {
        const handleMouseMove = (e) => {
            mouseX.set(e.clientX);
            mouseY.set(e.clientY);
        };

        window.addEventListener("mousemove", handleMouseMove);

        return () => {
            window.removeEventListener(
                "mousemove",
                handleMouseMove
            );
        };
    }, [mouseX, mouseY]);

    return (
        <>
            {/* Primary Light */}

            <motion.div
                style={{
                    x,
                    y,
                    translateX: "-50%",
                    translateY: "-50%",
                }}
                className="
                    pointer-events-none
                    fixed
                    left-0
                    top-0
                    z-0
                    h-[520px]
                    w-[520px]
                    rounded-full
                    bg-cyan-400/12
                    blur-[140px]
                "
            />

            {/* Secondary Ambient Light */}

            <motion.div
                style={{
                    x,
                    y,
                    translateX: "-50%",
                    translateY: "-50%",
                }}
                className="
                    pointer-events-none
                    fixed
                    left-0
                    top-0
                    z-0
                    h-[260px]
                    w-[260px]
                    rounded-full
                    bg-white/10
                    blur-[90px]
                "
            />
        </>
    );
}

export default DynamicLight;