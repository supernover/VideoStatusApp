package com.video.status_downloader.supernover.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.video.status_downloader.supernover.ForceUpdateAsync;
import com.video.status_downloader.supernover.R;
import com.video.status_downloader.supernover.TabLayoutWhatsApp.WhatsAppPicture;
import com.video.status_downloader.supernover.TabLayoutWhatsApp.WhatsAppSaveStatuses;
import com.video.status_downloader.supernover.TabLayoutWhatsApp.WhatsAppVideos;

import java.util.ArrayList;
import java.util.List;


public class StatusDown extends Fragment {

    private final String TAG = "DrawerTAG";
    boolean doubleBackToExitPressedOnce = false;
    NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView imageView;

    public StatusDown() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statusdown, container, false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpagersave);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        //  TabLayout tabs = (TabLayout) view.findViewById(R.id.res);
        //  tabs.setupWithViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        imageView = (ImageView) view.findViewById(R.id.idmenu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });


        return view;
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_TEXT, "My app name");

                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        String strShareMessage = "\nHey!  I Got new amazing App For Download Video and Image Status\n\n";
                        strShareMessage = strShareMessage + "https://play.google.com/store/apps/details?id=" + getString(R.string.packegname);

                        i.putExtra(Intent.EXTRA_TEXT, strShareMessage);

                        i.setPackage("com.whatsapp");
                        startActivity(i);

                        return true;
                    case R.id.item2:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacylink)));
                        startActivity(intent2);

                        return true;

                    case R.id.item3:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getString(R.string.packegname)));
                        startActivity(intent);
                        return true;
                    default:
                }
                return false;
            }
        });

    }
    private void setupViewPager(ViewPager viewPager2) {

        ForceUpdateAsync forceUpdateAsync = new ForceUpdateAsync(getContext());
        forceUpdateAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        Adapterrr adapterrr = new Adapterrr(getChildFragmentManager());

        adapterrr.addFragment(new WhatsAppPicture(), "Picture");
        adapterrr.addFragment(new WhatsAppVideos(), "Videos");
        adapterrr.addFragment(new WhatsAppSaveStatuses(), "Saved");
        viewPager2.setAdapter(adapterrr);
       // viewPager2.setCurrentItem(0);
    }

    static class Adapterrr extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();



        public Adapterrr(FragmentManager fm) {
            super(fm);
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
