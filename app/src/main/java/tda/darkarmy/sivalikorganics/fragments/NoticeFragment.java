package tda.darkarmy.sivalikorganics.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.activity.notice.AddEditNoticeActivity;
import tda.darkarmy.sivalikorganics.adapter.NoticeAdapter;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.NoticeList;

public class NoticeFragment extends Fragment {
    private Button addNotice;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notice, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESSTOKEN", null);
        bind(root);

        getAllNotices(accessToken);

        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddEditNoticeActivity.class));
            }
        });

        return root;
    }

    private void getAllNotices(String accessToken) {
        RetrofitClient.getInstance().getNoticeService().getAllNotices("Bearer "+accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        NoticeList noticeList = new GsonBuilder().create().fromJson(response.body().string(), NoticeList.class);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        NoticeAdapter noticeAdapter = new NoticeAdapter(noticeList, getActivity());
                        recyclerView.setAdapter(noticeAdapter);
                        //noticeAdapter.notifyDataSetChanged();

                    }catch (Exception ex){
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorObject.class);
                        Toast.makeText(getActivity(), errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bind(ViewGroup root) {
        addNotice = root.findViewById(R.id.add_notice);
        recyclerView = root.findViewById(R.id.notice_recycler);
    }


}
