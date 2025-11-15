import Login from "../components/login/Login";
import { GoogleOAuthProvider } from "@react-oauth/google";

export default function AuthPage() {
    return (
        <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
            <Login />
        </GoogleOAuthProvider>
    );
}
