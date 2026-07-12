import { ArrowRight, Eye, EyeOff, LockKeyhole, Mail } from "lucide-react";
import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import LoadingSpinner from "../components/ui/LoadingSpinner";
import { useAuth } from "../context/AuthContext";

function LoginPage() {
    const navigate = useNavigate();
    const location = useLocation();
    const { login } = useAuth();

    const [form, setForm] = useState({
        email: "",
        password: "",
    });

    const [showPassword, setShowPassword] = useState(false);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

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

    const handleSubmit = async (event) => {
        event.preventDefault();

        setError("");
        setLoading(true);

        try {
            await login(form.email.trim(), form.password);

            const destination = location.state?.from || "/dashboard";
            navigate(destination, { replace: true });
        } catch (requestError) {
            setError(
                requestError.response?.data?.message ||
                requestError.response?.data ||
                "Unable to sign in. Check your credentials and try again.",
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <section className="w-full max-w-md">
            <div className="mb-8 text-center">
                <p className="text-sm font-medium text-violet-400">Welcome back</p>

                <h1 className="mt-3 text-4xl font-semibold tracking-[-0.045em] text-white">
                    Sign in to SnapLink
                </h1>

                <p className="mt-3 text-zinc-500">
                    Manage your links and see what gets clicked.
                </p>
            </div>

            <div className="rounded-[28px] border border-white/[0.08] bg-white/[0.035] p-6 shadow-2xl shadow-black/30 backdrop-blur-xl sm:p-8">
                {error && (
                    <div
                        role="alert"
                        className="mb-6 rounded-2xl border border-red-400/15 bg-red-500/10 px-4 py-3 text-sm leading-6 text-red-300"
                    >
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-5">
                    <div>
                        <label
                            htmlFor="email"
                            className="mb-2 block text-sm font-medium text-zinc-300"
                        >
                            Email address
                        </label>

                        <div className="relative">
                            <Mail className="pointer-events-none absolute left-4 top-1/2 h-4 w-4 -translate-y-1/2 text-zinc-600" />

                            <input
                                id="email"
                                name="email"
                                type="email"
                                autoComplete="email"
                                required
                                value={form.email}
                                onChange={handleChange}
                                placeholder="you@example.com"
                                className="h-13 w-full rounded-2xl border border-white/[0.08] bg-black/20 pl-11 pr-4 text-sm text-white outline-none transition placeholder:text-zinc-700 focus:border-violet-400/40 focus:ring-4 focus:ring-violet-500/10"
                            />
                        </div>
                    </div>

                    <div>
                        <label
                            htmlFor="password"
                            className="mb-2 block text-sm font-medium text-zinc-300"
                        >
                            Password
                        </label>

                        <div className="relative">
                            <LockKeyhole className="pointer-events-none absolute left-4 top-1/2 h-4 w-4 -translate-y-1/2 text-zinc-600" />

                            <input
                                id="password"
                                name="password"
                                type={showPassword ? "text" : "password"}
                                autoComplete="current-password"
                                required
                                value={form.password}
                                onChange={handleChange}
                                placeholder="Enter your password"
                                className="h-13 w-full rounded-2xl border border-white/[0.08] bg-black/20 pl-11 pr-12 text-sm text-white outline-none transition placeholder:text-zinc-700 focus:border-violet-400/40 focus:ring-4 focus:ring-violet-500/10"
                            />

                            <button
                                type="button"
                                onClick={() => setShowPassword((current) => !current)}
                                className="absolute right-4 top-1/2 -translate-y-1/2 text-zinc-600 transition hover:text-zinc-300"
                                aria-label={showPassword ? "Hide password" : "Show password"}
                            >
                                {showPassword ? (
                                    <EyeOff className="h-4 w-4" />
                                ) : (
                                    <Eye className="h-4 w-4" />
                                )}
                            </button>
                        </div>
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        className="flex h-13 w-full items-center justify-center gap-2 rounded-2xl bg-violet-500 px-5 text-sm font-semibold text-white shadow-[0_12px_35px_rgba(124,58,237,0.25)] transition hover:bg-violet-400 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {loading ? (
                            <LoadingSpinner />
                        ) : (
                            <>
                                Sign in
                                <ArrowRight className="h-4 w-4" />
                            </>
                        )}
                    </button>
                </form>

                <p className="mt-7 text-center text-sm text-zinc-500">
                    New to SnapLink?{" "}
                    <Link
                        to="/register"
                        className="font-medium text-violet-400 transition hover:text-violet-300"
                    >
                        Create an account
                    </Link>
                </p>
            </div>
        </section>
    );
}

export default LoginPage;