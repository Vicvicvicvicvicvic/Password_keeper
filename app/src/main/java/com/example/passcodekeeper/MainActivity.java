package com.example.passcodekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.add_fragment:
                    Intent add_page = new Intent(MainActivity.this, AddPasswordActivity.class);
                    startActivity(add_page);
                    return true;
                case R.id.remove_fragment:
                    Intent remove_page = new Intent(MainActivity.this, SavedPasswordsActivity.class);
                    startActivity(remove_page);
                    return true;
                case R.id.send_fragment:
                    Intent send_page = new Intent(MainActivity.this, SendActivity.class);
                    startActivity(send_page);
                    return true;
                case R.id.generator_fragment:
                    Intent generate_page = new Intent(MainActivity.this, PasswordGeneratorActivity.class);
                    startActivity(generate_page);
                    return true;
            }
            return false;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }
}
