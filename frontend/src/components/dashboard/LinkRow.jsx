import {
    ChartColumn,
    Copy,
    ExternalLink,
    Link2,
    MousePointerClick,
    Pencil,
    Trash2,
    Check,
} from "lucide-react";

import ClayCard from "./ClayCard";
import ClayIconButton from "./ClayIconButton";

function LinkRow({
                     link,
                     copied,
                     onCopy,
                     onEdit,
                     onDelete,
                     onAnalytics,
                 }) {
    return (
        <ClayCard
            className="
                relative
                overflow-hidden

                rounded-[34px]

                p-7

                transition-all
                duration-300

                hover:-translate-y-1
                hover:shadow-[14px_14px_34px_rgba(163,177,198,.22),-14px_-14px_34px_rgba(255,255,255,.95)]
            "
        >
            {/* Decorative Blob */}

            <div
                className="
                    absolute

                    -right-12
                    -top-12

                    h-36
                    w-36

                    rounded-full

                    bg-sky-100/70

                    blur-3xl
                "
            />

            <div className="relative z-10">
                {/* Top */}

                <div className="flex items-start justify-between gap-5 flex-wrap">
                    <div className="flex items-start gap-5 min-w-0 flex-1">
                        <div
                            className="
                                flex

                                h-16
                                w-16

                                shrink-0

                                items-center
                                justify-center

                                rounded-[22px]

                                bg-sky-100

                                shadow-[6px_6px_18px_rgba(163,177,198,.18),-6px_-6px_18px_rgba(255,255,255,.95)]
                            "
                        >
                            <Link2
                                size={28}
                                className="text-sky-600"
                            />
                        </div>

                        <div className="min-w-0">
                            <h3 className="text-xl font-bold text-slate-800">
                                {link.shortCode}
                            </h3>

                            <p className="mt-2 break-all text-sm leading-6 text-slate-500">
                                {link.originalUrl}
                            </p>
                        </div>
                    </div>

                    <div
                        className="
                            flex
                            items-center
                            gap-2

                            rounded-full

                            bg-sky-50

                            px-5
                            py-3

                            text-sm
                            font-semibold

                            text-sky-700
                        "
                    >
                        <MousePointerClick size={17} />

                        {Number(
                            link.totalClicks ?? 0
                        ).toLocaleString()}{" "}
                        Clicks
                    </div>
                </div>

                {/* Divider */}

                <div className="my-7 h-px bg-slate-100" />

                {/* Actions */}

                <div className="flex flex-wrap gap-3">
                    <ClayIconButton
                        onClick={() => onEdit(link)}
                        title="Edit"
                    >
                        <Pencil size={18} />
                    </ClayIconButton>

                    <ClayIconButton
                        onClick={() =>
                            onAnalytics(link.shortCode)
                        }
                        title="Analytics"
                    >
                        <ChartColumn size={18} />
                    </ClayIconButton>

                    <ClayIconButton
                        onClick={() =>
                            onCopy(link.shortCode)
                        }
                        title="Copy"
                    >
                        {copied ? (
                            <Check
                                size={18}
                                className="text-emerald-500"
                            />
                        ) : (
                            <Copy size={18} />
                        )}
                    </ClayIconButton>

                    <a
                        href={`${import.meta.env.VITE_API_BASE_URL}/r/${link.shortCode}`}
                        target="_blank"
                        rel="noreferrer"
                    >
                        <ClayIconButton title="Open">
                            <ExternalLink size={18} />
                        </ClayIconButton>
                    </a>

                    <ClayIconButton
                        onClick={() => onDelete(link)}
                        title="Delete"
                        className="
                            text-red-500

                            hover:text-red-600
                        "
                    >
                        <Trash2 size={18} />
                    </ClayIconButton>
                </div>
            </div>
        </ClayCard>
    );
}

export default LinkRow;