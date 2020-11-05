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
import tda.darkarmy.sivalikorganics.activity.importdetails.EditImportDetailsActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ImportDetailList;

public class ImportDetailsAdapter extends RecyclerView.Adapter<ImportDetailsAdapter.ImportViewHolder> {
    Context context;
    ImportDetailList importDetailsList;

    public ImportDetailsAdapter(Context context, ImportDetailList importDetailsList) {
        this.context = context;
        this.importDetailsList = importDetailsList;
    }

    @NonNull
    @Override
    public ImportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_export_import, parent, false);
        return new ImportViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ImportDetailsAdapter.ImportViewHolder holder, int position) {
        holder.date.setText("Date: "+importDetailsList.getImportDetails().get(position).getDate().substring(0,10));
        holder.consumer.setText("Consumer: "+importDetailsList.getImportDetails().get(position).getUser().getName());
        holder.amountOfMilk.setText("Amount of Milk: "+importDetailsList.getImportDetails().get(position).getAmountOfMilkBought());
        holder.amountPaid.setText("Amount Paid: "+importDetailsList.getImportDetails().get(position).getAmountPaid());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditImportDetailsActivity.class);
                intent.putExtra("IMPORT_OBJECT", (Serializable) importDetailsList.getImportDetails().get(position));
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
                String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
                RetrofitClient.getInstance().getImportDetailsService().deleteImportDetails("Bearer "+accessToken, importDetailsList.getImportDetails().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            importDetailsList.getImportDetails().remove(position);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "Failed to delete import details.", Toast.LENGTH_SHORT).show();
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
        return importDetailsList.getImportDetails().size();
    }

    public class ImportViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView consumer;
        TextView amountOfMilk;
        TextView amountPaid;
        ImageView edit;
        ImageView delete;
        public ImportViewHolder(@NonNull View itemView) {
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
