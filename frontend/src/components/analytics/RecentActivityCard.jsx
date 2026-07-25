import {
    MousePointerClick,
    Clock3,
    Monitor,
    Smartphone,
} from "lucide-react";

import "./neumorphism.css";

function RecentActivityCard({

                                activities = [],

                            }) {

    return (

        <section
            className="
                neo-card
                neo-hover
                neo-highlight
                rounded-[34px]
                p-8
                min-h-[430px]
            "
        >

            <div className="flex items-center justify-between">

                <div>

                    <p className="neo-title">

                        Activity

                    </p>

                    <h2
                        className="
                            text-[28px]
                            font-bold
                            text-[#2F343C]
                        "
                    >

                        Recent Clicks

                    </h2>

                </div>

                <div
                    className="
                        neo-icon
                        text-blue-600
                    "
                >

                    <Clock3 size={18} />

                </div>

            </div>

            <div className="mt-8 space-y-5">

                {

                    activities.length === 0

                        ? (

                            <div
                                className="
                                    flex
                                    h-[260px]
                                    items-center
                                    justify-center
                                    text-[#64707C]
                                "
                            >

                                No Recent Activity

                            </div>

                        )

                        : (

                            activities.map((item, index) => (

                                <div
                                    key={index}
                                    className="
                                        neo-small
                                        flex
                                        items-center
                                        justify-between
                                        px-5
                                        py-4
                                    "
                                >

                                    <div className="flex items-center gap-4">

                                        <div
                                            className="
                                                neo-icon
                                                text-blue-600
                                            "
                                        >

                                            {

                                                item.device === "Mobile"

                                                    ?

                                                    <Smartphone size={18} />

                                                    :

                                                    <Monitor size={18} />

                                            }

                                        </div>

                                        <div>

                                            <p
                                                className="
                                                    text-lg
                                                    font-semibold
                                                    text-[#2F343C]
                                                "
                                            >

                                                {item.browser}

                                            </p>

                                            <p
                                                className="
                                                    text-sm
                                                    text-[#64707C]
                                                "
                                            >

                                                {item.device} • {item.country}

                                            </p>

                                        </div>

                                    </div>

                                    <div className="text-right">

                                        <MousePointerClick
                                            size={16}
                                            className="
                                                ml-auto
                                                mb-2
                                                text-blue-600
                                            "
                                        />

                                        <p
                                            className="
                                                text-sm
                                                text-[#64707C]
                                            "
                                        >

                                            {

                                                new Date(item.clickedAt)

                                                    .toLocaleString()

                                            }

                                        </p>

                                    </div>

                                </div>

                            ))

                        )

                }

            </div>

        </section>

    );

}

export default RecentActivityCard;