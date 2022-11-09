package ahmed.haikal.myplanner.ClientServerCalls;

import java.util.List;

import ahmed.haikal.myplanner.Model.ProjectCard;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProjectApi {

    @GET("projects/get-all-projects")
    Call<List<ProjectCard>> getAllProjects();

    @GET("projects/get-project-by-user")
    Call<List<ProjectCard>> getProjectsByUserID(@Body int id);

    @POST("projects/new-project")
    Call<ProjectCard> createNewProject(@Body ProjectCard projectCard);
}
