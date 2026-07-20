import { motion } from "framer-motion";
import {
    ArrowUpRight,
    Copy,
    Link2,
} from "lucide-react";

function UrlPreviewCard() {

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

            <div className="flex items-center justify-between">

                <div className="flex items-center gap-3">

                    <div
                        className="
                            flex
                            h-12
                            w-12
                            items-center
                            justify-center
                            rounded-2xl
                            bg-cyan-400/10
                        "
                    >

                        <Link2
                            size={22}
                            className="text-cyan-300"
                        />

                    </div>

                    <div>

                        <p className="text-sm text-zinc-500">
                            Short URL
                        </p>

                        <h3 className="text-xl font-semibold text-white">
                            snap.link/AbC12
                        </h3>

                    </div>

                </div>

                <button
                    className="
                        rounded-xl
                        border
                        border-white/10
                        bg-white/5
                        p-2
                        transition
                        hover:bg-white/10
                    "
                >

                    <Copy
                        size={18}
                        className="text-zinc-300"
                    />

                </button>

            </div>

            <div
                className="
                    mt-6
                    rounded-2xl
                    border
                    border-white/10
                    bg-black/20
                    p-4
                "
            >

                <p className="text-xs uppercase tracking-widest text-zinc-500">
                    Original URL
                </p>

                <p className="mt-2 truncate text-sm text-zinc-300">
                    https://www.youtube.com/watch?v=XXXXXXXX
                </p>

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
                    bg-cyan-400
                    py-3
                    font-semibold
                    text-black
                    transition
                    hover:scale-[1.02]
                "
            >

                Open Link

                <ArrowUpRight size={18} />

            </button>

        </motion.div>

    );

}

export default UrlPreviewCard;