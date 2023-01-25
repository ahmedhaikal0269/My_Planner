package ahmed.haikal.myplanner.ClientServerCalls;

import java.util.List;

import ahmed.haikal.myplanner.Model.ListCard;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ListApi {

    @GET("/lists/get-all")
    Call<List<ListCard>> getAllLists();

    @GET("/lists/get-lists/{userid}")
    Call<List<ListCard>> getListsByUserID(@Body int userid);

    @POST("/lists/new-list")
    Call<ListCard> createNewList(@Body ListCard listCard);
}
