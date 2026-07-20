import { Download } from "lucide-react";
import { motion } from "framer-motion";

function QrCard() {

    return (

        <motion.div

            whileHover={{
                y: -5,
                scale: 1.02,
            }}

            className="
                rounded-[30px]
                border
                border-white/10
                bg-white/[0.05]
                backdrop-blur-2xl
                p-6
            "

        >

            <p className="text-sm text-zinc-500">
                QR Code
            </p>

            <div className="mt-6 flex justify-center">

                <div
                    className="
                        flex
                        h-40
                        w-40
                        items-center
                        justify-center
                        rounded-3xl
                        bg-white
                    "
                >

                    <div className="grid grid-cols-6 gap-1">

                        {Array.from({ length: 36 }).map((_, index) => (

                            <div
                                key={index}
                                className={`
                                    h-4
                                    w-4
                                    rounded-sm
                                    ${Math.random() > .5 ? "bg-black" : "bg-white"}
                                `}
                            />

                        ))}

                    </div>

                </div>

            </div>

            <button
                className="
                    mt-6
                    flex
                    w-full
                    items-center
                    justify-center
                    gap-2
                    rounded-2xl
                    border
                    border-white/10
                    bg-white/5
                    py-3
                    text-white
                    transition
                    hover:bg-white/10
                "
            >

                <Download size={18} />

                Download

            </button>

        </motion.div>

    );

}

export default QrCard;