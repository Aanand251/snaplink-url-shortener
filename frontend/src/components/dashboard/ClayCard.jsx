import clsx from "clsx";

function ClayCard({
                      children,
                      className = "",
                      hover = true,
                      onClick,
                  }) {
    return (
        <div
            onClick={onClick}
            className={clsx(
                `
                rounded-[32px]
                border
                border-white/70

                bg-gradient-to-br
                from-white
                via-[#f9fbff]
                to-[#eef5ff]

                shadow-[12px_12px_28px_rgba(163,177,198,0.26),-12px_-12px_28px_rgba(255,255,255,0.95)]

                transition-all
                duration-300
                `,
                hover &&
                `
                    hover:-translate-y-1
                    hover:shadow-[18px_18px_42px_rgba(163,177,198,0.28),-18px_-18px_42px_rgba(255,255,255,1)]
                `,
                onClick && "cursor-pointer",
                className
            )}
        >
            {children}
        </div>
    );
}

export default ClayCard;