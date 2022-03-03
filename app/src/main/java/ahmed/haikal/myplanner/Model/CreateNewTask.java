package ahmed.haikal.myplanner.Model;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ahmed.haikal.myplanner.R;

public class CreateNewTask extends DialogFragment {

    EditText taskInputText;
    TextView taskDate, taskReminder;
    Button saveTask, cancel;
    String list_title;
    String dialogTitle = "Add New Task";

    /**
     * the constructor to this class is empty because it implements the
     * singleton design pattern.
     */

    private CreateNewTask(){
        //empty constructor.
    }

    public static CreateNewTask newInstance(){
        return new CreateNewTask();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstance){

        this.getDialog().setTitle("dialog title");
        this.getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.create_new_task, group, false);
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

        //button functionality
        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskInput = taskInputText.getText().toString();
                // get Date from User
                TaskCard task = new TaskCard(0,0, taskInput,
                        taskDate.getText().toString());
                //add to adapter
                //taskList_adapter.insertTask(task);
                //add to database
                System.out.println("task " + task.getTask() + " in list 'no idea'"  + " in index 0");
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
