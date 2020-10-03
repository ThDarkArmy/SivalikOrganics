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
import tda.darkarmy.sivalikorganics.model.ImportDetail;

public interface ImportDetailsService {
    @GET("/import-details/detail")
    Call<ResponseBody> getAllImports(@Header("Authorization") String accessToken);

    @GET("/import-details/byId/{id}")
    Call<ResponseBody> getImportsById(@Header("Authorization") String accessToken, @Path("id") String id);

    @GET("/import-details/bySellerAndMonth/detail/{id}/{my}")
    Call<ResponseBody> getImportsBySellerAndMonth(@Header("Authorization") String accessToken, @Path("id") String id, @Path("my") String my);

    @GET("/import-details/bySeller/detail/{id}")
    Call<ResponseBody> getImportsBySeller(@Header("Authorization") String accessToken, @Path("id") String id);

    @GET("/import-details/byMonthYear/detail/{my}")
    Call<ResponseBody> getImportsByMonthYear(@Header("Authorization") String accessToken, @Path("my") String my);

    @POST("/import-details/add")
    Call<ResponseBody> addImportDetails(@Header("Authorization") String accessToken, @Body ImportDetail importDetail);

    @PUT("/import-details/{id}")
    Call<ResponseBody> updateImportDetails(@Header("Authorization") String accessToken,@Path("id") String id,  @Body ImportDetail importDetail);

    @DELETE("/import-details/{id}")
    Call<ResponseBody> deleteImportDetails(@Header("Authorization") String accessToken,@Path("id") String id);


}
