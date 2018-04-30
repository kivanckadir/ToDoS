package mobileprogramming.kivanc.com.todos.database;

import java.util.ArrayList;

import mobileprogramming.kivanc.com.todos.model.Todo;

/**
 * Created by Kivanc on 12.12.2017.
 */

public interface TodoDAO {

    public Todo addTodo(Todo todo);
    public Todo fetchTodoById(long id);
    public int updateTodo(Todo todo);
    public void deleteTodo(Todo todo);
    public ArrayList<Todo> fetchAllTodos();
}
