import "./neumorphism.css";

function StatIcon({
                      icon: Icon,
                  }) {

    return (

        <div
            className="
                relative
                flex
                h-[72px]
                w-[72px]
                items-center
                justify-center
                rounded-[24px]
                bg-[#EEF2F5]
                transition-all
                duration-300
                group-hover:scale-[1.04]
            "
            style={{
                boxShadow:
                    "10px 10px 20px rgba(163,177,198,.24), -10px -10px 20px rgba(255,255,255,.95)",
            }}
        >

            {/* Soft Highlight */}

            <div
                className="
                    absolute
                    top-0
                    left-0
                    h-1/2
                    w-full
                    rounded-t-[24px]
                    bg-gradient-to-b
                    from-white/70
                    to-transparent
                "
            />

            <Icon
                size={28}
                strokeWidth={2}
                className="
                    relative
                    z-10
                    text-[#38404B]
                "
            />

        </div>

    );

}

export default StatIcon;