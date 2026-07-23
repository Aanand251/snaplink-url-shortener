import { AlertTriangle } from "lucide-react";

import ClayButton from "./ClayButton";

function ErrorBanner({
                         message,
                         onRetry,
                     }) {
    if (!message) {
        return null;
    }

    return (
        <div
            className="
                mb-8
                flex
                flex-col
                items-start
                justify-between
                gap-5
                rounded-3xl
                border
                border-red-500/20
                bg-red-500/10
                px-6
                py-5
                md:flex-row
                md:items-center
            "
        >
            <div className="flex items-start gap-4">
                <div
                    className="
                        flex
                        h-12
                        w-12
                        items-center
                        justify-center
                        rounded-2xl
                        bg-red-500/20
                        text-red-400
                    "
                >
                    <AlertTriangle className="h-6 w-6" />
                </div>

                <div>
                    <h3 className="font-semibold text-white">
                        Something went wrong
                    </h3>

                    <p className="mt-1 text-sm text-zinc-300">
                        {message}
                    </p>
                </div>
            </div>

            <ClayButton
                variant="danger"
                onClick={onRetry}
            >
                Try Again
            </ClayButton>
        </div>
    );
}

export default ErrorBanner;