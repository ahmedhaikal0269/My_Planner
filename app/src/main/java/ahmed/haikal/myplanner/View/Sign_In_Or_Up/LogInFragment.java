package ahmed.haikal.myplanner.View.Sign_In_Or_Up;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ahmed.haikal.myplanner.Controller.Database.DatabaseController;
import ahmed.haikal.myplanner.Controller.Database.DatabaseTask;
import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Activities.HomeScreenActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment implements View.OnClickListener{

    private EditText usernameInput, passwordInput;
    private Button login;
    private static TextView wrongCredentials;

    private DatabaseController databaseController;

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

        //create connection with the database to validate login
        databaseController = DatabaseController.getInstance();

        DatabaseTask databaseConnection = new DatabaseTask.Connect(databaseController);
        databaseConnection.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View loginView = inflater.inflate(R.layout.fragment_log_in, container, false);
        usernameInput = loginView.findViewById(R.id.userName_login);
        passwordInput = loginView.findViewById(R.id.password_login);
        wrongCredentials = loginView.findViewById(R.id.wrongCredentials);
        login = loginView.findViewById(R.id.login_button);
        login.setOnClickListener(this);
        return loginView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this will make the "wrong username or password warning disappear
        usernameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                wrongCredentials.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                wrongCredentials.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        //get given username and password to validate the login
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        //check database for credentials
        DatabaseTask loginValidation = new DatabaseTask.Login(databaseController, username, password, getContext());
        loginValidation.execute();

        //minimize keyboard
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public static void loginSuccess(Context context, String username, int userID, String activeUserName) {
        Intent intent = new Intent(context, HomeScreenActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("username", username);
        intent.putExtra("activeUserName", activeUserName);
        context.startActivity(intent);
        System.out.println("user id: " + userID);
        System.out.println("username: " + username);
        System.out.println("user first name: " + activeUserName);
    }
    public static void loginFailed(){
        wrongCredentials.setText("wrong username or password");
    }
}