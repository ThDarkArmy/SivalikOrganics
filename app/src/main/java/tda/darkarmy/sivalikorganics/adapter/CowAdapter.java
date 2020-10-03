package tda.darkarmy.sivalikorganics.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.cow.AddCowActivity;
import tda.darkarmy.sivalikorganics.activity.cow.CowDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.cow.EditCowActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.CowList;
import tda.darkarmy.sivalikorganics.model.ErrorObject;

public class CowAdapter extends RecyclerView.Adapter<CowAdapter.CowViewHolder> {
    Context context;
    private CowList cowList;
    private ConstraintLayout mainLayout;

    public CowAdapter(Context context, CowList cowList) {
        this.context = context;
        this.cowList = cowList;
    }

    @NonNull
    @Override
    public CowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_common, parent, false);
        return new CowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CowAdapter.CowViewHolder holder, int position) {
        holder.name.setText(cowList.getCows().get(position).getName());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditCowActivity.class);
                intent.putExtra("COW_OBJECT", (Serializable) cowList.getCows().get(position));
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
                String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
                RetrofitClient.getInstance().getCowService().deleteCow("Bearer "+accessToken, cowList.getCows().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            cowList.getCows().remove(position);
                            notifyDataSetChanged();
                        }else{
                            try{
                                ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                                Toast.makeText(context, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                            }catch (Exception ex){
                                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CowDetailsActivity.class);
                intent.putExtra("COW_ID", cowList.getCows().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cowList.getCows().size();
    }

    public class CowViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        Button edit, delete;
        public CowViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_common_name);
            edit = itemView.findViewById(R.id.edit_common);
            delete = itemView.findViewById(R.id.delete_common);
            mainLayout = itemView.findViewById(R.id.common_card_view);
        }
    }
}
