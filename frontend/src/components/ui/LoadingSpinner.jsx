function LoadingSpinner({ size = "md" }) {
    const sizes = {
        sm: "h-4 w-4",
        md: "h-5 w-5",
        lg: "h-8 w-8",
    };

    return (
        <span
            className={`${sizes[size]} inline-block animate-spin rounded-full border-2 border-white/20 border-t-white`}
            aria-label="Loading"
        />
    );
}

export default LoadingSpinner;
