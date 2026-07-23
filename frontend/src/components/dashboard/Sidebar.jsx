import {
    BarChart3,
    LayoutDashboard,
    Link2,
    LogOut,
    Menu,
    Plus,
    X,
} from "lucide-react";

import { NavLink, useNavigate } from "react-router-dom";

import { useAuth } from "../../context/AuthContext";

const navItems = [
    {
        title: "Dashboard",
        icon: LayoutDashboard,
        path: "/dashboard",
    },
    {
        title: "My Links",
        icon: Link2,
        path: "/links",
    },
    {
        title: "Analytics",
        icon: BarChart3,
        path: "/analytics",
    },
];

function Sidebar({
                     open,
                     onClose,
                     onCreateLink,
                 }) {
    const navigate = useNavigate();

    const { logout } = useAuth();

    const handleLogout = () => {
        logout();

        navigate("/login", {
            replace: true,
        });
    };

    return (
        <>
            {/* Mobile Toggle */}

            <button
                type="button"
                onClick={onClose}
                className="
                    fixed
                    left-6
                    top-6
                    z-50

                    flex
                    h-14
                    w-14
                    items-center
                    justify-center

                    rounded-[22px]

                    bg-white

                    shadow-[8px_8px_22px_rgba(163,177,198,.22),-8px_-8px_22px_rgba(255,255,255,.95)]

                    lg:hidden
                "
            >
                {open ? (
                    <X size={22} />
                ) : (
                    <Menu size={22} />
                )}
            </button>

            <aside
                className={`
                    fixed
                    left-0
                    top-0
                    z-40

                    h-screen
                    w-[320px]

                    overflow-hidden

                    transform

                    bg-gradient-to-br
                    from-[#eef6ff]
                    via-[#f8fbff]
                    to-[#edf7ff]

                    transition-transform
                    duration-300

                    lg:sticky
                    lg:translate-x-0

                    ${
                    open
                        ? "translate-x-0"
                        : "-translate-x-full"
                }
                `}
            >
                {/* Background */}

                <div
                    className="
                        absolute
                        -right-16
                        -top-16

                        h-72
                        w-72

                        rounded-full

                        bg-sky-200/30

                        blur-3xl
                    "
                />

                <div
                    className="
                        absolute

                        bottom-0
                        -left-10

                        h-56
                        w-56

                        rounded-full

                        bg-blue-100/40

                        blur-3xl
                    "
                />

                <div className="relative flex h-full flex-col p-7">

                    {/* Logo */}

                    <button
                        type="button"
                        onClick={() =>
                            navigate("/dashboard")
                        }
                        className="
                            rounded-[40px]

                            bg-white

                            p-7

                            text-left

                            shadow-[16px_16px_36px_rgba(163,177,198,.22),-16px_-16px_36px_rgba(255,255,255,.95)]

                            transition-all

                            hover:-translate-y-1
                        "
                    >
                        <div className="flex items-center gap-5">

                            <div
                                className="
                                    flex

                                    h-16
                                    w-16

                                    items-center
                                    justify-center

                                    rounded-[24px]

                                    bg-gradient-to-br
                                    from-sky-400
                                    via-cyan-400
                                    to-blue-500

                                    text-white
                                "
                            >
                                <Link2 size={30} />
                            </div>

                            <div>
                                <h2 className="text-2xl font-bold text-slate-800">
                                    SnapLink
                                </h2>

                                <p className="mt-1 text-sm text-slate-500">
                                    Premium URL Shortener
                                </p>
                            </div>

                        </div>
                    </button>

                    {/* Navigation */}

                    <div
                        className="
                            mt-8

                            flex-1

                            rounded-[40px]

                            bg-white

                            p-6

                            shadow-[16px_16px_36px_rgba(163,177,198,.22),-16px_-16px_36px_rgba(255,255,255,.95)]
                        "
                    >
                        <p
                            className="
                                mb-6

                                px-2

                                text-xs

                                font-bold

                                uppercase

                                tracking-[0.22em]

                                text-slate-400
                            "
                        >
                            Navigation
                        </p>

                        <nav className="space-y-3">

                            {navItems.map(
                                ({
                                     title,
                                     path,
                                     icon: Icon,
                                 }) => (
                                    <NavLink
                                        key={path}
                                        to={path}
                                        onClick={onClose}
                                        className={({ isActive }) =>
                                            `
                                            flex
                                            items-center
                                            gap-4

                                            rounded-[24px]

                                            px-5
                                            py-4

                                            font-semibold

                                            transition-all
                                            duration-300

                                            ${
                                                isActive
                                                    ? "bg-gradient-to-r from-sky-400 via-cyan-400 to-blue-500 text-white shadow-[8px_8px_22px_rgba(14,165,233,.25)]"
                                                    : "text-slate-600 hover:bg-sky-50"
                                            }
                                            `
                                        }
                                    >
                                        <Icon size={22} />

                                        <span>
                                            {title}
                                        </span>
                                    </NavLink>
                                )
                            )}
                        </nav>
                    </div>
                    {/* Bottom Card */}

                    <div
                        className="
                            mt-8

                            rounded-[40px]

                            bg-white

                            p-6

                            shadow-[16px_16px_36px_rgba(163,177,198,.22),-16px_-16px_36px_rgba(255,255,255,.95)]
                        "
                    >
                        {/* User */}

                        <div className="flex items-center gap-4">

                            <div
                                className="
                                    flex

                                    h-16
                                    w-16

                                    items-center
                                    justify-center

                                    rounded-full

                                    bg-gradient-to-br
                                    from-sky-400
                                    via-cyan-400
                                    to-blue-500

                                    text-lg
                                    font-bold

                                    text-white
                                "
                            >
                                A
                            </div>

                            <div className="flex-1">

                                <h3 className="text-lg font-bold text-slate-800">
                                    Anand
                                </h3>

                                <p className="text-sm text-slate-500">
                                    Premium User
                                </p>

                            </div>

                        </div>

                        {/* Divider */}

                        <div className="my-6 h-px bg-slate-100" />

                        {/* Create Link */}

                        <button
                            type="button"
                            onClick={() => {
                                onClose();
                                onCreateLink?.();
                            }}
                            className="
                                flex
                                w-full
                                items-center
                                justify-center
                                gap-3

                                rounded-[22px]

                                bg-gradient-to-r
                                from-sky-400
                                via-cyan-400
                                to-blue-500

                                px-5
                                py-4

                                font-semibold
                                text-white

                                shadow-[8px_8px_22px_rgba(14,165,233,.28)]

                                transition-all
                                duration-300

                                hover:-translate-y-1
                                hover:scale-[1.01]

                                active:scale-[0.98]
                            "
                        >
                            <Plus size={20} />

                            Create Link
                        </button>

                        {/* Logout */}

                        <button
                            type="button"
                            onClick={handleLogout}
                            className="
                                mt-4

                                flex
                                w-full
                                items-center
                                justify-center
                                gap-3

                                rounded-[22px]

                                bg-red-50

                                px-5
                                py-4

                                font-semibold

                                text-red-500

                                transition-all
                                duration-300

                                hover:bg-red-100
                                hover:-translate-y-1

                                active:scale-[0.98]
                            "
                        >
                            <LogOut size={20} />

                            Logout
                        </button>

                    </div>

                </div>

            </aside>

        </>
    );
}

export default Sidebar;