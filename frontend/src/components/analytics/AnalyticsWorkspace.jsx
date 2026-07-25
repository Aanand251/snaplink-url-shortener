import "./neumorphism.css";

function AnalyticsWorkspace({ children }) {

    return (

        <section
            className="
                neo-card
                neo-highlight
                relative
                overflow-hidden
                rounded-[42px]
                px-8
                py-8
                md:px-10
                md:py-10
                xl:px-12
                xl:py-12
            "
        >

            {/* Top Ambient Glow */}

            <div
                className="
                    pointer-events-none
                    absolute
                    inset-x-0
                    top-0
                    h-40
                    opacity-90
                    bg-gradient-to-b
                    from-white/80
                    via-white/25
                    to-transparent
                "
            />

            {/* Right Floating Light */}

            <div
                className="
                    pointer-events-none
                    absolute
                    -right-44
                    top-24
                    h-80
                    w-80
                    rounded-full
                    opacity-60
                    blur-[90px]
                "
                style={{
                    background:
                        "radial-gradient(circle, rgba(255,255,255,.70) 0%, rgba(255,255,255,.15) 70%, transparent 100%)"
                }}
            />

            {/* Left Floating Light */}

            <div
                className="
                    pointer-events-none
                    absolute
                    -left-44
                    bottom-0
                    h-72
                    w-72
                    rounded-full
                    opacity-55
                    blur-[90px]
                "
                style={{
                    background:
                        "radial-gradient(circle, rgba(255,255,255,.65) 0%, rgba(255,255,255,.12) 70%, transparent 100%)"
                }}
            />

            {/* Soft Center Reflection */}

            <div
                className="
                    pointer-events-none
                    absolute
                    left-1/2
                    top-1/2
                    h-[520px]
                    w-[520px]
                    -translate-x-1/2
                    -translate-y-1/2
                    rounded-full
                    opacity-25
                    blur-[140px]
                "
                style={{
                    background:
                        "radial-gradient(circle, rgba(255,255,255,.55), transparent 72%)"
                }}
            />

            {/* Main Content */}

            <div
                className="
                    relative
                    z-10
                    space-y-10
                "
            >
                {children}
            </div>

        </section>

    );

}

export default AnalyticsWorkspace;