import clsx from "clsx";

function AuthInput({
                       id,
                       name,
                       type = "text",
                       label,
                       placeholder,
                       value,
                       onChange,
                       icon: Icon,
                       autoComplete,
                       required = false,
                       className,
                       ...props
                   }) {
    return (
        <div className={clsx("space-y-2", className)}>
            <label
                htmlFor={id}
                className="block text-sm font-medium text-zinc-300"
            >
                {label}
            </label>

            <div
                className="
                    group
                    relative
                    overflow-hidden
                    rounded-2xl
                    border
                    border-white/10
                    bg-white/[0.04]
                    transition-all
                    duration-300
                    focus-within:border-cyan-400/40
                    focus-within:bg-white/[0.06]
                    focus-within:shadow-[0_0_0_4px_rgba(34,211,238,0.08)]
                "
            >
                {Icon && (
                    <Icon
                        className="
                            pointer-events-none
                            absolute
                            left-4
                            top-1/2
                            h-5
                            w-5
                            -translate-y-1/2
                            text-zinc-500
                            transition-colors
                            duration-300
                            group-focus-within:text-cyan-300
                        "
                    />
                )}

                <input
                    id={id}
                    name={name}
                    type={type}
                    value={value}
                    onChange={onChange}
                    autoComplete={autoComplete}
                    placeholder={placeholder}
                    required={required}
                    className="
                        h-14
                        w-full
                        bg-transparent
                        pl-12
                        pr-4
                        text-sm
                        text-white
                        outline-none
                        placeholder:text-zinc-500
                    "
                    {...props}
                />
            </div>
        </div>
    );
}

export default AuthInput;