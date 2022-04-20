package ahmed.haikal.myplanner.Controller.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ahmed.haikal.myplanner.Model.ProjectCard;
import ahmed.haikal.myplanner.R;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.Project_View_Holder> {
    private ArrayList<ProjectCard> all_projects_list;

    public ProjectsAdapter(ArrayList<ProjectCard> all_projects_list) {
        this.all_projects_list = all_projects_list;
    }

    @NonNull
    @Override
    public Project_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_cardview, parent, false);
        Project_View_Holder projectViewHolder = new Project_View_Holder(view);
        return projectViewHolder;
    }

    @Override
    public void onBindViewHolder(Project_View_Holder holder, int position) {
        ProjectCard projectCard = all_projects_list.get(position);
        holder.project_title.setText(projectCard.getProjectName());
        holder.project_description.setText(projectCard.getProjectDescription());
        holder.numMembers.setText(String.valueOf(projectCard.getNumOfTeamMembers()));
    }

    @Override
    public int getItemCount() {
        return all_projects_list.size();
    }


    public void insert(ProjectCard projectCard){
        all_projects_list.add(projectCard);
        notifyItemInserted(all_projects_list.size() - 1);
    }

    public void remove(ProjectCard projectCard){
        int position = all_projects_list.indexOf(projectCard);
        all_projects_list.remove(position);
        notifyItemRemoved(position);
    }

    // ----------------- View Holder Class ------------------//
    class Project_View_Holder extends RecyclerView.ViewHolder {

        private CardView project_cardview;
        private TextView numMembers, project_title, project_description;

        public Project_View_Holder(@NonNull View itemView) {
            super(itemView);
            project_cardview = itemView.findViewById(R.id.project_cardview);
            numMembers = itemView.findViewById(R.id.num_of_members);
            project_title = itemView.findViewById(R.id.project_title);
            project_description = itemView.findViewById(R.id.project_description);
        }
    }
}
