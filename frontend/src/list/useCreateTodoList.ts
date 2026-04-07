import { useMutation, useQueryClient } from "@tanstack/react-query";
import api from "../api/axios";
import type { TodoListResponse } from "./types";
import type { AxiosResponse } from "axios";

const createTodoList = async (name: string): Promise<TodoListResponse> => {
    const response: AxiosResponse<TodoListResponse> = await api.post('/lists', { name })
    return response.data
}

export const useCreateTodoList = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: createTodoList,
        onSuccess: () => {            
            queryClient.invalidateQueries({
                queryKey: ['todoLists']
            });
        }
    })
}