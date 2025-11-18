import { BrowserRouter, Routes, Route } from "react-router-dom";
import AuthPage from "./pages/AuthPage";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                {/* Página de inicio: login y registro */}
                <Route path="/" element={<AuthPage />} />

                {/* Recuperar contraseña */}
                <Route path="/forgot-password" element={<ForgotPassword />} />

                {/* Restablecer contraseña (token en la URL) */}
                <Route path="/reset-password" element={<ResetPassword />} />

                {/* Ruta opcional si tu login está en otra URL */}
                <Route path="/login" element={<AuthPage />} />
            </Routes>
        </BrowserRouter>
    );
}
