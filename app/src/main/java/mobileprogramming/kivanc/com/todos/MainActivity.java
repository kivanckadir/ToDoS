package mobileprogramming.kivanc.com.todos;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import mobileprogramming.kivanc.com.todos.database.TodoDAOImpl;
import mobileprogramming.kivanc.com.todos.model.Todo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static RecyclerView list;
    static LinearLayout empty;
    static ProgressBar wait;

    FragmentManager fragmentManager = getFragmentManager();
    FloatingActionButton fab;
    AddOrEditTodoDialogFragment addOrEditTodoDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        empty = (LinearLayout) findViewById(R.id.empty);
        list = (RecyclerView) findViewById(R.id.list);
        wait = (ProgressBar) findViewById(R.id.progressBar);

        fab.setOnClickListener(this);

        myAsyncTask myAsyncTask = new myAsyncTask(this, OperationType.OPERATION_LIST);
        myAsyncTask.execute();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==fab.getId()){
            addOrEditTodoDialogFragment = new AddOrEditTodoDialogFragment();
            addOrEditTodoDialogFragment.setDialogTitle("New ToDo");
            addOrEditTodoDialogFragment.show(fragmentManager,"addOrEditTodoDialogFragment");
        }
    }

    /**
     * Created by Kivanc on 13.12.2017.
     */

    public static class myAsyncTask extends AsyncTask<Void, ArrayList, ArrayList> {

        Activity mContext;
        int mOperationType;
        Todo mTodo;
        ArrayList<Todo> todoArrayList;

        public myAsyncTask(Activity context, int operationType) {
            mContext = context;
            mOperationType = operationType;
        }

        public myAsyncTask(Activity context, int operationType, Todo todo) {
            mContext = context;
            mOperationType = operationType;
            mTodo = todo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            wait.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {

            TodoDAOImpl todoDAO = new TodoDAOImpl(mContext);

            switch (mOperationType){
                case OperationType.OPERATION_LIST:
                    todoDAO.open();
                    todoArrayList = todoDAO.fetchAllTodos();
                    todoDAO.close();
                    return todoArrayList;

                case OperationType.OPERATION_ADD:
                    todoDAO.open();
                    todoDAO.addTodo(mTodo);
                    todoArrayList = todoDAO.fetchAllTodos();
                    todoDAO.close();
                    return todoArrayList;

                case OperationType.OPERATION_DELETE:
                    todoDAO.open();
                    todoDAO.deleteTodo(mTodo);
                    todoArrayList = todoDAO.fetchAllTodos();
                    todoDAO.close();
                    return todoArrayList;

                case OperationType.OPERATION_EDIT:
                    todoDAO.open();
                    todoDAO.updateTodo(mTodo);
                    todoArrayList = todoDAO.fetchAllTodos();
                    todoDAO.close();
                    return todoArrayList;

                default: return todoArrayList;
            }
        }

        @Override
        protected void onPostExecute(ArrayList todoArrayList) {
            super.onPostExecute(todoArrayList);

            wait.setVisibility(View.INVISIBLE);

            Log.w("Record Count", String.valueOf(todoArrayList.size()));

            if(todoArrayList.size() <= 0){
                list.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }

            else {

                MyAdapter adapter = new MyAdapter(mContext, todoArrayList);
                list.setAdapter(adapter);

                empty.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
            }
        }
    }
}
