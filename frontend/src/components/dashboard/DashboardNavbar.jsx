import { Link2, LogOut, Plus } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

function DashboardNavbar({ onCreateLink }) {
    const navigate = useNavigate();
    const { logout } = useAuth();

    const handleLogout = () => {
        logout();
        navigate("/login", { replace: true });
    };

    return (
        <header className="dashboard-navbar">
            <div className="dashboard-navbar__content">
                <button
                    type="button"
                    className="dashboard-brand"
                    onClick={() => navigate("/dashboard")}
                    aria-label="Go to dashboard"
                >
                    <span className="dashboard-brand__icon">
                        <Link2 size={22} strokeWidth={2.4} />
                    </span>

                    <span className="dashboard-brand__text">
                        Snap<span>Link</span>
                    </span>
                </button>

                <div className="dashboard-navbar__actions">
                    <button
                        type="button"
                        className="dashboard-create-button"
                        onClick={onCreateLink}
                    >
                        <Plus size={18} />
                        <span>Create link</span>
                    </button>

                    <button
                        type="button"
                        className="dashboard-logout-button"
                        onClick={handleLogout}
                        aria-label="Sign out"
                        title="Sign out"
                    >
                        <LogOut size={19} />
                    </button>
                </div>
            </div>
        </header>
    );
}

export default DashboardNavbar;