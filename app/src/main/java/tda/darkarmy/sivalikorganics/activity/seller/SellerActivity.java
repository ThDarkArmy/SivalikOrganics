package tda.darkarmy.sivalikorganics.activity.seller;

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
import tda.darkarmy.sivalikorganics.adapter.SellerAdapter;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.UserList;

public class SellerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        recyclerView = findViewById(R.id.consumer_recycler);
        setTitle("               Sellers");
        getAllSeller(accessToken);
    }

    private void getAllSeller(String accessToken) {
        RetrofitClient.getInstance().getSellerService().getAll("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        UserList userList = new GsonBuilder().create().fromJson(response.body().string(), UserList.class);
                        Log.i("USER_LIST", userList.toString());
                        SellerAdapter sellerAdapter = new SellerAdapter(userList, SellerActivity.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(SellerActivity.this));
                        recyclerView.setAdapter(sellerAdapter);
                    }catch (Exception ex){
                        Toast.makeText(SellerActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(SellerActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(SellerActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SellerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}