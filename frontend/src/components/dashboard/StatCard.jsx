function StatCard({
                      label,
                      value,
                      description,
                      icon: Icon,
                      accent = false,
                  }) {
    return (
        <article
            className={`stat-card ${accent ? "stat-card--accent" : ""}`}
        >
            <div className="stat-card__header">
                <span className="stat-card__label">
                    {label}
                </span>

                {Icon && (
                    <span className="stat-card__icon">
                        <Icon size={19} strokeWidth={2} />
                    </span>
                )}
            </div>

            <strong className="stat-card__value">
                {value}
            </strong>

            <span className="stat-card__description">
                {description}
            </span>
        </article>
    );
}

export default StatCard;