import clsx from "clsx";

import "./neumorphism.css";

function NeumorphicCard({
                            children,
                            className = "",
                            hover = true,
                        }) {

    return (

        <section
            className={clsx(
                "neo-surface p-8",
                hover && "neo-hover",
                className
            )}
        >

            {children}

        </section>

    );

}

export default NeumorphicCard;