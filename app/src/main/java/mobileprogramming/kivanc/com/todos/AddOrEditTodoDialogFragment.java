package mobileprogramming.kivanc.com.todos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import mobileprogramming.kivanc.com.todos.model.Todo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddOrEditTodoDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddOrEditTodoDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOrEditTodoDialogFragment extends android.app.DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText title;
    private EditText detail;
    private EditText date;
    private EditText time;
    private RadioGroup radioGroup;
    private RadioButton unimportant;
    private RadioButton normal;
    private RadioButton important;
    private ImageButton datePickerButton;
    private ImageButton timePickerButton;
    private Button save;
    private Button cancel;

    private String dialogTitle;
    private long id;
    private String titleStr;
    private String detailStr;
    private String dateStr;
    private String timeStr;
    private String priority;

    public AddOrEditTodoDialogFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddOrEditTodoDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOrEditTodoDialogFragment newInstance(String param1, String param2) {
        AddOrEditTodoDialogFragment fragment = new AddOrEditTodoDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);

        title = (EditText) v.findViewById(R.id.title);
        detail = (EditText) v.findViewById(R.id.detail);
        date = (EditText) v.findViewById(R.id.datee);
        time = (EditText) v.findViewById(R.id.timee);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup1);
        unimportant = (RadioButton) v.findViewById(R.id.radioButton1);
        normal  = (RadioButton) v.findViewById(R.id.radioButton2);
        important = (RadioButton) v.findViewById(R.id.radioButton3);
        datePickerButton = (ImageButton) v.findViewById(R.id.datePickerButton);
        timePickerButton = (ImageButton) v.findViewById(R.id.timePickerButton);
        save = (Button) v.findViewById(R.id.save);
        cancel = (Button) v.findViewById(R.id.cancel);

        normal.setChecked(true);

        datePickerButton.setOnClickListener(this);
        timePickerButton.setOnClickListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        getDialog().setTitle(dialogTitle);

        if(dialogTitle.equals("Edit ToDo")){
            title.setText(titleStr);
            detail.setText(detailStr);
            date.setText(dateStr);
            time.setText(timeStr);

            switch (priority){
                case "Unimportant": unimportant.setChecked(true); break;
                case "Normal": normal.setChecked(true); break;
                case "Important": important.setChecked(true); break;
            }
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
              //      + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        Activity mContext = getActivity();

        if(v.getId()==save.getId()){

            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            switch (checkedRadioButtonId){
                case R.id.radioButton1: priority="Unimportant"; break;
                case R.id.radioButton2: priority="Normal";      break;
                case R.id.radioButton3: priority="Important";   break;
            }

            Todo todo;
            MainActivity.myAsyncTask myAsyncTask;
            switch (dialogTitle){

                case "New ToDo":
                    todo = new Todo(0, title.getText().toString(), detail.getText().toString(), date.getText().toString(), time.getText().toString(), priority);
                    myAsyncTask = new MainActivity.myAsyncTask(mContext, OperationType.OPERATION_ADD, todo);
                    myAsyncTask.execute();
                    break;

                case "Edit ToDo":
                    todo = new Todo(id, title.getText().toString(), detail.getText().toString(), date.getText().toString(), time.getText().toString(), priority);
                    myAsyncTask = new MainActivity.myAsyncTask(mContext, OperationType.OPERATION_EDIT, todo);
                    myAsyncTask.execute();
                    break;
            }

            dismiss();
        }

        if (v.getId()==cancel.getId()) {
            dismiss();
        }

        if (v.getId()==datePickerButton.getId()) {

            final DatePicker datePicker = new DatePicker(mContext);
            AlertDialog.Builder db = new AlertDialog.Builder(mContext);
            db.setView(datePicker);

            db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    date.setText(datePicker.getDayOfMonth()+"."+datePicker.getMonth()+"."+datePicker.getYear());
                }
            });

            db.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = db.show();
        }

        if (v.getId()==timePickerButton.getId()) {

            final TimePicker timePicker = new TimePicker(mContext);
            AlertDialog.Builder db = new AlertDialog.Builder(mContext);
            db.setView(timePicker);

            db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    time.setText(timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute());
                }
            });

            db.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = db.show();
        }
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public void setDetailStr(String detailStr) {
        this.detailStr = detailStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
