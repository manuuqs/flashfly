
import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import "../styles/ResetPassword.css";
import api from "../api/axiosConfig";

export default function ResetPassword() {
    const [params] = useSearchParams();
    const token = params.get("token");

    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (password !== confirm) {
            alert("Las contraseñas no coinciden.");
            return;
        }

        setLoading(true);

        try {
            const res = await api.post("/auth/reset-password", {
                token,
                newPassword: password
            });

            alert("Contraseña restablecida correctamente. Ya puedes iniciar sesión.");
            window.location.href = "/login";
        } catch (err) {
            alert("Error al conectar con el servidor.");
            console.error(err);
        }

        setLoading(false);
    };

    if (!token) {
        return (
            <div className="reset-container">
                <div className="reset-box">
                    <h2>Enlace inválido</h2>
                    <p>El enlace para restablecer tu contraseña no es válido.</p>
                    <a href="/forgot-password">Solicitar nuevo enlace</a>
                </div>
            </div>
        );
    }

    return (
        <div className="reset-container">
            <form className="reset-box" onSubmit={handleSubmit}>
                <h2>Restablecer contraseña</h2>
                <p>Introduce tu nueva contraseña.</p>
                <input
                    type="password"
                    placeholder="Nueva contraseña"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    disabled={loading}
                />
                <input
                    type="password"
                    placeholder="Confirmar contraseña"
                    value={confirm}
                    onChange={(e) => setConfirm(e.target.value)}
                    required
                    disabled={loading}
                />
                <button type="submit" disabled={loading}>
                    {loading ? "Guardando..." : "Cambiar contraseña"}
                </button>
                <a className="back-login" href="/login">
                    ← Volver al inicio de sesión
                </a>
            </form>
        </div>
    );
}
