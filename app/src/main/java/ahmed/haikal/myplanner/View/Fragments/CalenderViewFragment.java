package ahmed.haikal.myplanner.View.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ahmed.haikal.myplanner.Model.TaskCard;
import ahmed.haikal.myplanner.R;

public class CalenderViewFragment extends Fragment {

    public static CalenderViewFragment calenderViewFragment = new CalenderViewFragment();
    private CompactCalendarView compactCalendarView;
    private CalendarView calendarView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy", Locale.getDefault());
    private static ArrayList<TaskCard> tasks;
    private static ArrayList<String> dates;
    public CalenderViewFragment(){
        //empty constructor
    }

    public static CalenderViewFragment newInstance(){
        return calenderViewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasks = new ArrayList<>();
        dates = new ArrayList<>();
        String query = "SELECT * FROM TASKS";

        /*
        DatabaseTask getAllTasks = new DatabaseTask.RetrieveAll(DatabaseController.getInstance(), query, getContext());
        getAllTasks.execute();
        */
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dates_view, container, false);
        //compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        calendarView = view.findViewById(R.id.my_calender);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //compactCalendarView.setUseThreeLetterAbbreviation(false);
        //Event event = new Event(Color.RED, 1652200423000L, "");
        //compactCalendarView.addEvent(event);

    }

    public static boolean loadEvents(ResultSet tasks) throws SQLException {
        TaskCard task = new TaskCard(tasks.getInt("Status"), tasks.getString("TaskName"),
                tasks.getString("DeadLine"), tasks.getString("Reminder"), tasks.getInt("CheckBoxColor"));
        dates.add(task.getDate());
        long date = 0l;
        Event event = new Event(Color.RED, date, task.getTask());
        return true;
    }
}
