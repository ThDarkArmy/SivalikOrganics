package tda.darkarmy.sivalikorganics.activity.consumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.adapter.ConsumerAdapter;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.UserList;

public class ConsumerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        recyclerView = findViewById(R.id.consumer_recycler);
        recyclerView.setNestedScrollingEnabled(false);

        if(getIntent().getBooleanExtra("DEFAULTER", false)){
            setTitle("                    Defaulters");
            getAllDefaulters(accessToken);
        }else if(getIntent().getBooleanExtra("IS_EMPLOYEE", false)){
            setTitle("                    Employees");
            getAllEmployees(accessToken);
        }
        else{
            setTitle("                    Consumers");
            getAllConsumer(accessToken);
        }

    }

    private void getAllEmployees(String accessToken) {
        RetrofitClient.getInstance().getEmployeeService().getAllEmployees("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        UserList userList = new GsonBuilder().create().fromJson(response.body().string(), UserList.class);
                        Log.i("USER_LIST", userList.toString());
                        ConsumerAdapter consumerAdapter = new ConsumerAdapter(userList,ConsumerActivity.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ConsumerActivity.this));
                        recyclerView.setAdapter(consumerAdapter);
                    }catch (Exception ex){
                        Toast.makeText(ConsumerActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ConsumerActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ConsumerActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ConsumerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllDefaulters(String accessToken) {
        RetrofitClient.getInstance().getConsumerService().getDefaulters("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        UserList userList = new GsonBuilder().create().fromJson(response.body().string(), UserList.class);
                        Log.i("USER_LIST", userList.toString());
                        ConsumerAdapter consumerAdapter = new ConsumerAdapter(userList,ConsumerActivity.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ConsumerActivity.this));
                        recyclerView.setAdapter(consumerAdapter);
                    }catch (Exception ex){
                        Toast.makeText(ConsumerActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ConsumerActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ConsumerActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ConsumerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllConsumer(String accessToken) {
        RetrofitClient.getInstance().getConsumerService().getAll("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        UserList userList = new GsonBuilder().create().fromJson(response.body().string(), UserList.class);
                        Log.i("USER_LIST", userList.toString());
                        ConsumerAdapter consumerAdapter = new ConsumerAdapter(userList,ConsumerActivity.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ConsumerActivity.this));
                        recyclerView.setAdapter(consumerAdapter);
                    }catch (Exception ex){
                        Toast.makeText(ConsumerActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ConsumerActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ConsumerActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ConsumerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}