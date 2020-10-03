package tda.darkarmy.sivalikorganics.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import tda.darkarmy.sivalikorganics.model.User;

public interface UserService {
    @GET("/users/id")
    Call<ResponseBody> getUser(@Header("Authorization") String accessToken);

    @GET("/users/id/{id}")
    Call<ResponseBody> getUserById(@Path ("id") String id, @Header("Authorization") String accessToken);

    @GET("/users/role/{role}")
    Call<ResponseBody> getUserByRole(@Path("role") String role, @Header("Authorization") String accessToken);

    @Multipart
    @POST("/users/register")
    Call<ResponseBody> registerUser(@Part MultipartBody.Part profilePic,
                                    @Part("role") RequestBody role,
                                    @Part("name") RequestBody name,
                                    @Part("email") RequestBody email,
                                    @Part("mobile") RequestBody mobile,
                                    @Part("password") RequestBody password);

    @POST("/users/login")
    Call<ResponseBody> loginUser(@Body User user);

    @PUT("/users")
    Call<ResponseBody> updateUser(@Body User user, @Header("Authorization") String accessToken);

    @PUT("/users/resetPassword")
    Call<ResponseBody> resetPassword(@Body User user, @Header("Authorization") String accessToken);

    @DELETE("/users")
    Call<ResponseBody> deleteUser(@Header("Authorization") String accessToken);

}
