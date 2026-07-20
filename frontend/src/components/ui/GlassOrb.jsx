import { motion } from "framer-motion";

function GlassOrb() {
    return (

        <motion.div

            animate={{

                y: [-20, 20, -20],

                rotate: [-6, 6, -6],

                scale: [1, 1.05, 1],

            }}

            transition={{

                duration: 18,

                repeat: Infinity,

                ease: "easeInOut",

            }}

            className="
                pointer-events-none
                absolute
                right-[6%]
                top-24
                z-10
                h-80
                w-80
                rounded-full
            "

        >

            {/* Glass Body */}

            <div
                className="
                    absolute
                    inset-0
                    rounded-full
                    border
                    border-white/10
                    bg-white/[0.05]
                    backdrop-blur-[60px]
                "
            />

            {/* Cyan Core */}

            <div
                className="
                    absolute
                    left-1/2
                    top-1/2
                    h-40
                    w-40
                    -translate-x-1/2
                    -translate-y-1/2
                    rounded-full
                    bg-cyan-300/20
                    blur-[70px]
                "
            />

            {/* Reflection */}

            <div
                className="
                    absolute
                    left-16
                    top-12
                    h-20
                    w-10
                    rotate-[-30deg]
                    rounded-full
                    bg-white/25
                    blur-xl
                "
            />

            {/* Outer Glow */}

            <div
                className="
                    absolute
                    inset-[-40px]
                    rounded-full
                    bg-cyan-300/10
                    blur-[90px]
                "
            />

        </motion.div>

    );
}

export default GlassOrb;