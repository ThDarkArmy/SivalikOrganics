package tda.darkarmy.sivalikorganics.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tda.darkarmy.sivalikorganics.model.Calf;

public interface CalfService {
    @GET("/calves/all")
    Call<ResponseBody> getAllCalves(@Header("Authorization") String accessToken);

    @GET("/calves/byId/{id}")
    Call<ResponseBody> getCalfById(@Header("Authorization") String accessToken, @Path("id") String id);

    @POST("/calves/add")
    Call<ResponseBody> addCalf(@Header("Authorization") String accessToken, @Body Calf calf);

    @PUT("/calves/{id}")
    Call<ResponseBody> updateCalf(@Header("Authorization") String accessToken,@Path("id") String id, @Body Calf calf);

    @DELETE("/calves/{id}")
    Call<ResponseBody> deleteCalf(@Header("Authorization") String accessToken, @Path("id") String id);

}
