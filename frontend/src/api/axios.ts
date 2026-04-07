import axios from 'axios'
import { clearTokens, getAccessToken, getRefreshToken, storeTokens } from '../auth/tokenStorage'

let refreshPromise: Promise<void> | null = null

const api = axios.create({
    baseURL: '/api',
    headers: {'Content-Type': 'application/json'}
})

api.interceptors.request.use(config => {
    const accessToken = getAccessToken()
    if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`
    }
    return config
})

api.interceptors.response.use(response => response, async error => {
    if (error.response && error.response.status === 401) {        
        return unauthorizedAccess(error)
    }
    return Promise.reject(error)
})

const unauthorizedAccess = async (error: any) => {
    try {
        if (!refreshPromise) {
            refreshPromise = refreshToken().finally(() => {
                refreshPromise = null
            })
            await refreshPromise
        } else {
            await refreshPromise            
        }
        error.config.headers.Authorization = `Bearer ${getAccessToken()}`
        return api(error.config)
    } catch (error) {
        console.error('Token refresh failed:', error)            
        window.location.href = '/login'
        return Promise.reject(error)
    }
}

const refreshApi = axios.create({
    baseURL: '/api',
    headers: {'Content-Type': 'application/json'}
})

const refreshToken = async () => {
    const refreshToken = getRefreshToken() 
    if (refreshToken) {
        try {
            const response = await refreshApi.post('/auth/refresh', { refresh_token: refreshToken })
            const { access_token, refresh_token } = response.data
            storeTokens(access_token, refresh_token)
            
        } catch (refreshError) {
            console.error('Token refresh failed:', refreshError)
            clearTokens()
            throw refreshError
        }
    } else {
        throw new Error('No refresh token available')
    }
}

export default api