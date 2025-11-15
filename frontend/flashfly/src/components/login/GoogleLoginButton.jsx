import { GoogleLogin } from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";

export default function GoogleLoginButton() {
    const handleGoogleSuccess = async (credentialResponse) => {
        const token = credentialResponse.credential;
        const userInfo = jwtDecode(token);

        console.log("👤 Usuario de Google:", userInfo);

        try {
            const res = await fetch("http://localhost:8080/api/auth/google/verify", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ token }),
            });

            const data = await res.json();
            localStorage.setItem("jwt", data.jwt);
            alert(`Bienvenido, ${userInfo.name}`);
        } catch (err) {
            console.error("Error al conectar con el backend:", err);
        }
    };

    const handleGoogleError = () => {
        console.error("❌ Error al iniciar sesión con Google");
    };

    return (
        <div className="google-login">
            <GoogleLogin
                onSuccess={handleGoogleSuccess}
                onError={handleGoogleError}
                size="large"
                shape="pill"
                theme="outline"
            />
        </div>
    );
}
