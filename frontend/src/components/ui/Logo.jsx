import { Link2 } from "lucide-react";

function Logo({ compact = false }) {
    return (
        <div className="flex items-center gap-3">
            <div className="flex h-10 w-10 items-center justify-center rounded-xl border border-violet-400/20 bg-violet-500/10 shadow-[0_0_30px_rgba(139,92,246,0.15)]">
                <Link2 className="h-5 w-5 text-violet-300" strokeWidth={2.4} />
            </div>

            {!compact && (
                <span className="text-xl font-semibold tracking-[-0.04em] text-white">
          Snap<span className="text-violet-400">Link</span>
        </span>
            )}
        </div>
    );
}

export default Logo;