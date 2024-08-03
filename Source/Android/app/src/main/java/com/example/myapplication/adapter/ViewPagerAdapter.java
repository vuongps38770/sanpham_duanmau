package com.example.myapplication.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.SigninFg;
import com.example.myapplication.Signupfg;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new SigninFg();
            case 1:
                return new Signupfg();
            default:
                return new SigninFg();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
