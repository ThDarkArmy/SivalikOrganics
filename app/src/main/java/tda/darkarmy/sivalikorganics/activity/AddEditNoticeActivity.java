package tda.darkarmy.sivalikorganics.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.fragments.NoticeFragment;
import tda.darkarmy.sivalikorganics.model.Notice;

public class AddEditNoticeActivity extends AppCompatActivity {

    private TextView header;
    private TextInputLayout title;
    private TextInputLayout description;
    private Button addEditNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_notice);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();

        if(getIntent().getSerializableExtra("NOTICE")==null){
            header.setText("Add Notice");
            addEditNotice.setText("Add Notice");
            if(validation()){
                addNotice(accessToken);
            }

        }else{
            Notice notice = (Notice)getIntent().getSerializableExtra("NOTICE");
            header.setText("Update Notice");
            addEditNotice.setText("Update Notice");
            description.getEditText().setText(notice.getDescription());
            title.getEditText().setText(notice.getTitle());
            if(validation()){
                updateNotice(accessToken, notice.getId());
            }

        }
    }

    private void updateNotice(String accessToken, String id) {
        Notice notice = new Notice(title.getEditText().getText().toString(), description.getEditText().getText().toString());
        RetrofitClient.getInstance().getNoticeService().updateNotice("Bearer "+accessToken, id, notice).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), NoticeFragment.class));
                }else{
                    Toast.makeText(AddEditNoticeActivity.this, "Failed to add Notice", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddEditNoticeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNotice(String accessToken) {
        Notice notice = new Notice(title.getEditText().getText().toString(), description.getEditText().getText().toString());
        RetrofitClient.getInstance().getNoticeService().addNotice("Bearer "+accessToken, notice).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), NoticeFragment.class));
                }else{
                    Toast.makeText(AddEditNoticeActivity.this, "Failed to add Notice", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddEditNoticeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validation(){
        String vDesc = description.getEditText().getText().toString();
        String vTitle = title.getEditText().getText().toString();
        if(vTitle.isEmpty()){
            title.setError("Enter valid title");
            return false;
        }
        else if(vDesc.isEmpty()){
            description.setError("Enter valid description");
            return false;
        }else{
            return true;
        }

    }

    public void bind(){
        header = findViewById(R.id.add_edit_header);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        addEditNotice = findViewById(R.id.add_edit_notice);
    }
}