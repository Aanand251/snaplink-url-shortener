import RefreshButton from "./RefreshButton";

function DashboardHeading({
                              title = "Your Link Dashboard",
                              subtitle = "Track your links and understand what gets clicked.",
                              refreshing,
                              onRefresh,
                              rightContent,
                          }) {
    return (
        <section
            className="
                mb-8

                flex
                flex-col
                gap-8

                lg:flex-row
                lg:items-center
                lg:justify-between
            "
        >
            {/* Left */}

            <div className="max-w-3xl">
                <div
                    className="
                        inline-flex
                        items-center
                        gap-2

                        rounded-full

                        bg-sky-100

                        px-4
                        py-2

                        text-xs
                        font-semibold

                        uppercase
                        tracking-[0.18em]

                        text-sky-600
                    "
                >
                    Dashboard Overview
                </div>

                <h1
                    className="
                        mt-6

                        text-4xl
                        font-bold
                        leading-tight

                        text-slate-800

                        lg:text-5xl
                    "
                >
                    {title}
                </h1>

                <p
                    className="
                        mt-5

                        max-w-2xl

                        text-lg
                        leading-8

                        text-slate-500
                    "
                >
                    {subtitle}
                </p>
            </div>

            {/* Right */}

            <div
                className="
                    flex
                    items-center
                    gap-4
                "
            >
                {rightContent}

                <RefreshButton
                    loading={refreshing}
                    onClick={onRefresh}
                />
            </div>
        </section>
    );
}

export default DashboardHeading;