package tda.darkarmy.sivalikorganics.activity.exportdetails;

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
import tda.darkarmy.sivalikorganics.adapter.ExportDetailsAdapter;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.ExportDetail;
import tda.darkarmy.sivalikorganics.model.ExportDetailsList;

public class ExportDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button search;
    private TextInputLayout monthYear;
    private TextView totalMilk;
    private TextView totalMoney;
    private boolean isConsumer = false;
    private String consumerId = null;
    private ExportDetailsList exportDetailsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_details);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        if(getIntent().getStringExtra("CONSUMER_ID")!=null){
            isConsumer = true;
            consumerId = getIntent().getStringExtra("CONSUMER_ID");
        }

        setTitle("\t\t\t\t\t\t\tExport Details");

        if(isConsumer){
            getAllExportsByConsumer(accessToken, consumerId);
        }else{
            getAllExports(accessToken);
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String my = monthYear.getEditText().getText().toString();
                   if(isConsumer){
                       getExportsByConsumerAndMonth(accessToken, consumerId, my);
                   }else{
                       getExportByMonth(accessToken, my);
                   }
            }
        });

    }

    private void getExportByMonth(String accessToken, String my) {
        RetrofitClient.getInstance().getExportDetailsService().getExportsByMonthYear("Bearer "+accessToken, my).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        exportDetailsList = new GsonBuilder().create().fromJson(response.body().string(), ExportDetailsList.class);
                        int money=0;
                        int milk=0;
                        for(ExportDetail exportDetail: exportDetailsList.getExportDetails()){
                            money = money + exportDetail.getAmountPaid();
                            milk = milk + exportDetail.getAmountOfMilkSold();
                        }
                        totalMoney.setText("Total Money Paid: "+money+"  Rupees");
                        totalMilk.setText("Total Amount of Milk: "+milk+"  Litres");
                        ExportDetailsAdapter exportDetailsAdapter = new ExportDetailsAdapter(ExportDetailsActivity.this, exportDetailsList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ExportDetailsActivity.this));
                        recyclerView.setAdapter(exportDetailsAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ExportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ExportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getExportsByConsumerAndMonth(String accessToken, String consumerId, String my) {
        RetrofitClient.getInstance().getExportDetailsService().getExportsByConsumerAndMonth("Bearer "+accessToken, consumerId, my).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        exportDetailsList = new GsonBuilder().create().fromJson(response.body().string(), ExportDetailsList.class);
                        setTitle("\t\t\t"+exportDetailsList.getExportDetails().get(0).getUser().getName()+" Export Details");
                        int money=0;
                        int milk=0;
                        for(ExportDetail exportDetail: exportDetailsList.getExportDetails()){
                            money = money + exportDetail.getAmountPaid();
                            milk = milk + exportDetail.getAmountOfMilkSold();
                        }
                        totalMoney.setText("Total Money Paid: "+money+"  Rupees");
                        totalMilk.setText("Total Amount of Milk: "+milk+"  Litres");
                        ExportDetailsAdapter exportDetailsAdapter = new ExportDetailsAdapter(ExportDetailsActivity.this, exportDetailsList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ExportDetailsActivity.this));
                        recyclerView.setAdapter(exportDetailsAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ExportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ExportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllExportsByConsumer(String accessToken, String id){
        RetrofitClient.getInstance().getExportDetailsService().getExportsByConsumer("Bearer "+accessToken, id).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        exportDetailsList = new GsonBuilder().create().fromJson(response.body().string(), ExportDetailsList.class);
                        setTitle("\t\t\t"+exportDetailsList.getExportDetails().get(0).getUser().getName()+" Export Details");
                        int money=0;
                        int milk=0;
                        for(ExportDetail exportDetail: exportDetailsList.getExportDetails()){
                            money = money + exportDetail.getAmountPaid();
                            milk = milk + exportDetail.getAmountOfMilkSold();
                        }
                        totalMoney.setText("Total Money Paid: "+money+"  Rupees");
                        totalMilk.setText("Total Amount of Milk: "+milk+"  Litres");
                        ExportDetailsAdapter exportDetailsAdapter = new ExportDetailsAdapter(ExportDetailsActivity.this, exportDetailsList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ExportDetailsActivity.this));
                        recyclerView.setAdapter(exportDetailsAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ExportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ExportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllExports(String accessToken) {
        RetrofitClient.getInstance().getExportDetailsService().getAllExports("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        exportDetailsList = new GsonBuilder().create().fromJson(response.body().string(), ExportDetailsList.class);
                        Log.i("EXPORT_DETAILS", exportDetailsList.toString());
                        int money=0;
                        int milk=0;
                        for(ExportDetail exportDetail: exportDetailsList.getExportDetails()){
                            money = money + exportDetail.getAmountPaid();
                            milk = milk + exportDetail.getAmountOfMilkSold();
                        }
                        totalMoney.setText("Total Money Paid: "+money+"  Rupees");
                        totalMilk.setText("Total Amount of Milk: "+milk+"  Litres");
                        ExportDetailsAdapter exportDetailsAdapter = new ExportDetailsAdapter(ExportDetailsActivity.this, exportDetailsList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ExportDetailsActivity.this));
                        recyclerView.setAdapter(exportDetailsAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ExportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ExportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(){
        recyclerView = findViewById(R.id.export_recycler);
        search = findViewById(R.id.search);
        monthYear = findViewById(R.id.month_year);
        totalMilk = findViewById(R.id.total_money);
        totalMoney = findViewById(R.id.total_milk);
    }
}