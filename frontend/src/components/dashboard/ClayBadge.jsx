import clsx from "clsx";

function ClayBadge({
                       children,
                       color = "blue",
                   }) {
    const colors = {
        blue: "bg-sky-100 text-sky-700",
        green: "bg-emerald-100 text-emerald-700",
        red: "bg-red-100 text-red-700",
        yellow: "bg-amber-100 text-amber-700",
        gray: "bg-slate-100 text-slate-700",
    };

    return (
        <span
            className={clsx(
                `
                inline-flex
                items-center
                justify-center

                rounded-full

                px-4
                py-1.5

                text-xs
                font-semibold

                shadow-[4px_4px_10px_rgba(163,177,198,0.18),-4px_-4px_10px_rgba(255,255,255,0.9)]
                `,
                colors[color]
            )}
        >
            {children}
        </span>
    );
}

export default ClayBadge;