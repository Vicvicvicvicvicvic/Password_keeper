package com.example.passcodekeeper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainViewPagerAdapter extends FragmentStateAdapter {

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new RemoveFragment();
            case 2:
                return new generator_Fragment();
            case 3:
                return new SendFragment();
            default:
                return new AddFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

