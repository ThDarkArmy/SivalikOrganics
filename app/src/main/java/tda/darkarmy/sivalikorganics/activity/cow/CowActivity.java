package tda.darkarmy.sivalikorganics.activity.cow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.adapter.CowAdapter;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.CowList;

public class CowActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button addCow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow);

        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        Log.i("TOKEN", "Bearer "+accessToken);
        recyclerView = findViewById(R.id.cow_recycler);
        recyclerView.setNestedScrollingEnabled(false);
        addCow = findViewById(R.id.add_cow);
        getAllCows(accessToken);

        addCow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddCowActivity.class));
            }
        });
    }

    public void getAllCows(String accessToken){

        RetrofitClient.getInstance().getCowService().getAllCows("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        CowList cowList = new GsonBuilder().create().fromJson(response.body().string(), CowList.class);
                        CowAdapter cowAdapter = new CowAdapter(CowActivity.this, cowList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CowActivity.this));
                        recyclerView.setAdapter(cowAdapter);
                        setTitle("                       COWS");
                        Log.i("COWLIST", cowList.toString());
                        Log.i("COW_ID", cowList.getCows().get(2).getId());

                    }catch (Exception ex){
                        Toast.makeText(CowActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        Log.i("COWFAIL", response.errorBody().string());
                    } catch (IOException e) {
                        Log.i("COWFAIL", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("COWFAIL", t.getMessage());
            }
        });
    }
}