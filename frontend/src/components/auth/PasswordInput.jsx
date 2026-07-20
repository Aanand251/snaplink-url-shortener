import { Eye, EyeOff, LockKeyhole } from "lucide-react";
import clsx from "clsx";

function PasswordInput({
                           id,
                           name,
                           label,
                           value,
                           onChange,
                           showPassword,
                           setShowPassword,
                           placeholder = "Enter your password",
                           autoComplete = "current-password",
                           required = false,
                           className,
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
                    border-white/8
                    bg-zinc-900/50
                    transition-all
                    duration-300
                    focus-within:border-cyan-300/60
                    focus-within:bg-zinc-900/70
                    focus-within:shadow-[0_0_25px_rgba(34,211,238,0.18)]
                "
            >
                {/* Left Icon */}
                <LockKeyhole
                    className="
                        pointer-events-none
                        absolute
                        left-4
                        top-1/2
                        h-5
                        w-5
                        -translate-y-1/2
                        text-zinc-500
                        transition-all
                        duration-300
                        group-focus-within:text-cyan-300
                        group-focus-within:scale-110
                    "
                />

                {/* Input */}
                <input
                    id={id}
                    name={name}
                    type={showPassword ? "text" : "password"}
                    value={value}
                    onChange={onChange}
                    autoComplete={autoComplete}
                    placeholder={placeholder}
                    required={required}
                    className="
                        h-14
                        w-full
                        rounded-2xl
                        bg-transparent
                        pl-12
                        pr-12
                        text-sm
                        font-medium
                        text-white
                        outline-none
                        transition-all
                        duration-300
                        placeholder:text-zinc-500
                        selection:bg-cyan-400/30
                    "
                />

                {/* Toggle Button */}
                <button
                    type="button"
                    onClick={() => setShowPassword((current) => !current)}
                    className="
                        absolute
                        right-4
                        top-1/2
                        flex
                        -translate-y-1/2
                        items-center
                        justify-center
                        text-zinc-500
                        transition-all
                        duration-300
                        hover:scale-110
                        hover:text-cyan-200
                        active:scale-95
                    "
                    aria-label={
                        showPassword
                            ? "Hide password"
                            : "Show password"
                    }
                >
                    {showPassword ? (
                        <EyeOff className="h-5 w-5" />
                    ) : (
                        <Eye className="h-5 w-5" />
                    )}
                </button>

                {/* Bottom Glow */}
                <div
                    className="
                        pointer-events-none
                        absolute
                        inset-x-8
                        bottom-0
                        h-px
                        scale-x-0
                        bg-gradient-to-r
                        from-transparent
                        via-cyan-300/70
                        to-transparent
                        transition-transform
                        duration-300
                        group-focus-within:scale-x-100
                    "
                />
            </div>
        </div>
    );
}

export default PasswordInput;