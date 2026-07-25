import "./neumorphism.css";
import StatIcon from "./StatIcon";

function AnalyticsCard({
                           title,
                           value,
                           secondaryValue,
                           icon,
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
                neo-card
                neo-hover
                group
                relative
                overflow-hidden
                rounded-[34px]
                p-10
                min-h-[260px]
                flex
                flex-col
                justify-between
            "
        >

            {/* Top Ambient Highlight */}

            <div
                className="
                    absolute
                    inset-x-0
                    top-0
                    h-24
                    rounded-t-[34px]
                    bg-gradient-to-b
                    from-white/70
                    via-white/20
                    to-transparent
                    pointer-events-none
                "
            />

            {/* Floating Icon */}

            <div className="relative z-10 flex justify-end">

                <StatIcon icon={icon} />

            </div>

            {/* Content */}

            <div
                className="
                    relative
                    z-10
                    flex
                    flex-col
                    gap-5
                "
            >

                <span
                    className="
                        text-[13px]
                        font-semibold
                        uppercase
                        tracking-[0.22em]
                        text-[#8C96A3]
                    "
                >

                    {title}

                </span>

                <h2
                    className="
                        leading-none
                        tracking-tight
                        font-bold
                        text-[#2F343C]
                        text-[46px]
                        xl:text-[52px]
                    "
                >

                    {displayValue}

                </h2>

                {secondaryValue && (

                    <p
                        className="
                            text-[15px]
                            font-medium
                            text-[#98A2AE]
                        "
                    >

                        {secondaryValue}

                    </p>

                )}

            </div>

        </article>

    );

}

export default AnalyticsCard;