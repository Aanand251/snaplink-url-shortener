import {
    ExternalLink,
    MousePointerClick,
} from "lucide-react";

import "./neumorphism.css";

function TopLinksCard({

                          links = [],

                      }) {

    return (

        <section
            className="
                neo-card
                neo-hover
                neo-highlight
                rounded-[34px]
                p-8
            "
        >

            <div>

                <p className="neo-title">

                    Performance

                </p>

                <h2 className="neo-heading text-[28px]">

                    Top Links

                </h2>

            </div>

            <div className="mt-8 space-y-5">

                {

                    links.length === 0 ?

                        (

                            <div
                                className="
                                py-20
                                text-center
                                neo-secondary
                            "
                            >

                                No Link Data

                            </div>

                        )

                        :

                        (

                            links.map((link,index)=>(

                                <div
                                    key={index}
                                    className="
                                    neo-small
                                    flex
                                    items-center
                                    justify-between
                                    px-5
                                    py-5
                                "
                                >

                                    <div>

                                        <p className="font-semibold">

                                            {link.shortCode}

                                        </p>

                                        <p className="neo-secondary mt-2">

                                            {link.originalUrl}

                                        </p>

                                    </div>

                                    <div
                                        className="
                                        flex
                                        items-center
                                        gap-5
                                    "
                                    >

                                        <div className="text-right">

                                            <p className="font-bold">

                                                {link.clicks}

                                            </p>

                                            <p className="neo-secondary">

                                                Clicks

                                            </p>

                                        </div>

                                        <div className="neo-icon">

                                            <ExternalLink size={18}/>

                                        </div>

                                    </div>

                                </div>

                            ))

                        )

                }

            </div>

        </section>

    );

}

export default TopLinksCard;