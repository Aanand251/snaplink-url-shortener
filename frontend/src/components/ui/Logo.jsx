import { Link2 } from "lucide-react";
import { Link } from "react-router-dom";
import clsx from "clsx";

function Logo({ compact = false, className = "" }) {
    return (
        <Link
            to="/"
            className={clsx(
                "group inline-flex items-center gap-3 transition-transform duration-300 hover:scale-[1.02]",
                className,
            )}
        >
            {/* Logo Icon */}
            <div
                className="
                    relative
                    flex
                    h-11
                    w-11
                    items-center
                    justify-center
                    overflow-hidden
                    rounded-2xl
                    border
                    border-cyan-400/20
                    bg-white/5
                    backdrop-blur-xl
                    transition-all
                    duration-300
                    group-hover:border-cyan-300/40
                    group-hover:shadow-[0_0_35px_rgba(34,211,238,0.22)]
                "
            >
                {/* Glow */}
                <div className="absolute inset-0 bg-gradient-to-br from-cyan-400/15 via-transparent to-transparent" />

                <Link2
                    className="relative h-5 w-5 text-cyan-300"
                    strokeWidth={2.5}
                />
            </div>

            {!compact && (
                <div className="flex flex-col leading-none">
                    <span className="text-xl font-semibold tracking-[-0.04em] text-white">
                        Snap
                        <span className="text-cyan-300">Link</span>
                    </span>

                    <span className="mt-1 text-[11px] uppercase tracking-[0.28em] text-zinc-500">
                        URL SHORTENER
                    </span>
                </div>
            )}
        </Link>
    );
}

export default Logo;