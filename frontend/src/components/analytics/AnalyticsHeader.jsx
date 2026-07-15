import { ArrowLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";

import LiveBadge from "./LiveBadge";

function AnalyticsHeader({ shortCode }) {
    const navigate = useNavigate();

    return (
        <div className="mb-12">

            <div className="mb-8 flex items-center justify-between">

                <button
                    onClick={() => navigate("/dashboard")}
                    className="
                        inline-flex
                        items-center
                        gap-2
                        rounded-xl
                        border
                        border-white/10
                        bg-white/5
                        px-4
                        py-2
                        text-sm
                        font-medium
                        text-slate-300
                        transition-all
                        duration-300
                        hover:border-violet-500/40
                        hover:bg-violet-500/10
                        hover:text-white
                    "
                >
                    <ArrowLeft size={18} />
                    Back to Dashboard
                </button>

                <LiveBadge />
            </div>

            <div className="flex flex-col gap-6 lg:flex-row lg:items-end lg:justify-between">

                <div>

                    <h1 className="text-5xl font-black tracking-tight text-white">

                        Link Analytics

                    </h1>

                    <p className="mt-4 max-w-2xl text-lg text-slate-400">

                        Detailed insights about your short URL including
                        clicks, browser, device, country and activity.

                    </p>

                </div>

                <div
                    className="
                        inline-flex
                        items-center
                        rounded-2xl
                        border
                        border-violet-500/20
                        bg-violet-500/10
                        px-6
                        py-4
                    "
                >
                    <span
                        className="
                            font-mono
                            text-2xl
                            font-bold
                            tracking-wide
                            text-violet-300
                        "
                    >
                        /{shortCode}
                    </span>
                </div>

            </div>

        </div>
    );
}

export default AnalyticsHeader;