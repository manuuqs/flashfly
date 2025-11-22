
import { GoogleLogin } from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";
import api from "../../api/axiosConfig";

export default function GoogleLoginButton({ disabled }) {
    const handleGoogleSuccess = async (credentialResponse) => {
        const tokenGoogle = credentialResponse.credential;
        const userInfo = jwtDecode(tokenGoogle);

        console.log("👤 Usuario de Google:", userInfo);

        try {
            const res = await api.post("/auth/google", { token: tokenGoogle });
            const data = res.data;

            localStorage.setItem("token", data.token);
            localStorage.setItem("user", JSON.stringify(data.user));
            alert(`¡Bienvenido, ${data.user.name}!`);
            window.location.href = "/dashboard";
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
