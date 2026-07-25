import { Activity } from "lucide-react";

function LiveBadge() {
    return (
        <div
            className="
                inline-flex
                items-center
                gap-3
                rounded-full
                px-5
                py-3
                bg-[#EEF2F5]
            "
            style={{
                boxShadow:
                    "8px 8px 18px rgba(163,177,198,.20), -8px -8px 18px rgba(255,255,255,.95)"
            }}
        >
            <span className="relative flex h-3 w-3">
                <span className="absolute inline-flex h-full w-full rounded-full bg-emerald-400 opacity-60 animate-ping" />
                <span className="relative inline-flex h-3 w-3 rounded-full bg-emerald-500" />
            </span>

            <span
                className="
                    text-sm
                    font-semibold
                    tracking-[0.12em]
                    uppercase
                    text-[#5F6975]
                "
            >
                Live Analytics
            </span>

        </div>
    );
}

export default LiveBadge;