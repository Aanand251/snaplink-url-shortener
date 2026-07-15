export function decodeJwtPayload(token) {
    if (!token) {
        return null;
    }

    try {
        const parts = token.split(".");

        if (parts.length !== 3) {
            return null;
        }

        const normalizedPayload = parts[1]
            .replace(/-/g, "+")
            .replace(/_/g, "/");

        const paddedPayload = normalizedPayload.padEnd(
            Math.ceil(normalizedPayload.length / 4) * 4,
            "=",
        );

        const payload = decodeURIComponent(
            atob(paddedPayload)
                .split("")
                .map(
                    (character) =>
                        `%${character
                            .charCodeAt(0)
                            .toString(16)
                            .padStart(2, "0")}`,
                )
                .join(""),
        );

        return JSON.parse(payload);
    } catch {
        return null;
    }
}

export function isTokenExpired(token) {
    const payload = decodeJwtPayload(token);

    if (!payload?.exp) {
        return true;
    }

    return payload.exp * 1000 <= Date.now();
}

export function isTokenUsable(token) {
    return Boolean(token) && !isTokenExpired(token);
}