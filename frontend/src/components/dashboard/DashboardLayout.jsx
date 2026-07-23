import { useState } from "react";
import Sidebar from "./Sidebar";
import DashboardNavbar from "./DashboardNavbar";

function DashboardLayout({
                             children,
                             onCreateLink,
                         }) {
    const [sidebarOpen, setSidebarOpen] = useState(false);

    return (
        <div className="min-h-screen bg-[#eef4fb]">
            {/* Mobile Overlay */}
            {sidebarOpen && (
                <div
                    onClick={() => setSidebarOpen(false)}
                    className="fixed inset-0 z-30 bg-black/30 backdrop-blur-sm lg:hidden"
                />
            )}

            <div className="mx-auto flex min-h-screen max-w-[1700px]">

                {/* Sidebar */}

                <Sidebar
                    open={sidebarOpen}
                    onClose={() => setSidebarOpen(false)}
                    onCreateLink={onCreateLink}
                />

                {/* Main Content */}

                <div className="flex min-h-screen flex-1 flex-col">

                    <DashboardNavbar
                        onMenuClick={() => setSidebarOpen(true)}
                        onCreateLink={onCreateLink}
                    />

                    <main
                        className="
                            flex-1
                            px-4
                            py-5
                            sm:px-6
                            lg:px-8
                            xl:px-10
                        "
                    >
                        <div
                            className="
                                mx-auto
                                flex
                                w-full
                                max-w-[1450px]
                                flex-col
                                gap-7
                            "
                        >
                            {children}
                        </div>
                    </main>

                </div>

            </div>
        </div>
    );
}

export default DashboardLayout;