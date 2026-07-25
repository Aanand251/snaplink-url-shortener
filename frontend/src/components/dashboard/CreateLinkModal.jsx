import {
    CalendarClock,
    Check,
    Info,
    Link2,
    ShieldCheck,
    Sparkles,
    Tag,
    X,
} from "lucide-react";

import { AnimatePresence, motion } from "framer-motion";
import { useEffect, useMemo, useState } from "react";

import { createShortUrl } from "../../api/urlApi";

import ClayButton from "../dashboard/ClayButton";
import ClayCard from "../dashboard/ClayCard";
import ClayIconButton from "../dashboard/ClayIconButton";

const initialForm = {
    originalUrl: "",
    customAlias: "",
    expiresAt: "",
};

function getErrorMessage(error) {
    const responseData = error.response?.data;

    if (typeof responseData === "string") {
        return responseData;
    }

    if (responseData?.message) {
        return responseData.message;
    }

    if (responseData?.errors) {
        const validationErrors = Object.values(responseData.errors);

        if (validationErrors.length > 0) {
            return validationErrors[0];
        }
    }

    return "Unable to create your SnapLink.";
}

function CreateLinkModal({
                             open,
                             onClose,
                             onCreated,
                         }) {

    const [form, setForm] = useState(initialForm);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {

        if (!open) return;

        document.body.style.overflow = "hidden";

        const handleEscape = (event) => {

            if (event.key === "Escape" && !loading) {
                onClose();
            }

        };

        window.addEventListener("keydown", handleEscape);

        return () => {

            document.body.style.overflow = "";

            window.removeEventListener(
                "keydown",
                handleEscape
            );

        };

    }, [open, loading, onClose]);

    useEffect(() => {

        if (!open) {

            setForm(initialForm);
            setLoading(false);
            setError("");

        }

    }, [open]);

    const previewUrl = useMemo(() => {

        if (form.customAlias.trim()) {
            return `snaplink.app/${form.customAlias.trim()}`;
        }

        return "snaplink.app/auto-generated";

    }, [form.customAlias]);

    const isValidUrl =
        form.originalUrl.startsWith("http://") ||
        form.originalUrl.startsWith("https://");

    const handleBackdropClick = (event) => {

        if (
            event.target === event.currentTarget &&
            !loading
        ) {
            onClose();
        }

    };

    const handleChange = (event) => {

        const { name, value } = event.target;

        setForm((current) => ({
            ...current,
            [name]: value,
        }));

        if (error) {
            setError("");
        }

    };

    const handleSubmit = async (event) => {

        event.preventDefault();

        const originalUrl = form.originalUrl.trim();
        const customAlias = form.customAlias.trim();

        if (!originalUrl) {
            setError("Original URL is required.");
            return;
        }

        if (
            !originalUrl.startsWith("http://") &&
            !originalUrl.startsWith("https://")
        ) {
            setError(
                "URL must start with http:// or https://"
            );
            return;
        }

        if (
            customAlias &&
            (customAlias.length < 4 ||
                customAlias.length > 30)
        ) {
            setError(
                "Alias must be between 4 and 30 characters."
            );
            return;
        }

        if (
            customAlias &&
            !/^[a-zA-Z0-9_-]+$/.test(customAlias)
        ) {
            setError(
                "Alias can contain only letters, numbers, '-' and '_'."
            );
            return;
        }

        setLoading(true);
        setError("");

        try {

            const payload = {

                originalUrl,

                customAlias:
                    customAlias || null,

                expiresAt:
                    form.expiresAt || null,

            };

            const created =
                await createShortUrl(payload);

            await onCreated(created);

        } catch (err) {

            console.error(err);

            setError(getErrorMessage(err));

        } finally {

            setLoading(false);

        }

    };

    return (

        <AnimatePresence>

            {open && (

                <motion.div

                    className="
                        fixed
                        inset-0
                        z-50

                        flex
                        items-center
                        justify-center

                        bg-slate-900/35
                        backdrop-blur-md

                        p-4
                    "

                    onMouseDown={handleBackdropClick}

                    initial={{ opacity: 0 }}

                    animate={{ opacity: 1 }}

                    exit={{ opacity: 0 }}

                >

                    <motion.div

                        className="
                            w-full
                            max-w-4xl
                        "

                        initial={{
                            opacity: 0,
                            y: 24,
                            scale: .98,
                        }}

                        animate={{
                            opacity: 1,
                            y: 0,
                            scale: 1,
                        }}

                        exit={{
                            opacity: 0,
                            y: 16,
                            scale: .98,
                        }}

                        transition={{
                            duration: .28,
                            ease: "easeOut",
                        }}

                    >

                        <ClayCard

                            className="
                                rounded-[32px]

                                p-8

                                max-h-[85vh]

                                overflow-y-auto
                            "

                        >

                            {/* ---------- Header ---------- */}

                            <div className="flex items-start justify-between">

                                <div className="space-y-2">

                                    <div className="flex items-center gap-3">

                                        <div
                                            className="
                                                flex
                                                h-12
                                                w-12
                                                items-center
                                                justify-center

                                                rounded-2xl

                                                bg-sky-100
                                            "
                                        >
                                            <Sparkles
                                                className="text-sky-600"
                                                size={22}
                                            />
                                        </div>

                                        <div>

                                            <h2 className="text-3xl font-bold text-slate-800">
                                                Create SnapLink
                                            </h2>

                                            <p className="mt-1 text-slate-500">
                                                Create secure and memorable short links.
                                            </p>

                                        </div>

                                    </div>

                                </div>

                                <ClayIconButton
                                    onClick={onClose}
                                    title="Close"
                                >
                                    <X size={18} />
                                </ClayIconButton>

                            </div>

                            <div className="my-8 h-px bg-slate-100" />

                            {error && (

                                <motion.div

                                    initial={{
                                        opacity: 0,
                                        x: -10,
                                    }}

                                    animate={{
                                        opacity: 1,
                                        x: 0,
                                    }}

                                    className="
                                        mb-6

                                        rounded-2xl

                                        border
                                        border-red-100

                                        bg-red-50

                                        px-5
                                        py-4

                                        text-red-600
                                    "

                                >

                                    {error}

                                </motion.div>

                            )}

                            <form
                                onSubmit={handleSubmit}
                            >

                                {/* ========= CONTENT GRID ========= */}

                                <div
                                    className="
                                        grid
                                        gap-8

                                        lg:grid-cols-[1.4fr_.9fr]
                                    "
                                >
                                    {/* ================= LEFT PANEL ================= */}

                                    <div className="space-y-7">

                                        {/* Destination URL */}

                                        <div>

                                            <label className="mb-3 block text-sm font-semibold text-slate-700">
                                                Destination URL
                                            </label>

                                            <div
                                                className="
                                                    group

                                                    flex
                                                    items-center
                                                    gap-4

                                                    rounded-[24px]

                                                    border
                                                    border-slate-200/70

                                                    bg-white

                                                    px-5
                                                    py-4

                                                    transition-all
                                                    duration-200

                                                    shadow-[inset_3px_3px_8px_rgba(163,177,198,.10),inset_-3px_-3px_8px_rgba(255,255,255,.95)]

                                                    focus-within:border-sky-300
                                                    focus-within:shadow-[0_0_0_4px_rgba(56,189,248,.10)]
                                                "
                                            >

                                                <Link2
                                                    size={20}
                                                    className="
                                                        shrink-0
                                                        text-sky-600
                                                    "
                                                />

                                                <input
                                                    type="url"
                                                    name="originalUrl"
                                                    value={form.originalUrl}
                                                    onChange={handleChange}
                                                    placeholder="https://example.com/article"

                                                    className="
                                                        flex-1

                                                        bg-transparent

                                                        text-slate-700

                                                        placeholder:text-slate-400

                                                        outline-none
                                                    "
                                                />

                                                {form.originalUrl && (

                                                    <div
                                                        className={`
                                                            rounded-full
                                                            px-3
                                                            py-1

                                                            text-xs
                                                            font-semibold

                                                            transition-colors

                                                            ${
                                                            isValidUrl
                                                                ? "bg-emerald-100 text-emerald-600"
                                                                : "bg-red-100 text-red-600"
                                                        }
                                                        `}
                                                    >
                                                        {isValidUrl
                                                            ? "Valid"
                                                            : "Invalid"}
                                                    </div>

                                                )}

                                            </div>

                                        </div>



                                        {/* Alias */}

                                        <div>

                                            <label className="mb-3 block text-sm font-semibold text-slate-700">
                                                Custom Alias
                                            </label>

                                            <div
                                                className="
                                                    flex
                                                    items-center

                                                    overflow-hidden

                                                    rounded-[24px]

                                                    border
                                                    border-slate-200/70

                                                    bg-white

                                                    shadow-[inset_3px_3px_8px_rgba(163,177,198,.10),inset_-3px_-3px_8px_rgba(255,255,255,.95)]

                                                    focus-within:border-sky-300
                                                    focus-within:shadow-[0_0_0_4px_rgba(56,189,248,.10)]
                                                "
                                            >

                                                <div
                                                    className="
                                                        flex
                                                        items-center
                                                        gap-2

                                                        border-r
                                                        border-slate-100

                                                        bg-slate-50

                                                        px-5
                                                        py-4

                                                        text-sky-600
                                                        font-semibold
                                                    "
                                                >

                                                    <Tag size={18}/>

                                                    /

                                                </div>

                                                <input
                                                    name="customAlias"
                                                    value={form.customAlias}
                                                    onChange={handleChange}

                                                    placeholder="anand"

                                                    className="
                                                        flex-1

                                                        bg-transparent

                                                        px-5

                                                        outline-none

                                                        text-slate-700

                                                        placeholder:text-slate-400
                                                    "
                                                />

                                            </div>

                                            <p className="mt-2 text-xs text-slate-400">
                                                Optional • 4–30 characters
                                            </p>

                                        </div>



                                        {/* Expiry */}

                                        <div>

                                            <label className="mb-3 block text-sm font-semibold text-slate-700">
                                                Expiry Date
                                            </label>

                                            <div
                                                className="
                                                    flex
                                                    items-center
                                                    gap-4

                                                    rounded-[24px]

                                                    border
                                                    border-slate-200/70

                                                    bg-white

                                                    px-5
                                                    py-4

                                                    shadow-[inset_3px_3px_8px_rgba(163,177,198,.10),inset_-3px_-3px_8px_rgba(255,255,255,.95)]

                                                    focus-within:border-sky-300
                                                    focus-within:shadow-[0_0_0_4px_rgba(56,189,248,.10)]
                                                "
                                            >

                                                <CalendarClock
                                                    size={20}
                                                    className="text-sky-600"
                                                />

                                                <input
                                                    type="datetime-local"
                                                    name="expiresAt"
                                                    value={form.expiresAt}
                                                    onChange={handleChange}

                                                    className="
                                                        flex-1

                                                        bg-transparent

                                                        outline-none

                                                        text-slate-700
                                                    "
                                                />

                                            </div>

                                            <p className="mt-2 text-xs text-slate-400">
                                                Leave empty if the link should never expire.
                                            </p>

                                        </div>

                                    </div>

                                    {/* ================= RIGHT PANEL ================= */}

                                    <div className="space-y-6">

                                        {/* Live Preview */}

                                        <motion.div
                                            layout
                                            transition={{
                                                duration: 0.2,
                                            }}
                                        >
                                            <ClayCard
                                                className="
                                                    rounded-[28px]
                                                    p-6
                                                "
                                            >
                                                <div className="flex items-center justify-between">

                                                    <div>

                                                        <p className="text-xs font-semibold uppercase tracking-[0.2em] text-sky-600">
                                                            Live Preview
                                                        </p>

                                                        <motion.h3
                                                            key={previewUrl}
                                                            initial={{
                                                                opacity: 0,
                                                                y: 6,
                                                            }}
                                                            animate={{
                                                                opacity: 1,
                                                                y: 0,
                                                            }}
                                                            transition={{
                                                                duration: .18,
                                                            }}
                                                            className="
                                                                mt-4

                                                                break-all

                                                                text-xl
                                                                font-bold

                                                                text-slate-800
                                                            "
                                                        >
                                                            {previewUrl}
                                                        </motion.h3>

                                                    </div>

                                                    <div
                                                        className="
                                                            flex
                                                            h-14
                                                            w-14
                                                            items-center
                                                            justify-center

                                                            rounded-2xl

                                                            bg-sky-100
                                                        "
                                                    >
                                                        <Link2
                                                            className="text-sky-600"
                                                            size={22}
                                                        />
                                                    </div>

                                                </div>

                                            </ClayCard>

                                        </motion.div>



                                        {/* Link Status */}

                                        <ClayCard
                                            className="
                                                rounded-[28px]
                                                p-6
                                            "
                                        >

                                            <p className="text-sm font-semibold text-slate-800">
                                                Link Status
                                            </p>

                                            <div className="mt-5 space-y-4">

                                                <div className="flex items-center gap-3">

                                                    <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-emerald-100">

                                                        <ShieldCheck
                                                            size={18}
                                                            className="text-emerald-600"
                                                        />

                                                    </div>

                                                    <div>

                                                        <p className="font-medium text-slate-700">
                                                            Secure Redirect
                                                        </p>

                                                        <p className="text-xs text-slate-400">
                                                            HTTPS enabled
                                                        </p>

                                                    </div>

                                                </div>

                                                <div className="flex items-center gap-3">

                                                    <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-sky-100">

                                                        <Check
                                                            size={18}
                                                            className="text-sky-600"
                                                        />

                                                    </div>

                                                    <div>

                                                        <p className="font-medium text-slate-700">
                                                            Analytics Ready
                                                        </p>

                                                        <p className="text-xs text-slate-400">
                                                            Every click is tracked
                                                        </p>

                                                    </div>

                                                </div>

                                                <div className="flex items-center gap-3">

                                                    <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-violet-100">

                                                        <Sparkles
                                                            size={18}
                                                            className="text-violet-600"
                                                        />

                                                    </div>

                                                    <div>

                                                        <p className="font-medium text-slate-700">
                                                            Custom Alias
                                                        </p>

                                                        <p className="text-xs text-slate-400">
                                                            Personalize your link
                                                        </p>

                                                    </div>

                                                </div>

                                            </div>

                                        </ClayCard>



                                        {/* Tips */}

                                        <ClayCard
                                            className="
                                                rounded-[28px]
                                                p-6
                                            "
                                        >

                                            <div className="flex items-start gap-3">

                                                <div
                                                    className="
                                                        flex
                                                        h-10
                                                        w-10
                                                        items-center
                                                        justify-center

                                                        rounded-xl

                                                        bg-amber-100
                                                    "
                                                >

                                                    <Info
                                                        size={18}
                                                        className="text-amber-600"
                                                    />

                                                </div>

                                                <div>

                                                    <h4 className="font-semibold text-slate-800">
                                                        Quick Tip
                                                    </h4>

                                                    <p className="mt-2 text-sm leading-6 text-slate-500">
                                                        Short, memorable aliases
                                                        improve readability and
                                                        make links easier to
                                                        share.
                                                    </p>

                                                </div>

                                            </div>

                                        </ClayCard>

                                    </div>

                                </div>
                                {/* ================= FOOTER ================= */}

                                <div
                                    className="
                                        mt-10

                                        flex
                                        flex-col-reverse
                                        gap-4

                                        border-t
                                        border-slate-100

                                        pt-6

                                        sm:flex-row
                                        sm:items-center
                                        sm:justify-between
                                    "
                                >

                                    <div className="text-sm text-slate-400">
                                        Your short link will be created instantly.
                                    </div>

                                    <div className="flex gap-3">

                                        <button
                                            type="button"
                                            onClick={onClose}
                                            disabled={loading}
                                            className="
                                                rounded-2xl

                                                px-6
                                                py-3

                                                font-semibold

                                                text-slate-600

                                                transition-all
                                                duration-200

                                                hover:bg-slate-100

                                                disabled:opacity-50
                                            "
                                        >
                                            Cancel
                                        </button>

                                        <ClayButton
                                            type="submit"
                                            disabled={loading}
                                            className="
                                                min-w-[190px]

                                                justify-center
                                            "
                                        >

                                            {loading ? (

                                                <div className="flex items-center gap-3">

                                                    <span
                                                        className="
                                                            h-5
                                                            w-5

                                                            rounded-full

                                                            border-2
                                                            border-white

                                                            border-t-transparent

                                                            animate-spin
                                                        "
                                                    />

                                                    Creating...

                                                </div>

                                            ) : (

                                                <div className="flex items-center gap-2">

                                                    <Check size={18} />

                                                    Create SnapLink

                                                </div>

                                            )}

                                        </ClayButton>

                                    </div>

                                </div>

                            </form>

                        </ClayCard>

                    </motion.div>

                </motion.div>

            )}

        </AnimatePresence>

    );

}

export default CreateLinkModal;