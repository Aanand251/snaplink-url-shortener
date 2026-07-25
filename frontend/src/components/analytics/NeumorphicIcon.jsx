import "./neumorphism.css";

function NeumorphicIcon({

                            icon: Icon,

                            size = 22,

                        }) {

    return (

        <div

            className="
                neo-surface
                flex
                h-14
                w-14
                items-center
                justify-center
                rounded-2xl
                transition-all
                duration-300
                group-hover:scale-105
            "

            style={{

                boxShadow:
                    "7px 7px 14px rgba(163,177,198,.28), -7px -7px 14px rgba(255,255,255,.96)"

            }}

        >

            <Icon

                size={size}

                strokeWidth={2.2}

                className="text-[#2F343C]"

            />

        </div>

    );

}

export default NeumorphicIcon;