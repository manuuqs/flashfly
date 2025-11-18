import { useState } from "react";
import "./ForgotPassword.css";

export default function ForgotPassword() {
    const [email, setEmail] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            const res = await fetch("http://localhost:8080/api/auth/forgot-password", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email }),
            });

            const data = await res.json();

            if (res.ok) {
                alert("📩 Si el email existe, hemos enviado instrucciones de recuperación.");
            } else {
                alert("Error: " + data.error);
            }
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
