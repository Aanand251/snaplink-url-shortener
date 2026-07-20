import { useEffect } from "react";
import {
    motion,
    useMotionValue,
    useSpring,
    useTransform,
} from "framer-motion";

function MouseParallax({
                           children,
                           maxRotate = 5,
                           maxTranslate = 18,
                       }) {

    const mouseX = useMotionValue(0);
    const mouseY = useMotionValue(0);

    useEffect(() => {

        const handleMouseMove = (e) => {

            const x =
                (e.clientX / window.innerWidth - 0.5) * 2;

            const y =
                (e.clientY / window.innerHeight - 0.5) * 2;

            mouseX.set(x);
            mouseY.set(y);

        };

        window.addEventListener(
            "mousemove",
            handleMouseMove,
            { passive: true }
        );

        return () =>
            window.removeEventListener(
                "mousemove",
                handleMouseMove
            );

    }, [mouseX, mouseY]);

    const smoothX = useSpring(mouseX, {
        stiffness: 90,
        damping: 22,
    });

    const smoothY = useSpring(mouseY, {
        stiffness: 90,
        damping: 22,
    });

    const rotateX = useTransform(
        smoothY,
        [-1, 1],
        [maxRotate, -maxRotate]
    );

    const rotateY = useTransform(
        smoothX,
        [-1, 1],
        [-maxRotate, maxRotate]
    );

    const translateX = useTransform(
        smoothX,
        [-1, 1],
        [-maxTranslate, maxTranslate]
    );

    const translateY = useTransform(
        smoothY,
        [-1, 1],
        [-maxTranslate, maxTranslate]
    );

    return (

        <motion.div

            style={{

                rotateX,

                rotateY,

                x: translateX,

                y: translateY,

                transformStyle: "preserve-3d",

                perspective: 2200,

                willChange: "transform",

            }}

            className="relative"

        >

            {children}

        </motion.div>

    );

}

export default MouseParallax;