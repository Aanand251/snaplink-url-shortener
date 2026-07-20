import { motion } from "framer-motion";

function StatCard({

                      title,
                      value,
                      icon: Icon,
                      color = "text-cyan-300",

                  }) {

    return (

        <motion.div

            whileHover={{
                y: -5,
                scale: 1.02,
            }}

            transition={{
                duration: .25,
            }}

            className="
                rounded-3xl
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

                        {title}

                    </p>

                    <h3 className="mt-3 text-4xl font-bold text-white">

                        {value}

                    </h3>

                </div>

                <div
                    className="
                        flex
                        h-12
                        w-12
                        items-center
                        justify-center
                        rounded-2xl
                        bg-white/10
                    "
                >

                    <Icon
                        size={22}
                        className={color}
                    />

                </div>

            </div>

        </motion.div>

    );

}

export default StatCard;