package tda.darkarmy.sivalikorganics.activity.expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.Expense;

public class AddEditExpenseActivity extends AppCompatActivity {

    private TextInputLayout food;
    private TextInputLayout medicine;
    private TextInputLayout others;
    private Button addEditExpense;
    private TextView addEditHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_expense);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind();
        String id = null;
        Expense expense = null;
        if(getIntent().getSerializableExtra("EXPENSE")!=null){
            addEditHeader.setText("UPDATE EXPENSE");
            addEditExpense.setText("UPDATE EXPENSE");
            expense = (Expense) getIntent().getSerializableExtra("EXPENSE");
            food.getEditText().setText(expense.getFoodItems().toString());
            medicine.getEditText().setText(expense.getMedicinalItems().toString());
            others.getEditText().setText(expense.getOthers().toString());
        }else{
            addEditHeader.setText("ADD EXPENSE");
            addEditExpense.setText("ADD EXPENSE");
        }

        addEditExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    if(getIntent().getSerializableExtra("EXPENSE")==null){
                        addExpense(accessToken);
                    }else{
                        Expense expense = (Expense) getIntent().getSerializableExtra("EXPENSE");
                        editExpense(accessToken, expense.getId());
                    }
                }

            }
        });
    }

    private void editExpense(String accessToken, String id) {
        Expense expense = new Expense(Integer.parseInt(food.getEditText().getText().toString()),Integer.parseInt(medicine.getEditText().getText().toString()), Integer.parseInt(others.getEditText().getText().toString()));
        RetrofitClient.getInstance().getExpenseService().updateExpense("Bearer "+accessToken, id, expense).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), ExpenseActivity.class));
                }else{
                    try {
                        Toast.makeText(AddEditExpenseActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddEditExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addExpense(String accessToken) {
        Expense expense = new Expense(Integer.parseInt(food.getEditText().getText().toString()),Integer.parseInt(medicine.getEditText().getText().toString()), Integer.parseInt(others.getEditText().getText().toString()));
        RetrofitClient.getInstance().getExpenseService().addExpense("Bearer "+accessToken, expense).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), ExpenseActivity.class));
                }else{
                    Toast.makeText(AddEditExpenseActivity.this, "Failed to add Expense.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddEditExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation() {
        String vFood = food.getEditText().getText().toString();
        String vMed = medicine.getEditText().getText().toString();
        String vOthers = others.getEditText().getText().toString();
        if(!vFood.matches("^\\d+$")){
            food.setError("Enter valid food price.");
            return false;
        }else if(!vMed.matches("^\\d+$")){
            medicine.setError("Enter valid medicine price.");
            return false;
        }else if(!vOthers.matches("^\\d+$")){
            others.setError("Enter valid other items price.");
            return false;
        }
        else{
            return true;
        }
    }


    public void bind(){
        food = findViewById(R.id.food);
        medicine = findViewById(R.id.medicine);
        others = findViewById(R.id.others);
        addEditExpense = findViewById(R.id.add_edit_expense);
        addEditHeader = findViewById(R.id.add_edit_header);
    }
}