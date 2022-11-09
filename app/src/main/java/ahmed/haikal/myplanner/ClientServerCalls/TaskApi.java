package ahmed.haikal.myplanner.ClientServerCalls;

import java.util.List;

import ahmed.haikal.myplanner.Model.TaskCard;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TaskApi {

    @GET("/tasks/get-all")
    Call<List<TaskCard>> getAllTasks();

    @GET("/tasks/get-task-by-listid/{listid}")
    Call<List<TaskCard>> getTasksByListID(@Path("listid") int listid);

    @GET("/tasks/get-tasks-by-date/{taskDate}")
    Call<List<TaskCard>> getTasksByDate(@Body String taskDate);

    @POST("/tasks/add-new-task")
    Call<TaskCard> addNewTask(@Body TaskCard taskCard);

    @DELETE("/tasks/delete/{taskid}")
    Call<TaskCard> deleteTask(@Body int taskid);
}
