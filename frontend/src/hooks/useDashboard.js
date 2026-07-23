import { useCallback, useEffect, useMemo, useState } from "react";

import { getMyUrls, getUrlDashboard } from "../api/urlApi";
import { createChartData } from "../utils/dashboard/chartData";
import { copyShortUrl } from "../utils/dashboard/copyShortUrl";
import { calculateDashboardStats } from "../utils/dashboard/dashboardStats";

function useDashboard() {
    const [links, setLinks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = useState(false);
    const [error, setError] = useState("");

    const [copiedShortCode, setCopiedShortCode] = useState(null);

    const [isCreateModalOpen, setIsCreateModalOpen] =
        useState(false);

    const [editingLink, setEditingLink] = useState(null);

    const [deletingLink, setDeletingLink] = useState(null);

    const loadDashboard = useCallback(
        async (isRefresh = false) => {
            try {
                setError("");

                if (isRefresh) {
                    setRefreshing(true);
                } else {
                    setLoading(true);
                }

                const urls = await getMyUrls();

                const dashboardResults =
                    await Promise.all(
                        urls.map(async (url) => {
                            try {
                                const analytics =
                                    await getUrlDashboard(
                                        url.shortCode
                                    );

                                return {
                                    ...url,
                                    ...analytics,
                                    id: url.id,
                                    originalUrl:
                                    url.originalUrl,
                                    shortCode:
                                    url.shortCode,
                                    createdAt:
                                        analytics.createdAt ??
                                        url.createdAt,
                                };
                            } catch (error) {
                                console.error(
                                    `Unable to load analytics for ${url.shortCode}`,
                                    error
                                );

                                return {
                                    ...url,
                                    totalClicks: 0,
                                    topBrowser: null,
                                    topDevice: null,
                                    topCountry: null,
                                    lastClickedAt: null,
                                };
                            }
                        })
                    );

                setLinks(dashboardResults);
            } catch (error) {
                console.error(
                    "Unable to load dashboard",
                    error
                );

                setError(
                    "Unable to load your dashboard. Please try again."
                );
            } finally {
                setLoading(false);
                setRefreshing(false);
            }
        },
        []
    );

    useEffect(() => {
        loadDashboard();
    }, [loadDashboard]);

    const dashboardStats = useMemo(
        () => calculateDashboardStats(links),
        [links]
    );

    const chartData = useMemo(
        () => createChartData(links),
        [links]
    );

    const handleCopy = async (shortCode) => {
        try {
            await copyShortUrl(shortCode);

            setCopiedShortCode(shortCode);

            setTimeout(() => {
                setCopiedShortCode((current) =>
                    current === shortCode
                        ? null
                        : current
                );
            }, 1800);
        } catch (error) {
            console.error(
                "Unable to copy short URL",
                error
            );
        }
    };

    const handleLinkCreated = async () => {
        setIsCreateModalOpen(false);
        await loadDashboard(true);
    };

    const handleLinkUpdated = async () => {
        setEditingLink(null);
        await loadDashboard(true);
    };

    const handleLinkDeleted = async () => {
        setDeletingLink(null);
        await loadDashboard(true);
    };

    return {
        loading,
        refreshing,
        error,

        links,

        chartData,
        dashboardStats,

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
    };
}

export default useDashboard;