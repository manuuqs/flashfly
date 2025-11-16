import { GoogleLogin } from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";

export default function GoogleLoginButton({ disabled }) {
    const handleGoogleSuccess = async (credentialResponse) => {
        const token = credentialResponse.credential;
        const userInfo = jwtDecode(token);

        console.log("👤 Usuario de Google:", userInfo);

        try {
            const res = await fetch("http://localhost:8080/api/auth/google", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ token }),
            });

            const data = await res.json();

            if (res.ok) {
                localStorage.setItem("user", JSON.stringify(data));
                alert(`¡Bienvenido, ${data.name}!`);
                // Redirigir al dashboard
                window.location.href = "/dashboard";
            } else {
                alert(`Error: ${data.error}`);
            }
        } catch (err) {
            console.error("Error al conectar con el backend:", err);
            alert("Error al conectar con el servidor");
        }
    };

    const handleGoogleError = () => {
        console.error("❌ Error al iniciar sesión con Google");
        alert("Error al iniciar sesión con Google");
    };

    return (
        <div className="google-login">
            <GoogleLogin
                onSuccess={handleGoogleSuccess}
                onError={handleGoogleError}
                size="large"
                shape="pill"
                theme="outline"
                disabled={disabled}
            />
        </div>
    );
}