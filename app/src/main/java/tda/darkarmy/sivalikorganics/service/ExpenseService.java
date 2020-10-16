package tda.darkarmy.sivalikorganics.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tda.darkarmy.sivalikorganics.model.Expense;

public interface ExpenseService {
    @GET("/expenses/all")
    Call<ResponseBody> getAllExpense(@Header("Authorization") String accessToken);

    @GET("/expenses/byMonthYear/{my}")
    Call<ResponseBody> getAllExpenseByMonth(@Header("Authorization") String accessToken, @Path("my") String monthYear);

    @POST("/expenses/add")
    Call<ResponseBody> addExpense(@Header("Authorization") String accessToken, @Body Expense expense);

    @PUT("/expenses/{id}")
    Call<ResponseBody> updateExpense(@Header("Authorization") String accessToken, @Path("id") String id, @Body Expense expense);

    @DELETE("/expenses/{id}")
    Call<ResponseBody> deleteExpense(@Header("Authorization") String accessToken, @Path("id") String id);
}
