import clsx from "clsx";

function ClayInput({
                       label,
                       error,
                       className = "",
                       ...props
                   }) {
    return (
        <div className="space-y-2">
            {label && (
                <label className="pl-2 text-sm font-semibold text-slate-600">
                    {label}
                </label>
            )}

            <input
                {...props}
                className={clsx(
                    `
                    w-full

                    rounded-3xl

                    border
                    border-white/80

                    bg-gradient-to-br
                    from-white
                    via-[#f8fbff]
                    to-[#eef6ff]

                    px-5
                    py-4

                    text-slate-700
                    placeholder:text-slate-400

                    outline-none

                    shadow-[inset_4px_4px_10px_rgba(163,177,198,0.16),inset_-4px_-4px_10px_rgba(255,255,255,0.95)]

                    transition-all
                    duration-300

                    focus:border-sky-300
                    focus:ring-4
                    focus:ring-sky-100
                    `,
                    className
                )}
            />

            {error && (
                <p className="pl-2 text-sm text-red-500">
                    {error}
                </p>
            )}
        </div>
    );
}

export default ClayInput;