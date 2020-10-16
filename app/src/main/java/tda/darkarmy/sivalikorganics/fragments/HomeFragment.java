package tda.darkarmy.sivalikorganics.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.calf.CalfActivity;
import tda.darkarmy.sivalikorganics.activity.consumer.ConsumerActivity;
import tda.darkarmy.sivalikorganics.activity.cow.CowActivity;
import tda.darkarmy.sivalikorganics.activity.ProfitActivity;
import tda.darkarmy.sivalikorganics.activity.expense.ExpenseActivity;
import tda.darkarmy.sivalikorganics.activity.exportdetails.ExportDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.importdetails.ImportDetailsActivity;
import tda.darkarmy.sivalikorganics.activity.seller.SellerActivity;

public class HomeFragment extends Fragment {
    private RelativeLayout importRelativeLayout;
    private RelativeLayout exportRelativeLayout;
    private RelativeLayout consumersRelativeLayout;
    private RelativeLayout sellersRelativeLayout;
    private RelativeLayout defaultersRelativeLayout;
    private RelativeLayout profitRelativeLayout;
    private RelativeLayout employeesRelativeLayout;
    private RelativeLayout expensesRelativeLayout;
    private RelativeLayout cowsRelativeLayout;
    private RelativeLayout calvesRelativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind(root);
        importRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ImportDetailsActivity.class));
            }
        });

        exportRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExportDetailsActivity.class));
            }
        });

        consumersRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ConsumerActivity.class));
            }
        });

        sellersRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SellerActivity.class));
            }
        });

        defaultersRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ConsumerActivity.class);
                intent.putExtra("DEFAULTER", true);
                startActivity(intent);
            }
        });

        profitRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfitActivity.class));
            }
        });

        employeesRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ConsumerActivity.class);
                intent.putExtra("IS_EMPLOYEE", true);
                startActivity(intent);
            }
        });

        expensesRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExpenseActivity.class));
            }
        });

        cowsRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CowActivity.class));
            }
        });

        calvesRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CalfActivity.class));
            }
        });


        return root;
    }

    public void bind(ViewGroup root){
        importRelativeLayout = root.findViewById(R.id.import_rl);
        exportRelativeLayout = root.findViewById(R.id.export_rl);
        consumersRelativeLayout = root.findViewById(R.id.consumers_rl);
        sellersRelativeLayout = root.findViewById(R.id.sellers_rl);
        defaultersRelativeLayout = root.findViewById(R.id.defaulters_rl);
        profitRelativeLayout = root.findViewById(R.id.profit_rl);
        employeesRelativeLayout = root.findViewById(R.id.employees_rl);
        expensesRelativeLayout = root.findViewById(R.id.expenses_rl);
        cowsRelativeLayout = root.findViewById(R.id.cows_rl);
        calvesRelativeLayout = root.findViewById(R.id.calves_rl);
    }
}
