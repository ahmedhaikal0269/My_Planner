package ahmed.haikal.myplanner.View.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ahmed.haikal.myplanner.Controller.Adapters.TaskListAdapter;
import ahmed.haikal.myplanner.Controller.Adapters.TodayViewAdapter;
import ahmed.haikal.myplanner.Controller.Database.DatabaseTask;
import ahmed.haikal.myplanner.Controller.Listeners.Task_Touch_Listener;
import ahmed.haikal.myplanner.Model.TaskCard;
import ahmed.haikal.myplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayViewFragment extends Fragment {

    private static TextView today_page_greeting;
    private static RecyclerView today_recyclerview;
    private static TodayViewAdapter taskList_adapter;
    private static List<TaskCard> today_task_list;
    private static List<Integer> today_task_IDs;
    private ItemTouchHelper.Callback callback;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodayViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayViewFragment newInstance(String param1, String param2) {
        TodayViewFragment fragment = new TodayViewFragment();
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
        today_task_list = new ArrayList<>();
        today_task_IDs = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        System.out.println((month+1) + "/" + day + "/" + year);
        String today = (month+1) + "/" + day + "/" + year;
        ArrayList<String> fields = new ArrayList<>();
        fields.add("DeadLine");

        ArrayList<String> values = new ArrayList<>();
        values.add("'" + today + "'");

        /*
        DatabaseTask getTasks = new DatabaseTask.Retrieve(DatabaseController.getInstance(), "TASKS",
                fields, values, getContext());
        getTasks.execute();
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_view, container, false);
        today_page_greeting = view.findViewById(R.id.no_tasks_today_text);
        today_recyclerview = view.findViewById(R.id.today_view_recyclerview);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        taskList_adapter = new TodayViewAdapter(today_task_list);
        today_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        today_recyclerview.setAdapter(taskList_adapter);

        //add animation to the recyclerview
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        today_recyclerview.setItemAnimator(itemAnimator);

        //set onclick listener for the recyclerview
        callback = new Task_Touch_Listener(null, taskList_adapter, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(today_recyclerview);

    }

    public static boolean loadTasks(ResultSet tasks) throws SQLException {
        TaskCard task = new TaskCard(tasks.getInt("Status"), tasks.getString("TaskName"),
                tasks.getString("DeadLine"), tasks.getString("Reminder"), tasks.getInt("CheckBoxColor"));
        today_task_list.add(task);
        today_task_IDs.add(tasks.getInt("TaskID"));
        if (today_task_list.size() == 0)
            today_page_greeting.setVisibility(View.VISIBLE);
        else
            today_page_greeting.setVisibility(View.INVISIBLE);
        return true;
    }

    public static void refreshTodayViewPage(){
        taskList_adapter = new TodayViewAdapter(today_task_list);
        today_recyclerview.setAdapter(taskList_adapter);
    }

    public static void updateTaskInDB(int position, String field, String status){
        ArrayList<String> updateFields = new ArrayList<>();
        updateFields.add(field);
        ArrayList<String> updateValues = new ArrayList<>();
        updateValues.add(status);

        /*
        DatabaseTask update = new DatabaseTask.UpdateRecords(DatabaseController.getInstance(), "", "TASKS",
                "TaskID", String.valueOf(today_task_IDs.get(position)), updateFields, updateValues, null);
        update.execute();
        */
    }

    public static void deleteTask(int position){
        /*
        DatabaseTask deleteTask = new DatabaseTask.Delete(DatabaseController.getInstance(), "", "TASKS",
                "TaskID", String.valueOf(today_task_IDs.get(position)), null);
        deleteTask.execute();
        */
    }
}