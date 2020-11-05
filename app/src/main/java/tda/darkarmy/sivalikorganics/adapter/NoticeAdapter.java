package tda.darkarmy.sivalikorganics.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.notice.NoticeDetailActivity;
import tda.darkarmy.sivalikorganics.model.NoticeList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    NoticeList noticeList;
    Context context;
    ConstraintLayout mainLayout;

    public NoticeAdapter(NoticeList noticeList, Context context) {
        this.noticeList = noticeList;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_common, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.NoticeViewHolder holder, int position) {
        holder.name.setText(noticeList.getNotices().get(position).getTitle());
        holder.edit.setVisibility(View.INVISIBLE);
        holder.delete.setVisibility(View.INVISIBLE);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoticeDetailActivity.class);
                intent.putExtra("NOTICE", (Serializable)noticeList.getNotices().get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeList.getNotices().size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        Button edit, delete;
        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_common_name);
            edit = itemView.findViewById(R.id.edit_common);
            delete = itemView.findViewById(R.id.delete_common);
            mainLayout = itemView.findViewById(R.id.common_card_view);
        }
    }
}
