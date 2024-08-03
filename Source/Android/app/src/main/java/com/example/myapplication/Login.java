package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;


import com.example.myapplication.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class Login extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Đăng nhập"));
        tabLayout.addTab(tabLayout.newTab().setText("Đăng ký"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager,getLifecycle());
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //đổi màu chữ của tab
//        tabLayout.setTabTextColors(
//                ContextCompat.getColor(this, R.color.tab_unselected_text_color),
//                ContextCompat.getColor(this, R.color.tab_selected_text_color)
//        );

    }

}