import { motion } from "framer-motion";
import { RefreshCw } from "lucide-react";

function RefreshButton({
                           loading = false,
                           onClick,
                       }) {
    return (
        <motion.button
            whileHover={{
                y: -3,
                scale: 1.02,
            }}
            whileTap={{
                scale: 0.97,
            }}
            transition={{
                duration: 0.2,
            }}
            type="button"
            onClick={onClick}
            disabled={loading}
            className="
                inline-flex
                items-center
                justify-center
                gap-3

                rounded-full

                border
                border-white/80

                bg-gradient-to-br
                from-white
                via-[#f8fbff]
                to-[#eef6ff]

                px-6
                py-3

                text-sm
                font-semibold
                text-slate-700

                shadow-[10px_10px_26px_rgba(163,177,198,.22),-10px_-10px_26px_rgba(255,255,255,.95)]

                transition-all
                duration-300

                hover:border-sky-200
                hover:text-sky-600

                disabled:cursor-not-allowed
                disabled:opacity-60
            "
        >
            <RefreshCw
                size={18}
                className={`
                    transition-transform
                    ${
                    loading
                        ? "animate-spin text-sky-500"
                        : "text-slate-500"
                }
                `}
            />

            <span>
                {loading
                    ? "Refreshing..."
                    : "Refresh"}
            </span>
        </motion.button>
    );
}

export default RefreshButton;