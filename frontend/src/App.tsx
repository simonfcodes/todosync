import { Route, Routes } from 'react-router-dom'
import { LoginForm } from './auth/LoginForm'
import { PublicRoute } from './auth/PublicRoute'
import { ProtectedRoute } from './auth/ProtectedRoute'


function App() {  

  return (
    <>
      <Routes>
        <Route path="/login" element={<PublicRoute><LoginForm /></PublicRoute>} /> 
        <Route path="/" element={<ProtectedRoute><h1>Home</h1></ProtectedRoute>} />
      </Routes>
    </>
  )
}

export default App