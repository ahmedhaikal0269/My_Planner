package ahmed.haikal.myplanner.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ahmed.haikal.myplanner.Controller.All_Lists_Adapter;
import ahmed.haikal.myplanner.Controller.MainList_ItemClickListener;
import ahmed.haikal.myplanner.Model.CreateNewList;
import ahmed.haikal.myplanner.Model.ListCard;
import ahmed.haikal.myplanner.Model.List_Touch_Listener;
import ahmed.haikal.myplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link All_Lists_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class All_Lists_Fragment extends Fragment {

    RecyclerView all_lists_recyclerview;
    FloatingActionButton addNewList;
    static All_Lists_Adapter all_lists_adapter;
    static List<ListCard> all_listCards;

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

    public All_Lists_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        //List of all the lists that will be appear on the page
        all_listCards = new ArrayList<>();

        //create adapter and attach recyclerview to it
        all_lists_adapter = new All_Lists_Adapter(all_listCards, this.getContext());
        all_lists_recyclerview.setAdapter(all_lists_adapter);
        all_lists_recyclerview.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        //create a listener to the recyclerview so that it opens the list when clicked
        all_lists_recyclerview.addOnItemTouchListener(new List_Touch_Listener(this.getActivity(), all_lists_recyclerview, new MainList_ItemClickListener() {

            // this is the simple click on the list
            // this should take the list title and open a new page with all the tasks in that list
            @Override
            public void onClick(View view, int position) {

                All_Lists_Adapter.List_View_Holder holder = new All_Lists_Adapter.List_View_Holder(view);

                //open the list
                Intent intent = new Intent(getActivity(), TaskListActivity.class);
                String listName = all_listCards.get(position).getNewListName();
                intent.putExtra("ListTitle", listName);
                startActivity(intent);
                //overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

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

        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ListCard list = new ListCard("First List", 0, 0, R.color.black);
                //all_lists_adapter.insert(list);

                CreateNewList.newInstance().show(getActivity().getSupportFragmentManager(), "");
            }
        });
    }

    //reference to the adapter
    public static All_Lists_Adapter getAll_lists_adapter() {
        return all_lists_adapter;
    }
}