import AuroraBackground from "./AuroraBackground";
import MouseGlow from "./MouseGlow";
import SpatialLights from "./SpatialLights";
// import DynamicLight from "./DynamicLight";
import GlassOrb from "./GlassOrb";
import MouseParallax from "./MouseParallax";

import HeroContent from "./HeroContent";
import HeroDashboard from "./HeroDashboard";
import FloatingMetrics from "./FloatingMetrics";
import TrustedTech from "./TrustedTech";

function HeroSection() {
    return (
        <section
            className="
                relative
                isolate
                overflow-hidden
                bg-[#050505]
                text-white
            "
        >
            {/* Background Layers */}

            <AuroraBackground />

            <SpatialLights />

            {/*<DynamicLight />*/}

            <GlassOrb />

            <MouseGlow />

            {/* Soft Top Glow */}

            <div
                className="
                    pointer-events-none
                    absolute
                    left-1/2
                    top-0
                    h-[700px]
                    w-[700px]
                    -translate-x-1/2
                    rounded-full
                    bg-cyan-400/10
                    blur-[180px]
                "
            />

            {/* Bottom Glow */}

            <div
                className="
                    pointer-events-none
                    absolute
                    bottom-[-180px]
                    left-1/2
                    h-[600px]
                    w-[900px]
                    -translate-x-1/2
                    rounded-full
                    bg-white/5
                    blur-[220px]
                "
            />

            {/* Main Content */}

            <div
                className="
                    relative
                    z-20
                    mx-auto
                    flex
                    max-w-7xl
                    flex-col
                    px-6
                    pt-24
                    pb-20
                    lg:px-10
                "
            >
                <HeroContent />

                <MouseParallax>

                    <div
                        className="
                            relative
                            mt-12
                            lg:mt-16
                        "
                    >

                        <FloatingMetrics />

                        <HeroDashboard />

                    </div>

                </MouseParallax>

                <div className="mt-24">

                    <TrustedTech />

                </div>

            </div>
        </section>
    );
}

export default HeroSection;