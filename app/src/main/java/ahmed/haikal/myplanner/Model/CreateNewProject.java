package ahmed.haikal.myplanner.Model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ahmed.haikal.myplanner.Controller.Adapters.ProjectsAdapter;
import ahmed.haikal.myplanner.R;

public class CreateNewProject extends DialogFragment {

    private EditText projectTitleInputText;
    private Button create, cancel;

    private ProjectsAdapter projectsAdapter;

    public CreateNewProject(){
        //empty constructor
    }

    public static CreateNewProject newInstance(){
        return new CreateNewProject();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.create_new_project, container, false);
        projectTitleInputText = view.findViewById(R.id.projectTitleInputText);
        create = view.findViewById(R.id.create_project_button);
        cancel = view.findViewById(R.id.back);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
