import { motion } from "framer-motion";

const technologies = [
    "Spring Boot",
    "React",
    "PostgreSQL",
    "Redis",
    "Docker",
];

function TrustedTech() {
    return (
        <section className="mx-auto mt-24 max-w-7xl px-6 pb-24">

            <motion.p
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.5 }}
                viewport={{ once: true }}
                className="
                    mb-12
                    text-center
                    text-sm
                    font-medium
                    uppercase
                    tracking-[0.35em]
                    text-zinc-500
                "
            >
                Built With Modern Technologies
            </motion.p>

            <div
                className="
                    grid
                    gap-6
                    sm:grid-cols-2
                    lg:grid-cols-5
                "
            >

                {technologies.map((tech, index) => (

                    <motion.div

                        key={tech}

                        initial={{
                            opacity: 0,
                            y: 30,
                        }}

                        whileInView={{
                            opacity: 1,
                            y: 0,
                        }}

                        transition={{
                            delay: index * 0.1,
                            duration: 0.5,
                        }}

                        viewport={{
                            once: true,
                        }}

                        whileHover={{
                            y: -10,
                            scale: 1.03,
                        }}

                        className="
                            group
                            relative
                            overflow-hidden
                            rounded-3xl
                            border
                            border-white/10
                            bg-white/5
                            px-6
                            py-8
                            text-center
                            backdrop-blur-3xl
                            transition-all
                            duration-300
                            hover:border-cyan-400/30
                            hover:bg-white/10
                        "

                    >

                        {/* Glow */}

                        <div
                            className="
                                absolute
                                inset-0
                                opacity-0
                                transition
                                duration-300
                                group-hover:opacity-100
                            "
                        >
                            <div
                                className="
                                    absolute
                                    left-1/2
                                    top-1/2
                                    h-32
                                    w-32
                                    -translate-x-1/2
                                    -translate-y-1/2
                                    rounded-full
                                    bg-cyan-400/10
                                    blur-3xl
                                "
                            />
                        </div>

                        <span
                            className="
                                relative
                                text-base
                                font-semibold
                                tracking-wide
                                text-zinc-200
                            "
                        >
                            {tech}
                        </span>

                    </motion.div>

                ))}

            </div>

        </section>
    );
}

export default TrustedTech;