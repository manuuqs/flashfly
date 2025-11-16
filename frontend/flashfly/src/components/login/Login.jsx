import { useRef, useState } from "react";
import "./Login.css";
import GoogleLoginButton from "./GoogleLoginButton";

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
            const res = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    email: loginData.email,
                    password: loginData.password
                }),
            });

            const data = await res.json();

            if (res.ok) {
                localStorage.setItem("user", JSON.stringify(data));
                alert(`¡Bienvenido, ${data.name}!`);
                // Redirigir al dashboard o página principal
                window.location.href = "/dashboard";
            } else {
                alert(`Error: ${data.error}`);
            }
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
            const res = await fetch("http://localhost:8080/api/auth/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: registerData.name,
                    email: registerData.email,
                    password: registerData.password
                }),
            });

            const data = await res.json();

            if (res.ok) {
                localStorage.setItem("user", JSON.stringify(data));
                alert(`¡Cuenta creada exitosamente para ${data.name}!`);
                // Redirigir al dashboard o página principal
                window.location.href = "/dashboard";
            } else {
                alert(`Error: ${data.error}`);
            }
        } catch (err) {
            console.error("Error al registrar:", err);
            alert("Error al conectar con el servidor");
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

                    <p>
                        ¿No tienes cuenta? <span onClick={handleRegister} style={{cursor: "pointer", color: "blue"}}>Regístrate</span>
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

                    <p>
                        ¿Ya tienes cuenta? <span onClick={handleLogin} style={{cursor: "pointer", color: "blue"}}>Inicia sesión</span>
                    </p>
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