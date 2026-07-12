import { ArrowRight, BarChart3, Link2, ShieldCheck, Zap } from "lucide-react";
import { Link } from "react-router-dom";
import Logo from "../components/ui/Logo";

const features = [
    {
        icon: Zap,
        title: "Shorten in seconds",
        description:
            "Turn long, messy URLs into clean links built for sharing.",
    },
    {
        icon: BarChart3,
        title: "Understand every click",
        description:
            "Track clicks, browsers and devices with focused link analytics.",
    },
    {
        icon: ShieldCheck,
        title: "Built with control",
        description:
            "Secure authentication, owned links and protected management APIs.",
    },
];

function LandingPage() {
    return (
        <main className="min-h-screen overflow-hidden bg-[#08080c] text-white">
            <section className="relative">
                <div className="pointer-events-none absolute inset-0 bg-[radial-gradient(circle_at_50%_-10%,rgba(124,58,237,0.28),transparent_38%)]" />

                <div className="pointer-events-none absolute inset-0 bg-[linear-gradient(rgba(255,255,255,0.018)_1px,transparent_1px),linear-gradient(90deg,rgba(255,255,255,0.018)_1px,transparent_1px)] bg-[size:72px_72px]" />

                <nav className="relative mx-auto flex max-w-7xl items-center justify-between px-6 py-6 lg:px-8">
                    <Logo />

                    <div className="flex items-center gap-3">
                        <Link
                            to="/login"
                            className="rounded-xl px-4 py-2.5 text-sm font-medium text-zinc-300 transition hover:text-white"
                        >
                            Sign in
                        </Link>

                        <Link
                            to="/register"
                            className="rounded-xl border border-violet-400/20 bg-violet-500 px-4 py-2.5 text-sm font-semibold text-white shadow-[0_10px_35px_rgba(124,58,237,0.25)] transition hover:bg-violet-400"
                        >
                            Get started
                        </Link>
                    </div>
                </nav>

                <div className="relative mx-auto flex max-w-7xl flex-col items-center px-6 pb-24 pt-24 text-center lg:px-8 lg:pb-32 lg:pt-32">
                    <div className="mb-7 inline-flex items-center gap-2 rounded-full border border-violet-400/15 bg-violet-500/5 px-4 py-2 text-sm text-violet-200">
                        <span className="h-1.5 w-1.5 rounded-full bg-violet-400 shadow-[0_0_12px_rgba(167,139,250,0.9)]" />
                        Smarter links. Clearer insights.
                    </div>

                    <h1 className="max-w-5xl text-5xl font-semibold leading-[1.02] tracking-[-0.055em] sm:text-6xl lg:text-8xl">
                        Every link deserves
                        <span className="block bg-gradient-to-r from-violet-300 via-violet-500 to-fuchsia-400 bg-clip-text text-transparent">
              a better destination.
            </span>
                    </h1>

                    <p className="mt-8 max-w-2xl text-lg leading-8 text-zinc-400 sm:text-xl">
                        Create focused short links, manage them from one workspace and
                        understand how people engage with every click.
                    </p>

                    <div className="mt-10 flex flex-col gap-4 sm:flex-row">
                        <Link
                            to="/register"
                            className="group inline-flex items-center justify-center gap-2 rounded-2xl bg-white px-6 py-3.5 text-sm font-semibold text-zinc-950 transition hover:bg-zinc-200"
                        >
                            Start shortening
                            <ArrowRight className="h-4 w-4 transition group-hover:translate-x-1" />
                        </Link>

                        <Link
                            to="/login"
                            className="inline-flex items-center justify-center rounded-2xl border border-white/10 bg-white/[0.03] px-6 py-3.5 text-sm font-semibold text-white backdrop-blur transition hover:bg-white/[0.07]"
                        >
                            Open dashboard
                        </Link>
                    </div>

                    <div className="relative mt-20 w-full max-w-5xl">
                        <div className="absolute -inset-10 bg-violet-600/10 blur-3xl" />

                        <div className="relative overflow-hidden rounded-[28px] border border-white/10 bg-[#0d0d13]/90 p-3 shadow-2xl shadow-violet-950/30 backdrop-blur-xl">
                            <div className="rounded-2xl border border-white/[0.06] bg-[#101017] p-5 sm:p-8">
                                <div className="flex items-center justify-between border-b border-white/[0.06] pb-5">
                                    <div className="flex items-center gap-2">
                                        <span className="h-2.5 w-2.5 rounded-full bg-zinc-700" />
                                        <span className="h-2.5 w-2.5 rounded-full bg-zinc-700" />
                                        <span className="h-2.5 w-2.5 rounded-full bg-zinc-700" />
                                    </div>

                                    <span className="text-xs text-zinc-600">
                    app.snaplink
                  </span>
                                </div>

                                <div className="grid gap-4 pt-6 md:grid-cols-3">
                                    <div className="rounded-2xl border border-white/[0.06] bg-white/[0.025] p-5 text-left">
                                        <p className="text-sm text-zinc-500">Total links</p>
                                        <p className="mt-3 text-3xl font-semibold tracking-tight">
                                            128
                                        </p>
                                        <p className="mt-2 text-xs text-emerald-400">
                                            +12 this month
                                        </p>
                                    </div>

                                    <div className="rounded-2xl border border-white/[0.06] bg-white/[0.025] p-5 text-left">
                                        <p className="text-sm text-zinc-500">Total clicks</p>
                                        <p className="mt-3 text-3xl font-semibold tracking-tight">
                                            24.8K
                                        </p>
                                        <p className="mt-2 text-xs text-violet-400">
                                            Growing steadily
                                        </p>
                                    </div>

                                    <div className="rounded-2xl border border-white/[0.06] bg-white/[0.025] p-5 text-left">
                                        <p className="text-sm text-zinc-500">Top link</p>
                                        <div className="mt-3 flex items-center gap-2">
                                            <Link2 className="h-5 w-5 text-violet-400" />
                                            <p className="truncate text-lg font-medium">
                                                snap.link/github
                                            </p>
                                        </div>
                                        <p className="mt-2 text-xs text-zinc-500">
                                            8,421 interactions
                                        </p>
                                    </div>
                                </div>

                                <div className="mt-4 rounded-2xl border border-white/[0.06] bg-white/[0.02] p-6">
                                    <div className="flex h-40 items-end gap-2 sm:h-52">
                                        {[28, 42, 35, 58, 48, 72, 63, 88, 70, 92, 78, 100].map(
                                            (height, index) => (
                                                <div
                                                    key={index}
                                                    className="flex-1 rounded-t-md bg-gradient-to-t from-violet-700/30 to-violet-400/80"
                                                    style={{ height: `${height}%` }}
                                                />
                                            ),
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section className="border-t border-white/[0.06] bg-[#0a0a0f]">
                <div className="mx-auto max-w-7xl px-6 py-24 lg:px-8">
                    <div className="mx-auto max-w-2xl text-center">
                        <p className="text-sm font-medium uppercase tracking-[0.2em] text-violet-400">
                            Built for clarity
                        </p>

                        <h2 className="mt-4 text-3xl font-semibold tracking-[-0.04em] sm:text-5xl">
                            More than a shorter URL.
                        </h2>

                        <p className="mt-5 text-zinc-400">
                            One focused workspace for creating, managing and understanding
                            your links.
                        </p>
                    </div>

                    <div className="mt-16 grid gap-5 md:grid-cols-3">
                        {features.map(({ icon: Icon, title, description }) => (
                            <article
                                key={title}
                                className="rounded-3xl border border-white/[0.07] bg-white/[0.025] p-7 transition duration-300 hover:-translate-y-1 hover:border-violet-400/20 hover:bg-white/[0.04]"
                            >
                                <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-violet-500/10">
                                    <Icon className="h-5 w-5 text-violet-300" />
                                </div>

                                <h3 className="mt-6 text-xl font-semibold">{title}</h3>

                                <p className="mt-3 leading-7 text-zinc-500">
                                    {description}
                                </p>
                            </article>
                        ))}
                    </div>
                </div>
            </section>

            <footer className="border-t border-white/[0.06] bg-[#08080c]">
                <div className="mx-auto flex max-w-7xl flex-col gap-5 px-6 py-8 text-sm text-zinc-600 sm:flex-row sm:items-center sm:justify-between lg:px-8">
                    <Logo />

                    <p>Built for fast links and meaningful insights.</p>
                </div>
            </footer>
        </main>
    );
}

export default LandingPage;