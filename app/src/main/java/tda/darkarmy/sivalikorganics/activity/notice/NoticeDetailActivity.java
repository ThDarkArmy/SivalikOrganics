package tda.darkarmy.sivalikorganics.activity.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.MainActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Notice;

public class NoticeDetailActivity extends AppCompatActivity {
    private ImageView edit;
    private ImageView delete;
    private TextView date;
    private TextView title;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        setTitle("                    Notice");

        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        String role = sharedPreferences.getString("ROLE", null);
        bind();
        setTitle("                    Notice");
        if(!role.equalsIgnoreCase("ADMIN")){
            edit.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }
        if(getIntent().getSerializableExtra("NOTICE")!=null){
            Notice notice = (Notice)getIntent().getSerializableExtra("NOTICE");
            date.setText("Date: "+notice.getDate().substring(0,10));
            title.setText(notice.getTitle());
            description.setText(notice.getDescription());
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notice notice = (Notice)getIntent().getSerializableExtra("NOTICE");
                Intent intent = new Intent(getApplicationContext(), AddEditNoticeActivity.class);
                intent.putExtra("NOTICE", (Serializable)notice);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notice notice = (Notice)getIntent().getSerializableExtra("NOTICE");
                RetrofitClient.getInstance().getNoticeService().deleteNotice("Bearer "+accessToken, notice.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed to delete notice.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void bind() {
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        date = findViewById(R.id.date);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
    }
}