package hehvph21007.poly.comicpoly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import hehvph21007.poly.comicpoly.Adapter.viewpager2.ViewPager2Adapter;
import hehvph21007.poly.comicpoly.models.SharedPreferencesUtil;
import hehvph21007.poly.comicpoly.models.UserDTO;


public class HomeActivity extends AppCompatActivity {
    private Toolbar Tbr;
    private ViewPager2 viewPager2;
 // Biến để lưu thông tin người dùng
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = HomeActivity.this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String base64 = preferences.getString("userDTO", null);
        Log.d("HOME", "Base64: " + base64);
        UserDTO userDTO = SharedPreferencesUtil.getObject(this, "userDTO", UserDTO.class);
        Log.d("HOME", "onCreate: " + userDTO);


        Tbr = findViewById(R.id.id_tollBar);
        viewPager2 = findViewById(R.id.view_pager2);

        // Thiết lập Toolbar
        setSupportActionBar(Tbr);
        Tbr.setTitle("Comic Manager");
        Tbr.setTitleTextColor(Color.WHITE);

        // Thiết lập ViewPager
        ViewPager2Adapter adapter = new ViewPager2Adapter(this, userDTO);
        viewPager2.setAdapter(adapter);

        // Thiết lập Bottom Navigation
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.home_item, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.baseline_favorite_24, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.presonal_info, R.color.color_tab_3);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);


//// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);

// Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

// Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);


// Enable / disable item & set disable color
        bottomNavigation.enableItemAtPosition(3);
        bottomNavigation.disableItemAtPosition(3);
        bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));

// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position){
                    case 0:
                        viewPager2.setCurrentItem(0);
                        Tbr.setTitle("Comic Manager");
                        Tbr.setTitleTextColor(Color.WHITE);
                        Tbr.setNavigationIcon(R.drawable.home_item);

                        break;
                    case 1:
                        viewPager2.setCurrentItem(1);
                        Tbr.setTitle("Yêu Thích");
                        Tbr.setTitleTextColor(Color.WHITE);
                        Tbr.setNavigationIcon(R.drawable.baseline_favorite_24);
                        break;
                    case 2:
                        viewPager2.setCurrentItem(2);
                        Tbr.setTitle("Tài Khoản");
                        Tbr.setTitleTextColor(Color.WHITE);
                        Tbr.setNavigationIcon(R.drawable.account_item);
                        break;
                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }


}