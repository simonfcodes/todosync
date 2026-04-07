import { Navigate } from "react-router-dom"
import { Spinner } from "../common/Spinner"
import { useAuth } from "./useAuth"

export const PublicRoute = ({ children }: { children: React.ReactNode }) => {
    const { user, isLoading } = useAuth()

    return (
        <>
            {isLoading ? <Spinner /> : user ? <Navigate to="/" /> : children}
        </>
    )
}