import { useState } from "react";
import { useAuth } from "./useAuth";

export const LoginForm = () => {

    const [email, setEmail] = useState<string>('')
    const [password, setPassword] = useState<string>('')
    const [error, setError] = useState<string | null>(null)
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false)

    const { login } = useAuth()
    
    const handleSubmit = async (e: React.SyntheticEvent) => {
        e.preventDefault()
        setIsSubmitting(true)
        setError(null)
        try {
            await login(email, password)
        } catch (error) {
            console.error("Login failed", error)
            setError("Login failed. Please check your credentials and try again.")
        } finally {
            setIsSubmitting(false)
        }
    }
    
    return (
        <>
            <div className="flex items-center justify-center min-h-screen bg-gray-100">
                <div className="bg-white p-8 rounded shadow-md w-full max-w-md">
                    <h2 className="font-sans text-3xl font-bold">TodoSync Login</h2>
                    <form onSubmit={handleSubmit} className="mt-6">
                        <input 
                            type="email" 
                            value={email} 
                            onChange={(e) => setEmail(e.target.value)} 
                            disabled={isSubmitting}
                            placeholder="Email" 
                            className="w-full p-3 mb-4 border rounded" />
                        <input 
                            type="password" 
                            value={password} 
                            onChange={(e) => setPassword(e.target.value)} 
                            disabled={isSubmitting}
                            placeholder="Password" 
                            className="w-full p-3 mb-4 border rounded" />
                        {error && <p className="text-red-500 text-sm mb-4">{error}</p>}
                        <button 
                            type="submit" 
                            disabled={isSubmitting}
                            className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600">
                            {isSubmitting ? "Logging in..." : "Login"}
                        </button>
                    </form>
                </div>
            </div>
        </>
    )
}