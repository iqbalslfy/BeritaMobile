package com.samsung.muhammad.polisi.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.adapter.TabsPagerAdapter;
import com.samsung.muhammad.polisi.fragment.HomeFragment;
import com.samsung.muhammad.polisi.fragment.PengaduanFragment;
import com.samsung.muhammad.polisi.fragment.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainPageActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNav;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);

        setupViewPager(viewPager);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home: {
                        viewPager.setCurrentItem(0);
                        break;
                    }
                    case R.id.action_pengaduan:{
                        viewPager.setCurrentItem(1);
                        break;
                    }
                    case R.id.action_profile:{
                        viewPager.setCurrentItem(2);
                        break;
                    }
                }
                return true;
            }
        });

        viewPager.setOffscreenPageLimit(2);
    }

    // setup viewpager for tab
    private void setupViewPager(ViewPager viewPager){
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new PengaduanFragment(), "PENGADUAN");
        adapter.addFragment(new ProfileFragment(), "PROFILE");
        viewPager.setAdapter(adapter);
    }
}
