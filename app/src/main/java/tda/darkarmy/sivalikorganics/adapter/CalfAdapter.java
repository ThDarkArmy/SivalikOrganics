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

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.calf.CalfDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.calf.EditCalfActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.CalfList;

public class CalfAdapter extends RecyclerView.Adapter<CalfAdapter.CalfViewHolder> {
    Context context;
    private CalfList calfList;
    private ConstraintLayout mainLayout;

    public CalfAdapter(Context context, CalfList calfList) {
        this.context = context;
        this.calfList = calfList;
    }

    @NonNull
    @Override
    public CalfAdapter.CalfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_common, parent, false);
        return new CalfAdapter.CalfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalfAdapter.CalfViewHolder holder, int position) {
        holder.name.setText(calfList.getCalves().get(position).getName());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditCalfActivity.class);
                intent.putExtra("CALF_OBJECT", (Serializable) calfList.getCalves().get(position));
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
                String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
                RetrofitClient.getInstance().getCalfService().deleteCalf("Bearer "+accessToken, calfList.getCalves().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            calfList.getCalves().remove(position);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "Delete Unsuccessful.", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(context, CalfDetailsActivity.class);
                intent.putExtra("CALF_ID", calfList.getCalves().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calfList.getCalves().size();
    }

    public class CalfViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        Button edit, delete;
        public CalfViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_common_name);
            edit = itemView.findViewById(R.id.edit_common);
            delete = itemView.findViewById(R.id.delete_common);
            mainLayout = itemView.findViewById(R.id.common_card_view);
        }
    }
}

