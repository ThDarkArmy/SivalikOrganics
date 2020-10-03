package tda.darkarmy.sivalikorganics.activity.calf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Calf;
import tda.darkarmy.sivalikorganics.model.Cow;
import tda.darkarmy.sivalikorganics.model.ErrorObject;

public class CalfDetailsActivity extends AppCompatActivity {
    private TextView name;
    private TextView isHealthy;
    private TextView dob;
    private TextView mother;
    private TextView gender;
    private TextView profileName;
    private ImageView editProfile;
    private ImageView deleteProfile;
    private Calf calf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf_details);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();

        String calfId = getIntent().getStringExtra("CALF_ID");
        getCalf(accessToken, calfId);

        editProfile.setOnClickListener((View.OnClickListener) view -> {
            Intent intent = new Intent(getApplicationContext(), EditCalfActivity.class);
            intent.putExtra("CALF_OBJECT", (Serializable) calf);
            startActivity(intent);
        });

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getInstance().getCalfService().deleteCalf("Bearer "+accessToken, calf.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(CalfDetailsActivity.this, "Calf deleted successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CalfActivity.class));
                        }else {
                            Toast.makeText(CalfDetailsActivity.this, "Failed to delete calf.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(CalfDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getCalf(String accessToken, String id){

        RetrofitClient.getInstance().getCalfService().getCalfById("Bearer "+accessToken, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        calf = new GsonBuilder().create().fromJson(response.body().string(), Calf.class);
                        Log.i("Calf__", calf.toString());
                        name.setText(calf.getName());
                        gender.setText(calf.getGender());
                        isHealthy.setText(calf.getIsHealthy()? "Healthy": "Not Healthy");
                        dob.setText(calf.getDob());
                        profileName.setText(calf.getName());
                        mother.setText(calf.getCow().getName());

                    }catch (Exception ex){
                        Toast.makeText(CalfDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(CalfDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(CalfDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CalfDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(){
        name = findViewById(R.id.name);
        isHealthy = findViewById(R.id.health);
        dob = findViewById(R.id.dob);
        mother = findViewById(R.id.child_of);
        profileName = findViewById(R.id.profile_name);
        gender = findViewById(R.id.gender);
        editProfile = findViewById(R.id.edit_profile);
        deleteProfile = findViewById(R.id.delete_profile);
    }
}