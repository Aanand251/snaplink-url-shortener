export function createChartData(links) {
    return links.map((link) => ({
        name: link.shortCode,
        clicks: Number(link.totalClicks ?? 0),
    }));
}