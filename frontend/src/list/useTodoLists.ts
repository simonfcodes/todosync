import { useQuery } from "@tanstack/react-query";
import api from "../api/axios";
import type { AxiosResponse } from "axios";
import type { TodoListResponse } from "./types";

const getTodoLists = async () => {
    const response: AxiosResponse<TodoListResponse[]> = await api.get('/lists')
    return response.data    
}

export const useTodoLists = () => {
    return useQuery({
        queryKey: ['todoLists'],
        queryFn: getTodoLists
    })
}