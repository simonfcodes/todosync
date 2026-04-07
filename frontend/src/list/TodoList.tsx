import { Spinner } from "../common/Spinner";
import { useTodoLists } from "./useTodoLists";

export const TodoList = () => {
    const { data: todoLists, isLoading, error } = useTodoLists();

    if (isLoading) {
        return <Spinner />;
    }

    if (error) {
        return <div>Error loading todo lists</div>;
    }

    return (
        <>
            <ul>
                {todoLists?.map((list) => (
                    <li key={list.id}>{list.name}</li>
                ))}
            </ul>
        </>
    )
}