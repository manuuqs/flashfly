import { useRef, useState } from "react";
import "../../styles/Login.css";
import GoogleLoginButton from "./GoogleLoginButton";
import { Link } from "react-router-dom";
import api from "../../api/axiosConfig";

export default function Login() {
    const containerRef = useRef();
    const [loginData, setLoginData] = useState({ email: "", password: "" });
    const [registerData, setRegisterData] = useState({ name: "", email: "", password: "" });
    const [loading, setLoading] = useState(false);

    const handleRegister = () => containerRef.current.classList.add("active");
    const handleLogin = () => containerRef.current.classList.remove("active");

    // 🔹 Login tradicional
    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            const res = await api.post("/auth/login", {
                email: loginData.email,
                password: loginData.password
            });

            const data = res.data;

            localStorage.setItem("token", data.token);
            localStorage.setItem("user", JSON.stringify(data.user));
            alert(`¡Bienvenido, ${data.user.name}!`);
            window.location.href = "/dashboard";
        } catch (err) {
            console.error("Error al iniciar sesión:", err);
            alert("Error al conectar con el servidor");
        } finally {
            setLoading(false);
        }
    };


    // 🔹 Registro tradicional
    const handleRegisterSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            const res = await api.post("/auth/register", {
                name: registerData.name,
                email: registerData.email,
                password: registerData.password
            });

            const data = res.data;

            // Guardar token y usuario
            localStorage.setItem("token", data.token);
            localStorage.setItem("user", JSON.stringify(data.user));

            alert(`¡Cuenta creada exitosamente para ${data.user.name}!`);
            window.location.href = "/dashboard";

        } catch (err) {
            // Axios coloca el status en err.response.status
            if (err.response && err.response.status === 409) {
                alert(err.response.data.error); // "El usuario ya está registrado"
            } else if (err.response) {
                alert(`Error: ${err.response.data.error || "Error desconocido"}`);
            } else {
                alert("Error al conectar con el servidor");
            }

            console.error("Error al registrar:", err);
        } finally {
            setLoading(false);
        }
    };


    return (
        <div className="container" ref={containerRef}>
            {/* FORM LOGIN */}
            <div className="form-container sign-in">
                <form onSubmit={handleLoginSubmit}>
                    <h2>Iniciar sesión</h2>
                    <input
                        type="email"
                        placeholder="Email"
                        value={loginData.email}
                        onChange={(e) => setLoginData({...loginData, email: e.target.value})}
                        required
                        disabled={loading}
                    />
                    <input
                        type="password"
                        placeholder="Contraseña"
                        value={loginData.password}
                        onChange={(e) => setLoginData({...loginData, password: e.target.value})}
                        required
                        disabled={loading}
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? "Cargando..." : "Login"}
                    </button>

                    <div className="divider">
                        <span>o</span>
                    </div>

                    <GoogleLoginButton disabled={loading} />

                    <p style={{ marginTop: "10px" }}>
                        ¿Olvidaste tu contraseña?{" "}
                        <Link to="/forgot-password" style={{ color: "blue" }}>
                            Recuperarla
                        </Link>
                    </p>
                </form>
            </div>

            {/* FORM REGISTRO */}
            <div className="form-container sign-up">
                <form onSubmit={handleRegisterSubmit}>
                    <h2>Crear cuenta</h2>
                    <input
                        type="text"
                        placeholder="Nombre completo"
                        value={registerData.name}
                        onChange={(e) => setRegisterData({...registerData, name: e.target.value})}
                        required
                        disabled={loading}
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        value={registerData.email}
                        onChange={(e) => setRegisterData({...registerData, email: e.target.value})}
                        required
                        disabled={loading}
                    />
                    <input
                        type="password"
                        placeholder="Contraseña"
                        value={registerData.password}
                        onChange={(e) => setRegisterData({...registerData, password: e.target.value})}
                        required
                        disabled={loading}
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? "Creando cuenta..." : "Registrar"}
                    </button>

                    <div className="divider">
                        <span>o</span>
                    </div>

                    <GoogleLoginButton disabled={loading} />
                </form>
            </div>

            {/* PANEL LATERAL */}
            <div className="toggle-container">
                <div className="toggle">
                    <div className="toggle-panel toggle-left">
                        <h1>FlashFly</h1>
                        <p>
                            Vuela rápido, viaja simple. Encuentra rutas directas y ahorra
                            tiempo.
                        </p>
                        <button onClick={handleLogin} disabled={loading}>
                            Iniciar Sesión
                        </button>
                    </div>

                    <div className="toggle-panel toggle-right">
                        <h1>FlashFly</h1>
                        <p>
                            Tu viaje empieza aquí. Planea rutas directas y aprovecha tu
                            tiempo al máximo.
                        </p>
                        <button onClick={handleRegister} disabled={loading}>
                            Registrarse
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}