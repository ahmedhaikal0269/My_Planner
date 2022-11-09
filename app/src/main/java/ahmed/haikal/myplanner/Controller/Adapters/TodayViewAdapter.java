package ahmed.haikal.myplanner.Controller.Adapters;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Calendar;
import java.util.List;
import ahmed.haikal.myplanner.Controller.Listeners.ItemTouchListener;
import ahmed.haikal.myplanner.Model.TaskCard;
import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Fragments.TaskListFragment;
import ahmed.haikal.myplanner.View.Fragments.TodayViewFragment;

public class TodayViewAdapter extends RecyclerView.Adapter<TodayViewAdapter.Task_View_Holder> implements ItemTouchListener {

    private List<TaskCard> taskList;
    private final int taskCard_originalColor = Color.rgb(255,255,153);
    private final int taskCard_isDoneColor = Color.LTGRAY;

    public TodayViewAdapter (List<TaskCard> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public Task_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_cardview, parent, false);
        Task_View_Holder holder = new Task_View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Task_View_Holder holder, @SuppressLint("RecyclerView") int position) {
        TaskCard taskCard = taskList.get(position);
        holder.task_cardview.setCardBackgroundColor(getCardColor(taskCard.getStatus()));
        holder.task.setText(taskCard.getTask());
        holder.date.setText(taskCard.getDate());

        holder.date.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                //pick the date
                DatePickerDialog picker = new DatePickerDialog(view.getContext(),
                        (view1, year1, monthOfYear, dayOfMonth) -> {
                            holder.date.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1);
                            // update task date in database
                            TaskListFragment.updateTaskInDB(position, "DeadLine", "'" + holder.date.getText().toString() + "'");
                        }, year, month, day);

                picker.show();
            }
        });

        //set task checkBox color to list background color
        holder.taskCheckBox.setButtonTintList(ColorStateList.valueOf(taskCard.getCheckBoxColor()));

        //check task status
        holder.taskCheckBox.setChecked(checkTaskStatus(taskCard.getStatus()));

        //if task is done in the databse, set the components of the cardview to the grey color
        if(checkTaskStatus(taskCard.getStatus())){
            holder.task.setTextColor(Color.GRAY);
            holder.date.setTextColor(Color.GRAY);
            holder.reminder.setTextColor(Color.GRAY);
            holder.task_cardview.setCardBackgroundColor(taskCard_isDoneColor);
            holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        //create a click listener for the checkbox
        holder.taskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.taskCheckBox.isChecked()){
                    holder.task.setTextColor(Color.GRAY);
                    holder.date.setTextColor(Color.GRAY);
                    holder.reminder.setTextColor(Color.GRAY);
                    holder.task_cardview.setCardBackgroundColor(taskCard_isDoneColor);
                    holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    //update task date in database
                    TodayViewFragment.updateTaskInDB(position, "Status", String.valueOf(1));
                }
                else{
                    holder.task.setPaintFlags(0);
                    holder.task.setTextColor(Color.BLACK);
                    holder.date.setTextColor(Color.BLACK);
                    holder.task_cardview.setCardBackgroundColor(taskCard_originalColor);
                    holder.reminder.setTextColor(Color.BLACK);
                    //update task date in database
                    TodayViewFragment.updateTaskInDB(position, "Status", String.valueOf(0));
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

    public int getCardColor(int status){
        if(status == 0)
            return taskCard_originalColor;
        return taskCard_isDoneColor;
    }

    //item touch(swipe and drag) listener methods
    @Override
    public void onItemMove(int fromPos, int toPos) {
        TaskCard task = taskList.get(fromPos);
        taskList.remove(fromPos);
        taskList.add(toPos, task);
        notifyItemMoved(fromPos, toPos);
    }

    @Override
    public void onItemSwiped(int position, int direction) {

    }


    public void insertTask(TaskCard task){
        taskList.add(task);
        notifyItemInserted(taskList.size() - 1);
    }

    //this method should allow the user to edit the task, date, or cancel the reminder
    public void editTask(int position, String task){
        taskList.get(position).setTask(task);
    }

    public void editTaskStatus(){

    }

    public void removeTask(int position){
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    // ===================== View Holder inner class =====================//
    class Task_View_Holder extends RecyclerView.ViewHolder {

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
