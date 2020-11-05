package tda.darkarmy.sivalikorganics.activity.employee;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.Salary;

public class PaySalaryActivity extends AppCompatActivity {

    private TextInputLayout amount;
    private TextInputLayout monthYear;
    private Button paySalary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_salary);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        String id = getIntent().getStringExtra("EMPLOYEE_ID");

        paySalary.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(validation()){
                    pay(accessToken, id);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pay(String accessToken, String id) {
        Date date = new Date();
        Salary salary = new Salary(Integer.parseInt(amount.getEditText().getText().toString()), monthYear.getEditText().getText().toString(), id);
        RetrofitClient.getInstance().getEmployeeService().paySalary("Bearer "+accessToken, salary).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), EmployeeDetailsActivity.class);
                    intent.putExtra("EMPLOYEE_ID", id);
                    startActivity(intent);
                }else {

                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(PaySalaryActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PaySalaryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validation(){
        String vAmount = amount.getEditText().getText().toString();
        String vMonthYear = monthYear.getEditText().getText().toString();
        if(!vAmount.matches("^\\d+$")){
            amount.setError("Enter valid amount.");
            return false;
        }else if(vMonthYear.isEmpty() || vMonthYear.length()<7){
            monthYear.setError("Enter month year in the format mm-yyyy");
            return false;
        }
        else{
            return true;
        }
    }

    private void bind() {
        amount = findViewById(R.id.amount);
        monthYear = findViewById(R.id.month_year);
        paySalary = findViewById(R.id.pay_salary);
    }
}