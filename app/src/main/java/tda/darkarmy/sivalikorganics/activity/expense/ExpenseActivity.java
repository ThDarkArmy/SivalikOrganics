package tda.darkarmy.sivalikorganics.activity.expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import tda.darkarmy.sivalikorganics.adapter.ExpenseAdapter;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.Expense;
import tda.darkarmy.sivalikorganics.model.ExpenseList;

public class ExpenseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button search;
    private Button addExpense;
    private TextInputLayout month;
    private TextView totalFood;
    private TextView totalMedicine;
    private TextView totalOthers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();

        getAllExpense(accessToken);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllExpenseByMonth(accessToken);
            }
        });

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddEditExpenseActivity.class));
            }
        });
    }

    private void getAllExpenseByMonth(String accessToken) {
        RetrofitClient.getInstance().getExpenseService().getAllExpenseByMonth("Bearer "+accessToken, month.getEditText().getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        ExpenseList expenseList = new GsonBuilder().create().fromJson(response.body().string(), ExpenseList.class);
                        Integer tFood = 0;
                        Integer tMedicine = 0;
                        Integer tOthers = 0;

                        for(Expense expense: expenseList.getExpenses()){
                            tFood += expense.getFoodItems();
                            tMedicine += expense.getMedicinalItems();
                            tOthers += expense.getOthers();
                        }
                        totalFood.setText("Food: "+tFood);
                        totalMedicine.setText("Medicine: "+tMedicine);
                        totalOthers.setText("Others: "+tOthers);
                        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseList, getApplicationContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(expenseAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ExpenseActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ExpenseActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ExpenseActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAllExpense(String accessToken) {
        RetrofitClient.getInstance().getExpenseService().getAllExpense("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        ExpenseList expenseList = new GsonBuilder().create().fromJson(response.body().string(), ExpenseList.class);
                        Integer tFood = 0;
                        Integer tMedicine = 0;
                        Integer tOthers = 0;

                        for(Expense expense: expenseList.getExpenses()){
                            tFood += expense.getFoodItems();
                            tMedicine += expense.getMedicinalItems();
                            tOthers += expense.getOthers();
                        }
                        totalFood.setText("Food: "+tFood);
                        totalMedicine.setText("Medicine: "+tMedicine);
                        totalOthers.setText("Others: "+tOthers);

                        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseList, getApplicationContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(expenseAdapter);

                    }catch (Exception ex){
                        Toast.makeText(ExpenseActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(ExpenseActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(ExpenseActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bind(){
        recyclerView = findViewById(R.id.expense_recycler);
        search = findViewById(R.id.search);
        addExpense = findViewById(R.id.add_expense);
        month = findViewById(R.id.month_year);
        totalFood = findViewById(R.id.total_food);
        totalMedicine = findViewById(R.id.total_medicine);
        totalOthers = findViewById(R.id.total_others);

    }
}