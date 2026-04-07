import { useState } from "react";
import { useAuth } from "./useAuth";

import { Navigate } from "react-router-dom";
import { Spinner } from "../common/Spinner";

export const RegistrationForm = () => {
    const { user, register } = useAuth()
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [confirmPassword, setConfirmPassword] = useState("")
    const [error, setError] = useState("")
    const [isSubmitting, setIsSubmitting] = useState(false)
    const [confirmPasswordBoxTouched, setConfirmPasswordBoxTouched] = useState(false)

    const passwordMatch = password === confirmPassword
    const canSubmit = email !== "" && password !== "" && passwordMatch && !isSubmitting
    
    const handleSubmit = async (e: React.SyntheticEvent) => {
        e.preventDefault()
        setError("")        
        setIsSubmitting(true)
        try {
            await register(email, password)
        } catch (error) {
            console.error("Registration failed", error)
            setError("Registration failed. Please check your details and try again.")
        } finally {
            setIsSubmitting(false)
        }
    }

    return (
        <>
            {user ? <Navigate to="/" /> : null}
            <div className="flex items-center justify-center min-h-screen bg-gray-100">
                <div className="bg-white p-8 rounded shadow-md w-full max-w-md">
                    <h2 className="font-sans text-3xl font-bold">Register for Todosync</h2>
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
                        <input 
                            type="password" 
                            value={confirmPassword} 
                            onChange={(e) => setConfirmPassword(e.target.value)} 
                            onBlur={() => setConfirmPasswordBoxTouched(true)}
                            disabled={isSubmitting}
                            placeholder="Confirm Password" 
                            className="w-full p-3 mb-4 border rounded" />
                        {!passwordMatch && confirmPasswordBoxTouched && <p className="text-red-500 text-sm mb-4">Passwords must match for registration</p>}
                        {error && <p className="text-red-500 text-sm mb-4">{error}</p>}
                        <button 
                            type="submit" 
                            disabled={!canSubmit} 
                            className="w-full p-3 bg-blue-500 text-white rounded hover:bg-blue-600">
                            {isSubmitting ? <Spinner /> : "Register"}
                        </button>
                    </form>
                </div>
            </div>
        </>
    )
}