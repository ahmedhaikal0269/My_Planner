package ahmed.haikal.myplanner.View.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ahmed.haikal.myplanner.ClientServerCalls.ListApi;
import ahmed.haikal.myplanner.ClientServerCalls.RetrofitService;
import ahmed.haikal.myplanner.Controller.Adapters.All_Lists_Adapter;
import ahmed.haikal.myplanner.Controller.Listeners.ItemClickListener;
import ahmed.haikal.myplanner.Model.CreateNewList;
import ahmed.haikal.myplanner.Model.ListCard;
import ahmed.haikal.myplanner.Controller.Listeners.List_Touch_Listener;
import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Activities.HomeScreenActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link All_Lists_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class All_Lists_Fragment extends Fragment {

    private static RecyclerView all_lists_recyclerview;
    private FloatingActionButton addNewList;
    private static All_Lists_Adapter all_lists_adapter;
    private static List<ListCard> all_listCards;
    private static List<Integer> all_listIDs;
    private static int listID;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment All_Lists_Fragment.
     */
    // TODO: Rename and change types and number of parameters

    public static All_Lists_Fragment newInstance(String param1, String param2) {
        All_Lists_Fragment fragment = new All_Lists_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    
    /*
    public All_Lists_Fragment() {
        // Required empty public constructor
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        all_listCards = new ArrayList<>();
        all_listIDs = new ArrayList<>();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("UserID");
        ArrayList<String> values = new ArrayList<>();
        values.add(String.valueOf(HomeScreenActivity.getActingUserID()));

/*
        DatabaseTask getLists = new DatabaseTask.Retrieve(DatabaseController.getInstance(),
                "LISTS", fields, values, getContext());
        getLists.execute();
*/

        RetrofitService retrofitService = new RetrofitService();
        ListApi listApi = retrofitService.getRetrofit().create(ListApi.class);
                listApi.getListsByUserID(HomeScreenActivity.getActingUserID())
                .enqueue(new Callback<List<ListCard>>() {
                    @Override
                    public void onResponse(Call<List<ListCard>> call, Response<List<ListCard>> response) {
                        all_listCards = response.body();
                    }

                    @Override
                    public void onFailure(Call<List<ListCard>> call, Throwable throwable) {

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all__lists_, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        all_lists_recyclerview = view.findViewById(R.id.all_Lists_recyclerview);
        addNewList = view.findViewById(R.id.newListButton);

        //List of all the lists that will appear on the page

        System.out.println("There are " + all_listCards.size() + " lists .");
        //create adapter and attach recyclerview to it
        all_lists_adapter = new All_Lists_Adapter(all_listCards);
        all_lists_recyclerview.setAdapter(all_lists_adapter);
        all_lists_recyclerview.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        //create a listener to the recyclerview so that it opens the list when clicked
        all_lists_recyclerview.addOnItemTouchListener(new List_Touch_Listener(this.getActivity(), all_lists_recyclerview, new ItemClickListener() {

            // this is the simple click on the list
            // this should take the list title and open a new page with all the tasks in that list
            @Override
            public void onClick(View view, int position) {

                //All_Lists_Adapter.List_View_Holder holder = new All_Lists_Adapter.List_View_Holder(view);

                String listName = all_listCards.get(position).getNewListName();
                int bgColor = all_listCards.get(position).getCardBackground();
                listID = all_listIDs.get(position);
                //Bundle bundle = new Bundle();
                //bundle.putString("title", listName);
                System.out.println("list name: " + listName);
                System.out.println("list ID: " + listID);
                System.out.println("list Color: " + bgColor);

                //getActivity().getSupportFragmentManager().setFragmentResult("title", bundle);
                //getActivity().getSupportFragmentManager().beginTransaction()
                //        .replace(R.id.fragment_container, new TaskListFragment()).commit();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                TaskListFragment.newInstance(listName, getListID(), HomeScreenActivity.getActingUserID(), bgColor));
                transaction.commit();
                //getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }

            /*
            create a menu with two options (edit, delete)
             1- the option edit will edit the list title
             2- the option delete will delete the whole list and all the tasks in it
            */
            @Override
            public void onLongClick(View view, int position) {

                //create a menu of options
                TextView textView = view.findViewById(R.id.list_title);
                String listTitle = textView.getText().toString();
                all_lists_adapter.remove(all_listCards.get(position));
            }
        }));

        //Add new list recyclerview Animation
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        all_lists_recyclerview.setItemAnimator(itemAnimator);

        //Add new List button functionality
        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateNewList.newInstance().show(getActivity().getSupportFragmentManager(), "");
            }
        });
    }

    public static void setListID(int id){
        listID = id;
    }

    public static int getListID(){
        return listID;
    }
/*
    public static boolean loadLists(ResultSet listCard) throws SQLException {
        ListCard list = new ListCard(listCard.getString("ListTitle"), listCard.getInt("NumberOfTasks"),
                listCard.getInt("BackgroundColor"));
        all_listCards.add(list);
        all_listIDs.add(listCard.getInt("ListID"));
        System.out.println("the list has been added and the size now is: " + all_listCards.size());
        return true;
    }
*/
    public static void refreshPage(){
        all_lists_adapter = new All_Lists_Adapter(all_listCards);
        all_lists_recyclerview.setAdapter(all_lists_adapter);
    }
    //reference to the adapter
    public static All_Lists_Adapter getAll_lists_adapter() {
        return all_lists_adapter;
    }
}