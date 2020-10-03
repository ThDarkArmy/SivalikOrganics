package tda.darkarmy.sivalikorganics.activity.cow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Cow;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.ResponseBodyFromServer;

public class EditCowActivity extends AppCompatActivity {

    private TextInputLayout name;
    private TextInputLayout age;
    private TextInputLayout amountOfMilk;
    private TextInputLayout pregnantFrom;
    private TextInputLayout isHealthy;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cow);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        //Log.i("Access_token", accessToken);
        bind();
        Cow cow = (Cow)getIntent().getSerializableExtra("COW_OBJECT");
        Log.i("COW_OBJECT", cow.toString());
        name.getEditText().setText(cow.getName());

        age.getEditText().setText(cow.getAge().toString());

        pregnantFrom.getEditText().setText(cow.getIsPregnant()? cow.getPregnantFrom(): null);
        amountOfMilk.getEditText().setText(cow.getAmountOfMilk().toString());
        isHealthy.getEditText().setText(cow.getIsHealthy()? "Yes": " No");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isProductive = false;
                boolean health = false;
                String date = null;
                if(Integer.parseInt(amountOfMilk.getEditText().getText().toString())>0){
                    isProductive = true;
                }

                if(isHealthy.getEditText().getText().toString().equalsIgnoreCase("yes")){
                    health = true;
                }

                Cow updatedCow;

                if(pregnantFrom.getEditText().getText().toString().isEmpty()){

                    updatedCow = new Cow(isProductive, false, null, health,  name.getEditText().getText().toString(), Integer.parseInt(age.getEditText().getText().toString()), Integer.parseInt(amountOfMilk.getEditText().getText().toString()));
                }else{
                    date = pregnantFrom.getEditText().getText().toString();
                    updatedCow = new Cow(isProductive, true, date, health,  name.getEditText().getText().toString(), Integer.parseInt(age.getEditText().getText().toString()), Integer.parseInt(amountOfMilk.getEditText().getText().toString()));
                }
                if(validation()){
                    updateCow(accessToken, updatedCow, cow.getId());
                }
            }
        });


    }

    public void updateCow(String accessToken , Cow cow, String cowId){
        RetrofitClient.getInstance().getCowService().updateCow("Bearer "+accessToken, cowId, cow).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        ResponseBodyFromServer res = new GsonBuilder().create().fromJson(response.body().string(), ResponseBodyFromServer.class);

                        Toast.makeText(EditCowActivity.this, res.getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CowDetailsActivity.class);
                        intent.putExtra("COW_ID", cowId);
                        startActivity(intent);
                    }catch (Exception ex){
                        Toast.makeText(EditCowActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Log.i("msgh",errorObject.toString());
                        Toast.makeText(EditCowActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(EditCowActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditCowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // form validation
    public boolean validation(){
        String vName = name.getEditText().getText().toString();
        String vAge = age.getEditText().getText().toString();
        String vAmountOfMilk = amountOfMilk.getEditText().getText().toString();
        String vIsHealthy = isHealthy.getEditText().getText().toString();

        if(vName.isEmpty() || vName.length()<3){
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
        update = findViewById(R.id.edit_cow);
    }
}