package mobileprogramming.kivanc.com.todos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;
import java.util.List;

import mobileprogramming.kivanc.com.todos.model.Todo;

/**
 * Created by Kivanc on 12.12.2017.
 */

public class TodoDAOImpl implements TodoDAO {
    public static final String LOGTAG = "TODO_DATABASE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DatabaseHandler.COLUMN_ID,
            DatabaseHandler.COLUMN_TITLE,
            DatabaseHandler.COLUMN_DETAIL,
            DatabaseHandler.COLUMN_DATE,
            DatabaseHandler.COLUMN_TIME,
            DatabaseHandler.COLUMN_PRIORITY
    };

    public TodoDAOImpl(Context context) {
        dbhandler = new DatabaseHandler(context);
    }

    public void open(){
        database = dbhandler.getWritableDatabase();
        Log.w( LOGTAG,"DB Connection Opened.");
    }

    public void close(){
        dbhandler.close();
        Log.w(LOGTAG, "DB Connection Closed");
    }

    @Override
    public Todo addTodo(Todo todo) {
        ContentValues values  = new ContentValues();
        values.put(DatabaseHandler.COLUMN_TITLE     ,   todo.getTitle());
        values.put(DatabaseHandler.COLUMN_DETAIL    ,   todo.getDetail());
        values.put(DatabaseHandler.COLUMN_DATE      ,   todo.getDate());
        values.put(DatabaseHandler.COLUMN_TIME      ,   todo.getTime());
        values.put(DatabaseHandler.COLUMN_PRIORITY  ,   todo.getPriority());

        long insertId = database.insert(DatabaseHandler.TABLE_TODO,null,values);
        todo.setId(insertId);

        Log.w(LOGTAG, "Record added: "+todo.getId()+" "+todo.getTitle()+" "+todo.getDetail()+" "+todo.getDate()+" "+todo.getTime()+" "+todo.getPriority());
        return todo;
    }

    @Override
    public Todo fetchTodoById(long id) {
        Cursor cursor = database.query(DatabaseHandler.TABLE_TODO, allColumns,DatabaseHandler.COLUMN_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Todo todo = new Todo(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        Log.w(LOGTAG, "Record fetched: "+todo.getId()+" "+todo.getTitle()+" "+todo.getDetail()+" "+todo.getDate()+" "+todo.getTime()+" "+todo.getPriority());
        return todo;
    }

    @Override
    public int updateTodo(Todo todo) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHandler.COLUMN_TITLE, todo.getTitle());
        values.put(DatabaseHandler.COLUMN_DETAIL, todo.getDetail());
        values.put(DatabaseHandler.COLUMN_DATE, todo.getDate());
        values.put(DatabaseHandler.COLUMN_TIME, todo.getTime());
        values.put(DatabaseHandler.COLUMN_PRIORITY, todo.getPriority());

        Log.w(LOGTAG, "Record updated: "+todo.getId()+" "+todo.getTitle()+" "+todo.getDetail()+" "+todo.getDate()+" "+todo.getTime()+" "+todo.getPriority());
        return database.update(DatabaseHandler.TABLE_TODO, values, DatabaseHandler.COLUMN_ID + "=?", new String[]{String.valueOf(todo.getId())});
    }

    @Override
    public void deleteTodo(Todo todo) {
        database.delete(DatabaseHandler.TABLE_TODO, DatabaseHandler.COLUMN_ID + "=" + todo.getId(), null);
        Log.w(LOGTAG, "Record deleted: "+todo.getId()+" "+todo.getTitle()+" "+todo.getDetail()+" "+todo.getDate()+" "+todo.getTime()+" "+todo.getPriority());
    }

    @Override
    public ArrayList<Todo> fetchAllTodos() {

        Cursor cursor = database.query(DatabaseHandler.TABLE_TODO, allColumns,null,null,null, null, null);

        ArrayList<Todo> todos = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Todo todo = new Todo();

                todo.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.COLUMN_ID)));
                todo.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_TITLE)));
                todo.setDetail(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_DETAIL)));
                todo.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_DATE)));
                todo.setTime(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_TIME)));
                todo.setPriority(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PRIORITY)));

                todos.add(todo);
            }
        }
        Log.w(LOGTAG, "All records fetched");
        return todos;
    }
}
