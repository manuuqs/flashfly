
import { useState } from "react";
import "./ForgotPassword.css";
import api from "../api/axiosConfig";

export default function ForgotPassword() {
    const [email, setEmail] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            const res = await api.post("/auth/forgot-password", { email });
            alert("📩 Si el email existe, hemos enviado instrucciones de recuperación.");
        } catch (error) {
            console.error(error);
            alert("Error al conectar con el servidor");
        }

        setLoading(false);
    };

    return (
        <div className="forgot-container">
            <form className="forgot-form" onSubmit={handleSubmit}>
                <h2>Recuperar contraseña</h2>
                <p className="subtitle">
                    Introduce tu correo y te enviaremos un enlace para restablecer tu contraseña.
                </p>
                <input
                    type="email"
                    placeholder="Tu email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    disabled={loading}
                />
                <button type="submit" disabled={loading}>
                    {loading ? "Enviando..." : "Enviar enlace"}
                </button>
                <a className="back-link" href="/login">
                    ← Volver al inicio de sesión
                </a>
            </form>
        </div>
    );
}
