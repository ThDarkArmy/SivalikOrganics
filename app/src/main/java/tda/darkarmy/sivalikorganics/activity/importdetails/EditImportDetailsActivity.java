package tda.darkarmy.sivalikorganics.activity.importdetails;

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
import tda.darkarmy.sivalikorganics.activity.exportdetails.ExportDetailsActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.ExportDetail;
import tda.darkarmy.sivalikorganics.model.ImportDetail;
import tda.darkarmy.sivalikorganics.model.ResponseBodyFromServer;

public class EditImportDetailsActivity extends AppCompatActivity {

    private TextInputLayout date;
    private TextInputLayout amountPaid;
    private TextInputLayout milkAmount;
    private Button updateImportDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_import_details);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        ImportDetail importDetail = (ImportDetail) getIntent().getSerializableExtra("IMPORT_OBJECT");
        date.getEditText().setText(importDetail.getDate());
        amountPaid.getEditText().setText(importDetail.getAmountPaid().toString());
        milkAmount.getEditText().setText(importDetail.getAmountOfMilkBought().toString());

        updateImportDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    updateImport(accessToken, importDetail.getId(), importDetail.getUser().getId());
                }
            }
        });
    }

    private void updateImport(String accessToken, String id, String userId) {
        ImportDetail importDetail = new ImportDetail(Integer.parseInt(amountPaid.getEditText().getText().toString()),date.getEditText().getText().toString(), Integer.parseInt(milkAmount.getEditText().getText().toString()), userId);
        RetrofitClient.getInstance().getImportDetailsService().updateImportDetails("Bearer "+accessToken,id, importDetail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        ResponseBodyFromServer bodyFromServer = new GsonBuilder().create().fromJson(response.body().string(), ResponseBodyFromServer.class);
                        Toast.makeText(EditImportDetailsActivity.this, bodyFromServer.getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ExportDetailsActivity.class);
                        intent.putExtra("SELLER_ID", userId);
                        startActivity(intent);
                    }catch (Exception ex){
                        Toast.makeText(EditImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(EditImportDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(EditImportDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditImportDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        updateImportDetails = findViewById(R.id.update_import_details);
    }
}