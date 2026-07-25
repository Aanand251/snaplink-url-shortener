import {
    Globe2,
    Monitor,
    Compass,
} from "lucide-react";

import "./neumorphism.css";

function BrowserStatsCard({

                              browserStats = [],

                          }) {

    function getBrowserIcon(browser) {

        switch (browser) {

            case "Chrome":
                return <Globe2 size={18} />;

            case "Edge":
                return <Monitor size={18} />;

            default:
                return <Compass size={18} />;

        }

    }

    return (

        <section
            className="
                neo-card
                neo-hover
                rounded-[34px]
                p-8
            "
        >

            <h2
                className="
                    text-[28px]
                    font-bold
                    text-[#2F343C]
                "
            >
                Browser Usage
            </h2>

            <div className="mt-8 space-y-5">

                {

                    browserStats.length === 0

                        ? (

                            <div
                                className="
                                    py-12
                                    text-center
                                    text-[#64707C]
                                "
                            >
                                No Browser Data
                            </div>

                        )

                        : (

                            browserStats.map((browser, index) => (

                                <div

                                    key={index}

                                    className="
                                        neo-small
                                        flex
                                        items-center
                                        justify-between
                                        px-5
                                        py-4
                                    "

                                >

                                    <div className="flex items-center gap-4">

                                        <div
                                            className="
                                                neo-icon
                                                text-blue-600
                                            "
                                        >
                                            {getBrowserIcon(browser.browser)}
                                        </div>

                                        <p
                                            className="
                                                text-lg
                                                font-semibold
                                                text-[#2F343C]
                                            "
                                        >
                                            {browser.browser}
                                        </p>

                                    </div>

                                    <div
                                        className="
                                            text-3xl
                                            font-bold
                                            text-[#2F343C]
                                        "
                                    >
                                        {browser.clicks}
                                    </div>

                                </div>

                            ))

                        )

                }

            </div>

        </section>

    );

}

export default BrowserStatsCard;