import { motion } from "framer-motion";
import {
    Globe,
    MousePointerClick,
    Smartphone,
} from "lucide-react";

const activities = [
    {
        user: "John",
        country: "India",
        time: "2 sec ago",
        icon: Globe,
    },
    {
        user: "Emma",
        country: "Germany",
        time: "14 sec ago",
        icon: MousePointerClick,
    },
    {
        user: "Alex",
        country: "USA",
        time: "1 min ago",
        icon: Smartphone,
    },
];

function ActivityTimeline() {

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
                Live Activity
            </h3>

            <p className="mt-1 text-sm text-zinc-500">
                Recent redirects happening around the world.
            </p>

            <div className="mt-6 space-y-4">

                {activities.map((activity, index) => {

                    const Icon = activity.icon;

                    return (

                        <motion.div
                            key={index}
                            whileHover={{
                                x: 6,
                            }}
                            className="
                                flex
                                items-center
                                justify-between
                                rounded-2xl
                                border
                                border-white/10
                                bg-black/20
                                p-4
                            "
                        >

                            <div className="flex items-center gap-4">

                                <div
                                    className="
                                        flex
                                        h-12
                                        w-12
                                        items-center
                                        justify-center
                                        rounded-xl
                                        bg-cyan-400/10
                                    "
                                >

                                    <Icon
                                        size={20}
                                        className="text-cyan-300"
                                    />

                                </div>

                                <div>

                                    <h4 className="font-medium text-white">
                                        {activity.user}
                                    </h4>

                                    <p className="text-sm text-zinc-500">
                                        {activity.country}
                                    </p>

                                </div>

                            </div>

                            <span className="text-xs text-zinc-500">
                                {activity.time}
                            </span>

                        </motion.div>

                    );

                })}

            </div>

        </div>

    );

}

export default ActivityTimeline;