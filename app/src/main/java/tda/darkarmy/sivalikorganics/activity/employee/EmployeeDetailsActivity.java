package tda.darkarmy.sivalikorganics.activity.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.consumer.ConsumerDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.exportdetails.AddExportDetailsActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ConsumerDetails;
import tda.darkarmy.sivalikorganics.model.EmployeeDetails;
import tda.darkarmy.sivalikorganics.model.ErrorObject;

public class EmployeeDetailsActivity extends AppCompatActivity {
    private CircleImageView imageView;
    private TextView profileName;
    private TextView profileNumber;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView dateJoined;
    private TextView salaryStatus;
    private ImageView paySalary;
    private LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        String id = getIntent().getStringExtra("EMPLOYEE_ID");
        RetrofitClient.getInstance().getEmployeeService().getEmployeeDetails("Bearer "+accessToken, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        EmployeeDetails employeeDetails = new GsonBuilder().create().fromJson(response.body().string(), EmployeeDetails.class);
                        Log.i("Employee_DE", employeeDetails.toString());
                        Picasso.get().load(Uri.parse(RetrofitClient.BASE_URL+"/"+employeeDetails.getProfilePic())).into(imageView);
                        profileName.setText(employeeDetails.getName());
                        profileNumber.setText(employeeDetails.getMobile());
                        name.setText(employeeDetails.getName());
                        phone.setText(employeeDetails.getMobile());
                        email.setText(employeeDetails.getEmail());
                        dateJoined.setText(employeeDetails.getDateJoined().substring(0,10));
                        salaryStatus.setText(employeeDetails.getSalaryStatus());


                    }catch (Exception ex){
                        mainLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(EmployeeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        mainLayout.setVisibility(View.INVISIBLE);
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(EmployeeDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        mainLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(EmployeeDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mainLayout.setVisibility(View.INVISIBLE);
                Toast.makeText(EmployeeDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        paySalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PaySalaryActivity.class);
                intent.putExtra("EMPLOYEE_ID", id);
                startActivity(intent);
            }
        });
    }

    public void bind(){
        imageView = findViewById(R.id.profile_img);
        profileName = findViewById(R.id.profile_name);
        profileNumber = findViewById(R.id.profile_number);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        dateJoined = findViewById(R.id.date_joined);
        phone = findViewById(R.id.phone);
        salaryStatus = findViewById(R.id.salary_status);
        paySalary = findViewById(R.id.pay_salary);

        mainLayout = findViewById(R.id.main_layout);
    }
}