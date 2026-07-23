import { ArrowRight, Mail, UserRound } from "lucide-react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import AuthCard from "../components/auth/AuthCard";
import AuthInput from "../components/auth/AuthInput";
import PasswordInput from "../components/auth/PasswordInput";
import LoadingSpinner from "../components/ui/LoadingSpinner";
import Logo from "../components/ui/Logo";
import { useAuth } from "../context/AuthContext";

function RegisterPage() {
    const navigate = useNavigate();
    const { register } = useAuth();

    const [form, setForm] = useState({
        name: "",
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
            await register(
                form.name.trim(),
                form.email.trim(),
                form.password,
            );

            navigate("/login", {
                replace: true,
                state: {
                    registered: true,
                },
            });
        } catch (requestError) {
            setError(
                requestError.response?.data?.message ||
                requestError.response?.data ||
                "Unable to create your account. Please try again.",
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
                    Create Account
                </span>

                <h1 className="mt-6 text-4xl font-bold tracking-[-0.05em] text-white sm:text-5xl">
                    Join the
                    <span className="block bg-gradient-to-r from-cyan-300 via-sky-300 to-white bg-clip-text text-transparent">
                        SnapLink Platform
                    </span>
                </h1>

                <p className="mt-5 max-w-md text-sm leading-7 text-zinc-400">
                    Create your account to shorten URLs, track analytics,
                    and manage everything from one elegant dashboard.
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
                        id="name"
                        name="name"
                        label="Full Name"
                        icon={UserRound}
                        value={form.name}
                        onChange={handleChange}
                        placeholder="John Doe"
                        autoComplete="name"
                        required
                    />

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
                        placeholder="Create a secure password"
                        autoComplete="new-password"
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
                                Create Account
                                <ArrowRight className="h-4 w-4" />
                            </>
                        )}
                    </button>
                </form>

                <div className="mt-8 flex items-center justify-center">
                    <div className="h-px flex-1 bg-white/10" />
                    <span className="px-4 text-xs uppercase tracking-[0.25em] text-zinc-500">
                        Secure Registration
                    </span>
                    <div className="h-px flex-1 bg-white/10" />
                </div>

                <p className="mt-8 text-center text-sm text-zinc-400">
                    Already have an account?{" "}
                    <Link
                        to="/login"
                        className="
                            font-semibold
                            text-cyan-300
                            transition-colors
                            duration-300
                            hover:text-cyan-200
                        "
                    >
                        Sign In
                    </Link>
                </p>
            </AuthCard>

            <div className="mt-8 text-center">
                <p className="text-xs leading-6 text-zinc-500">
                    By creating an account, you agree to securely manage your
                    links with SnapLink and enjoy fast, reliable URL shortening.
                </p>
            </div>
        </section>
    );
}

export default RegisterPage;