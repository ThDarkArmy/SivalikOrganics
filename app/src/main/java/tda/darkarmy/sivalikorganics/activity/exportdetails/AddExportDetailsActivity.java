package tda.darkarmy.sivalikorganics.activity.exportdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.ExportDetail;
import tda.darkarmy.sivalikorganics.model.ResponseBodyFromServer;

public class AddExportDetailsActivity extends AppCompatActivity {
    private TextInputLayout date;
    private TextInputLayout amountPaid;
    private TextInputLayout milkAmount;
    private Button addExportDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_export_details);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        String id = getIntent().getStringExtra("CONSUMER_ID");
        addExportDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    addExport(accessToken, id);
                }
            }
        });

    }

    private void addExport(String accessToken, String id) {
        ExportDetail exportDetail = new ExportDetail(Integer.parseInt(amountPaid.getEditText().getText().toString()),date.getEditText().getText().toString(), Integer.parseInt(milkAmount.getEditText().getText().toString()), id);
        RetrofitClient.getInstance().getExportDetailsService().addExportDetails("Bearer "+accessToken, exportDetail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        ResponseBodyFromServer bodyFromServer = new GsonBuilder().create().fromJson(response.body().string(), ResponseBodyFromServer.class);
                        Toast.makeText(AddExportDetailsActivity.this, bodyFromServer.getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ExportDetailsActivity.class);
                        intent.putExtra("CONSUMER_ID", id);
                        startActivity(intent);
                    }catch (Exception ex){
                        Toast.makeText(AddExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(AddExportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(AddExportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddExportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation() {
        String vDate = date.getEditText().getText().toString();
        String vMoney = amountPaid.getEditText().getText().toString();
        String vMilk = milkAmount.getEditText().getText().toString();
        if(vDate.isEmpty()){
            date.setError("Enter valid date.");
            return false;
        }else if(!vMoney.matches("^\\d+$")){
            amountPaid.setError("Enter valid money.");
            return false;
        }else if(!vMilk.matches("^\\d+$")){
            milkAmount.setError("Enter valid milk amount in litres.");
            return false;
        }else if(Integer.parseInt(vMilk)<1){
            milkAmount.setError("Milk amount should be greater than or equal to 1.");
            return false;
        }
        else{
            return true;
        }
    }

    public void bind(){
        date = findViewById(R.id.date);
        amountPaid = findViewById(R.id.paid_amount);
        milkAmount = findViewById(R.id.milk_amount);
        addExportDetails = findViewById(R.id.add_export_details);
    }
}