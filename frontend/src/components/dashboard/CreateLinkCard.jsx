import { CalendarDays, Link2, Sparkles } from "lucide-react";

import ClayButton from "./ClayButton";
import ClayCard from "./ClayCard";
import ClayInput from "./ClayInput";

function CreateLinkCard({
                            originalUrl = "",
                            customAlias = "",
                            expiresAt = "",

                            onOriginalUrlChange,
                            onAliasChange,
                            onExpiryChange,

                            onSubmit,

                            loading = false,
                        }) {
    return (
        <ClayCard className="w-full">
            <div className="flex items-center gap-3">
                <div
                    className="
                        flex
                        h-12
                        w-12
                        items-center
                        justify-center
                        rounded-2xl
                        bg-cyan-400/15
                        text-cyan-300
                    "
                >
                    <Sparkles className="h-6 w-6" />
                </div>

                <div>
                    <h2 className="text-xl font-semibold text-white">
                        Create Short Link
                    </h2>

                    <p className="mt-1 text-sm text-zinc-400">
                        Generate a beautiful short URL in seconds.
                    </p>
                </div>
            </div>

            <form
                onSubmit={onSubmit}
                className="mt-8 space-y-6"
            >
                <ClayInput
                    label="Original URL"
                    name="originalUrl"
                    placeholder="https://example.com/very/long/url"
                    icon={Link2}
                    value={originalUrl}
                    onChange={onOriginalUrlChange}
                    required
                />

                <div className="grid gap-5 lg:grid-cols-2">
                    <ClayInput
                        label="Custom Alias"
                        name="alias"
                        placeholder="my-awesome-link"
                        value={customAlias}
                        onChange={onAliasChange}
                    />

                    <ClayInput
                        label="Expiry Date"
                        type="datetime-local"
                        name="expiresAt"
                        icon={CalendarDays}
                        value={expiresAt}
                        onChange={onExpiryChange}
                    />
                </div>

                <ClayButton
                    type="submit"
                    fullWidth
                    disabled={loading}
                >
                    {loading
                        ? "Creating..."
                        : "Create Short Link"}
                </ClayButton>
            </form>
        </ClayCard>
    );
}

export default CreateLinkCard;