package ahmed.haikal.myplanner.View.Sign_In_Or_Up;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Main_Screen.MainScreenActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment implements View.OnClickListener{

    private EditText usernameInput, passwordInput;
    private Button login;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
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

    }

    /*
    public void login(){
        startActivity(new Intent(getActivity(), MainScreenActivity.class));
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View loginView = inflater.inflate(R.layout.fragment_log_in, container, false);
        usernameInput = loginView.findViewById(R.id.userName_login);
        passwordInput = loginView.findViewById(R.id.password_login);
        login = loginView.findViewById(R.id.login_button);
        login.setOnClickListener(this);
        return loginView;
    }


    @Override
    public void onClick(View view) {

        //retrieve user's lists from database and display it in the next page
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        //check database for credentials

        //if success, go to the next page
        startActivity(new Intent(getActivity().getApplication(), MainScreenActivity.class));
    }
}