package tda.darkarmy.sivalikorganics.activity.importdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.adapter.ImportDetailsAdapter;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.ImportDetail;
import tda.darkarmy.sivalikorganics.model.ImportDetailList;

public class ImportDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button search;
    private TextInputLayout monthYear;
    private TextView totalMilk;
    private TextView totalMoney;
    private boolean isSeller = false;
    private String sellerId = null;
    private ImportDetailList importDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_details);

        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        if(getIntent().getStringExtra("SELLER_ID")!=null){
            isSeller = true;
            sellerId = getIntent().getStringExtra("SELLER_ID");
        }

        setTitle("\t\t\t\t\t\t\tImport Details");

        if(isSeller){
            getAllImportsBySeller(accessToken, sellerId);

        }else{
            getAllImports(accessToken);
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String my = monthYear.getEditText().getText().toString();
                if(isSeller){
                    getImportsBySellerAndMonth(accessToken, sellerId, my);
                }else{
                    getImportByMonth(accessToken, my);
                }
            }
        });
    }

    private void getImportByMonth(String accessToken, String my) {
        RetrofitClient.getInstance().getImportDetailsService().getImportsByMonthYear("Bearer "+accessToken, my).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        importDetailList = new GsonBuilder().create().fromJson(response.body().string(), ImportDetailList.class);
                        int money=0;
                        int milk=0;
                        for(ImportDetail importDetail: importDetailList.getImportDetails()){
                            money = money + importDetail.getAmountPaid();
                            milk = milk + importDetail.getAmountOfMilkBought();
                        }
                        totalMoney.setText("Total Money Paid: "+money+"  Rupees");
                        totalMilk.setText("Total Amount of Milk: "+milk+"  Litres");
                        ImportDetailsAdapter importDetailsAdapter = new ImportDetailsAdapter(ImportDetailsActivity.this, importDetailList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ImportDetailsActivity.this));
                        recyclerView.setAdapter(importDetailsAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ImportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ImportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getImportsBySellerAndMonth(String accessToken, String consumerId, String my) {
        RetrofitClient.getInstance().getImportDetailsService().getImportsBySellerAndMonth("Bearer "+accessToken, consumerId, my).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        importDetailList = new GsonBuilder().create().fromJson(response.body().string(), ImportDetailList.class);
                        int money=0;
                        int milk=0;
                        for(ImportDetail importDetail: importDetailList.getImportDetails()){
                            money = money + importDetail.getAmountPaid();
                            milk = milk + importDetail.getAmountOfMilkBought();
                        }
                        totalMoney.setText("Total Money Paid: "+money+"  Rupees");
                        totalMilk.setText("Total Amount of Milk: "+milk+"  Litres");
                        ImportDetailsAdapter importDetailsAdapter = new ImportDetailsAdapter(ImportDetailsActivity.this, importDetailList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ImportDetailsActivity.this));
                        recyclerView.setAdapter(importDetailsAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ImportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ImportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllImportsBySeller(String accessToken, String id){
        RetrofitClient.getInstance().getImportDetailsService().getImportsBySeller("Bearer "+accessToken, id).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        importDetailList = new GsonBuilder().create().fromJson(response.body().string(), ImportDetailList.class);
                        int money=0;
                        int milk=0;
                        for(ImportDetail importDetail: importDetailList.getImportDetails()){
                            money = money + importDetail.getAmountPaid();
                            milk = milk + importDetail.getAmountOfMilkBought();
                        }
                        totalMoney.setText("Total Money Paid: "+money+"  Rupees");
                        totalMilk.setText("Total Amount of Milk: "+milk+"  Litres");
                        ImportDetailsAdapter importDetailsAdapter = new ImportDetailsAdapter(ImportDetailsActivity.this, importDetailList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ImportDetailsActivity.this));
                        recyclerView.setAdapter(importDetailsAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ImportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ImportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllImports(String accessToken) {
        RetrofitClient.getInstance().getImportDetailsService().getAllImports("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        importDetailList = new GsonBuilder().create().fromJson(response.body().string(), ImportDetailList.class);
                        int money=0;
                        int milk=0;
                        for(ImportDetail importDetail: importDetailList.getImportDetails()){
                            money = money + importDetail.getAmountPaid();
                            milk = milk + importDetail.getAmountOfMilkBought();
                        }
                        totalMoney.setText("Total Money Paid: "+money+"  Rupees");
                        totalMilk.setText("Total Amount of Milk: "+milk+"  Litres");
                        ImportDetailsAdapter importDetailsAdapter = new ImportDetailsAdapter(ImportDetailsActivity.this, importDetailList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ImportDetailsActivity.this));
                        recyclerView.setAdapter(importDetailsAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ImportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ImportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(){
        recyclerView = findViewById(R.id.import_recycler);
        recyclerView.setNestedScrollingEnabled(false);
        search = findViewById(R.id.search);
        monthYear = findViewById(R.id.month_year);
        totalMilk = findViewById(R.id.total_money);
        totalMoney = findViewById(R.id.total_milk);
    }
}