package tda.darkarmy.sivalikorganics.activity.seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.consumer.ConsumerDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.exportdetails.AddExportDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.exportdetails.ExportDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.importdetails.AddImportDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.importdetails.ImportDetailsActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ConsumerDetails;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.SellerDetails;

public class SellerDetailsActivity extends AppCompatActivity {
    private CircleImageView imageView;
    private TextView profileName;
    private TextView profileNumber;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView dateJoined;
    private TextView milkAmount;
    private TextView moneyPaid;
    private ImageView addImportDetails;
    private ImageView importDetails;
    private LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        String id = getIntent().getStringExtra("SELLER_ID");
        RetrofitClient.getInstance().getSellerService().getById("Bearer "+accessToken, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        SellerDetails sellerDetails = new GsonBuilder().create().fromJson(response.body().string(), SellerDetails.class);
                        Picasso.get().load(Uri.parse(RetrofitClient.BASE_URL+"/"+sellerDetails.getProfilePic())).into(imageView);
                        profileName.setText(sellerDetails.getName());
                        profileNumber.setText(sellerDetails.getMobile());
                        name.setText(sellerDetails.getName());
                        phone.setText(sellerDetails.getMobile());
                        email.setText(sellerDetails.getEmail());
                        dateJoined.setText(sellerDetails.getDateJoined().substring(0,10));
                        milkAmount.setText(sellerDetails.getTotalAmountOfMilkSold()+" Litres");
                        moneyPaid.setText(sellerDetails.getTotalAmountPaid()+" Rupees");

                    }catch (Exception ex){
                        mainLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(SellerDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        mainLayout.setVisibility(View.INVISIBLE);
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(SellerDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        mainLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(SellerDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mainLayout.setVisibility(View.INVISIBLE);
                Toast.makeText(SellerDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addImportDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddImportDetailsActivity.class);
                intent.putExtra("SELLER_ID", id);
                startActivity(intent);
            }
        });

        importDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ImportDetailsActivity.class);
                intent.putExtra("SELLER_ID", id);
                startActivity(intent);
            }
        });
    }

    public void bind(){
        imageView = findViewById(R.id.profile_img);
        profileName = findViewById(R.id.profile_name);
        profileNumber = findViewById(R.id.profile_number);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        dateJoined = findViewById(R.id.date_joined);
        phone = findViewById(R.id.phone);
        milkAmount = findViewById(R.id.milk_amount);
        moneyPaid = findViewById(R.id.amount_paid);
        addImportDetails = findViewById(R.id.add_import);
        importDetails = findViewById(R.id.import_details);
        mainLayout = findViewById(R.id.main_layout);
    }
}