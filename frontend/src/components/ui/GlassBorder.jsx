import clsx from "clsx";

function GlassBorder({ className = "" }) {
    return (
        <>
            {/* Outer Border */}

            <div
                className={clsx(
                    `
                        pointer-events-none
                        absolute
                        inset-0
                        rounded-[inherit]
                        border
                        border-white/10
                    `,
                    className
                )}
            />

            {/* Top Highlight */}

            <div
                className="
                    pointer-events-none
                    absolute
                    left-[8%]
                    top-0
                    h-px
                    w-[84%]
                    bg-gradient-to-r
                    from-transparent
                    via-white/70
                    to-transparent
                "
            />

            {/* Left Highlight */}

            <div
                className="
                    pointer-events-none
                    absolute
                    left-0
                    top-[10%]
                    h-[80%]
                    w-px
                    bg-gradient-to-b
                    from-transparent
                    via-white/20
                    to-transparent
                "
            />

            {/* Inner Rim */}

            <div
                className="
                    pointer-events-none
                    absolute
                    inset-[1px]
                    rounded-[inherit]
                    border
                    border-white/5
                "
            />
        </>
    );
}

export default GlassBorder;