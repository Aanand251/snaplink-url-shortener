import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { Menu, X, ArrowRight } from "lucide-react";
import Logo from "./Logo";

function Navbar() {
    const location = useLocation();
    const [isOpen, setIsOpen] = useState(false);

    const isActive = (path) => location.pathname === path;

    const closeMenu = () => setIsOpen(false);

    return (
        <header className="sticky top-0 z-50 w-full">
            <div className="mx-auto max-w-7xl px-6 pt-5 lg:px-8">

                <nav
                    className="
                        rounded-3xl
                        border
                        border-white/10
                        bg-white/[0.06]
                        backdrop-blur-3xl
                        shadow-[0_15px_60px_rgba(0,0,0,.35)]
                    "
                >

                    <div className="flex items-center justify-between px-6 py-4">

                        <Logo />

                        {/* Desktop Navigation */}

                        <div className="hidden items-center gap-8 md:flex">

                            <a
                                href="#features"
                                className="text-sm text-zinc-400 transition hover:text-cyan-300"
                            >
                                Features
                            </a>

                            <a
                                href="#testimonials"
                                className="text-sm text-zinc-400 transition hover:text-cyan-300"
                            >
                                Reviews
                            </a>

                            <a
                                href="#faq"
                                className="text-sm text-zinc-400 transition hover:text-cyan-300"
                            >
                                FAQ
                            </a>

                        </div>

                        {/* Desktop Buttons */}

                        <div className="hidden items-center gap-3 md:flex">

                            <Link
                                to="/login"
                                className={`rounded-2xl px-5 py-2.5 text-sm transition ${
                                    isActive("/login")
                                        ? "border border-white/10 bg-white/10 text-white"
                                        : "text-zinc-400 hover:text-white"
                                }`}
                            >
                                Login
                            </Link>

                            <Link
                                to="/register"
                                className="
                                    group
                                    inline-flex
                                    items-center
                                    gap-2
                                    rounded-2xl
                                    border
                                    border-cyan-400/20
                                    bg-cyan-400
                                    px-5
                                    py-2.5
                                    text-sm
                                    font-semibold
                                    text-black
                                    transition
                                    duration-300
                                    hover:scale-105
                                    hover:shadow-[0_0_35px_rgba(34,211,238,.35)]
                                "
                            >
                                Get Started

                                <ArrowRight
                                    size={16}
                                    className="transition group-hover:translate-x-1"
                                />
                            </Link>

                        </div>

                        {/* Mobile Toggle */}

                        <button
                            onClick={() => setIsOpen(!isOpen)}
                            className="text-white md:hidden"
                            aria-label="Toggle navigation"
                        >
                            {isOpen ? <X size={28} /> : <Menu size={28} />}
                        </button>

                    </div>

                    {/* Mobile Menu */}

                    {isOpen && (

                        <div className="border-t border-white/10 md:hidden">

                            <div className="flex flex-col gap-5 px-6 py-6">

                                <a
                                    href="#features"
                                    onClick={closeMenu}
                                    className="text-zinc-300 transition hover:text-cyan-300"
                                >
                                    Features
                                </a>

                                <a
                                    href="#testimonials"
                                    onClick={closeMenu}
                                    className="text-zinc-300 transition hover:text-cyan-300"
                                >
                                    Reviews
                                </a>

                                <a
                                    href="#faq"
                                    onClick={closeMenu}
                                    className="text-zinc-300 transition hover:text-cyan-300"
                                >
                                    FAQ
                                </a>

                                <Link
                                    to="/login"
                                    onClick={closeMenu}
                                    className="
                                        rounded-2xl
                                        border
                                        border-white/10
                                        py-3
                                        text-center
                                        text-white
                                        transition
                                        hover:bg-white/10
                                    "
                                >
                                    Login
                                </Link>

                                <Link
                                    to="/register"
                                    onClick={closeMenu}
                                    className="
                                        rounded-2xl
                                        bg-cyan-400
                                        py-3
                                        text-center
                                        font-semibold
                                        text-black
                                        transition
                                        hover:brightness-110
                                    "
                                >
                                    Get Started
                                </Link>

                            </div>

                        </div>

                    )}

                </nav>

            </div>
        </header>
    );
}

export default Navbar;