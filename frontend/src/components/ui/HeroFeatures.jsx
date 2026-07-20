import { motion } from "framer-motion";
import {
    ShieldCheck,
    Zap,
    BarChart3,
} from "lucide-react";

const features = [
    {
        icon: Zap,
        title: "Lightning Fast",
    },
    {
        icon: ShieldCheck,
        title: "Secure by Default",
    },
    {
        icon: BarChart3,
        title: "Real-time Analytics",
    },
];

function HeroFeatures() {
    return (
        <motion.div
            initial={{
                opacity: 0,
                y: 20,
            }}
            animate={{
                opacity: 1,
                y: 0,
            }}
            transition={{
                delay: 0.8,
                duration: 0.7,
            }}
            className="
                mt-14
                flex
                flex-wrap
                items-center
                justify-center
                gap-4
            "
        >

            {features.map(({ icon: Icon, title }, index) => (

                <motion.div
                    key={title}
                    whileHover={{
                        y: -4,
                        scale: 1.03,
                    }}
                    transition={{
                        type: "spring",
                        stiffness: 260,
                        damping: 18,
                        delay: index * 0.05,
                    }}
                    className="
                        group
                        relative
                        overflow-hidden
                        rounded-full
                        border
                        border-white/10
                        bg-white/5
                        px-5
                        py-3
                        backdrop-blur-3xl
                        transition-all
                        duration-300
                        hover:border-cyan-300/30
                        hover:bg-white/10
                    "
                >

                    {/* Hover Glow */}

                    <div
                        className="
                            absolute
                            inset-0
                            rounded-full
                            bg-cyan-300/0
                            blur-xl
                            transition-all
                            duration-500
                            group-hover:bg-cyan-300/10
                        "
                    />

                    <div className="relative flex items-center gap-3">

                        <div
                            className="
                                flex
                                h-9
                                w-9
                                items-center
                                justify-center
                                rounded-full
                                border
                                border-cyan-300/20
                                bg-cyan-300/10
                            "
                        >

                            <Icon
                                size={18}
                                className="text-cyan-300"
                            />

                        </div>

                        <span
                            className="
                                text-sm
                                font-medium
                                text-zinc-200
                            "
                        >
                            {title}
                        </span>

                    </div>

                </motion.div>

            ))}

        </motion.div>
    );
}

export default HeroFeatures;