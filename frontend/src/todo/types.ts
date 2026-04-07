export type Priority = 'LOW' | 'MEDIUM' | 'HIGH';

export interface TodoResponse {
    id: string
    listId: string
    title: string
    description: string
    completed: boolean
    priority: Priority
    dueDate: string | null
    createdAt: string
    updatedAt: string
}

