package tda.darkarmy.sivalikorganics.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.api.RetrofitClient;
import tda.darkarmy.sivalikorganics.fragments.HomeFragment;
import tda.darkarmy.sivalikorganics.fragments.NoticeDetailFragment;
import tda.darkarmy.sivalikorganics.fragments.NoticeFragment;
import tda.darkarmy.sivalikorganics.fragments.SettingsFragment;
import tda.darkarmy.sivalikorganics.model.ErrorObject;
import tda.darkarmy.sivalikorganics.model.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private CircleImageView profileImage;
    private TextView name;
    private TextView number;
    private static String ROLE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("ISLOGIN", false);
        String accessToken;
        ROLE = sharedPreferences.getString("ROLE", null);
        if(!isLogin){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        accessToken = sharedPreferences.getString("ACCESSTOKEN", null);

        bind();
        setProfile(accessToken);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(null);
        // Navigation View
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        setHomeFragment();

    }

    private void setProfile(String accessToken) {
        RetrofitClient.getInstance().getUserService().getUser(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        User user = new GsonBuilder().create().fromJson(response.body().string(), User.class);
                        Picasso.get().load(user.getProfilePic()).into(profileImage);
                        name.setText(user.getName());
                        number.setText(user.getMobile());
                        Log.i("PROFILE", user.toString());
                    }catch (Exception ex){
                        Log.i("PROFILE_EX",  ex.getMessage());
                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try{
                        ErrorObject errorObject = new GsonBuilder().create().fromJson(response.body().string(), ErrorObject.class);
                        Log.i("PROFILE_ERROR", errorObject.getError().getMsg());
                        Toast.makeText(MainActivity.this, errorObject.getError().getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("PROFILE_F", t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * set home fragment
     */
    public void setHomeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Class fragmentClass = HomeFragment.class;
        Fragment fragment = null;
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    /**
     * on back pressed close or open the drawer
     */
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    /**
     * bind the widgets
     */
    public void bind(){
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        profileImage = findViewById(R.id.nav_header_profile);
        name = findViewById(R.id.nav_header_name);
        number = findViewById(R.id.nav_header_number);
    }


    /**
     * @param menuItem
     * @return
     * on navigation item selection perform actions
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Class fragmentClass = null;
        switch (menuItem.getItemId()){
            case R.id.home:
                fragmentClass = HomeFragment.class;
                setFragment(fragmentClass, menuItem);
                break;
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case R.id.notification:
                fragmentClass = NoticeFragment.class;
                setFragment(fragmentClass, menuItem);
                break;
            case R.id.settings:
                fragmentClass = SettingsFragment.class;
                setFragment(fragmentClass, menuItem);
                break;
            case R.id.logout:
                logout();
                Toast.makeText(this, "LOGOUT SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "No Fragment for this menu", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }


    /**
     * @param fragmentClass
     * @param menuItem
     * to set fragments
     */
    public void setFragment(Class fragmentClass, MenuItem menuItem){
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Toast.makeText(this, "No Fragment for this menu", Toast.LENGTH_SHORT).show();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
    }


}