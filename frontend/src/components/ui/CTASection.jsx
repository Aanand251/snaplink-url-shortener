import { motion } from "framer-motion";
import { ArrowRight } from "lucide-react";
import { Link } from "react-router-dom";

function CTASection() {
    return (
        <section
            className="
                relative
                overflow-hidden
                bg-[#050505]
                py-32
            "
        >

            {/* Background Glow */}

            <div
                className="
                    absolute
                    left-1/2
                    top-1/2
                    h-[650px]
                    w-[650px]
                    -translate-x-1/2
                    -translate-y-1/2
                    rounded-full
                    bg-cyan-400/10
                    blur-[220px]
                "
            />

            <div className="relative z-10 mx-auto max-w-6xl px-6">

                <motion.div

                    initial={{
                        opacity: 0,
                        y: 50,
                    }}

                    whileInView={{
                        opacity: 1,
                        y: 0,
                    }}

                    transition={{
                        duration: .6,
                    }}

                    viewport={{
                        once: true,
                    }}

                    className="
                        overflow-hidden
                        rounded-[40px]
                        border
                        border-white/10
                        bg-white/[0.05]
                        backdrop-blur-3xl
                    "

                >

                    {/* Inner Glow */}

                    <div
                        className="
                            absolute
                            inset-0
                            bg-gradient-to-br
                            from-cyan-400/10
                            via-transparent
                            to-white/5
                        "
                    />

                    <div
                        className="
                            relative
                            px-10
                            py-20
                            text-center
                            md:px-20
                        "
                    >

                        {/* Badge */}

                        <span
                            className="
                                inline-flex
                                rounded-full
                                border
                                border-cyan-400/20
                                bg-cyan-400/10
                                px-5
                                py-2
                                text-sm
                                font-medium
                                tracking-wide
                                text-cyan-300
                            "
                        >

                            START TODAY

                        </span>

                        {/* Heading */}

                        <h2
                            className="
                                mt-8
                                text-4xl
                                font-black
                                leading-tight
                                tracking-tight
                                text-white
                                md:text-6xl
                            "
                        >

                            Ready to simplify

                            <br />

                            <span className="text-cyan-300">

                                every link you share?

                            </span>

                        </h2>

                        {/* Description */}

                        <p
                            className="
                                mx-auto
                                mt-8
                                max-w-2xl
                                text-lg
                                leading-8
                                text-zinc-400
                            "
                        >

                            Join thousands of developers, startups and creators
                            who shorten, track and manage every URL with
                            lightning-fast performance and beautiful analytics.

                        </p>

                        {/* Buttons */}

                        <div
                            className="
                                mt-14
                                flex
                                flex-col
                                justify-center
                                gap-5
                                sm:flex-row
                            "
                        >

                            <Link
                                to="/register"
                                className="
                                    group
                                    inline-flex
                                    items-center
                                    justify-center
                                    gap-3
                                    rounded-2xl
                                    border
                                    border-cyan-400/20
                                    bg-cyan-400
                                    px-8
                                    py-4
                                    text-sm
                                    font-semibold
                                    text-black
                                    transition
                                    duration-300
                                    hover:scale-105
                                    hover:shadow-[0_0_40px_rgba(34,211,238,.35)]
                                "
                            >

                                Get Started

                                <ArrowRight
                                    size={18}
                                    className="
                                        transition
                                        group-hover:translate-x-1
                                    "
                                />

                            </Link>

                            <Link
                                to="/login"
                                className="
                                    rounded-2xl
                                    border
                                    border-white/10
                                    bg-white/5
                                    px-8
                                    py-4
                                    text-sm
                                    font-semibold
                                    text-white
                                    backdrop-blur-xl
                                    transition
                                    hover:bg-white/10
                                "
                            >

                                Sign In

                            </Link>

                        </div>

                        {/* Bottom Stats */}

                        <div
                            className="
                                mt-14
                                flex
                                flex-wrap
                                justify-center
                                gap-8
                                text-sm
                                text-zinc-400
                            "
                        >

                            <span>⚡ 18ms Redirect</span>

                            <span>🔒 Secure JWT Authentication</span>

                            <span>📈 Real-Time Analytics</span>

                        </div>

                    </div>

                </motion.div>

            </div>

        </section>
    );
}

export default CTASection;