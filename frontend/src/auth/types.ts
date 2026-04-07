export interface User {
    id: string
    email: string
}

export interface AuthContextType {
    user: User | null
    isLoading: boolean
    login: (email: string, passsword: string) => Promise<void>
    logout: () => void
    register: (email: string, password: string) => Promise<void>
}

export interface LoginResponse {
    access_token: string
    refresh_token: string
    token_type: string
    expires_in: number
}