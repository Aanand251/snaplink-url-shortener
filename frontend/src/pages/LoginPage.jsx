import { ArrowRight, Mail } from "lucide-react";
import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";

import AuthCard from "../components/auth/AuthCard";
import AuthInput from "../components/auth/AuthInput";
import PasswordInput from "../components/auth/PasswordInput";
import LoadingSpinner from "../components/ui/LoadingSpinner";
import Logo from "../components/ui/Logo";
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
        <section className="w-full max-w-lg px-4">
            <div className="mb-10 flex flex-col items-center text-center">
                <Logo />

                <span className="mt-8 rounded-full border border-cyan-400/20 bg-cyan-400/10 px-4 py-1 text-xs font-semibold uppercase tracking-[0.25em] text-cyan-300">
                    Welcome Back
                </span>

                <h1 className="mt-6 text-4xl font-bold tracking-[-0.05em] text-white sm:text-5xl">
                    Sign in to your
                    <span className="block bg-gradient-to-r from-cyan-300 via-sky-300 to-white bg-clip-text text-transparent">
                        SnapLink Account
                    </span>
                </h1>

                <p className="mt-5 max-w-md text-sm leading-7 text-zinc-400">
                    Continue managing your shortened URLs, analytics and
                    branded links from one beautiful dashboard.
                </p>
            </div>

            <AuthCard>
                {error && (
                    <div
                        role="alert"
                        className="mb-6 rounded-2xl border border-red-400/20 bg-red-500/10 px-4 py-3 text-sm text-red-300"
                    >
                        {error}
                    </div>
                )}

                <form
                    onSubmit={handleSubmit}
                    className="space-y-6"
                >
                    <AuthInput
                        id="email"
                        name="email"
                        label="Email Address"
                        type="email"
                        icon={Mail}
                        value={form.email}
                        onChange={handleChange}
                        placeholder="you@example.com"
                        autoComplete="email"
                        required
                    />

                    <PasswordInput
                        id="password"
                        name="password"
                        label="Password"
                        value={form.password}
                        onChange={handleChange}
                        placeholder="Enter your password"
                        autoComplete="current-password"
                        required
                        showPassword={showPassword}
                        setShowPassword={setShowPassword}
                    />

                    <button
                        type="submit"
                        disabled={loading}
                        className="
                            flex
                            h-14
                            w-full
                            items-center
                            justify-center
                            gap-2
                            rounded-2xl
                            bg-cyan-400
                            text-sm
                            font-semibold
                            text-slate-950
                            transition-all
                            duration-300
                            hover:-translate-y-0.5
                            hover:bg-cyan-300
                            hover:shadow-[0_18px_40px_rgba(34,211,238,0.35)]
                            disabled:cursor-not-allowed
                            disabled:opacity-60
                        "
                    >
                        {loading ? (
                            <LoadingSpinner />
                        ) : (
                            <>
                                Sign In
                                <ArrowRight className="h-4 w-4" />
                            </>
                        )}
                    </button>
                </form>

                <div className="mt-8 flex items-center justify-center">
                    <div className="h-px flex-1 bg-white/10" />
                    <span className="px-4 text-xs uppercase tracking-[0.25em] text-zinc-500">
                        Secure Login
                    </span>
                    <div className="h-px flex-1 bg-white/10" />
                </div>

                <p className="mt-8 text-center text-sm text-zinc-400">
                    New to SnapLink?{" "}
                    <Link
                        to="/register"
                        className="
                            font-semibold
                            text-cyan-300
                            transition-colors
                            duration-300
                            hover:text-cyan-200
                        "
                    >
                        Create an account
                    </Link>
                </p>
            </AuthCard>

            <div className="mt-8 text-center">
                <p className="text-xs leading-6 text-zinc-500">
                    By signing in, you agree to securely manage your links using
                    SnapLink's authentication system.
                </p>
            </div>
        </section>
    );
}

export default LoginPage;