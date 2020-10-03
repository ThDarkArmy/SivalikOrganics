package tda.darkarmy.sivalikorganics.activity.cow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Cow;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.ResponseBodyFromServer;

public class AddCowActivity extends AppCompatActivity {
    private TextInputLayout name;
    private TextInputLayout age;
    private TextInputLayout amountOfMilk;
    private TextInputLayout pregnantFrom;
    private TextInputLayout isHealthy;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cow);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isProductive = false;
                boolean isPregnant = false;
                boolean health = false;
                String date = null;
                if(Integer.parseInt(amountOfMilk.getEditText().getText().toString())>0){
                    isProductive = true;
                }
                if(pregnantFrom.getEditText().getText()!=null){
                    isPregnant = true;
                    date = pregnantFrom.getEditText().getText().toString();
                }
                if(isHealthy.getEditText().getText().toString().equalsIgnoreCase("yes")){
                    health = true;
                }

                Cow cow = new Cow(isProductive, isPregnant, date, health, name.getEditText().getText().toString(), Integer.parseInt(age.getEditText().getText().toString()), Integer.parseInt(amountOfMilk.getEditText().getText().toString()));
                if(validation()){
                    addCow(cow, accessToken);
                }
            }
        });


    }

    public void addCow(Cow cow, String accessToken){
        RetrofitClient.getInstance().getCowService().addCow("Bearer "+accessToken, cow).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        ResponseBodyFromServer res = new GsonBuilder().create().fromJson(response.body().string(), ResponseBodyFromServer.class);
                        Toast.makeText(AddCowActivity.this, res.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), CowActivity.class));
                    }catch (Exception ex){
                        Toast.makeText(AddCowActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(AddCowActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(AddCowActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddCowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // form validation
    public boolean validation(){
        String vName = name.getEditText().getText().toString();
        String vAge = age.getEditText().getText().toString();
        String vAmountOfMilk = amountOfMilk.getEditText().getText().toString();
        String vIsHealthy = isHealthy.getEditText().getText().toString();

        if(vName.length()<3){
            name.setError("Enter a valid name.");
            return false;
        }else if(vAge.isEmpty()){
            age.setError("Enter a valid age.");
            return false;
        }else if(!vAmountOfMilk.matches("^\\d+$")){
            amountOfMilk.setError("Enter valid milk amount.");
            return false;
        }else if(!vIsHealthy.equalsIgnoreCase("yes") && !vIsHealthy.equalsIgnoreCase("no")){
            isHealthy.setError("Enter only yes or no. ");
            return false;
        }else{
            return true;
        }

    }

    public void bind(){
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        amountOfMilk = findViewById(R.id.milk_amount);
        pregnantFrom = findViewById(R.id.pregnant_from);
        isHealthy = findViewById(R.id.health);
        add = findViewById(R.id.add_cow);
    }
}