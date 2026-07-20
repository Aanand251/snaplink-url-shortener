import AuroraBackground from "../ui/AuroraBackground";
import MouseGlow from "../ui/MouseGlow";
import SpatialLights from "../ui/SpatialLights";
import GlassOrb from "../ui/GlassOrb";

function AuthBackground() {
    return (
        <>
            {/* Base Aurora */}
            <AuroraBackground />

            {/* Interactive mouse light */}
            <MouseGlow />

            {/* Ambient lighting */}
            <SpatialLights />

            {/* Floating glass elements */}
            <GlassOrb
                className="absolute left-[8%] top-24"
                size={220}
                opacity={0.18}
            />

            <GlassOrb
                className="absolute right-[10%] bottom-24"
                size={180}
                opacity={0.14}
            />
        </>
    );
}

export default AuthBackground;