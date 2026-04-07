import type { TodoResponse } from "../todo/types"

export interface TodoListResponse {
    id: string
    name: string
    createdAt: string
}

export interface TodoListDetailResponse extends TodoListResponse {
    todos: TodoResponse[]
}