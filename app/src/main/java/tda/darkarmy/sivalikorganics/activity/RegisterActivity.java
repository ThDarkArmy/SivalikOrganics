package tda.darkarmy.sivalikorganics.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.model.ResponseBodyFromServer;
import tda.darkarmy.sivalikorganics.model.User;

public class RegisterActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUESTS = 0;
    private TextInputLayout name;
    private TextInputLayout email;
    private TextInputLayout mobile;
    private TextInputLayout password;
    private Button choseProfilePic;
    private Button register;
    private Button backToLogin;
    private Spinner spinner;
    private Uri imageUri;
    private Bitmap bitmap=null;
    private File imageFile = null;
    private ImageView profilePicture;
    private String role;
    private InputStream inputStream = null;
    private LinearLayout linearLayoutMain;
    private TextView confirmMessage;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestPermissions();
        getSupportActionBar().hide();
        bind();
        name.getEditText().setText("Dark Army");
        email.getEditText().setText("darkarmy@gmail.com");
        mobile.getEditText().setText("9989765432");
        password.getEditText().setText("password");

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.ChooseRole,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(adapterView.getId()==R.id.choose_role_spinner){
                    if(position==0){
                        Toast.makeText(RegisterActivity.this, "Please select a role.", Toast.LENGTH_SHORT).show();
                    }else{
                        role = adapterView.getItemAtPosition(position).toString();
                        Toast.makeText(RegisterActivity.this, role, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(RegisterActivity.this, "Please select a role.", Toast.LENGTH_SHORT).show();
            }
        });

        choseProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    registerUser();
                }

            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUESTS){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // go ahead
            }else{
                // fuck off
            }
        }
    }

    private void requestPermissions() {
        List<String> requiredPermissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.CAMERA);
        }

        if (!requiredPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    requiredPermissions.toArray(new String[]{}),
                    MY_PERMISSIONS_REQUESTS);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData()!=null){
            imageUri = data.getData();
            Random random = new Random();
            double num = random.nextDouble();

            try{
                inputStream = getContentResolver().openInputStream(imageUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inSampleSize = 2;
                options.inScreenDensity = DisplayMetrics.DENSITY_LOW;
                bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                imageFile = new File(Environment.getExternalStorageDirectory()+"/"+num+"image.jpg");
                imageFile.createNewFile();
                if(!imageFile.exists()){
                    imageFile.mkdir();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);

                fileOutputStream.flush();
                fileOutputStream.close();


            }catch (Exception ex){
                ex.printStackTrace();
            }
            //Log.i("IMAGE_PATH", "image"+imagePath);
            Picasso.get().load(imageUri).into(profilePicture);
        }
    }



    public void registerUser() {


        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        RequestBody roleRequestBody = RequestBody.create(MediaType.parse("text/plane"), role.toUpperCase());
        RequestBody nameRequestBody = RequestBody.create(MediaType.parse("text/plane"), name.getEditText().getText().toString());
        RequestBody emailRequestBody = RequestBody.create(MediaType.parse("text/plane"), email.getEditText().getText().toString());
        RequestBody mobileRequestBody = RequestBody.create(MediaType.parse("text/plane"), mobile.getEditText().getText().toString());
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plane"), password.getEditText().getText().toString());

        MultipartBody.Part part = MultipartBody.Part.createFormData("profilePic",imageFile.getName(), requestBody);
        RetrofitClient.getInstance().getUserService().registerUser(part,
                roleRequestBody, nameRequestBody, emailRequestBody, mobileRequestBody, passwordRequestBody).enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    linearLayoutMain.setVisibility(LinearLayout.GONE);
                    confirmMessage.setVisibility(TextView.VISIBLE);
                    try {
                        ResponseBodyFromServer fromServer = new GsonBuilder().create().fromJson(response.body().string(),ResponseBodyFromServer.class);
//                        SpannableString spannableString = new SpannableString("Login..");
//                        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),"Login..".length(), "Login..".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        confirmMessage.setText(fromServer.getMsg());

                        ActionBar actionBar = getSupportActionBar();
                        actionBar.show();
                    } catch (IOException e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    try {
                        Toast.makeText(RegisterActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("REGISTER_FAILURE", t.toString());
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    // form validation
    public boolean validation(){
        String vName = name.getEditText().getText().toString();
        String vEmail = email.getEditText().getText().toString();
        String vMobile = mobile.getEditText().getText().toString();
        String vPassword = password.getEditText().getText().toString();
        String re = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

        if(vName.length()<5){
            name.setError("Enter a valid name.");
            return false;
        }else if(!vEmail.matches(re)){
            email.setError("Enter a valid email.");
            return false;
        }else if(vMobile.length()!=10){
            mobile.setError("Enter a valid mobile number.");
            return false;
        }else if(vPassword.length() < 6){
            password.setError("Password must be atleast 6 characters long.");
            return false;
        }else if(role==null){
            Toast.makeText(this, "Please select a role.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(imageFile==null){
            Toast.makeText(this, "Please choose a profile picture.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }

    public void bind(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        spinner = findViewById(R.id.choose_role_spinner);
        choseProfilePic = findViewById(R.id.chose_profile_pic);
        register = findViewById(R.id.register);
        backToLogin = findViewById(R.id.back_to_login);
        profilePicture = findViewById(R.id.profile_picture);
        linearLayoutMain = findViewById(R.id.linear_layout_main);
        confirmMessage = findViewById(R.id.confirm_message);
    }
}