package ahmed.haikal.myplanner.View;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ahmed.haikal.myplanner.Controller.All_Lists_Adapter;
import ahmed.haikal.myplanner.Model.CreateNewTask;
import ahmed.haikal.myplanner.Model.ListCard;
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
    All_Lists_Adapter all_lists_adapter;


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

        List<ListCard> list = new ArrayList<>();
        all_lists_adapter = new All_Lists_Adapter(list, this.getContext());
        all_lists_recyclerview.setAdapter(all_lists_adapter);
        all_lists_recyclerview.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ListCard list = new ListCard("First List", 0, 0, R.color.black);
                //all_lists_adapter.insert(list);

                CreateNewTask.newInstance().show(getFragmentManager(), "Title");

            }
        });
    }
}