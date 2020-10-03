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
import tda.darkarmy.sivalikorganics.model.ExportDetail;

public interface ExportDetailsService {
    @GET("/export-details/detail")
    Call<ResponseBody> getAllExports(@Header("Authorization") String accessToken);

    @GET("/export-details/byId/{id}")
    Call<ResponseBody> getExportsById(@Header("Authorization") String accessToken, @Path("id") String id);

    @GET("/export-details/byConsumerAndMonth/detail/{id}/{my}")
    Call<ResponseBody> getExportsByConsumerAndMonth(@Header("Authorization") String accessToken, @Path("id") String id, @Path("my") String my);

    @GET("/export-details/byConsumer/detail/{id}")
    Call<ResponseBody> getExportsByConsumer(@Header("Authorization") String accessToken, @Path("id") String id);

    @GET("/export-details/byMonthYear/detail/{my}")
    Call<ResponseBody> getExportsByMonthYear(@Header("Authorization") String accessToken, @Path("my") String my);

    @POST("/export-details/add")
    Call<ResponseBody> addExportDetails(@Header("Authorization") String accessToken, @Body ExportDetail exportDetail);

    @PUT("/export-details/{id}")
    Call<ResponseBody> updateExportDetails(@Header("Authorization") String accessToken,@Path("id") String id,  @Body ExportDetail exportDetail);

    @DELETE("/export-details/{id}")
    Call<ResponseBody> deleteExportDetails(@Header("Authorization") String accessToken,@Path("id") String id);

}
