export function calculateDashboardStats(links) {
    const totalLinks = links.length;

    const totalClicks = links.reduce(
        (sum, link) => sum + Number(link.totalClicks ?? 0),
        0
    );

    const topLink =
        links.length === 0
            ? null
            : links.reduce((best, current) =>
                Number(current.totalClicks ?? 0) >
                Number(best.totalClicks ?? 0)
                    ? current
                    : best
            );

    return {
        totalLinks,
        totalClicks,
        topLink,
    };
}