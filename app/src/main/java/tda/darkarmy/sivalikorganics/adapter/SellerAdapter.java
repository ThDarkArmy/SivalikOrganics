package tda.darkarmy.sivalikorganics.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.seller.SellerDetailsActivity;
import tda.darkarmy.sivalikorganics.model.UserList;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.SellerViewHolder> {
    UserList userList;
    Context context;
    private ConstraintLayout mainLayout;

    public SellerAdapter(UserList userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_seller_consumer, parent, false);
        return new SellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerViewHolder holder, int position) {
        holder.name.setText(userList.getUsers().get(position).getName());
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), SellerDetailsActivity.class);
                intent.putExtra("SELLER_ID", userList.getUsers().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.getUsers().size();
    }

    public class SellerViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mainLayout = itemView.findViewById(R.id.card_consumer_seller);
        }
    }
}
