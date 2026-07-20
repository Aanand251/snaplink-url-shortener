import FloatingWindow from "./FloatingWindow";
import DepthLayer from "./DepthLayer";

function FloatingCard({
                          children,
                          className = "",
                          depth = 40,
                          delay = 0,
                          duration = 8,
                      }) {
    return (
        <DepthLayer
            depth={depth}
            delay={delay}
        >
            <FloatingWindow
                delay={delay}
                duration={duration}
                className={className}
            >
                {children}
            </FloatingWindow>
        </DepthLayer>
    );
}

export default FloatingCard;