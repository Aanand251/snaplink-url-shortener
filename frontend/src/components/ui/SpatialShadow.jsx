import clsx from "clsx";

function SpatialShadow({
                           className = "",
                           intensity = "medium",
                       }) {

    const shadow = {
        low: "opacity-40 blur-2xl scale-90",
        medium: "opacity-60 blur-3xl scale-100",
        high: "opacity-80 blur-[80px] scale-110",
    };

    return (

        <div
            className={clsx(
                `
                    pointer-events-none
                    absolute
                    left-1/2
                    bottom-[-42px]
                    h-20
                    w-[82%]
                    -translate-x-1/2
                    rounded-full
                    bg-black
                    transition-all
                    duration-500
                `,
                shadow[intensity],
                className
            )}
        />

    );

}

export default SpatialShadow;