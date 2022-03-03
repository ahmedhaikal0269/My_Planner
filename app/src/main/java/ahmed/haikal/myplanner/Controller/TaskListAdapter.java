package ahmed.haikal.myplanner.Controller;

/**
 *  This class will be responsible for filling up the task list (recyclerview) when
 *  the user clicks on the list, this class will be called and it will receive a list of the tasks
 *  and it will fill out the task cards and create the view for the user
 *  */
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import ahmed.haikal.myplanner.Controller.TaskListAdapter.Task_View_Holder;
import ahmed.haikal.myplanner.Model.TaskCard;
import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.TaskListActivity;

public class TaskListAdapter extends RecyclerView.Adapter<Task_View_Holder> {

    private List<TaskCard> taskList;
    private TaskListActivity taskListActivity;


    public TaskListAdapter(List<TaskCard> taskList, TaskListActivity taskListActivity) {
        this.taskList = taskList;
        this.taskListActivity = taskListActivity;
    }

    @Override
    public Task_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_cardview, parent, false);
        Task_View_Holder holder = new Task_View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Task_View_Holder holder, int position) {
        TaskCard taskCard = taskList.get(position);
        holder.task.setText(taskCard.getTask());
        holder.date.setText(taskCard.getDate());
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                //pick the date
                DatePickerDialog picker = new DatePickerDialog(taskListActivity.getApplicationContext(),
                        (view1, year1, monthOfYear, dayOfMonth) -> holder.date.setText((monthOfYear+1) + "/" + dayOfMonth + "/" + year1), year, month, day);
                picker.show();
            }
        });

        //check task status
        holder.taskCheckBox.setChecked(checkTaskStatus(taskCard.getStatus()));

        //create a click listener for the checkbox
        holder.taskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.taskCheckBox.isChecked()){
                    holder.task.setTextColor(Color.GRAY);
                    holder.date.setTextColor(Color.GRAY);
                    holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else{
                    holder.task.setPaintFlags(0);
                    holder.task.setTextColor(Color.BLACK);
                    holder.date.setTextColor(Color.BLACK);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public boolean checkTaskStatus(int status){
        return status != 0;
    }


    public void insertTask(TaskCard task){
        taskList.add(task);
        notifyItemInserted(taskList.size() - 1);
    }

    //this method should allow the user to edit the task, date, or cancel the reminder
    public void editTask(int position){

    }

    public void editTaskStatus(){

    }

    public void removeTask(int position){
        taskList.remove(position);
        notifyItemRemoved(position);
    }



    // ===================== View Holder inner class =====================//
    class Task_View_Holder extends RecyclerView.ViewHolder{

        CardView task_cardview;
        TextView task, date, reminder;
        CheckBox taskCheckBox;

        public Task_View_Holder(View itemView) {
            super(itemView);
            task_cardview = itemView.findViewById(R.id.taskCardView);
            task = itemView.findViewById(R.id.task_name);
            date = itemView.findViewById(R.id.task_date);
            reminder = itemView.findViewById(R.id.set_reminder);
            taskCheckBox = itemView.findViewById(R.id.taskCheckBox);
        }

    }
}