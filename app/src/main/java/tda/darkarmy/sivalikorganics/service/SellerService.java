package tda.darkarmy.sivalikorganics.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SellerService {
    @GET("/sellers/all")
    Call<ResponseBody> getAll(@Header("Authorization") String accessToken);

    @GET("/sellers/byId/{id}")
    Call<ResponseBody> getById(@Header("Authorization") String accessToken, @Path("id") String id);
}
