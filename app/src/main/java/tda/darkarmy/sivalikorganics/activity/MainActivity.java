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
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import tda.darkarmy.sivalikorganics.R;
import tda.darkarmy.sivalikorganics.fragments.HomeFragment;
import tda.darkarmy.sivalikorganics.fragments.NotificationFragment;
import tda.darkarmy.sivalikorganics.fragments.ProfileFragment;
import tda.darkarmy.sivalikorganics.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private static String ROLE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("ISLOGIN", false);
        ROLE = sharedPreferences.getString("ROLE", null);
        if(!isLogin){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        bind();
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
                fragmentClass = NotificationFragment.class;
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