import { useNavigate } from "react-router-dom";

import AnalyticsCard from "../components/dashboard/AnalyticsCard";
import CreateLinkModal from "../components/dashboard/CreateLinkModal";
import DashboardHeading from "../components/dashboard/DashboardHeading";
import DashboardLayout from "../components/dashboard/DashboardLayout";
import DashboardStats from "../components/dashboard/DashboardStats";
import DeleteLinkModal from "../components/dashboard/DeleteLinkModal";
import EditLinkModal from "../components/dashboard/EditLinkModal";
import ErrorBanner from "../components/dashboard/ErrorBanner";
import RecentLinksCard from "../components/dashboard/RecentLinksCard";

import useDashboard from "../hooks/useDashboard";

function DashboardPage() {
    const navigate = useNavigate();

    const {
        loading,
        refreshing,
        error,
        links,
        dashboardStats,
        chartData,
        copiedShortCode,

        isCreateModalOpen,
        editingLink,
        deletingLink,

        setEditingLink,
        setDeletingLink,
        setIsCreateModalOpen,

        loadDashboard,

        handleCopy,
        handleLinkCreated,
        handleLinkUpdated,
        handleLinkDeleted,
    } = useDashboard();

    if (loading) {
        return (
            <DashboardLayout
                onCreateLink={() =>
                    setIsCreateModalOpen(true)
                }
            >
                <div className="flex min-h-[70vh] items-center justify-center">
                    <div className="flex flex-col items-center gap-5">
                        <div className="h-14 w-14 animate-spin rounded-full border-4 border-cyan-400 border-t-transparent" />

                        <p className="text-zinc-400">
                            Loading your dashboard...
                        </p>
                    </div>
                </div>
            </DashboardLayout>
        );
    }

    return (
        <>
            <DashboardLayout
                onCreateLink={() =>
                    setIsCreateModalOpen(true)
                }
            >
                <DashboardHeading
                    refreshing={refreshing}
                    onRefresh={() =>
                        loadDashboard(true)
                    }
                />

                <ErrorBanner
                    message={error}
                    onRetry={() =>
                        loadDashboard()
                    }
                />

                <DashboardStats
                    totalLinks={
                        dashboardStats.totalLinks
                    }
                    totalClicks={
                        dashboardStats.totalClicks
                    }
                    topLink={dashboardStats.topLink}
                />

                <AnalyticsCard
                    chartData={chartData}
                />

                <RecentLinksCard
                    links={links}
                    copiedShortCode={
                        copiedShortCode
                    }
                    onCopy={handleCopy}
                    onEdit={setEditingLink}
                    onDelete={setDeletingLink}
                    onAnalytics={(
                        shortCode
                    ) =>
                        navigate(
                            `/analytics/${shortCode}`
                        )
                    }
                    onCreate={() =>
                        setIsCreateModalOpen(true)
                    }
                />
            </DashboardLayout>

            <CreateLinkModal
                open={isCreateModalOpen}
                onClose={() =>
                    setIsCreateModalOpen(false)
                }
                onCreated={handleLinkCreated}
            />

            <EditLinkModal
                link={editingLink}
                onClose={() =>
                    setEditingLink(null)
                }
                onUpdated={handleLinkUpdated}
            />

            <DeleteLinkModal
                link={deletingLink}
                onClose={() =>
                    setDeletingLink(null)
                }
                onDeleted={handleLinkDeleted}
            />
        </>
    );
}

export default DashboardPage;