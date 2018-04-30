package mobileprogramming.kivanc.com.todos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import mobileprogramming.kivanc.com.todos.model.Todo;

/**
 * Created by Kivanc on 13.12.2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    Activity mContext;
    ArrayList<Todo> mTodos;

    public MyAdapter(Activity context, ArrayList<Todo> todos) {
        mContext = context;
        mTodos = todos;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.todo, parent,false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.id = mTodos.get(position).getId();
        holder.number.setText(String.valueOf(position + 1));
        holder.title.setText(String.valueOf(mTodos.get(position).getTitle()));
        holder.detail.setText(String.valueOf(mTodos.get(position).getDetail()));
        holder.date.setText(mTodos.get(position).getDate().toString());
        holder.time.setText(mTodos.get(position).getTime().toString());
        holder.priority.setText(String.valueOf(mTodos.get(position).getPriority()));
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

        public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView number, title, detail, date, time, priority;
            ImageButton edit, delete;
            long id;

            public MyHolder(View v) {
                super(v);

                number = (TextView) v.findViewById(R.id.num);
                title = (TextView) v.findViewById(R.id.title);
                date = (TextView) v.findViewById(R.id.date);
                time = (TextView) v.findViewById(R.id.time);
                detail = (TextView) v.findViewById(R.id.detail);
                priority = (TextView) v.findViewById(R.id.priority);
                edit=(ImageButton) v.findViewById(R.id.edit);
                delete=(ImageButton) v.findViewById(R.id.delete);
                edit.setOnClickListener(this);
                delete.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                if (v.getId()==edit.getId()){
                    AddOrEditTodoDialogFragment addOrEditTodoDialogFragment = new AddOrEditTodoDialogFragment();
                    addOrEditTodoDialogFragment.setDialogTitle("Edit ToDo");
                    addOrEditTodoDialogFragment.setId(id);
                    addOrEditTodoDialogFragment.setTitleStr(title.getText().toString());
                    addOrEditTodoDialogFragment.setDetailStr(detail.getText().toString());
                    addOrEditTodoDialogFragment.setDateStr(date.getText().toString());
                    addOrEditTodoDialogFragment.setTimeStr(time.getText().toString());
                    addOrEditTodoDialogFragment.setPriority(priority.getText().toString());
                    addOrEditTodoDialogFragment.show(mContext.getFragmentManager(), "");
                }

                if (v.getId()==delete.getId()){
                    Todo todo = new Todo(id, title.getText().toString(), detail.getText().toString(), date.getText().toString(), time.getText().toString(), priority.getText().toString());
                    MainActivity.myAsyncTask myAsync = new MainActivity.myAsyncTask(mContext, OperationType.OPERATION_DELETE, todo);
                    myAsync.execute();
                }

            }

        }
}
