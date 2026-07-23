import { Link2 } from "lucide-react";

import ClayCard from "./ClayCard";
import ClayButton from "./ClayButton";
import EmptyState from "./EmptyState";
import LinkRow from "./LinkRow";

function RecentLinksCard({
                             links,
                             copiedShortCode,
                             onCopy,
                             onEdit,
                             onDelete,
                             onAnalytics,
                             onCreate,
                         }) {
    return (
        <ClayCard
            className="
                relative
                overflow-hidden

                mt-10

                rounded-[38px]

                p-8
            "
        >
            {/* Decorative Background */}

            <div
                className="
                    absolute
                    -right-20
                    -top-20

                    h-72
                    w-72

                    rounded-full

                    bg-sky-100/60

                    blur-3xl
                "
            />

            <div
                className="
                    absolute

                    bottom-0
                    left-0

                    h-44
                    w-44

                    rounded-full

                    bg-blue-50

                    blur-3xl
                "
            />

            <div className="relative z-10">
                {/* Header */}

                <div className="flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">
                    <div>
                        <span
                            className="
                                inline-flex

                                rounded-full

                                bg-sky-100

                                px-4
                                py-2

                                text-xs
                                font-semibold

                                uppercase
                                tracking-[0.18em]

                                text-sky-600
                            "
                        >
                            Recent Links
                        </span>

                        <h2
                            className="
                                mt-5

                                text-3xl
                                font-bold

                                text-slate-800
                            "
                        >
                            Your Short URLs
                        </h2>

                        <p
                            className="
                                mt-2

                                text-slate-500
                            "
                        >
                            Manage, edit and monitor all your
                            shortened links.
                        </p>
                    </div>

                    <div className="flex items-center gap-4">
                        <div
                            className="
                                rounded-full

                                bg-sky-50

                                px-6
                                py-3

                                text-sm
                                font-semibold

                                text-sky-700
                            "
                        >
                            {links.length}{" "}
                            {links.length === 1
                                ? "Link"
                                : "Links"}
                        </div>

                        {links.length > 0 && (
                            <ClayButton
                                onClick={onCreate}
                            >
                                Create Link
                            </ClayButton>
                        )}
                    </div>
                </div>

                {/* Content */}

                <div className="mt-10">
                    {links.length === 0 ? (
                        <EmptyState
                            icon={Link2}
                            title="No Links Yet"
                            description="Create your first shortened URL and it will appear here."
                            buttonText="Create Link"
                            onButtonClick={onCreate}
                        />
                    ) : (
                        <div className="space-y-6">
                            {links.map((link) => (
                                <LinkRow
                                    key={link.id}
                                    link={link}
                                    copied={
                                        copiedShortCode ===
                                        link.shortCode
                                    }
                                    onCopy={onCopy}
                                    onEdit={onEdit}
                                    onDelete={onDelete}
                                    onAnalytics={
                                        onAnalytics
                                    }
                                />
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </ClayCard>
    );
}

export default RecentLinksCard;