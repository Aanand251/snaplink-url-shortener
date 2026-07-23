import ClayCard from "./ClayCard";

function StatCard({
                      title,
                      value,
                      subtitle,
                      icon: Icon,
                      iconColor = "text-sky-500",
                      iconBackground = "bg-sky-100",
                  }) {
    // Prevent React crash if an object is accidentally passed.
    let displayValue = value;

    if (
        displayValue !== null &&
        typeof displayValue === "object"
    ) {
        displayValue =
            displayValue.shortCode ??
            displayValue.originalUrl ??
            "-";
    }

    if (
        displayValue === undefined ||
        displayValue === null ||
        displayValue === ""
    ) {
        displayValue = "-";
    }

    return (
        <ClayCard
            className="
                group
                relative
                overflow-hidden
                h-full
                rounded-[36px]
                p-7
            "
        >
            {/* Background Blur */}

            <div
                className="
                    absolute
                    -right-10
                    -top-10
                    h-36
                    w-36
                    rounded-full
                    bg-sky-100/70
                    blur-3xl
                "
            />

            <div className="relative z-10">
                <div className="flex items-start justify-between">
                    <div className="min-w-0 flex-1">
                        <p
                            className="
                                text-sm
                                font-semibold
                                tracking-wide
                                text-slate-500
                            "
                        >
                            {title}
                        </p>

                        <h2
                            className="
                                mt-5
                                break-all
                                text-4xl
                                font-bold
                                tracking-tight
                                text-slate-800
                            "
                        >
                            {displayValue}
                        </h2>

                        {subtitle && (
                            <p
                                className="
                                    mt-3
                                    text-sm
                                    text-slate-400
                                "
                            >
                                {subtitle}
                            </p>
                        )}
                    </div>

                    <div
                        className={`
                            ml-4
                            flex
                            h-16
                            w-16
                            shrink-0
                            items-center
                            justify-center
                            rounded-[22px]
                            ${iconBackground}
                            shadow-[6px_6px_18px_rgba(163,177,198,.16),-6px_-6px_18px_rgba(255,255,255,.95)]
                        `}
                    >
                        {Icon && (
                            <Icon
                                size={30}
                                className={iconColor}
                            />
                        )}
                    </div>
                </div>

                <div
                    className="
                        mt-8
                        h-2
                        overflow-hidden
                        rounded-full
                        bg-slate-100
                    "
                >
                    <div
                        className="
                            h-full
                            w-3/4
                            rounded-full
                            bg-gradient-to-r
                            from-sky-400
                            via-cyan-400
                            to-blue-500
                        "
                    />
                </div>
            </div>
        </ClayCard>
    );
}

export default StatCard;