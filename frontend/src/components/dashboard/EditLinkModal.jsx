import {
    ArrowRight,
    Link2,
    Pencil,
    X,
} from "lucide-react";
import { useEffect, useState } from "react";

import { updateUrl } from "../../api/urlApi";

function getErrorMessage(error) {
    const responseData = error.response?.data;

    if (typeof responseData === "string") {
        return responseData;
    }

    if (responseData?.message) {
        return responseData.message;
    }

    if (responseData?.errors) {
        const validationErrors = Object.values(
            responseData.errors
        );

        if (validationErrors.length > 0) {
            return validationErrors[0];
        }
    }

    return "Unable to update this link. Please try again.";
}

function EditLinkModal({
                           link,
                           onClose,
                           onUpdated,
                       }) {
    const [originalUrl, setOriginalUrl] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        if (!link) {
            return;
        }

        setOriginalUrl(link.originalUrl ?? "");
        setError("");
    }, [link]);

    useEffect(() => {
        if (!link) {
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

    const handleSubmit = async (event) => {
        event.preventDefault();

        const destination = originalUrl.trim();

        if (!destination) {
            setError("Destination URL is required.");
            return;
        }

        if (
            !destination.startsWith("http://") &&
            !destination.startsWith("https://")
        ) {
            setError(
                "URL must start with http:// or https://"
            );

            return;
        }

        if (destination === link.originalUrl) {
            setError(
                "Enter a different destination URL."
            );

            return;
        }

        try {
            setLoading(true);
            setError("");

            await updateUrl(link.id, {
                originalUrl: destination,
            });

            await onUpdated();
        } catch (requestError) {
            console.error(
                "Unable to update URL",
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
                className="link-action-modal__card"
                role="dialog"
                aria-modal="true"
                aria-labelledby="edit-link-title"
            >
                <div className="link-action-modal__header">
                    <div className="link-action-modal__heading">
                        <span className="link-action-modal__icon">
                            <Pencil size={19} />
                        </span>

                        <div>
                            <span className="link-action-modal__eyebrow">
                                Edit destination
                            </span>

                            <h2 id="edit-link-title">
                                Update link
                            </h2>
                        </div>
                    </div>

                    <button
                        type="button"
                        className="link-action-modal__close"
                        onClick={onClose}
                        disabled={loading}
                        aria-label="Close edit link modal"
                    >
                        <X size={19} />
                    </button>
                </div>

                <div className="edit-link-identity">
                    <span>
                        /r/{link.shortCode}
                    </span>

                    <ArrowRight size={15} />

                    <span>Destination</span>
                </div>

                <p className="link-action-modal__description">
                    The short code stays the same. Only the
                    destination URL will be updated.
                </p>

                {error && (
                    <div
                        className="link-action-modal__error"
                        role="alert"
                    >
                        {error}
                    </div>
                )}

                <form
                    className="edit-link-form"
                    onSubmit={handleSubmit}
                >
                    <div className="create-link-field">
                        <label htmlFor="editOriginalUrl">
                            New destination URL
                        </label>

                        <div className="create-link-input">
                            <Link2 size={17} />

                            <input
                                id="editOriginalUrl"
                                type="url"
                                autoFocus
                                required
                                value={originalUrl}
                                onChange={(event) => {
                                    setOriginalUrl(
                                        event.target.value
                                    );

                                    if (error) {
                                        setError("");
                                    }
                                }}
                                placeholder="https://example.com/new-destination"
                            />
                        </div>

                        <span className="create-link-field__hint">
                            Must start with http:// or https://
                        </span>
                    </div>

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
                            type="submit"
                            className="link-action-primary"
                            disabled={loading}
                        >
                            {loading ? (
                                <>
                                    <span className="create-link-spinner" />
                                    Updating
                                </>
                            ) : (
                                <>
                                    <Pencil size={16} />
                                    Update link
                                </>
                            )}
                        </button>
                    </div>
                </form>
            </section>
        </div>
    );
}

export default EditLinkModal;