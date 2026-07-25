import {
    Activity,
    CalendarDays,
    RefreshCcw,
} from "lucide-react";

import "./neumorphism.css";

function AnalyticsHeader() {

    const today = new Date().toLocaleDateString("en-IN", {
        weekday: "long",
        day: "numeric",
        month: "long",
        year: "numeric",
    });

    return (

        <header
            className="
                flex
                flex-col
                gap-10
                xl:flex-row
                xl:items-center
                xl:justify-between
            "
        >

            {/* LEFT */}

            <div className="space-y-5">

                <div
                    className="
                        inline-flex
                        items-center
                        gap-3
                        rounded-full
                        bg-[#EEF2F5]
                        px-5
                        py-3
                    "
                    style={{
                        boxShadow:
                            "8px 8px 18px rgba(163,177,198,.20), -8px -8px 18px rgba(255,255,255,.95)",
                    }}
                >

                    <span
                        className="
                            h-3
                            w-3
                            rounded-full
                            bg-emerald-500
                            animate-pulse
                        "
                    />

                    <span
                        className="
                            text-sm
                            font-semibold
                            tracking-wide
                            text-[#606B77]
                        "
                    >
                        LIVE ANALYTICS
                    </span>

                </div>

                <div>

                    <h1
                        className="
                            text-[54px]
                            font-semibold
                            leading-none
                            tracking-[-0.04em]
                            text-[#2F343C]
                        "
                    >
                        Analytics
                    </h1>

                    <p
                        className="
                            mt-4
                            max-w-xl
                            text-[17px]
                            leading-8
                            text-[#8A94A2]
                        "
                    >
                        Monitor traffic, analyse visitors,
                        measure link performance and discover
                        how your shortened URLs are performing
                        in real time.
                    </p>

                </div>

            </div>

            {/* RIGHT */}

            <div
                className="
                    flex
                    flex-col
                    gap-5
                    xl:items-end
                "
            >

                {/* DATE */}

                <div
                    className="
                        flex
                        items-center
                        gap-4
                        rounded-full
                        bg-[#EEF2F5]
                        px-6
                        py-4
                    "
                    style={{
                        boxShadow:
                            "8px 8px 18px rgba(163,177,198,.20), -8px -8px 18px rgba(255,255,255,.95)",
                    }}
                >

                    <CalendarDays
                        size={20}
                        className="text-[#64707C]"
                    />

                    <span
                        className="
                            text-sm
                            font-medium
                            text-[#64707C]
                        "
                    >
                        {today}
                    </span>

                </div>

                {/* STATUS */}

                <div className="flex items-center gap-4">

                    <div
                        className="
                            flex
                            h-14
                            w-14
                            items-center
                            justify-center
                            rounded-2xl
                            bg-[#EEF2F5]
                        "
                        style={{
                            boxShadow:
                                "8px 8px 18px rgba(163,177,198,.20), -8px -8px 18px rgba(255,255,255,.95)",
                        }}
                    >

                        <RefreshCcw
                            size={20}
                            className="text-[#4A5563]"
                        />

                    </div>

                    <div
                        className="
                            flex
                            items-center
                            gap-3
                            rounded-full
                            bg-[#EEF2F5]
                            px-6
                            py-4
                        "
                        style={{
                            boxShadow:
                                "8px 8px 18px rgba(163,177,198,.20), -8px -8px 18px rgba(255,255,255,.95)",
                        }}
                    >

                        <Activity
                            size={18}
                            className="text-blue-600"
                        />

                        <span
                            className="
                                text-sm
                                font-medium
                                text-[#64707C]
                            "
                        >
                            Auto Refresh Enabled
                        </span>

                    </div>

                </div>

            </div>

        </header>

    );

}

export default AnalyticsHeader;