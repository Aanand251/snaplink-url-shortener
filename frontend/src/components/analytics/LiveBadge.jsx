import { Activity } from "lucide-react";

function LiveBadge() {
    return (
        <div
            className="
                inline-flex
                items-center
                gap-2
                rounded-full
                border
                border-emerald-500/20
                bg-emerald-500/10
                px-4
                py-2
            "
        >
            <Activity
                size={15}
                className="text-emerald-400"
            />

            <span className="text-sm font-semibold text-emerald-300">
                Tracking active
            </span>
        </div>
    );
}

export default LiveBadge;