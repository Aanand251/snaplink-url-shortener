import { ArrowRight, Sparkles } from "lucide-react";
import ClayButton from "./ClayButton";

function WelcomeCard({ onCreateLink }) {
    return (
        <section
            className="
                relative
                overflow-hidden
                rounded-[36px]

                bg-gradient-to-br
                from-white
                via-[#f8fbff]
                to-[#edf5ff]

                p-8

                shadow-[14px_14px_34px_rgba(163,177,198,.22),-14px_-14px_34px_rgba(255,255,255,.95)]
            "
        >
            {/* Decorative Circle */}

            <div className="absolute -right-16 -top-16 h-56 w-56 rounded-full bg-sky-100 blur-3xl opacity-60" />

            <div className="absolute bottom-0 right-0 h-44 w-44 rounded-full bg-blue-100 blur-3xl opacity-60" />

            <div className="relative z-10 flex flex-col gap-8 lg:flex-row lg:items-center lg:justify-between">
                {/* Left */}

                <div className="max-w-2xl">
                    <div className="mb-6 inline-flex items-center gap-2 rounded-full bg-sky-100 px-4 py-2 text-sm font-semibold text-sky-600">
                        <Sparkles size={16} />
                        Welcome Back
                    </div>

                    <h1 className="text-4xl font-bold leading-tight text-slate-800 lg:text-5xl">
                        Manage your links
                        <br />
                        smarter & faster.
                    </h1>

                    <p className="mt-6 max-w-xl text-lg leading-8 text-slate-500">
                        Create, organize and track every short URL from one
                        beautiful dashboard with real-time analytics.
                    </p>

                    <div className="mt-8">
                        <ClayButton onClick={onCreateLink}>
                            Create New Link
                            <ArrowRight size={18} />
                        </ClayButton>
                    </div>
                </div>

                {/* Right */}

                <div className="grid grid-cols-2 gap-5">
                    <div className="rounded-[28px] bg-white p-6 text-center shadow-[10px_10px_25px_rgba(163,177,198,.18),-10px_-10px_25px_rgba(255,255,255,.95)]">
                        <h2 className="text-3xl font-bold text-sky-500">
                            152
                        </h2>

                        <p className="mt-2 text-sm text-slate-500">
                            Total Links
                        </p>
                    </div>

                    <div className="rounded-[28px] bg-white p-6 text-center shadow-[10px_10px_25px_rgba(163,177,198,.18),-10px_-10px_25px_rgba(255,255,255,.95)]">
                        <h2 className="text-3xl font-bold text-green-500">
                            8.2K
                        </h2>

                        <p className="mt-2 text-sm text-slate-500">
                            Clicks
                        </p>
                    </div>

                    <div className="rounded-[28px] bg-white p-6 text-center shadow-[10px_10px_25px_rgba(163,177,198,.18),-10px_-10px_25px_rgba(255,255,255,.95)]">
                        <h2 className="text-3xl font-bold text-violet-500">
                            98%
                        </h2>

                        <p className="mt-2 text-sm text-slate-500">
                            Uptime
                        </p>
                    </div>

                    <div className="rounded-[28px] bg-white p-6 text-center shadow-[10px_10px_25px_rgba(163,177,198,.18),-10px_-10px_25px_rgba(255,255,255,.95)]">
                        <h2 className="text-3xl font-bold text-orange-500">
                            24h
                        </h2>

                        <p className="mt-2 text-sm text-slate-500">
                            Active
                        </p>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default WelcomeCard;