package ahmed.haikal.myplanner.Model;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

import ahmed.haikal.myplanner.ClientServerCalls.RetrofitService;
import ahmed.haikal.myplanner.ClientServerCalls.TaskApi;
import ahmed.haikal.myplanner.Controller.Adapters.TaskListAdapter;
import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Activities.HomeScreenActivity;
import ahmed.haikal.myplanner.View.Fragments.TaskListFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTask extends DialogFragment {

    EditText taskInputText;
    TextView taskDate, taskReminder;
    Button saveTask, cancel;
    static String list_title;
    String dialogTitle = "Add New Task";
    TaskListAdapter taskListAdapter;

    /**
     * the constructor to this class is empty because it implements the
     * singleton design pattern.
     */

    private AddNewTask(){
        //empty constructor.
    }

    public static AddNewTask newInstance(String title){
        list_title = title;
        return new AddNewTask();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstance){

        this.getDialog().setTitle("dialog title");
        this.getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.add_new_task, group, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

        //=============== define the components on the add new task page ===============//
        taskInputText = view.findViewById(R.id.taskInputText);
        taskDate = view.findViewById(R.id.select_date);
        taskReminder = view.findViewById(R.id.set_reminder);
        saveTask = view.findViewById(R.id.saveTask);
        cancel = view.findViewById(R.id.cancel);

        taskListAdapter = TaskListFragment.getTaskList_adapter();

        //=============== Add functionality to the components of the add new task page ===============//

        //activate the save button when there is text in the task input field
        taskInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // not needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    saveTask.setEnabled(false);
                    saveTask.setTextColor(Color.GRAY);
                }
                else {
                    saveTask.setEnabled(true);
                    saveTask.setTextColor(Color.BLUE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // not needed
            }
        });

        //select a date/set a reminder functionality
        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                //pick the date

                DatePickerDialog picker = new  DatePickerDialog(getContext(),
                        (view1, year1, monthOfYear, dayOfMonth) -> taskDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
                picker.show();
            }
        });

        taskReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //save/cancel button functionality
        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkBoxColor = TaskListFragment.getCheckBoxColor();
                System.out.println("color from add new task: " + checkBoxColor);
                String taskInput = taskInputText.getText().toString();
                // get Date from User
                String task_date = taskDate.getText().toString();
                String reminder = taskReminder.getText().toString();
                TaskCard task = new TaskCard(0, taskInput,
                        taskDate.getText().toString(), taskReminder.getText().toString(), checkBoxColor);
                //add to recyclerview
                taskListAdapter.insertTask(task);

                System.out.println("I'm adding a task");

                // ================ TRY USING THE SPRING SERVER ================ //

                RetrofitService retrofitService = new RetrofitService();
                TaskApi taskApi = retrofitService.getRetrofit().create(TaskApi.class);
                taskApi.addNewTask(task)
                        .enqueue(new Callback<TaskCard>() {
                            @Override
                            public void onResponse(Call<TaskCard> call, Response<TaskCard> response) {
                                if(response.body() != null)
                                    Toast.makeText(view.getContext(), "task added successfully", Toast.LENGTH_LONG);
                                else{
                                    Toast.makeText(view.getContext(), "something went wrong", Toast.LENGTH_LONG);
                                    System.out.println("didn't work out, bro");
                                }
                            }

                            @Override
                            public void onFailure(Call<TaskCard> call, Throwable t) {
                                Toast.makeText(view.getContext(), "couldn't add task, please try again later", Toast.LENGTH_LONG);
                            }
                        });

                /*add to database
                ArrayList<String> fields = new ArrayList<>();
                fields.add("TaskName");
                fields.add("Status");
                fields.add("DeadLine");
                fields.add("Reminder");
                fields.add("ListID");
                fields.add("UserID");
                fields.add("CheckBoxColor");

                ArrayList<String> values = new ArrayList<>();
                values.add("'" + taskInput + "'");
                values.add(String.valueOf(0));
                values.add("'" + taskDate.getText().toString() + "'");
                values.add("'" + taskReminder.getText().toString()+ "'");
                values.add(String.valueOf(TaskListFragment.getListID()));
                values.add(String.valueOf(HomeScreenActivity.getActingUserID()));
                values.add(String.valueOf(TaskListFragment.getCheckBoxColor()));

                DatabaseTask addTask = new DatabaseTask.Insert(DatabaseController.getInstance(),
                        "TASKS", fields, values, getContext());
                addTask.execute();
                */

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });
    }


    // this method expands the size of the dialog fragment to show all the content of the
    // add new task box
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
