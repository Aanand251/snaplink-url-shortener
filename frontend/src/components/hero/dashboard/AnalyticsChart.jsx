import { motion } from "framer-motion";

const bars = [
    18,
    24,
    35,
    28,
    42,
    38,
    55,
    48,
    62,
    74,
    68,
    96,
];

function AnalyticsChart() {

    return (

        <div
            className="
                rounded-[30px]
                border
                border-white/10
                bg-white/[0.05]
                backdrop-blur-2xl
                p-6
            "
        >

            <div className="flex items-center justify-between">

                <div>

                    <p className="text-sm text-zinc-500">
                        Click Analytics
                    </p>

                    <h3 className="mt-2 text-3xl font-bold text-white">
                        82.4K
                    </h3>

                </div>

                <span
                    className="
                        rounded-full
                        bg-emerald-400/15
                        px-3
                        py-1
                        text-xs
                        font-medium
                        text-emerald-300
                    "
                >
                    +18%
                </span>

            </div>

            <div className="mt-10 flex h-56 items-end gap-3">

                {bars.map((bar, index) => (

                    <motion.div

                        key={index}

                        initial={{
                            height: 0,
                            opacity: 0,
                        }}

                        animate={{
                            height: `${bar}%`,
                            opacity: 1,
                        }}

                        transition={{
                            delay: index * 0.05,
                            duration: .6,
                        }}

                        className="
                            flex-1
                            rounded-t-2xl
                            bg-gradient-to-t
                            from-cyan-500
                            via-sky-400
                            to-white
                        "
                    />

                ))}

            </div>

        </div>

    );

}

export default AnalyticsChart;