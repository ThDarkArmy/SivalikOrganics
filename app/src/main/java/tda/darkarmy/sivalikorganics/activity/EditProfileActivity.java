package tda.darkarmy.sivalikorganics.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.User;

public class EditProfileActivity extends AppCompatActivity {

    private TextInputLayout name;
    private TextInputLayout email;
    private TextInputLayout mobile;
    private TextInputLayout password;
    private Button editProfile;
    private Spinner spinner;
    private String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.ChooseRole,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);

        setUser(accessToken);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(adapterView.getId()==R.id.choose_role_spinner){
                    if(position==0){
                        Toast.makeText(EditProfileActivity.this, "Please select a role.", Toast.LENGTH_SHORT).show();
                    }else{
                        role = adapterView.getItemAtPosition(position).toString();
                        Toast.makeText(EditProfileActivity.this, role, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(EditProfileActivity.this, "Please select a role.", Toast.LENGTH_SHORT).show();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    updateUser(accessToken);
                }
            }
        });
    }

    private void setUser(String accessToken) {
        RetrofitClient.getInstance().getUserService().getUser("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        User user = new GsonBuilder().create().fromJson(response.body().string(), User.class);
                        name.getEditText().setText(user.getName());
                        mobile.getEditText().setText(user.getMobile());
                        email.getEditText().setText(user.getEmail());
                    }catch (Exception ex){
                        Toast.makeText(EditProfileActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.body().string(), ErrorObject.class);
                        Toast.makeText(EditProfileActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(EditProfileActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(String accessToken) {
        User user = new User(role, name.getEditText().getText().toString(), email.getEditText().getText().toString(), mobile.getEditText().getText().toString(), password.getEditText().getText().toString());
        RetrofitClient.getInstance().getUserService().updateUser(user, "Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }else{
                    Toast.makeText(EditProfileActivity.this, "Failed to update user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // form validation
    public boolean validation(){
        String vName = name.getEditText().getText().toString();
        String vEmail = email.getEditText().getText().toString();
        String vMobile = mobile.getEditText().getText().toString();
        String vPassword = password.getEditText().getText().toString();
        String re = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

        if(vName.length()<5){
            name.setError("Enter a valid name.");
            return false;
        }else if(!vEmail.matches(re)){
            email.setError("Enter a valid email.");
            return false;
        }else if(vMobile.length()!=10){
            mobile.setError("Enter a valid mobile number.");
            return false;
        }else if(vPassword.length() < 6){
            password.setError("Password must be atleast 6 characters long.");
            return false;
        }else if(role==null){
            Toast.makeText(this, "Please select a role.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }

    public void bind(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        spinner = findViewById(R.id.choose_role_spinner);
        editProfile = findViewById(R.id.update_profile);

    }

}