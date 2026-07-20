import HeroBadge from "./HeroBadge";
import HeroHeadline from "./HeroHeadline";
import HeroButtons from "./HeroButtons";
import HeroFeatures from "./HeroFeatures";

function HeroContent() {
    return (
        <div
            className="
                relative
                z-20
                mx-auto
                max-w-4xl
                text-center
            "
        >

            <HeroBadge />

            <HeroHeadline />

            <HeroButtons />

            <HeroFeatures />

        </div>
    );
}

export default HeroContent;