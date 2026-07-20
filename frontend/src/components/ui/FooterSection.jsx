import {
    Mail,
    Heart,
    ArrowUpRight,
} from "lucide-react";
import {  FaLinkedin } from "react-icons/fa";
import { FaGithub } from "react-icons/fa";
import { Link } from "react-router-dom";
import Logo from "./Logo";

function FooterSection() {
    return (
        <footer className="relative overflow-hidden border-t border-white/10 bg-[#050505]">

            {/* Background Glow */}

            <div
                className="
                    absolute
                    left-1/2
                    top-0
                    h-[450px]
                    w-[450px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-400/10
                    blur-[180px]
                "
            />

            <div className="relative z-10 mx-auto max-w-7xl px-6 py-20">

                <div className="grid gap-14 lg:grid-cols-4">

                    {/* Logo */}

                    <div>

                        <Logo />

                        <p className="mt-6 leading-8 text-zinc-400">
                            SnapLink is a modern URL shortening platform
                            designed for developers, creators and businesses.
                            Create, manage and analyze links effortlessly with
                            blazing-fast performance.
                        </p>

                    </div>

                    {/* Product */}

                    <div>

                        <h3 className="text-lg font-semibold text-white">
                            Product
                        </h3>

                        <div className="mt-6 flex flex-col gap-5">

                            <a
                                href="#features"
                                className="group inline-flex items-center gap-2 text-zinc-400 transition hover:text-cyan-300"
                            >
                                Features

                                <ArrowUpRight
                                    size={15}
                                    className="transition group-hover:translate-x-1 group-hover:-translate-y-1"
                                />

                            </a>

                            <Link
                                to="/register"
                                className="group inline-flex items-center gap-2 text-zinc-400 transition hover:text-cyan-300"
                            >
                                Get Started

                                <ArrowUpRight
                                    size={15}
                                    className="transition group-hover:translate-x-1 group-hover:-translate-y-1"
                                />

                            </Link>

                            <Link
                                to="/login"
                                className="group inline-flex items-center gap-2 text-zinc-400 transition hover:text-cyan-300"
                            >
                                Dashboard

                                <ArrowUpRight
                                    size={15}
                                    className="transition group-hover:translate-x-1 group-hover:-translate-y-1"
                                />

                            </Link>

                        </div>

                    </div>

                    {/* Resources */}

                    <div>

                        <h3 className="text-lg font-semibold text-white">
                            Resources
                        </h3>

                        <div className="mt-6 flex flex-col gap-5">

                            <a
                                href="#"
                                className="group inline-flex items-center gap-2 text-zinc-400 transition hover:text-cyan-300"
                            >
                                Documentation

                                <ArrowUpRight
                                    size={15}
                                    className="transition group-hover:translate-x-1 group-hover:-translate-y-1"
                                />

                            </a>

                            <a
                                href="#"
                                className="group inline-flex items-center gap-2 text-zinc-400 transition hover:text-cyan-300"
                            >
                                API Reference

                                <ArrowUpRight
                                    size={15}
                                    className="transition group-hover:translate-x-1 group-hover:-translate-y-1"
                                />

                            </a>

                            <a
                                href="#"
                                className="group inline-flex items-center gap-2 text-zinc-400 transition hover:text-cyan-300"
                            >
                                Support

                                <ArrowUpRight
                                    size={15}
                                    className="transition group-hover:translate-x-1 group-hover:-translate-y-1"
                                />

                            </a>

                        </div>

                    </div>

                    {/* Contact */}

                    <div>

                        <h3 className="text-lg font-semibold text-white">
                            Contact
                        </h3>

                        <div
                            className="
                                mt-6
                                rounded-3xl
                                border
                                border-white/10
                                bg-white/5
                                p-6
                                backdrop-blur-3xl
                            "
                        >

                            <p className="mb-6 text-sm leading-7 text-zinc-400">
                                Questions, feedback or partnership?
                                We'd love to hear from you.
                            </p>

                            <div className="flex flex-wrap items-center gap-3">

                                <a
                                    href="mailto:choudharyaanandkumar251@gmail.com?subject=SnapLink%20Support"
                                    className="
                                        inline-flex
                                        items-center
                                        gap-3
                                        rounded-2xl
                                        border
                                        border-cyan-400/20
                                        bg-cyan-400/10
                                        px-5
                                        py-3
                                        text-cyan-300
                                        transition
                                        hover:scale-105
                                        hover:border-cyan-400/40
                                        hover:bg-cyan-400/15
                                    "
                                >

                                    <Mail size={18} />

                                    Email Us

                                </a>

                                <a
                                    href="https://github.com/Aanand251"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="
                                        flex
                                        h-12
                                        w-12
                                        items-center
                                        justify-center
                                        rounded-2xl
                                        border
                                        border-white/10
                                        bg-white/5
                                        text-zinc-300
                                        transition
                                        hover:scale-105
                                        hover:border-cyan-400/40
                                        hover:text-cyan-300
                                    "
                                >

                                    <FaGithub size={20} />

                                </a>

                                <a
                                    href="https://www.linkedin.com/in/anand-choudhary-3673463b7"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="
                                        flex
                                        h-12
                                        w-12
                                        items-center
                                        justify-center
                                        rounded-2xl
                                        border
                                        border-white/10
                                        bg-white/5
                                        text-zinc-300
                                        transition
                                        hover:scale-105
                                        hover:border-cyan-400/40
                                        hover:text-cyan-300
                                    "
                                >

                                    <FaLinkedin size={20} />

                                </a>

                            </div>

                        </div>

                    </div>

                </div>

                {/* Bottom */}

                <div
                    className="
                        mt-20
                        flex
                        flex-col
                        items-center
                        justify-between
                        gap-5
                        border-t
                        border-white/10
                        pt-8
                        text-sm
                        text-zinc-500
                        md:flex-row
                    "
                >

                    <p>
                        © {new Date().getFullYear()} SnapLink.
                        Crafted with ❤️ by Anand Choudhary.
                    </p>

                    <p className="flex items-center gap-2">

                        Built with

                        <Heart
                            size={15}
                            className="fill-red-500 text-red-500"
                        />

                        using Spring Boot & React

                    </p>

                </div>

            </div>

        </footer>
    );
}

export default FooterSection;