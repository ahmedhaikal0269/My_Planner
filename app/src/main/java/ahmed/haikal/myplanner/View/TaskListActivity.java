package ahmed.haikal.myplanner.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ahmed.haikal.myplanner.Controller.Adapters.TaskListAdapter;
import ahmed.haikal.myplanner.Controller.Database.DatabaseController;
import ahmed.haikal.myplanner.Controller.Listeners.ItemTouchListener;
import ahmed.haikal.myplanner.Controller.Listeners.Task_Touch_Listener;
import ahmed.haikal.myplanner.Model.AddNewTask;
import ahmed.haikal.myplanner.Model.TaskCard;
import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Main_Screen.MainScreenActivity;

public class TaskListActivity extends AppCompatActivity {

    private TextView list_title;
    private RecyclerView taskList_recyclerview;
    private static final TaskListActivity activity = new TaskListActivity();
    private static TaskListAdapter taskList_adapter;
    private FloatingActionButton addTask, backToMainScreen;
    private static List<TaskCard> task_list;
    private ItemTouchHelper.Callback callback;
    private static DatabaseController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        list_title = findViewById(R.id.list_title);
        addTask = findViewById(R.id.add_item_fab);
        backToMainScreen = findViewById(R.id.back_to_mainScreen_fab);

        //set TaskList Title
        Bundle import_data = getIntent().getExtras();
        if(list_title != null){
            String listTitle = import_data.getString("ListTitle");
            //String title = listTitle;
            list_title.setText(listTitle);
        }

        //here connect to database and get all saved tasks if any
        task_list = new ArrayList<>();

        //define the recyclerview and add it to the adapter
        taskList_recyclerview = findViewById(R.id.task_list_recyclerview);
        taskList_adapter = new TaskListAdapter(task_list, this);
        taskList_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        taskList_recyclerview.setAdapter(taskList_adapter);

        //add animation to the recyclerview
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        taskList_recyclerview.setItemAnimator(itemAnimator);

        //set onclick listener for the recyclerview
        callback = new Task_Touch_Listener(taskList_adapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(taskList_recyclerview);
        //add button functionality
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = list_title.getText().toString();
                AddNewTask.newInstance(title).show(getSupportFragmentManager(), title);
            }
        });
        backToMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskListActivity.this, MainScreenActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
        });
    }

    public TaskListAdapter getTaskList_adapter(){
        return taskList_adapter;
    }

    public static TaskListActivity getActivity() {
        return activity;
    }
}