import { useEffect, useRef, useState } from "react";
import {
    Bell,
    ChevronDown,
    Link2,
    LogOut,
    Menu,
    Plus,
    Settings,
    User,
} from "lucide-react";
import { motion, AnimatePresence } from "framer-motion";
import { useNavigate } from "react-router-dom";

import { useAuth } from "../../context/AuthContext";
import ClayButton from "./ClayButton";

function ProfileDropdown({
                             open,
                             onClose,
                             onProfile,
                             onSettings,
                             onLogout,
                         }) {
    if (!open) return null;

    const menuItems = [
        {
            icon: User,
            label: "Profile",
            action: onProfile,
            color: "text-slate-700",
        },
        {
            icon: Settings,
            label: "Settings",
            action: onSettings,
            color: "text-slate-700",
        },
        {
            icon: LogOut,
            label: "Logout",
            action: onLogout,
            color: "text-red-500",
        },
    ];

    return (
        <AnimatePresence>
            <motion.div
                initial={{
                    opacity: 0,
                    y: 14,
                    scale: 0.96,
                }}
                animate={{
                    opacity: 1,
                    y: 0,
                    scale: 1,
                }}
                exit={{
                    opacity: 0,
                    y: 12,
                    scale: 0.97,
                }}
                transition={{
                    duration: 0.22,
                    ease: "easeOut",
                }}
                className="
                    absolute
                    right-0
                    top-[78px]
                    z-50

                    w-72

                    overflow-hidden

                    rounded-[34px]

                    border
                    border-white/70

                    bg-gradient-to-br
                    from-white
                    via-slate-50
                    to-sky-50

                    p-5

                    shadow-[16px_16px_40px_rgba(163,177,198,.22),-12px_-12px_32px_rgba(255,255,255,.95)]
                "
            >
                <div
                    className="
                        mb-5
                        flex
                        items-center
                        gap-4

                        rounded-[26px]

                        bg-white

                        p-4

                        shadow-inner
                    "
                >
                    <div
                        className="
                            flex
                            h-14
                            w-14
                            items-center
                            justify-center

                            rounded-full

                            bg-gradient-to-br
                            from-sky-400
                            to-cyan-500

                            text-lg
                            font-bold
                            text-white
                        "
                    >
                        A
                    </div>

                    <div>
                        <h3 className="font-bold text-slate-800">
                            Anand
                        </h3>

                        <p className="text-sm text-slate-500">
                            Premium User
                        </p>
                    </div>
                </div>

                <div className="space-y-3">
                    {menuItems.map((item) => {
                        const Icon = item.icon;

                        return (
                            <button
                                key={item.label}
                                type="button"
                                onClick={() => {
                                    item.action();
                                    onClose();
                                }}
                                className="
                                    group
                                    flex
                                    w-full
                                    items-center
                                    gap-4

                                    rounded-[22px]

                                    bg-white

                                    px-4
                                    py-4

                                    transition-all
                                    duration-300

                                    hover:-translate-y-0.5

                                    hover:shadow-[8px_8px_20px_rgba(163,177,198,.16),-6px_-6px_16px_rgba(255,255,255,.95)]
                                "
                            >
                                <div
                                    className="
                                        flex
                                        h-11
                                        w-11
                                        items-center
                                        justify-center

                                        rounded-2xl

                                        bg-sky-100

                                        text-sky-600
                                    "
                                >
                                    <Icon size={20} />
                                </div>

                                <span
                                    className={`font-semibold ${item.color}`}
                                >
                                    {item.label}
                                </span>
                            </button>
                        );
                    })}
                </div>
            </motion.div>
        </AnimatePresence>
    );
}

function DashboardNavbar({
                             onMenuClick,
                             onCreateLink,
                         }) {
    const navigate = useNavigate();

    const { logout } = useAuth();

    const [dropdownOpen, setDropdownOpen] =
        useState(false);

    const dropdownRef = useRef(null);

    const handleLogout = () => {
        logout();

        navigate("/login", {
            replace: true,
        });
    };

    useEffect(() => {
        function handleClickOutside(event) {
            if (
                dropdownRef.current &&
                !dropdownRef.current.contains(event.target)
            ) {
                setDropdownOpen(false);
            }
        }

        document.addEventListener(
            "mousedown",
            handleClickOutside
        );

        return () =>
            document.removeEventListener(
                "mousedown",
                handleClickOutside
            );
    }, []);

    return (
        <header
            className="
                sticky
                top-0
                z-40

                mx-4
                mt-4

                sm:mx-6
                sm:mt-6

                rounded-[36px]

                border
                border-white/70

                bg-gradient-to-br
                from-white/95
                via-slate-50/95
                to-sky-50/95

                backdrop-blur-2xl

                shadow-[18px_18px_45px_rgba(163,177,198,.20),-14px_-14px_40px_rgba(255,255,255,.95)]
            "
        >
            <div className="flex h-24 items-center justify-between px-5 sm:px-8 lg:px-10">

                {/* Left Section */}

                <div className="flex items-center gap-4">

                    {/* Mobile Menu */}

                    <button
                        type="button"
                        onClick={onMenuClick}
                        className="
                            flex
                            h-14
                            w-14
                            items-center
                            justify-center

                            rounded-[22px]

                            bg-white

                            text-slate-700

                            shadow-[8px_8px_18px_rgba(163,177,198,.16),-8px_-8px_18px_rgba(255,255,255,.95)]

                            transition-all

                            hover:-translate-y-0.5

                            lg:hidden
                        "
                    >
                        <Menu size={24} />
                    </button>

                    {/* Logo */}

                    <button
                        type="button"
                        onClick={() => navigate("/dashboard")}
                        className="
                            flex
                            items-center
                            gap-4

                            transition-all

                            hover:scale-[1.02]
                        "
                    >
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

                                shadow-[10px_10px_28px_rgba(14,165,233,.28)]
                            "
                        >
                            <Link2 size={30} />
                        </div>

                        <div className="text-left">
                            <h1
                                className="
                                    text-2xl
                                    font-extrabold
                                    tracking-tight
                                    text-slate-800
                                "
                            >
                                SnapLink
                            </h1>

                            <p className="hidden text-sm text-slate-500 sm:block">
                                Premium URL Shortener
                            </p>
                        </div>
                    </button>

                </div>


                {/* Right Section Starts */}

                <div className="flex items-center gap-3 sm:gap-4">

                    {/* Create Link */}

                    <ClayButton
                        onClick={onCreateLink}
                        className="hidden md:flex"
                    >
                        <Plus className="mr-2 h-5 w-5" />
                        Create Link
                    </ClayButton>

                    {/* Notification */}

                    <button
                        type="button"
                        className="
                            relative

                            flex
                            h-14
                            w-14

                            items-center
                            justify-center

                            rounded-[22px]

                            bg-white

                            text-slate-600

                            shadow-[8px_8px_18px_rgba(163,177,198,.16),-8px_-8px_18px_rgba(255,255,255,.95)]

                            transition-all

                            hover:-translate-y-0.5
                        "
                    >
                        <Bell size={22} />

                        <span
                            className="
                                absolute
                                right-4
                                top-4

                                h-3
                                w-3

                                rounded-full

                                border-2
                                border-white

                                bg-sky-500
                            "
                        />
                    </button>

                    {/* Profile */}

                    <div
                        ref={dropdownRef}
                        className="relative"
                    >
                        <button
                            type="button"
                            onClick={() =>
                                setDropdownOpen(
                                    (prev) => !prev
                                )
                            }
                            className="
                                flex
                                items-center
                                gap-3

                                rounded-[26px]

                                bg-white

                                px-3
                                py-3

                                sm:px-4

                                shadow-[8px_8px_18px_rgba(163,177,198,.16),-8px_-8px_18px_rgba(255,255,255,.95)]

                                transition-all

                                hover:-translate-y-0.5
                            "
                        >
                            <div
                                className="
                                    flex
                                    h-12
                                    w-12

                                    items-center
                                    justify-center

                                    rounded-full

                                    bg-gradient-to-br
                                    from-sky-400
                                    to-cyan-500

                                    font-bold

                                    text-white
                                "
                            >
                                A
                            </div>

                            <div className="hidden lg:block text-left">
                                <p className="font-semibold text-slate-800">
                                    Anand
                                </p>

                                <p className="text-xs text-slate-500">
                                    Premium User
                                </p>
                            </div>

                            <ChevronDown
                                className={`
                                    hidden
                                    lg:block

                                    h-5
                                    w-5

                                    text-slate-500

                                    transition-transform

                                    ${
                                    dropdownOpen
                                        ? "rotate-180"
                                        : ""
                                }
                                `}
                            />
                        </button>

                        <ProfileDropdown
                            open={dropdownOpen}
                            onClose={() =>
                                setDropdownOpen(false)
                            }
                            onProfile={() =>
                                navigate("/profile")
                            }
                            onSettings={() =>
                                navigate("/settings")
                            }
                            onLogout={handleLogout}
                        />
                    </div>
                </div>
            </div>
        </header>
    );
}

export default DashboardNavbar;