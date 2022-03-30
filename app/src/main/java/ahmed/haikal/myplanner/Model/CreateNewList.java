package ahmed.haikal.myplanner.Model;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ahmed.haikal.myplanner.Controller.Adapters.All_Lists_Adapter;
import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Main_Screen.All_Lists_Fragment;

public class CreateNewList extends DialogFragment {

    EditText listTitleInputText;
    Button create, cancel;
    All_Lists_Adapter all_lists_adapter;
    private CreateNewList() {
        //empty constructor
    }

    public static CreateNewList newInstance() {
        return new CreateNewList();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstance){

        this.getDialog().setTitle("dialog title");
        this.getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.create_new_list, group, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listTitleInputText = view.findViewById(R.id.listTitleInputText);
        //submitColor
        create = view.findViewById(R.id.create);
        cancel = view.findViewById(R.id.back);

        //reference the adapter
        all_lists_adapter = All_Lists_Fragment.getAll_lists_adapter();

        //check if text is inserted and change the color of "save" button to look active
        listTitleInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //not needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    create.setEnabled(false);
                    create.setTextColor(Color.GRAY);
                } else {
                    create.setEnabled(true);
                    create.setTextColor(Color.BLUE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //not needed
            }
        });


        //add button functionality
        create.setOnClickListener(view1 -> {
            //all lists will have a grey background until the choose color functionality is created
            ListCard newList = new ListCard(listTitleInputText.getText().toString(), 0, 0, Color.GRAY);
            all_lists_adapter.insert(newList);
            System.out.println("I'm adding a list");
            dismiss();
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

    }


    // this method expands the size of the dialog fragment to show all the content of the
    // add new task box
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
