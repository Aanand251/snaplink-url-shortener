import StatIcon from "./StatIcon";

function AnalyticsCard({
                           title,
                           value,
                           secondaryValue,
                           icon,
                           bgColor = "bg-violet-500/10",
                           iconColor = "text-violet-400",
                       }) {
    const displayValue =
        value === null ||
        value === undefined ||
        value === ""
            ? "--"
            : value;

    return (
        <article
            className="
                group
                relative
                min-h-[210px]
                overflow-hidden
                rounded-3xl
                border
                border-white/10
                bg-[#18181B]/90
                p-7
                shadow-[0_18px_60px_rgba(0,0,0,0.18)]
                backdrop-blur-xl
                transition-all
                duration-300
                hover:-translate-y-1
                hover:border-violet-500/40
                hover:shadow-[0_24px_70px_rgba(109,40,217,0.18)]
            "
        >
            <div
                className="
                    pointer-events-none
                    absolute
                    -right-16
                    -top-16
                    h-40
                    w-40
                    rounded-full
                    bg-violet-500/5
                    blur-3xl
                    transition-all
                    duration-500
                    group-hover:bg-violet-500/15
                "
            />

            <div className="relative z-10 flex h-full flex-col justify-between">
                <div className="flex items-start justify-between gap-5">
                    <p className="pt-1 text-base font-medium text-zinc-400">
                        {title}
                    </p>

                    <StatIcon
                        icon={icon}
                        bgColor={bgColor}
                        iconColor={iconColor}
                    />
                </div>

                <div className="mt-10 min-w-0">
                    <p
                        className="
                            break-words
                            font-mono
                            text-3xl
                            font-semibold
                            tracking-tight
                            text-zinc-50
                            xl:text-[2rem]
                        "
                    >
                        {displayValue}
                    </p>

                    {secondaryValue && (
                        <p className="mt-2 font-mono text-sm text-zinc-500">
                            {secondaryValue}
                        </p>
                    )}
                </div>
            </div>
        </article>
    );
}

export default AnalyticsCard;