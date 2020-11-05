package tda.darkarmy.sivalikorganics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.exportdetails.EditExportDetailsActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ExportDetailsList;

public class ExportDetailsAdapter extends RecyclerView.Adapter<ExportDetailsAdapter.ExportViewHolder> {
    Context context;
    ExportDetailsList exportDetailsList;

    public ExportDetailsAdapter(Context context, ExportDetailsList exportDetailsList) {
        this.context = context;
        this.exportDetailsList = exportDetailsList;
    }

    @NonNull
    @Override
    public ExportDetailsAdapter.ExportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_export_import, parent, false);
        return new ExportDetailsAdapter.ExportViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExportDetailsAdapter.ExportViewHolder holder, int position) {
        holder.date.setText("Date: "+exportDetailsList.getExportDetails().get(position).getDate().substring(0,10));
        holder.consumer.setText("Consumer: "+exportDetailsList.getExportDetails().get(position).getUser().getName());
        holder.amountOfMilk.setText("Amount of Milk: "+exportDetailsList.getExportDetails().get(position).getAmountOfMilkSold());
        holder.amountPaid.setText("Amount Paid: "+exportDetailsList.getExportDetails().get(position).getAmountPaid());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditExportDetailsActivity.class);
                intent.putExtra("EXPORT_OBJECT", (Serializable) exportDetailsList.getExportDetails().get(position));
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
                String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
                RetrofitClient.getInstance().getExportDetailsService().deleteExportDetails("Bearer "+accessToken, exportDetailsList.getExportDetails().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            exportDetailsList.getExportDetails().remove(position);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "Failed to delete export details.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return exportDetailsList.getExportDetails().size();
    }

    public class ExportViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView consumer;
        TextView amountOfMilk;
        TextView amountPaid;
        ImageView edit;
        ImageView delete;
        public ExportViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            consumer = itemView.findViewById(R.id.consumer);
            amountOfMilk = itemView.findViewById(R.id.milk_amount);
            amountPaid = itemView.findViewById(R.id.amount_paid);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
