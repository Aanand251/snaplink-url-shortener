import clsx from "clsx";

function ClayIconButton({
                            children,
                            onClick,
                            className = "",
                            size = "md",
                            title,
                            disabled = false,
                        }) {
    const sizes = {
        sm: "h-10 w-10",
        md: "h-12 w-12",
        lg: "h-14 w-14",
    };

    return (
        <button
            type="button"
            title={title}
            disabled={disabled}
            onClick={onClick}
            className={clsx(
                `
                flex
                items-center
                justify-center

                rounded-full

                bg-gradient-to-br
                from-white
                via-[#f9fbff]
                to-[#eef5ff]

                border
                border-white/80

                text-slate-600

                shadow-[8px_8px_20px_rgba(163,177,198,.25),-8px_-8px_20px_rgba(255,255,255,.95)]

                transition-all
                duration-300
                ease-out

                hover:-translate-y-1
                hover:scale-110

                active:scale-95

                hover:shadow-[12px_12px_28px_rgba(163,177,198,.30),-12px_-12px_28px_rgba(255,255,255,1)]

                disabled:cursor-not-allowed
                disabled:opacity-50
                `,
                sizes[size],
                className
            )}
        >
            {children}
        </button>
    );
}

export default ClayIconButton;