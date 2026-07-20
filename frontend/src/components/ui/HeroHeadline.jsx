import { motion } from "framer-motion";

function HeroHeadline() {
    return (
        <div className="mt-10">

            {/* Headline */}

            <motion.h1
                initial={{
                    opacity: 0,
                    y: 40,
                }}
                animate={{
                    opacity: 1,
                    y: 0,
                }}
                transition={{
                    delay: 0.2,
                    duration: 0.8,
                }}
                className="
                    text-5xl
                    font-black
                    leading-[1.05]
                    tracking-[-0.05em]
                    text-white
                    md:text-7xl
                    xl:text-8xl
                "
            >

                Every Link.

                <br />

                <span
                    className="
                        bg-gradient-to-r
                        from-white
                        via-cyan-200
                        to-cyan-400
                        bg-clip-text
                        text-transparent
                    "
                >
                    Beautifully Connected.
                </span>

            </motion.h1>

            {/* Description */}

            <motion.p
                initial={{
                    opacity: 0,
                    y: 30,
                }}
                animate={{
                    opacity: 1,
                    y: 0,
                }}
                transition={{
                    delay: 0.45,
                    duration: 0.8,
                }}
                className="
                    mx-auto
                    mt-10
                    max-w-2xl
                    text-lg
                    leading-8
                    text-zinc-400
                    md:text-xl
                "
            >

                Create short links, monitor live analytics,
                generate QR codes and manage every URL from
                one elegant dashboard designed for speed,
                simplicity and clarity.

            </motion.p>

        </div>
    );
}

export default HeroHeadline;