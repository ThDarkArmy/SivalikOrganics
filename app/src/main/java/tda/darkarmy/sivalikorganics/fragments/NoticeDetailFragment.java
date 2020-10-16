package tda.darkarmy.sivalikorganics.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.AddEditNoticeActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Notice;

public class NoticeDetailFragment extends Fragment {
    private ImageView edit;
    private ImageView delete;
    private TextView date;
    private TextView title;
    private TextView description;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notice_detail, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        String role = sharedPreferences.getString("ROLE", null);
        bind(root);
        if(!role.equalsIgnoreCase("ADMIN")){
            edit.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }
        Notice notice = (Notice)getActivity().getIntent().getSerializableExtra("NOTICE");
        date.setText("Date: "+notice.getDate());
        title.setText(notice.getTitle());
        description.setText(notice.getDescription());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEditNoticeActivity.class);
                intent.putExtra("NOTICE", (Serializable)notice);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getInstance().getNoticeService().deleteNotice(accessToken, notice.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            startActivity(new Intent(getActivity(), NoticeFragment.class));
                        }else{
                            Toast.makeText(getActivity(), "Failed to delete notice.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return root;
    }

    private void bind(ViewGroup root) {
        edit = root.findViewById(R.id.edit);
        delete = root.findViewById(R.id.delete);
        date = root.findViewById(R.id.date);
        title = root.findViewById(R.id.title);
        description = root.findViewById(R.id.description);
    }
}
