import Navbar from "../components/ui/Navbar";
import HeroSection from "../components/ui/HeroSection";
import StatsSection from "../components/ui/StatsSection";
import FeaturesSection from "../components/ui/FeaturesSection";
import CTASection from "../components/ui/CTASection";
import FooterSection from "../components/ui/FooterSection";
import TestimonialsSection from "../components/ui/TestimonialsSection";
import FAQSection from "../components/ui/FAQSection";

function LandingPage() {
    return (
        <main className="min-h-screen overflow-hidden bg-[#050505] text-white">

            <Navbar />

            <HeroSection />

            <StatsSection />

            <FeaturesSection />

            <TestimonialsSection />

            <FAQSection />

            <CTASection />

            <FooterSection />

        </main>
    );
}

export default LandingPage;