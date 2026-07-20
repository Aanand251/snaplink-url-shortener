import { motion, useMotionValue, useSpring } from "framer-motion";
import { useEffect } from "react";

function MouseGlow() {

    const mouseX = useMotionValue(
        window.innerWidth / 2
    );

    const mouseY = useMotionValue(
        window.innerHeight / 2
    );

    const x = useSpring(mouseX, {
        stiffness: 140,
        damping: 30,
        mass: 0.8,
    });

    const y = useSpring(mouseY, {
        stiffness: 140,
        damping: 30,
        mass: 0.8,
    });

    useEffect(() => {

        const handleMouseMove = (event) => {

            mouseX.set(event.clientX);

            mouseY.set(event.clientY);

        };

        window.addEventListener(
            "mousemove",
            handleMouseMove
        );

        return () =>
            window.removeEventListener(
                "mousemove",
                handleMouseMove
            );

    }, [mouseX, mouseY]);

    return (
        <>
            {/* Main Glow */}

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
                    h-[420px]
                    w-[420px]
                    rounded-full
                    bg-cyan-400/10
                    blur-[120px]
                "

            />

            {/* Secondary Glow */}

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
                    h-[220px]
                    w-[220px]
                    rounded-full
                    bg-white/8
                    blur-[70px]
                "

            />

        </>
    );

}

export default MouseGlow;