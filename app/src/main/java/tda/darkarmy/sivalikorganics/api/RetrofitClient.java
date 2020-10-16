package tda.darkarmy.sivalikorganics.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tda.darkarmy.sivalikorganics.service.CalfService;
import tda.darkarmy.sivalikorganics.service.ConsumerService;
import tda.darkarmy.sivalikorganics.service.CowService;
import tda.darkarmy.sivalikorganics.service.EmployeeService;
import tda.darkarmy.sivalikorganics.service.ExpenseService;
import tda.darkarmy.sivalikorganics.service.ExportDetailsService;
import tda.darkarmy.sivalikorganics.service.ImportDetailsService;
import tda.darkarmy.sivalikorganics.service.NoticeService;
import tda.darkarmy.sivalikorganics.service.SellerService;
import tda.darkarmy.sivalikorganics.service.UserService;

public class RetrofitClient {
    public static final String BASE_URL = "http://192.168.43.172:5000";
    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public UserService getUserService(){
        return retrofit.create(UserService.class);
    }

    public CowService getCowService(){
        return retrofit.create(CowService.class);
    }

    public CalfService getCalfService(){
        return retrofit.create(CalfService.class);
    }

    public ExportDetailsService getExportDetailsService(){
        return retrofit.create(ExportDetailsService.class);
    }

    public ImportDetailsService getImportDetailsService(){
        return retrofit.create(ImportDetailsService.class);
    }

    public ConsumerService getConsumerService(){
        return retrofit.create(ConsumerService.class);
    }

    public SellerService getSellerService(){
        return retrofit.create(SellerService.class);
    }

    public EmployeeService getEmployeeService(){
        return retrofit.create(EmployeeService.class);
    }

    public ExpenseService getExpenseService(){
        return retrofit.create(ExpenseService.class);
    }

    public NoticeService getNoticeService(){
        return retrofit.create(NoticeService.class);
    }
}
