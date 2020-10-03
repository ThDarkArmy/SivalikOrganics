package tda.darkarmy.sivalikorganics.activity.calf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.cow.CowActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Calf;
import tda.darkarmy.sivalikorganics.model.Cow;
import tda.darkarmy.sivalikorganics.model.Error;
import tda.darkarmy.sivalikorganics.model.ErrorObject;

public class AddCalfActivity extends AppCompatActivity {
    private TextInputLayout name;
    private TextInputLayout isHealthy;
    private TextInputLayout dob;
    private TextInputLayout gender;
    private Button addCalf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calf);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        Cow cow = (Cow)getIntent().getSerializableExtra("COW_OBJECT");
        Log.i("COW_OBJECT", cow.toString());
        String cowId = cow.getId();
        addCalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    addCalfToDatabase(accessToken, cowId);
                }
            }
        });

    }

    private void addCalfToDatabase(String accessToken, String cow) {
        boolean health=false;
        if(isHealthy.getEditText().getText().toString().equalsIgnoreCase("yes")){
            health = true;
        }
        Calf calf = new Calf(health ,name.getEditText().getText().toString(),dob.getEditText().getText().toString(),gender.getEditText().getText().toString(), cow);
        RetrofitClient.getInstance().getCalfService().addCalf("Bearer "+accessToken, calf).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        Toast.makeText(AddCalfActivity.this, "Cow added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), CalfActivity.class));

                    }catch (Exception ex){
                        Toast.makeText(AddCalfActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject error = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(AddCalfActivity.this, error.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(AddCalfActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddCalfActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // form validation
    public boolean validation(){
        String vName = name.getEditText().getText().toString();
        String vDob = dob.getEditText().getText().toString();
        String vGender = gender.getEditText().getText().toString();
        String vIsHealthy = isHealthy.getEditText().getText().toString();

        if(vName.length()<3){
            name.setError("Enter a valid name.");
            return false;
        }else if(vDob.isEmpty()){
            dob.setError("Enter a valid DOB.");
            return false;
        }else if(!vGender.equalsIgnoreCase("male") && !vGender.equalsIgnoreCase("female")){
            gender.setError("Enter only male or female");
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
        isHealthy = findViewById(R.id.health);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        addCalf = findViewById(R.id.add_calf);
    }
}