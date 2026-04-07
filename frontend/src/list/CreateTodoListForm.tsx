import { useState } from "react"
import { useCreateTodoList } from "./useCreateTodoList"

export const CreateTodoListForm = () => {
    const {mutate: createTodoList, isPending, isError} = useCreateTodoList()
    const [listName, setListName] = useState('')
    const [error, setError] = useState<string | null>(null)

    const handleSubmit = (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        if (listName.trim() === '') {
            return
        }

        createTodoList(listName, {
            onSuccess: () => {
                setListName('')
                console.log('Todo list created successfully') // TODO - replace with toast notification
            },
            onError: (error) => {
                console.error('Failed to create todo list', error)
                setError(error.message || 'Failed to create todo list')
                // TODO - replace with toast notification
            }
        })        
    }

    return (
        <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded shadow">
            <h2 className="text-2xl font-bold mb-4">Create New Todo List</h2>
            <form onSubmit={handleSubmit}>
                <input 
                    type="text" 
                    name="listName"
                    value={listName}
                    onChange={(e) => setListName(e.target.value)}
                    placeholder="List Name" 
                    className="w-full p-3 mb-4 border rounded" 
                />
                {isError && <p className="text-red-500 text-sm mb-4">{error}</p>}
                <button 
                    type="submit" 
                    className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600"
                    disabled={isPending}
                >
                    {isPending ? 'Creating...' : 'Create List'}
                </button>
            </form>
        </div>
    )
}