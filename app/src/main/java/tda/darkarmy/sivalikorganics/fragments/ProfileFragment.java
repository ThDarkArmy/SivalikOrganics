package tda.darkarmy.sivalikorganics.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.EditProfileActivity;

public class ProfileFragment extends Fragment {
    private TextView profile_name;
    private TextView profile_phone;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView role;
    private TextView dateJoined;
    private CircleImageView profileImg;
    private ImageView editProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        bind(root);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        return root;
    }

    public void bind(ViewGroup root){
        profile_name = root.findViewById(R.id.profile_name);
        profile_phone = root.findViewById(R.id.profile_number);
        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        phone = root.findViewById(R.id.mobile);
        role = root.findViewById(R.id.role);
        dateJoined = root.findViewById(R.id.date_joined);
        profileImg = root.findViewById(R.id.profile_img);
        editProfile = root.findViewById(R.id.edit_profile);
    }


}
