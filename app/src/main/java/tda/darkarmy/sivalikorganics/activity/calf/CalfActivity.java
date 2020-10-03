package tda.darkarmy.sivalikorganics.activity.calf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.cow.CowActivity;
import tda.darkarmy.sivalikorganics.adapter.CalfAdapter;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Calf;
import tda.darkarmy.sivalikorganics.model.CalfList;

public class CalfActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calf);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        Log.i("TOKEN", "Bearer "+accessToken);
        recyclerView = findViewById(R.id.calf_recycler);

        getAllCalves(accessToken);
    }

    private void getAllCalves(String accessToken) {
        RetrofitClient.getInstance().getCalfService().getAllCalves("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        CalfList calfList = new GsonBuilder().create().fromJson(response.body().string(), CalfList.class);
                        CalfAdapter calfAdapter = new CalfAdapter( CalfActivity.this ,calfList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CalfActivity.this));
                        recyclerView.setAdapter(calfAdapter);
                        setTitle("                     Calves");
                    }catch (Exception ex){
                        Toast.makeText(CalfActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        Toast.makeText(CalfActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CalfActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}