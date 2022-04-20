package ahmed.haikal.myplanner.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ahmed.haikal.myplanner.Controller.Adapters.ProjectsAdapter;
import ahmed.haikal.myplanner.Controller.Listeners.CustomClickListenerInterface;
import ahmed.haikal.myplanner.Controller.Listeners.CustomItemTouchListener;
import ahmed.haikal.myplanner.Model.ProjectCard;
import ahmed.haikal.myplanner.R;

public class ProjectsActivity extends AppCompatActivity {

    private RecyclerView projectsRecyclerview;
    private static ProjectsAdapter projectsAdapter;
    private static ArrayList<ProjectCard> all_projectCards;
    private FloatingActionButton addNewProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        projectsRecyclerview = findViewById(R.id.all_projects_recyclerview);
        all_projectCards = new ArrayList<>();
        projectsAdapter = new ProjectsAdapter(all_projectCards);

        projectsRecyclerview.setAdapter(projectsAdapter);
        projectsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        projectsRecyclerview.addOnItemTouchListener(new CustomItemTouchListener(
                this, projectsRecyclerview, projectsAdapter, new CustomClickListenerInterface() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(ProjectsActivity.this, "project clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public void onItemMove(int fromPos, int toPos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            }
        }
        ));

        //Add new list recyclerview Animation
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        projectsRecyclerview.setItemAnimator(itemAnimator);

        addNewProject = findViewById(R.id.newProjectButton);

        //Add new Project Button functionality
        addNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectCard projectCard = new ProjectCard("project 1", "nice project", 0, Color.BLUE);
                projectsAdapter.insert(projectCard);
                System.out.println("hell yeah");
            }
        });
    }
}