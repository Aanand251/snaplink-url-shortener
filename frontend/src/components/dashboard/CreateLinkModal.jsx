import {
    CalendarClock,
    Check,
    Link2,
    Sparkles,
    X,
} from "lucide-react";
import { useEffect, useState } from "react";

import { createShortUrl } from "../../api/urlApi";

const initialForm = {
    originalUrl: "",
    customAlias: "",
    expiresAt: "",
};

function getErrorMessage(error) {
    const responseData = error.response?.data;

    if (typeof responseData === "string") {
        return responseData;
    }

    if (responseData?.message) {
        return responseData.message;
    }

    if (responseData?.errors) {
        const validationErrors = Object.values(responseData.errors);

        if (validationErrors.length > 0) {
            return validationErrors[0];
        }
    }

    return "Unable to create your link. Please try again.";
}

function CreateLinkModal({
                             open,
                             onClose,
                             onCreated,
                         }) {
    const [form, setForm] = useState(initialForm);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        if (!open) {
            return;
        }

        const handleEscape = (event) => {
            if (event.key === "Escape" && !loading) {
                onClose();
            }
        };

        document.body.style.overflow = "hidden";
        window.addEventListener("keydown", handleEscape);

        return () => {
            document.body.style.overflow = "";
            window.removeEventListener(
                "keydown",
                handleEscape
            );
        };
    }, [open, loading, onClose]);

    useEffect(() => {
        if (!open) {
            setForm(initialForm);
            setError("");
            setLoading(false);
        }
    }, [open]);

    if (!open) {
        return null;
    }

    const handleChange = (event) => {
        const { name, value } = event.target;

        setForm((current) => ({
            ...current,
            [name]: value,
        }));

        if (error) {
            setError("");
        }
    };

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

        const originalUrl = form.originalUrl.trim();
        const customAlias = form.customAlias.trim();

        if (!originalUrl) {
            setError("Original URL is required.");
            return;
        }

        if (
            !originalUrl.startsWith("http://") &&
            !originalUrl.startsWith("https://")
        ) {
            setError(
                "URL must start with http:// or https://"
            );
            return;
        }

        if (
            customAlias &&
            (customAlias.length < 4 ||
                customAlias.length > 30)
        ) {
            setError(
                "Alias must be between 4 and 30 characters."
            );
            return;
        }

        if (
            customAlias &&
            !/^[a-zA-Z0-9_-]+$/.test(customAlias)
        ) {
            setError(
                "Alias can contain only letters, numbers, '-' and '_'."
            );
            return;
        }

        setLoading(true);
        setError("");

        try {
            const payload = {
                originalUrl,
                customAlias: customAlias || null,
                expiresAt: form.expiresAt || null,
            };

            const createdLink = await createShortUrl(payload);

            await onCreated(createdLink);
        } catch (requestError) {
            console.error(
                "Unable to create short URL",
                requestError
            );

            setError(getErrorMessage(requestError));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div
            className="create-link-modal"
            onMouseDown={handleBackdropClick}
            role="presentation"
        >
            <section
                className="create-link-modal__card"
                role="dialog"
                aria-modal="true"
                aria-labelledby="create-link-title"
            >
                <div className="create-link-modal__header">
                    <div className="create-link-modal__heading">
                        <span className="create-link-modal__icon">
                            <Sparkles size={20} />
                        </span>

                        <div>
                            <span className="create-link-modal__eyebrow">
                                New short link
                            </span>

                            <h2 id="create-link-title">
                                Create a link
                            </h2>
                        </div>
                    </div>

                    <button
                        type="button"
                        className="create-link-modal__close"
                        onClick={onClose}
                        disabled={loading}
                        aria-label="Close create link modal"
                    >
                        <X size={19} />
                    </button>
                </div>

                <p className="create-link-modal__description">
                    Turn a long URL into a focused SnapLink.
                    Add a custom alias or expiry when you need
                    more control.
                </p>

                {error && (
                    <div
                        className="create-link-modal__error"
                        role="alert"
                    >
                        {error}
                    </div>
                )}

                <form
                    className="create-link-form"
                    onSubmit={handleSubmit}
                >
                    <div className="create-link-field">
                        <label htmlFor="originalUrl">
                            Destination URL
                        </label>

                        <div className="create-link-input">
                            <Link2 size={17} />

                            <input
                                id="originalUrl"
                                name="originalUrl"
                                type="url"
                                required
                                autoFocus
                                value={form.originalUrl}
                                onChange={handleChange}
                                placeholder="https://example.com/very-long-url"
                            />
                        </div>

                        <span className="create-link-field__hint">
                            Must start with http:// or https://
                        </span>
                    </div>

                    <div className="create-link-field">
                        <div className="create-link-field__label-row">
                            <label htmlFor="customAlias">
                                Custom alias
                            </label>

                            <span>Optional</span>
                        </div>

                        <div className="create-link-alias">
                            <span className="create-link-alias__prefix">
                                /r/
                            </span>

                            <input
                                id="customAlias"
                                name="customAlias"
                                type="text"
                                minLength={4}
                                maxLength={30}
                                value={form.customAlias}
                                onChange={handleChange}
                                placeholder="my-link"
                            />
                        </div>

                        <span className="create-link-field__hint">
                            4–30 characters. Letters, numbers,
                            hyphens and underscores only.
                        </span>
                    </div>

                    <div className="create-link-field">
                        <div className="create-link-field__label-row">
                            <label htmlFor="expiresAt">
                                Expiry
                            </label>

                            <span>Optional</span>
                        </div>

                        <div className="create-link-input">
                            <CalendarClock size={17} />

                            <input
                                id="expiresAt"
                                name="expiresAt"
                                type="datetime-local"
                                value={form.expiresAt}
                                onChange={handleChange}
                            />
                        </div>

                        <span className="create-link-field__hint">
                            Leave empty to keep the link active
                            without a scheduled expiry.
                        </span>
                    </div>

                    <div className="create-link-modal__actions">
                        <button
                            type="button"
                            className="create-link-cancel"
                            onClick={onClose}
                            disabled={loading}
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="create-link-submit"
                            disabled={loading}
                        >
                            {loading ? (
                                <>
                                    <span className="create-link-spinner" />
                                    Creating
                                </>
                            ) : (
                                <>
                                    <Check size={17} />
                                    Create link
                                </>
                            )}
                        </button>
                    </div>
                </form>
            </section>
        </div>
    );
}

export default CreateLinkModal;