package ahmed.haikal.myplanner.View.Sign_In_Or_Up;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import ahmed.haikal.myplanner.Controller.Database.DatabaseController;
import ahmed.haikal.myplanner.Controller.Database.DatabaseTask;
import ahmed.haikal.myplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private EditText firstName, lastName, email, username, password, retypePassword;
    private Button signup;
    TextView inputErrorMsg;
    private DatabaseController databaseController;

    private static boolean usernameExists = false;

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

        databaseController = DatabaseController.getInstance();

        DatabaseTask databaseConnection = new DatabaseTask.Connect(databaseController);
        databaseConnection.execute();

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
        inputErrorMsg = signupView.findViewById(R.id.inputErrorMsg);
        return signupView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if username exists in database
                String f_name = "'" + firstName.getText().toString() + "'";
                String l_name = "'" + lastName.getText().toString() + "'";
                String e_mail = "'" + email.getText().toString() + "'";
                String u_name = "'" + username.getText().toString() + "'";
                String p_word = "'" + password.getText().toString() + "'";
                String re_password = "'" + retypePassword.getText().toString() + "'";
                //password and retype password fields should be equal
                if(checkPasswordRetypeMatch(p_word, re_password)){
                    ArrayList<String> userFields = new ArrayList<>();
                    //userFields.add("UserID");
                    userFields.add("FirstName");
                    userFields.add("LastName");
                    userFields.add("Email");
                    userFields.add("UserName");
                    userFields.add("Password");

                    ArrayList<String> userValues = new ArrayList<>();
                    //userValues.add("1");
                    userValues.add(f_name);
                    userValues.add(l_name);
                    userValues.add(e_mail);
                    userValues.add(u_name);
                    userValues.add(p_word);


                    //check if user already exists
                    DatabaseTask checkIfUnameExists = new DatabaseTask.Retrieve(databaseController, "USERS",
                            "Username", u_name, getActivity());
                    checkIfUnameExists.execute();
                    //create a record in the USER table
                    if(!isUsernameExists()) {
                        DatabaseTask createUserAccount = new DatabaseTask.Insert(databaseController,
                                "USERS", userFields, userValues, getActivity());
                        createUserAccount.execute();
                        System.out.println("username doesn't exists");
                    }
                    else
                        System.out.println("username already exists");

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                else {

                    ArrayList<EditText> errorFields = new ArrayList<>();
                    errorFields.add(password);
                    errorFields.add(retypePassword);
                    showErrorMessage(errorFields, "");
                }

            }
        });
    }

    //This boolean returns true if the user enter the same input in password and retype password fields
    public boolean checkPasswordRetypeMatch(String pword1, String pword2){
        return (pword1.equals(pword2));
    }

    //this boolean is to change the border an input field to red and show an error message above sign up button
    public void showErrorMessage(ArrayList<EditText> errorFields, String errorMessage){
        for(int i = 0; i < errorFields.size(); i++)
            errorFields.get(i).setBackground(getActivity().getDrawable(R.drawable.error_input_field_design));

        inputErrorMsg.setText(errorMessage);
    }

    public static boolean isUsernameExists(){
        return usernameExists;
    }

    public static void setUsernameExists(boolean exists){
        usernameExists = exists;
    }
}