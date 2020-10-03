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
import tda.darkarmy.sivalikorganics.model.Cow;

public interface CowService {
    @GET("/cows/all")
    Call<ResponseBody> getAllCows(@Header("Authorization") String accessToken);

    @GET("/cows/byId/{id}")
    Call<ResponseBody> getCowById(@Header("Authorization") String accessToken, @Path("id") String id);

    @POST("/cows/add")
    Call<ResponseBody> addCow(@Header("Authorization") String accessToken, @Body Cow cow);

    @PUT("/cows/{id}")
    Call<ResponseBody> updateCow(@Header("Authorization") String accessToken, @Path("id") String id, @Body Cow cow);

    @DELETE("/cows/{id}")
    Call<ResponseBody> deleteCow(@Header("Authorization") String accessToken, @Path("id") String id);

}
