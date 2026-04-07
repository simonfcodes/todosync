import { Route, Routes } from 'react-router-dom'
import { LoginForm } from './auth/LoginForm'
import { PublicRoute } from './auth/PublicRoute'
import { ProtectedRoute } from './auth/ProtectedRoute'
import { RegistrationForm } from './auth/RegistrationForm'
import { TodoList } from './list/TodoList'
import { CreateTodoListForm } from './list/CreateTodoListForm'


function App() {  
    
    return (
        <>
            <Routes>
                <Route path="/login" element={<PublicRoute><LoginForm /></PublicRoute>} /> 
                <Route path="/" element={
                    <ProtectedRoute>
                        <h1>Home</h1>
                        <TodoList />
                        <CreateTodoListForm />
                    </ProtectedRoute>} />
                <Route path="/register" element={<PublicRoute><RegistrationForm /></PublicRoute>} />
                <Route path="/test" element={<ProtectedRoute><h1>Test</h1></ProtectedRoute>} />
            </Routes>
        </>
    )
}
    
export default App