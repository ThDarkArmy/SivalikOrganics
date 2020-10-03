package tda.darkarmy.sivalikorganics.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tda.darkarmy.sivalikorganics.model.Salary;

public interface EmployeeService {
    @GET("/salaries/employee-details/{id}")
    Call<ResponseBody> getEmployeeDetails(@Header("Authorization") String accessToken, @Path("id") String id);

    @GET("/salaries/employees")
    Call<ResponseBody> getAllEmployees(@Header("Authorization") String accessToken);

    @POST("/salaries/add")
    Call<ResponseBody> paySalary(@Header("Authorization") String accessToken, @Body Salary salary);

    @PUT("/salaries/{id}")
    Call<ResponseBody> updateSalary(@Header("Authorization") String accessToken, @Path("id") String id, @Body Salary salary);


}
