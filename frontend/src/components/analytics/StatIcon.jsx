function StatIcon({
                      icon: Icon,
                      bgColor = "bg-violet-500/15",
                      iconColor = "text-violet-400",
                  }) {
    return (
        <div
            className={`
                flex h-14 w-14 items-center justify-center
                rounded-2xl
                ${bgColor}
                transition-all
                duration-300
                group-hover:scale-110
            `}
        >
            <Icon
                size={24}
                className={`${iconColor} transition-transform duration-300 group-hover:rotate-6`}
            />
        </div>
    );
}

export default StatIcon;