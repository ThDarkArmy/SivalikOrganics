package tda.darkarmy.sivalikorganics.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.LoginResponse;
import tda.darkarmy.sivalikorganics.model.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mobile;
    private TextInputLayout password;
    private Button login;
    private Button backToRegister;
    private Button forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        bind();

        mobile.getEditText().setText("8885777555");
        password.getEditText().setText("password");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    loginUser();
                }
            }
        });


        backToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    public void loginUser(){
        User user = new User(mobile.getEditText().getText().toString(), password.getEditText().getText().toString());
        RetrofitClient.getInstance().getUserService().loginUser(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        LoginResponse loginResponse = new GsonBuilder().create().fromJson(response.body().string(),LoginResponse.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ACCESSTOKEN", loginResponse.getAccessToken());
                        editor.putString("ROLE", loginResponse.getRole());
                        editor.putBoolean("ISLOGIN", true);
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(LoginActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // form validation
    public boolean validation(){

        String vMobile = mobile.getEditText().getText().toString();
        String vPassword = password.getEditText().getText().toString();
        if(vMobile.length()!=10){
            mobile.setError("Enter a valid mobile number.");
            return false;
        }else if(vPassword.isEmpty()){
            password.setError("Password field cannot be empty.");
            return false;
        }else{
            return true;
        }
    }


    public void bind(){
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);
        backToRegister = findViewById(R.id.back_to_register_button);
        forgotPassword = findViewById(R.id.forget_password_button);
    }
}