package com.video.status_downloader.supernover;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.video.status_downloader.supernover.Fragment.Payment_frag;
import com.video.status_downloader.supernover.Fragment.Paypalfrag;

import java.util.ArrayList;
import java.util.List;

public class Redeem extends AppCompatActivity {
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        //  TabLayout tabs = (TabLayout) view.findViewById(R.id.res);
        //  tabs.setupWithViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.res);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {


        MainFragment.Adapter adapter = new MainFragment.Adapter(getSupportFragmentManager());
        adapter.addFragment(new Payment_frag(), "Paytm");
        //  adapter.addFragment(new pubgFragment(), "PUBG");
        adapter.addFragment(new Paypalfrag(), "Paypal");

        viewPager.setAdapter(adapter);



    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
