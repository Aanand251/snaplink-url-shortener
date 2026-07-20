import { useState } from "react";
import { motion } from "framer-motion";
import { ChevronDown } from "lucide-react";

const faqs = [
    {
        question: "Is SnapLink free to use?",
        answer:
            "Yes. You can create and manage short links for free. Additional premium features can be added in the future.",
    },
    {
        question: "Can I create custom short URLs?",
        answer:
            "Absolutely. SnapLink supports custom aliases so you can create memorable and branded links.",
    },
    {
        question: "Does SnapLink provide analytics?",
        answer:
            "Yes. Track total clicks, browsers, devices, countries and other useful insights from your dashboard.",
    },
    {
        question: "Are my links secure?",
        answer:
            "Yes. Authentication is powered by JWT, and users can only manage links that they own.",
    },
    {
        question: "Does SnapLink support link expiration?",
        answer:
            "Yes. You can set an expiration date so links automatically become inactive after the specified time.",
    },
];

function FAQItem({ faq, open, onClick }) {
    return (
        <motion.div
            layout
            className="
                group
                overflow-hidden
                rounded-[28px]
                border
                border-white/10
                bg-white/5
                backdrop-blur-3xl
                transition-all
                duration-300
                hover:border-cyan-400/30
                hover:bg-white/10
            "
        >

            <button
                onClick={onClick}
                className="
                    flex
                    w-full
                    items-center
                    justify-between
                    px-7
                    py-6
                    text-left
                "
            >

                <span
                    className="
                        text-lg
                        font-semibold
                        text-white
                    "
                >
                    {faq.question}
                </span>

                <ChevronDown
                    size={22}
                    className={`
                        text-cyan-300
                        transition-transform
                        duration-300
                        ${open ? "rotate-180" : ""}
                    `}
                />

            </button>

            <div
                className={`
                    overflow-hidden
                    transition-all
                    duration-300
                    ${open ? "max-h-48 px-7 pb-7" : "max-h-0"}
                `}
            >

                <p
                    className="
                        leading-8
                        text-zinc-400
                    "
                >
                    {faq.answer}
                </p>

            </div>

        </motion.div>
    );
}

function FAQSection() {

    const [active, setActive] = useState(0);

    return (
        <section
            id="faq"
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
                    h-[450px]
                    w-[450px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-400/10
                    blur-[180px]
                "
            />

            <div className="relative z-10 mx-auto max-w-4xl px-6">

                {/* Heading */}

                <motion.div
                    initial={{
                        opacity: 0,
                        y: 40,
                    }}
                    whileInView={{
                        opacity: 1,
                        y: 0,
                    }}
                    transition={{
                        duration: 0.6,
                    }}
                    viewport={{
                        once: true,
                    }}
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
                        FAQ
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
                        Frequently Asked

                        <span className="text-cyan-300">
                            {" "}Questions
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
                        Everything you need to know about SnapLink before
                        getting started.
                    </p>

                </motion.div>

                {/* FAQ */}

                <div className="mt-20 space-y-5">

                    {faqs.map((faq, index) => (

                        <FAQItem
                            key={faq.question}
                            faq={faq}
                            open={active === index}
                            onClick={() =>
                                setActive(active === index ? -1 : index)
                            }
                        />

                    ))}

                </div>

            </div>

        </section>
    );
}

export default FAQSection;