package tda.darkarmy.sivalikorganics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
import tda.darkarmy.sivalikorganics.activity.expense.AddEditExpenseActivity;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ExpenseList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private ExpenseList expenseList;
    private Context context;

    public ExpenseAdapter(ExpenseList expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder holder, int position) {
        Log.i("Expense List:",expenseList.getExpenses().get(position).toString());
        holder.date.setText("Date: "+expenseList.getExpenses().get(position).getDate().substring(0,10));
        holder.food.setText("Food: "+expenseList.getExpenses().get(position).getFoodItems().toString());
        holder.medicine.setText("Medicine: "+expenseList.getExpenses().get(position).getMedicinalItems().toString());
        holder.others.setText("Others: "+expenseList.getExpenses().get(position).getOthers().toString());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), AddEditExpenseActivity.class);
                intent.putExtra("EXPENSE", (Serializable) expenseList.getExpenses().get(position));
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
                String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
                RetrofitClient.getInstance().getExpenseService().deleteExpense("Bearer "+accessToken, expenseList.getExpenses().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            expenseList.getExpenses().remove(position);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "Failed to delete expense.", Toast.LENGTH_SHORT).show();
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
        return expenseList.getExpenses().size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView food;
        TextView medicine;
        TextView others;
        ImageView edit;
        ImageView delete;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            food = itemView.findViewById(R.id.food);
            medicine = itemView.findViewById(R.id.medicine);
            others = itemView.findViewById(R.id.others);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
