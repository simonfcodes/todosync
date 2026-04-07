import { useState, useEffect } from "react"
import { AuthContext } from "./AuthContext"
import api from "../api/axios"
import { storeTokens, clearTokens, getAccessToken } from "./tokenStorage"
import type { User, LoginResponse } from "./types"
import type { AxiosResponse } from "axios"


export const AuthProvider = ({ children }: { children: React.ReactNode}) => {

    const [user, setUser] = useState<User | null>(null)
    const [isLoading, setIsLoading] = useState(true)

    // Callback on initial mount
    useEffect(() => {
        const initializeAuth = async () => {
            const accessToken = getAccessToken()
            if (accessToken) {
                try {
                    const user = await getMe()
                    setUser(user)
                } catch (error) {
                    clearTokens()
                }                
            }
            setIsLoading(false)
        }

        initializeAuth()
    }, [])

    const getMe = async (): Promise<User> => {
        try {
            const accessToken = getAccessToken()
            const userResponse: AxiosResponse<User> = await api.get('/users/me', {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
            return {
                id: userResponse.data.id,
                email: userResponse.data.email
            }
        } catch (error) {
            console.error('Get current user call failed: ', error)
            throw error
        }
    }

    const login = async (providedEmail: string, password: string): Promise<void> => {
        try {
            const response: AxiosResponse<LoginResponse> = await api.post('/auth/login', { email: providedEmail, password })
            const { access_token: accessToken, refresh_token: refreshToken } = response.data

            storeTokens(accessToken, refreshToken)

            const user = await getMe()
            
            setUser(user)
            
        } catch (error) {
            console.error('Login failed:', error)
            throw error
        }
    }

    const logout = (): void => {
        // make call to backend to future logout endpoint
        // TODO: Implement backend logout endpoint to invaliate refresh tokens
        setUser(null)
        clearTokens()
    }

    const register = async (email: string, password: string): Promise<void> => {
        try {
            const response: AxiosResponse<User> = await api.post('/users/register', { email, password })
            const { email: registeredEmail } = response.data
            try {
                await login(registeredEmail, password)
            } catch (loginError) {
                console.error('Auto-login after registration failed:', loginError)
                throw loginError
            }
        } catch (error) {
            console.error('Registration failed:', error)
            throw error
        }
        
    }

    return (
        <AuthContext.Provider value={{ user, isLoading, login, logout, register }}>
            {children}
        </AuthContext.Provider>
    )
}
