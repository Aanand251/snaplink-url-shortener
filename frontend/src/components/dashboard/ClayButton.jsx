import clsx from "clsx";

function ClayButton({
                        children,
                        type = "button",
                        onClick,
                        disabled = false,
                        fullWidth = false,
                        className = "",
                    }) {
    return (
        <button
            type={type}
            onClick={onClick}
            disabled={disabled}
            className={clsx(
                `
                inline-flex
                items-center
                justify-center
                gap-2

                rounded-full

                px-7
                py-3

                text-sm
                font-semibold
                text-white

                bg-gradient-to-r
                from-sky-400
                via-cyan-400
                to-blue-500

                shadow-[8px_8px_18px_rgba(163,177,198,0.30),-8px_-8px_18px_rgba(255,255,255,0.95)]

                transition-all
                duration-300

                hover:-translate-y-1
                hover:scale-[1.02]

                active:translate-y-[2px]
                active:scale-[0.98]

                disabled:cursor-not-allowed
                disabled:opacity-50
                `,
                fullWidth && "w-full",
                className
            )}
        >
            {children}
        </button>
    );
}

export default ClayButton;