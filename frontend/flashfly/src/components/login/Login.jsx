import { useRef } from "react";
import "./Login.css";
import GoogleLoginButton from "./GoogleLoginButton";

export default function Login() {
    const containerRef = useRef();

    const handleRegister = () => containerRef.current.classList.add("active");
    const handleLogin = () => containerRef.current.classList.remove("active");

    return (
        <div className="container" ref={containerRef}>
            {/* FORM LOGIN */}
            <div className="form-container sign-in">
                <form>
                    <h2>Iniciar sesión</h2>
                    <input type="text" placeholder="Email" />
                    <input type="password" placeholder="Contraseña" />
                    <button type="submit">Login</button>

                    <GoogleLoginButton />

                    <p>
                        ¿No tienes cuenta? <span onClick={handleRegister}>Regístrate</span>
                    </p>
                </form>
            </div>

            {/* FORM REGISTRO */}
            <div className="form-container sign-up">
                <form>
                    <h2>Crear cuenta</h2>
                    <input type="text" placeholder="Nombre" />
                    <input type="text" placeholder="Email" />
                    <input type="password" placeholder="Contraseña" />
                    <button type="submit">Registrar</button>
                    <p>
                        ¿Ya tienes cuenta? <span onClick={handleLogin}>Login</span>
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
                        <button onClick={handleLogin}>Login</button>
                    </div>

                    <div className="toggle-panel toggle-right">
                        <h1>FlashFly</h1>
                        <p>
                            Tu viaje empieza aquí. Planea rutas directas y aprovecha tu
                            tiempo al máximo.
                        </p>
                        <button onClick={handleRegister}>Regístrate</button>
                    </div>
                </div>
            </div>
        </div>
    );
}
