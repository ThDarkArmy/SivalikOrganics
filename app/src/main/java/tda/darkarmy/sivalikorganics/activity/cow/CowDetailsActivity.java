package tda.darkarmy.sivalikorganics.activity.cow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.calf.AddCalfActivity;
import tda.darkarmy.sivalikorganics.activity.calf.CalfDetailsActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Cow;
import tda.darkarmy.sivalikorganics.model.ErrorObject;

public class CowDetailsActivity extends AppCompatActivity {

    private TextView name;
    private TextView isPregnant;
    private TextView age;
    private TextView pregnantFrom;
    private TextView amountOfMilk;
    private TextView isHealthy;
    private TextView productive;
    private TextView profileName;
    private ImageView editProfile;
    private ImageView deleteProfile;
    private ImageView addCalf;
    private Cow cow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow_details);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        String cowId = getIntent().getStringExtra("COW_ID");
        getCow(accessToken, cowId);
        Log.i("COW_ID", cowId);


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditCowActivity.class);
                intent.putExtra("COW_OBJECT", (Serializable) cow);
                startActivity(intent);
            }
        });

        deleteProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RetrofitClient.getInstance().getCowService().deleteCow("Bearer "+accessToken, cow.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(CowDetailsActivity.this, "Cow deleted successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CowActivity.class));
                        }else {
                            try {
                                ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                                Toast.makeText(CowDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                            }catch (Exception ex){
                                Toast.makeText(CowDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(CowDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        addCalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCalfActivity.class);
                intent.putExtra("COW_OBJECT", (Serializable) cow);
                startActivity(intent);
            }
        });
    }

    private void getCow(String accessToken, String cowId) {
        RetrofitClient.getInstance().getCowService().getCowById("Bearer "+accessToken, cowId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        cow = new GsonBuilder().create().fromJson(response.body().string(), Cow.class);
                        name.setText(cow.getName());
                        isPregnant.setText(cow.getIsPregnant() ? "Yes" : "No");
                        age.setText(cow.getAge().toString()+" Years");

                        pregnantFrom.setText(cow.getIsPregnant()? cow.getPregnantFrom(): "Not Pregnant");
                        amountOfMilk.setText(cow.getAmountOfMilk().toString()+ " Litres");
                        isHealthy.setText(cow.getIsHealthy()? "Healthy": " Not Healthy");
                        productive.setText(cow.getIsProductive() ? "Yes" : "No");
                        profileName.setText(cow.getName());
                    }catch (Exception ex){
                        Toast.makeText(CowDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(CowDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(CowDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CowDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(){
        name = findViewById(R.id.name);
        isPregnant = findViewById(R.id.is_pregnant);
        age = findViewById(R.id.age);
        pregnantFrom = findViewById(R.id.pregnant_from);
        amountOfMilk = findViewById(R.id.milk_amount);
        isHealthy = findViewById(R.id.health);
        productive = findViewById(R.id.is_productive);
        profileName = findViewById(R.id.profile_name);
        editProfile = findViewById(R.id.edit_profile);
        deleteProfile = findViewById(R.id.delete_profile);
        addCalf = findViewById(R.id.add_calf);
    }
}