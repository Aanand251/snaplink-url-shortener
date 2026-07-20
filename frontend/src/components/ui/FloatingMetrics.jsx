import {
    Activity,
    Globe2,
    MousePointerClick,
    ShieldCheck,
} from "lucide-react";

import FloatingCard from "./FloatingCard";

const metrics = [
    {
        icon: MousePointerClick,
        value: "52M+",
        label: "Total Clicks",
        top: "8%",
        left: "-4%",
        depth: 80,
        duration: 7,
    },
    {
        icon: Globe2,
        value: "180+",
        label: "Countries",
        top: "18%",
        right: "-6%",
        depth: 60,
        duration: 8,
    },
    {
        icon: ShieldCheck,
        value: "99.99%",
        label: "Uptime",
        bottom: "18%",
        left: "-2%",
        depth: 70,
        duration: 6,
    },
    {
        icon: Activity,
        value: "Live",
        label: "Analytics",
        bottom: "10%",
        right: "-4%",
        depth: 90,
        duration: 9,
    },
];

function FloatingMetrics() {
    return (
        <div
            className="
                pointer-events-none
                absolute
                inset-0
                z-30
                hidden
                lg:block
            "
        >
            {metrics.map(
                (
                    {
                        icon: Icon,
                        value,
                        label,
                        depth,
                        duration,
                        ...position
                    },
                    index
                ) => (
                    <div
                        key={index}
                        className="absolute"
                        style={position}
                    >
                        <FloatingCard
                            depth={depth}
                            duration={duration}
                            delay={index * 0.12}
                            className="
                                px-5
                                py-4
                                min-w-[220px]
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
                                        rounded-2xl
                                        border
                                        border-cyan-300/20
                                        bg-cyan-300/10
                                    "
                                >
                                    <Icon
                                        size={22}
                                        className="text-cyan-300"
                                    />
                                </div>

                                <div>

                                    <h3
                                        className="
                                            text-xl
                                            font-bold
                                            text-white
                                        "
                                    >
                                        {value}
                                    </h3>

                                    <p
                                        className="
                                            text-sm
                                            text-zinc-400
                                        "
                                    >
                                        {label}
                                    </p>

                                </div>

                            </div>
                        </FloatingCard>
                    </div>
                )
            )}
        </div>
    );
}

export default FloatingMetrics;