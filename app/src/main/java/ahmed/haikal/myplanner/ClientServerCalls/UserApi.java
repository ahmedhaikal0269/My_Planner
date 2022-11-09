package ahmed.haikal.myplanner.ClientServerCalls;

import java.util.List;

import ahmed.haikal.myplanner.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @GET("/users/get-all")
    Call<List<User>> getAllUsers();

    @GET("/users/get-user-profile")
    Call<User> getUser(@Body int id);

    @GET("/users/{username}")
    Call<User> checkUserExists(@Path("username") String username);

    @POST("users/new-user")
    Call<User> addNewUser(@Body User user);
}
