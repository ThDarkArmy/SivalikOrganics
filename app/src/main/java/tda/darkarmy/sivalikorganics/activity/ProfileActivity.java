package tda.darkarmy.sivalikorganics.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.User;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName;
    private TextView profilePhone;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView role;
    private TextView dateJoined;
    private CircleImageView profileImg;
    private ImageView editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();

        getUser(accessToken);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
            }
        });
    }

    private void getUser(String accessToken) {
        RetrofitClient.getInstance().getUserService().getUser("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        User user = new GsonBuilder().create().fromJson(response.body().string(), User.class);
                        Picasso.get().load(Uri.parse(RetrofitClient.BASE_URL+"/"+user.getProfilePic())).into(profileImg);
                        name.setText(user.getName());
                        profileName.setText(user.getName());
                        phone.setText(user.getMobile());
                        profilePhone.setText(user.getMobile());
                        email.setText(user.getEmail());
                        role.setText(user.getRole());
                        dateJoined.setText(user.getDateJoined());
                        Log.i("PROFILE_U", user.toString());
                    }catch (Exception ex){
                        Log.i("PROFILE_EX",  ex.getMessage());
                        Toast.makeText(ProfileActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.body().string(), ErrorObject.class);
                        Log.i("PROFILE_ERR", errorObject.getError().getMsg());
                        Toast.makeText(ProfileActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Log.i("PROFILE_E",  ex.getMessage());
                        Toast.makeText(ProfileActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("PROFILE_EF",  t.getMessage());
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(){
        profileName = findViewById(R.id.profile_name);
        profilePhone = findViewById(R.id.profile_number);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.mobile);
        role = findViewById(R.id.role);
        dateJoined = findViewById(R.id.date_joined);
        profileImg = findViewById(R.id.profile_img);
        editProfile = findViewById(R.id.edit_profile);
    }
}