import { motion } from "framer-motion";
import {
    Link2,
    BarChart3,
    Share2,
    QrCode,
} from "lucide-react";

const actions = [
    {
        title: "Create Link",
        icon: Link2,
    },
    {
        title: "Analytics",
        icon: BarChart3,
    },
    {
        title: "Share",
        icon: Share2,
    },
    {
        title: "QR Code",
        icon: QrCode,
    },
];

function QuickActions() {

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

            <h3 className="text-xl font-semibold text-white">
                Quick Actions
            </h3>

            <div className="mt-6 grid grid-cols-2 gap-4">

                {actions.map((action, index) => {

                    const Icon = action.icon;

                    return (

                        <motion.button

                            key={index}

                            whileHover={{
                                scale: 1.05,
                                y: -4,
                            }}

                            whileTap={{
                                scale: .97,
                            }}

                            className="
                                flex
                                flex-col
                                items-center
                                justify-center
                                gap-3
                                rounded-2xl
                                border
                                border-white/10
                                bg-black/20
                                p-6
                                transition-colors
                                hover:bg-white/10
                            "
                        >

                            <Icon
                                size={24}
                                className="text-cyan-300"
                            />

                            <span className="text-sm font-medium text-white">
                                {action.title}
                            </span>

                        </motion.button>

                    );

                })}

            </div>

        </div>

    );

}

export default QuickActions;