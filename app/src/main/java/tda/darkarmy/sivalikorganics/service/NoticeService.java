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
import tda.darkarmy.sivalikorganics.model.Notice;

public interface NoticeService {
    @GET("/notices/all")
    Call<ResponseBody> getAllNotices(@Header("Authorization") String accessToken);

    @GET("/notices/byId/{id}")
    Call<ResponseBody> getNoticeById(@Header("Authorization") String accessToken, @Path("id") String id);

    @POST("/notices/add")
    Call<ResponseBody> addNotice(@Header("Authorization") String accessToken, @Body Notice notice);

    @PUT("/notices/{id}")
    Call<ResponseBody> updateNotice(@Header("Authorization") String accessToken, @Path("id") String id, @Body Notice notice);

    @DELETE("/notices/{id}")
    Call<ResponseBody> deleteNotice(@Header("Authorization") String accessToken, @Path("id") String id);
}
