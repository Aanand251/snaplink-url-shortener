import {
    Activity,
    Globe,
    Zap,
} from "lucide-react";

import FloatingCard from "./FloatingCard";

const stats = [
    {
        title: "Today's Clicks",
        value: "12.8K",
        icon: Activity,
        color: "text-cyan-300",
        className: "-left-12 top-24 hidden xl:block",
        depth: 70,
        duration: 7,
    },
    {
        title: "Countries",
        value: "142",
        icon: Globe,
        color: "text-cyan-300",
        className: "-right-12 top-40 hidden xl:block",
        depth: 85,
        duration: 8,
    },
    {
        title: "Redirect",
        value: "18ms",
        icon: Zap,
        color: "text-yellow-300",
        className: "left-20 bottom-10 hidden xl:block",
        depth: 60,
        duration: 6,
    },
];

function FloatingStats() {
    return (
        <>
            {stats.map((item, index) => {

                const Icon = item.icon;

                return (

                    <div
                        key={item.title}
                        className={`absolute z-30 ${item.className}`}
                    >

                        <FloatingCard
                            depth={item.depth}
                            duration={item.duration}
                            delay={index * 0.15}
                            className="
                                px-6
                                py-5
                                min-w-[250px]
                            "
                        >

                            <div className="flex items-center justify-between gap-8">

                                <div>

                                    <p
                                        className="
                                            text-sm
                                            text-zinc-400
                                        "
                                    >
                                        {item.title}
                                    </p>

                                    <h3
                                        className="
                                            mt-2
                                            text-5xl
                                            font-bold
                                            tracking-tight
                                            text-white
                                        "
                                    >
                                        {item.value}
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
                                        border
                                        border-white/10
                                        bg-white/10
                                    "
                                >

                                    <Icon
                                        size={22}
                                        className={item.color}
                                    />

                                </div>

                            </div>

                        </FloatingCard>

                    </div>

                );

            })}
        </>
    );
}

export default FloatingStats;