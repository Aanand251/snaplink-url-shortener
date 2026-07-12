import { useCallback, useEffect, useMemo, useState } from "react";
import {
    BarChart3,
    Copy,
    ExternalLink,
    Link2,
    MousePointerClick,
    RefreshCw,
    Trophy,
    Pencil,
    Trash2,
} from "lucide-react";
import {
    Bar,
    BarChart,
    CartesianGrid,
    ResponsiveContainer,
    Tooltip,
    XAxis,
    YAxis,
} from "recharts";


import {
    getMyUrls,
    getUrlDashboard,
} from "../api/urlApi";
import CreateLinkModal from "../components/dashboard/CreateLinkModal";
import DashboardNavbar from "../components/dashboard/DashboardNavbar";
import StatCard from "../components/dashboard/StatCard";
import DeleteLinkModal from "../components/dashboard/DeleteLinkModal";
import EditLinkModal from "../components/dashboard/EditLinkModal";

function DashboardPage() {
    const [links, setLinks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = useState(false);
    const [error, setError] = useState("");
    const [copiedShortCode, setCopiedShortCode] =
        useState(null);
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

                const dashboardResults = await Promise.all(
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
                                originalUrl: url.originalUrl,
                                shortCode: url.shortCode,
                                createdAt:
                                    analytics.createdAt ??
                                    url.createdAt,
                            };
                        } catch (analyticsError) {
                            console.error(
                                `Unable to load analytics for ${url.shortCode}`,
                                analyticsError
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
            } catch (requestError) {
                console.error(
                    "Unable to load dashboard",
                    requestError
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

    const dashboardStats = useMemo(() => {
        const totalLinks = links.length;

        const totalClicks = links.reduce(
            (sum, link) =>
                sum + Number(link.totalClicks ?? 0),
            0
        );

        const topLink =
            links.length > 0
                ? links.reduce((currentTop, link) => {
                    const currentClicks = Number(
                        currentTop.totalClicks ?? 0
                    );

                    const linkClicks = Number(
                        link.totalClicks ?? 0
                    );

                    return linkClicks > currentClicks
                        ? link
                        : currentTop;
                })
                : null;

        return {
            totalLinks,
            totalClicks,
            topLink,
        };
    }, [links]);

    const chartData = useMemo(
        () =>
            links.map((link) => ({
                name: link.shortCode,
                clicks: Number(link.totalClicks ?? 0),
            })),
        [links]
    );

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

    const handleCopy = async (shortCode) => {
        const shortUrl = `${
            import.meta.env.VITE_API_BASE_URL
        }/r/${shortCode}`;

        try {
            await navigator.clipboard.writeText(shortUrl);

            setCopiedShortCode(shortCode);

            window.setTimeout(() => {
                setCopiedShortCode((current) =>
                    current === shortCode ? null : current
                );
            }, 1800);
        } catch (copyError) {
            console.error(
                "Unable to copy short URL",
                copyError
            );
        }
    };

    if (loading) {
        return (
            <main className="dashboard-loading">
                <div className="dashboard-loading__spinner" />
                <p>Loading your links...</p>
            </main>
        );
    }

    return (
        <div className="dashboard-page">
            <DashboardNavbar
                onCreateLink={() =>
                    setIsCreateModalOpen(true)
                }
            />

            <main className="dashboard-main">
                <section className="dashboard-heading">
                    <div>
                        <span className="dashboard-eyebrow">
                            Overview
                        </span>

                        <h1>Your link dashboard</h1>

                        <p>
                            Track your links and understand what
                            gets clicked.
                        </p>
                    </div>

                    <button
                        type="button"
                        className="dashboard-refresh-button"
                        onClick={() => loadDashboard(true)}
                        disabled={refreshing}
                    >
                        <RefreshCw
                            size={17}
                            className={
                                refreshing
                                    ? "dashboard-refresh-icon--active"
                                    : ""
                            }
                        />

                        <span>
                            {refreshing
                                ? "Refreshing"
                                : "Refresh"}
                        </span>
                    </button>
                </section>

                {error && (
                    <div className="dashboard-error">
                        <span>{error}</span>

                        <button
                            type="button"
                            onClick={() => loadDashboard()}
                        >
                            Try again
                        </button>
                    </div>
                )}

                <section className="dashboard-stats-grid">
                    <StatCard
                        label="Total links"
                        value={dashboardStats.totalLinks}
                        description="Active in your account"
                        icon={Link2}
                    />

                    <StatCard
                        label="Total clicks"
                        value={dashboardStats.totalClicks.toLocaleString()}
                        description="Across all shortened links"
                        icon={MousePointerClick}
                        accent
                    />

                    <StatCard
                        label="Top link"
                        value={
                            dashboardStats.topLink
                                ? dashboardStats.topLink.shortCode
                                : "—"
                        }
                        description={
                            dashboardStats.topLink
                                ? `${Number(
                                    dashboardStats.topLink
                                        .totalClicks ?? 0
                                ).toLocaleString()} clicks`
                                : "No link data yet"
                        }
                        icon={Trophy}
                    />
                </section>

                <section className="dashboard-panel dashboard-chart-panel">
                    <div className="dashboard-panel__header">
                        <div>
                            <span className="dashboard-panel__eyebrow">
                                Analytics
                            </span>

                            <h2>Clicks by link</h2>
                        </div>

                        <BarChart3 size={21} />
                    </div>

                    {chartData.length === 0 ? (
                        <div className="dashboard-empty">
                            <span className="dashboard-empty__icon">
                                <BarChart3 size={26} />
                            </span>

                            <h3>No analytics yet</h3>

                            <p>
                                Create your first shortened link to
                                start tracking clicks.
                            </p>
                        </div>
                    ) : (
                        <div className="dashboard-chart">
                            <ResponsiveContainer
                                width="100%"
                                height="100%"
                            >
                                <BarChart
                                    data={chartData}
                                    margin={{
                                        top: 16,
                                        right: 8,
                                        left: -20,
                                        bottom: 0,
                                    }}
                                >
                                    <CartesianGrid
                                        strokeDasharray="4 4"
                                        vertical={false}
                                        stroke="rgba(255, 255, 255, 0.06)"
                                    />

                                    <XAxis
                                        dataKey="name"
                                        axisLine={false}
                                        tickLine={false}
                                        tick={{
                                            fill: "#777381",
                                            fontSize: 12,
                                        }}
                                    />

                                    <YAxis
                                        allowDecimals={false}
                                        axisLine={false}
                                        tickLine={false}
                                        tick={{
                                            fill: "#777381",
                                            fontSize: 12,
                                        }}
                                    />

                                    <Tooltip
                                        cursor={{
                                            fill: "rgba(139, 92, 246, 0.08)",
                                        }}
                                        contentStyle={{
                                            background: "#15131b",
                                            border:
                                                "1px solid rgba(255, 255, 255, 0.1)",
                                            borderRadius: "12px",
                                            color: "#ffffff",
                                        }}
                                        labelStyle={{
                                            color: "#a78bfa",
                                        }}
                                    />

                                    <Bar
                                        dataKey="clicks"
                                        fill="#8b5cf6"
                                        radius={[8, 8, 2, 2]}
                                        maxBarSize={72}
                                    />
                                </BarChart>
                            </ResponsiveContainer>
                        </div>
                    )}
                </section>

                <section className="dashboard-panel">
                    <div className="dashboard-panel__header">
                        <div>
                            <span className="dashboard-panel__eyebrow">
                                Links
                            </span>

                            <h2>Your links</h2>
                        </div>

                        <span className="dashboard-link-count">
                            {links.length}{" "}
                            {links.length === 1
                                ? "link"
                                : "links"}
                        </span>
                    </div>

                    {links.length === 0 ? (
                        <div className="dashboard-empty">
                            <span className="dashboard-empty__icon">
                                <Link2 size={26} />
                            </span>

                            <h3>No links yet</h3>

                            <p>
                                Shorten your first URL and it will
                                appear here.
                            </p>

                            <button
                                type="button"
                                className="dashboard-empty__button"
                                onClick={() =>
                                    setIsCreateModalOpen(true)
                                }
                            >
                                Create your first link
                            </button>
                        </div>
                    ) : (
                        <div className="dashboard-links-list">
                            {links.map((link) => (
                                <article
                                    className="dashboard-link-row"
                                    key={link.id}
                                >
                                    <div className="dashboard-link-row__main">
                                        <span className="dashboard-link-row__icon">
                                            <Link2 size={18} />
                                        </span>

                                        <div className="dashboard-link-row__content">
                                            <strong>
                                                {link.shortCode}
                                            </strong>

                                            <span title={link.originalUrl}>
                                                {link.originalUrl}
                                            </span>
                                        </div>
                                    </div>

                                    <div className="dashboard-link-row__meta">
                                        <span>
                                            <MousePointerClick
                                                size={15}
                                            />

                                            {Number(
                                                link.totalClicks ?? 0
                                            ).toLocaleString()}{" "}
                                            clicks
                                        </span>

                                        <button
                                            type="button"
                                            className="dashboard-link-action"
                                            onClick={() => setEditingLink(link)}
                                            aria-label={`Edit ${link.shortCode}`}
                                            title="Edit destination"
                                        >
                                            <Pencil size={17} />
                                        </button>

                                        <button
                                            type="button"
                                            className="dashboard-link-action dashboard-link-action--danger"
                                            onClick={() => setDeletingLink(link)}
                                            aria-label={`Delete ${link.shortCode}`}
                                            title="Delete link"
                                        >
                                            <Trash2 size={17} />
                                        </button>

                                        <button
                                            type="button"
                                            className="dashboard-link-action"
                                            onClick={() =>
                                                handleCopy(
                                                    link.shortCode
                                                )
                                            }
                                            aria-label={`Copy ${link.shortCode}`}
                                            title="Copy short link"
                                        >
                                            {copiedShortCode ===
                                            link.shortCode ? (
                                                <span className="dashboard-copy-success">
                                                    Copied
                                                </span>
                                            ) : (
                                                <Copy size={17} />
                                            )}
                                        </button>

                                        <a
                                            href={`${import.meta.env.VITE_API_BASE_URL}/r/${link.shortCode}`}
                                            target="_blank"
                                            rel="noreferrer"
                                            aria-label={`Open ${link.shortCode}`}
                                            title="Open short link"
                                        >
                                            <ExternalLink size={17} />
                                        </a>
                                    </div>
                                </article>
                            ))}
                        </div>
                    )}
                </section>
            </main>

            <CreateLinkModal
                open={isCreateModalOpen}
                onClose={() =>
                    setIsCreateModalOpen(false)
                }
                onCreated={handleLinkCreated}
            />

            <EditLinkModal
                link={editingLink}
                onClose={() => setEditingLink(null)}
                onUpdated={handleLinkUpdated}
            />

            <DeleteLinkModal
                link={deletingLink}
                onClose={() => setDeletingLink(null)}
                onDeleted={handleLinkDeleted}
            />
        </div>
    );
}

export default DashboardPage;