package ahmed.haikal.myplanner.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ahmed.haikal.myplanner.ClientServerCalls.RetrofitService;
import ahmed.haikal.myplanner.ClientServerCalls.TaskApi;
import ahmed.haikal.myplanner.Controller.Adapters.TaskListAdapter;
import ahmed.haikal.myplanner.Controller.Database.DatabaseController;
import ahmed.haikal.myplanner.Controller.Database.DatabaseTask;
import ahmed.haikal.myplanner.Controller.Listeners.Task_Touch_Listener;
import ahmed.haikal.myplanner.Model.AddNewTask;
import ahmed.haikal.myplanner.Model.TaskCard;
import ahmed.haikal.myplanner.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskListFragment extends Fragment {
    private TextView list_title;
    private static RecyclerView taskList_recyclerview;
    private static TaskListAdapter taskList_adapter;
    private FloatingActionButton addTask, backToMainScreen;
    private static List<TaskCard> task_list;
    private static List<Integer> tasks_IDs;
    private ItemTouchHelper.Callback callback;
    private static String title;
    private static int listID, userID, checkBoxColor;

    public static TaskListFragment newInstance(String list_title, int list_ID, int user_ID, int chBoxColor){
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(title, list_title);
        args.putInt("listID", list_ID);
        args.putInt("userID", user_ID);
        args.putInt("checkBoxColor", chBoxColor);
        fragment.setArguments(args);
        checkBoxColor = chBoxColor;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create task list arrayList to fill it with data from the database
        task_list = new ArrayList<>();
        tasks_IDs = new ArrayList<>();

        //retrieve the information from the bundle. Some of this information will be used to retrieve the data
        title = getArguments().getString(title);
        listID = getArguments().getInt("listID");
        userID = getArguments().getInt("userID");
        checkBoxColor = getArguments().getInt("checkBoxColor");

        /*
        //set date in array lists to create the database task (retrieve)
        ArrayList<String> fields = new ArrayList<>();
        fields.add("UserID");
        fields.add("ListID");

        ArrayList<String> values = new ArrayList<>();
        values.add(String.valueOf(userID) + " AND ");
        values.add(String.valueOf(listID));

        DatabaseTask getTasks = new DatabaseTask.Retrieve(DatabaseController.getInstance(), "TASKS",
                fields, values, getContext());
        getTasks.execute();

         */

        // ================ TRY USING THE SPRING SERVER ================ //

        RetrofitService retrofitService = new RetrofitService();
        TaskApi taskApi = retrofitService.getRetrofit().create(TaskApi.class);
        taskApi.getTasksByListID(listID)
                .enqueue(new Callback<List<TaskCard>>() {
            @Override
            public void onResponse(Call<List<TaskCard>> call, Response<List<TaskCard>> response) {
                if(response.body() != null)
                    task_list = response.body();
                else
                    Toast.makeText(null, "No Tasks in this List", Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<List<TaskCard>> call, Throwable t) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_task_list, container, false);
        list_title = view.findViewById(R.id.list_title);
        taskList_recyclerview = view.findViewById(R.id.task_list_recyclerview);
        addTask = view.findViewById(R.id.add_item_fab);
        backToMainScreen = view.findViewById(R.id.back_to_mainScreen_fab);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // load data from database

        //set list title
        /*
        getActivity().getSupportFragmentManager().setFragmentResultListener("title", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                title = result.getString("title");
                list_title.setText(title);

                listID = result.getInt("id");
                System.out.println("list name from all_lists_fragment: " + title);
                System.out.println("user ID from all_lists_fragment: " + listID);

            }
        });
        */
        //list title
        list_title.setText(title);
        //set recyclerview adapter
        taskList_adapter = new TaskListAdapter(task_list);
        taskList_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        taskList_recyclerview.setAdapter(taskList_adapter);

        //add animation to the recyclerview
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        taskList_recyclerview.setItemAnimator(itemAnimator);

        //set onclick listener for the recyclerview
        callback = new Task_Touch_Listener(taskList_adapter, null, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(taskList_recyclerview);

        // define buttons functionality
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = list_title.getText().toString();
                AddNewTask.newInstance(title);
            }
        });

        backToMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(
                //        R.id.fragment_container, new All_Lists_Fragment()).commit();
                getActivity().getFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new All_Lists_Fragment()).commit();
            }
        });
    }

    public static TaskListAdapter getTaskList_adapter(){
        return taskList_adapter;
    }

    public static int getListID(){
        return listID;
    }

    public static int getCheckBoxColor() {
        return checkBoxColor;
    }

    public static int getTaskID(int position){
        return tasks_IDs.get(position);
    }

    public static void updateTaskInDB(int position, String field, String status){
        ArrayList<String> updateFields = new ArrayList<>();
        updateFields.add(field);
        ArrayList<String> updateValues = new ArrayList<>();
        updateValues.add(status);
        DatabaseTask update = new DatabaseTask.UpdateRecords(DatabaseController.getInstance(), "", "TASKS",
                "TaskID", String.valueOf(getTaskID(position)), updateFields, updateValues, null);
        update.execute();
    }

    public static void updateTaskID(TaskCard task){

    }
    public static boolean loadTasks(ResultSet tasks) throws SQLException {
        TaskCard task = new TaskCard(tasks.getInt("Status"), tasks.getString("TaskName"),
                tasks.getString("DeadLine"), tasks.getString("Reminder"), tasks.getInt("CheckBoxColor"));
        task_list.add(task);
        tasks_IDs.add(tasks.getInt("TaskID"));
        return true;
    }

    public static void refreshTaskListPage(){
        taskList_adapter = new TaskListAdapter(task_list);
        taskList_recyclerview.setAdapter(taskList_adapter);
    }

    public static void deleteTask(int position){
        DatabaseTask deleteTask = new DatabaseTask.Delete(DatabaseController.getInstance(), "", "TASKS",
                "TaskID", String.valueOf(getTaskID(position)), null);
        deleteTask.execute();
    }
}
