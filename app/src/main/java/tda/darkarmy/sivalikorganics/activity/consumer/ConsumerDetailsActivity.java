package tda.darkarmy.sivalikorganics.activity.consumer;

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
import tda.darkarmy.sivalikorganics.activity.exportdetails.AddExportDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.exportdetails.ExportDetailsActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ConsumerDetails;
import tda.darkarmy.sivalikorganics.model.ErrorObject;

public class ConsumerDetailsActivity extends AppCompatActivity {
    private CircleImageView imageView;
    private TextView profileName;
    private TextView profileNumber;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView dateJoined;
    private TextView milkAmount;
    private TextView moneyPaid;
    private ImageView addExportDetails;
    private ImageView exportDetails;
    private LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_details);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        String id = getIntent().getStringExtra("CONSUMER_ID");
        RetrofitClient.getInstance().getConsumerService().getById("Bearer "+accessToken, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        ConsumerDetails consumerDetails = new GsonBuilder().create().fromJson(response.body().string(), ConsumerDetails.class);
                        Picasso.get().load(Uri.parse(RetrofitClient.BASE_URL+"/"+consumerDetails.getProfilePic())).into(imageView);
                        profileName.setText(consumerDetails.getName());
                        profileNumber.setText(consumerDetails.getMobile());
                        name.setText(consumerDetails.getName());
                        phone.setText(consumerDetails.getMobile());
                        email.setText(consumerDetails.getEmail());
                        dateJoined.setText(consumerDetails.getDateJoined());
                        milkAmount.setText(consumerDetails.getTotalAmountOfMilkBought()+" Litres");
                        moneyPaid.setText(consumerDetails.getTotalAmountPaid()+" Rupees");

                    }catch (Exception ex){
                        mainLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(ConsumerDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        mainLayout.setVisibility(View.INVISIBLE);
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ConsumerDetailsActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        mainLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(ConsumerDetailsActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mainLayout.setVisibility(View.INVISIBLE);
                Toast.makeText(ConsumerDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addExportDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddExportDetailsActivity.class);
                intent.putExtra("CONSUMER_ID", id);
                startActivity(intent);
            }
        });

        exportDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExportDetailsActivity.class);
                intent.putExtra("CONSUMER_ID", id);
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
        addExportDetails = findViewById(R.id.add_export);
        exportDetails = findViewById(R.id.export_details);
        mainLayout = findViewById(R.id.main_layout);
    }
}