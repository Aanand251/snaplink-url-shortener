import {
    Trash2,
    TriangleAlert,
    X,
} from "lucide-react";
import { useEffect, useState } from "react";

import { deleteUrl } from "../../api/urlApi";

function getErrorMessage(error) {
    const responseData = error.response?.data;

    if (typeof responseData === "string") {
        return responseData;
    }

    if (responseData?.message) {
        return responseData.message;
    }

    return "Unable to delete this link. Please try again.";
}

function DeleteLinkModal({
                             link,
                             onClose,
                             onDeleted,
                         }) {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        if (!link) {
            setLoading(false);
            setError("");
            return;
        }

        const handleEscape = (event) => {
            if (event.key === "Escape" && !loading) {
                onClose();
            }
        };

        document.body.style.overflow = "hidden";

        window.addEventListener(
            "keydown",
            handleEscape
        );

        return () => {
            document.body.style.overflow = "";

            window.removeEventListener(
                "keydown",
                handleEscape
            );
        };
    }, [link, loading, onClose]);

    if (!link) {
        return null;
    }

    const handleBackdropClick = (event) => {
        if (
            event.target === event.currentTarget &&
            !loading
        ) {
            onClose();
        }
    };

    const handleDelete = async () => {
        try {
            setLoading(true);
            setError("");

            await deleteUrl(link.id);

            await onDeleted();
        } catch (requestError) {
            console.error(
                "Unable to delete URL",
                requestError
            );

            setError(
                getErrorMessage(requestError)
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div
            className="link-action-modal"
            onMouseDown={handleBackdropClick}
            role="presentation"
        >
            <section
                className="link-action-modal__card link-delete-modal__card"
                role="dialog"
                aria-modal="true"
                aria-labelledby="delete-link-title"
            >
                <div className="link-action-modal__header">
                    <span className="link-delete-modal__icon">
                        <TriangleAlert size={22} />
                    </span>

                    <button
                        type="button"
                        className="link-action-modal__close"
                        onClick={onClose}
                        disabled={loading}
                        aria-label="Close delete link modal"
                    >
                        <X size={19} />
                    </button>
                </div>

                <span className="link-delete-modal__eyebrow">
                    Permanent action
                </span>

                <h2 id="delete-link-title">
                    Delete {link.shortCode}?
                </h2>

                <p className="link-action-modal__description">
                    This link will stop redirecting immediately.
                    Its stored URL data will be permanently
                    removed.
                </p>

                <div className="link-delete-preview">
                    <span>
                        /r/{link.shortCode}
                    </span>

                    <p title={link.originalUrl}>
                        {link.originalUrl}
                    </p>
                </div>

                {error && (
                    <div
                        className="link-action-modal__error"
                        role="alert"
                    >
                        {error}
                    </div>
                )}

                <div className="link-action-modal__actions">
                    <button
                        type="button"
                        className="link-action-cancel"
                        onClick={onClose}
                        disabled={loading}
                    >
                        Cancel
                    </button>

                    <button
                        type="button"
                        className="link-delete-button"
                        onClick={handleDelete}
                        disabled={loading}
                    >
                        {loading ? (
                            <>
                                <span className="create-link-spinner" />
                                Deleting
                            </>
                        ) : (
                            <>
                                <Trash2 size={16} />
                                Delete link
                            </>
                        )}
                    </button>
                </div>
            </section>
        </div>
    );
}

export default DeleteLinkModal;