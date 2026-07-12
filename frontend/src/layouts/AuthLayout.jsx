import { Outlet } from "react-router-dom";
import Logo from "../components/ui/Logo";

function AuthLayout() {
    return (
        <main className="relative min-h-screen overflow-hidden bg-[#08080c]">
            <div className="pointer-events-none absolute inset-0 bg-[radial-gradient(circle_at_20%_20%,rgba(124,58,237,0.16),transparent_28%),radial-gradient(circle_at_80%_70%,rgba(76,29,149,0.12),transparent_30%)]" />

            <div className="pointer-events-none absolute inset-0 bg-[linear-gradient(rgba(255,255,255,0.018)_1px,transparent_1px),linear-gradient(90deg,rgba(255,255,255,0.018)_1px,transparent_1px)] bg-[size:72px_72px]" />

            <div className="relative mx-auto flex min-h-screen max-w-[1500px] flex-col px-6 py-6 lg:px-10">
                <Logo />

                <div className="flex flex-1 items-center justify-center py-12">
                    <Outlet />
                </div>
            </div>
        </main>
    );
}

export default AuthLayout;