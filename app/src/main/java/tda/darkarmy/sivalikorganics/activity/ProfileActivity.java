package tda.darkarmy.sivalikorganics.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tda.darkarmy.sivalikorganics.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView profile_name;
    private TextView profile_phone;
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
        bind();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
            }
        });
    }

    public void bind(){
        profile_name = findViewById(R.id.profile_name);
        profile_phone = findViewById(R.id.profile_number);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.mobile);
        role = findViewById(R.id.role);
        dateJoined = findViewById(R.id.date_joined);
        profileImg = findViewById(R.id.profile_img);
        editProfile = findViewById(R.id.edit_profile);
    }
}