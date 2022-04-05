package ahmed.haikal.myplanner.View.Sign_In_Or_Up;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ahmed.haikal.myplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private EditText firstName, lastName, email, username, password, retypePassword;
    private Button signup;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View signupView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        firstName = signupView.findViewById(R.id.input_firstName);
        lastName = signupView.findViewById(R.id.input_lastName);
        email = signupView.findViewById(R.id.input_email);
        username = signupView.findViewById(R.id.username_signup);
        password = signupView.findViewById(R.id.password_signup);
        retypePassword = signupView.findViewById(R.id.retype_password_signup);
        signup = signupView.findViewById(R.id.sign_up_button);
        return signupView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if username exists in database


            }
        });
    }

    //This boolean returns true if the user enter the same input in password and retype password fields
    public boolean checkPasswordRetypeMatch(String pword1, String pword2){
        return (pword1.equals(pword2));
    }
}