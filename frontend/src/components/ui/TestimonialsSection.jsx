import { motion } from "framer-motion";
import { Star, Quote } from "lucide-react";

const testimonials = [
    {
        name: "Sarah Johnson",
        role: "Frontend Developer",
        message:
            "SnapLink made managing thousands of links effortless. The analytics dashboard is incredibly clean and fast.",
    },
    {
        name: "Michael Chen",
        role: "Content Creator",
        message:
            "I love the custom aliases and real-time click tracking. It has become part of my daily workflow.",
    },
    {
        name: "Emily Davis",
        role: "Startup Founder",
        message:
            "Beautiful UI, blazing-fast redirects, and reliable analytics. Exactly what our team needed.",
    },
];

function TestimonialsSection() {
    return (
        <section
            id="testimonials"
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
                    top-24
                    h-[500px]
                    w-[500px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-400/10
                    blur-[180px]
                "
            />

            <div className="relative z-10 mx-auto max-w-7xl px-6">

                {/* Heading */}

                <motion.div
                    initial={{ opacity: 0, y: 40 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    transition={{ duration: .6 }}
                    viewport={{ once: true }}
                    className="text-center"
                >

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
                        TESTIMONIALS
                    </span>

                    <h2
                        className="
                            mt-8
                            text-4xl
                            font-black
                            tracking-tight
                            text-white
                            md:text-6xl
                        "
                    >
                        Loved by

                        <span className="text-cyan-300">
                            {" "}developers worldwide
                        </span>

                    </h2>

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
                        Thousands of developers, startups and creators trust
                        SnapLink to shorten, manage and analyze every link.
                    </p>

                </motion.div>

                {/* Cards */}

                <div className="mt-20 grid gap-8 md:grid-cols-3">

                    {testimonials.map((item, index) => (

                        <motion.div
                            key={item.name}

                            initial={{
                                opacity: 0,
                                y: 40,
                            }}

                            whileInView={{
                                opacity: 1,
                                y: 0,
                            }}

                            transition={{
                                delay: index * .1,
                                duration: .5,
                            }}

                            viewport={{
                                once: true,
                            }}

                            whileHover={{
                                y: -10,
                            }}

                            className="
                                group
                                relative
                                overflow-hidden
                                rounded-[32px]
                                border
                                border-white/10
                                bg-white/5
                                p-8
                                backdrop-blur-3xl
                                transition-all
                                duration-300
                                hover:border-cyan-400/30
                                hover:bg-white/10
                            "
                        >

                            {/* Hover Glow */}

                            <div
                                className="
                                    absolute
                                    left-1/2
                                    top-1/2
                                    h-44
                                    w-44
                                    -translate-x-1/2
                                    -translate-y-1/2
                                    rounded-full
                                    bg-cyan-400/0
                                    blur-3xl
                                    transition-all
                                    duration-500
                                    group-hover:bg-cyan-400/10
                                "
                            />

                            <div className="relative">

                                <Quote
                                    size={34}
                                    className="mb-6 text-cyan-300/80"
                                />

                                <div className="mb-6 flex gap-1">

                                    {[...Array(5)].map((_, starIndex) => (

                                        <Star
                                            key={starIndex}
                                            size={18}
                                            className="
                                                fill-yellow-400
                                                text-yellow-400
                                            "
                                        />

                                    ))}

                                </div>

                                <p
                                    className="
                                        leading-8
                                        text-zinc-300
                                    "
                                >
                                    "{item.message}"
                                </p>

                                <div className="mt-8">

                                    <h3
                                        className="
                                            text-lg
                                            font-semibold
                                            text-white
                                        "
                                    >
                                        {item.name}
                                    </h3>

                                    <p
                                        className="
                                            mt-1
                                            text-sm
                                            text-zinc-500
                                        "
                                    >
                                        {item.role}
                                    </p>

                                </div>

                            </div>

                        </motion.div>

                    ))}

                </div>

            </div>

        </section>
    );
}

export default TestimonialsSection;