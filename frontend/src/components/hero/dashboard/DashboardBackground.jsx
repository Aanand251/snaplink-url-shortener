function DashboardBackground() {

    return (

        <>

            {/* Glass Reflection */}

            <div
                className="
                    absolute
                    inset-0
                    bg-gradient-to-br
                    from-white/10
                    via-white/[0.03]
                    to-transparent
                "
            />

            {/* Top Border */}

            <div
                className="
                    absolute
                    inset-x-0
                    top-0
                    h-px
                    bg-gradient-to-r
                    from-transparent
                    via-white/40
                    to-transparent
                "
            />

            {/* Bottom Glow */}

            <div
                className="
                    absolute
                    left-1/2
                    bottom-[-180px]
                    h-[300px]
                    w-[500px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-400/10
                    blur-[120px]
                "
            />

            {/* Noise */}

            <div
                className="
                    absolute
                    inset-0
                    opacity-[0.03]
                    bg-[radial-gradient(circle_at_center,white_1px,transparent_1px)]
                    bg-[size:18px_18px]
                "
            />

        </>

    );

}

export default DashboardBackground;