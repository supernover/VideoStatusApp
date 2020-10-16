package com.video.status_downloader.supernover;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.video.status_downloader.supernover.Fragment.video2Fragment;
import com.video.status_downloader.supernover.Fragment.video4Fragment;
import com.video.status_downloader.supernover.Fragment.video5Fragment;
import com.video.status_downloader.supernover.Fragment.image1Fragment;
import com.video.status_downloader.supernover.Fragment.image2Fragment;
import com.video.status_downloader.supernover.Fragment.image3Fragment;
import com.video.status_downloader.supernover.Fragment.video1Fragment;
import com.video.status_downloader.supernover.Fragment.video3Fragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new video1Fragment();
        }
        else if (position == 1)
        {
            fragment = new video2Fragment();
        }
        else if (position == 2)
        {
            fragment = new video3Fragment();
        }else if (position == 3)
        {

            fragment = new video4Fragment();
        }else if (position == 4)
        {

            fragment = new video5Fragment();
        }else if (position == 5)
        {

            fragment = new image1Fragment();
        }else if (position == 6)
        {

            fragment = new image2Fragment();
        }else if (position == 7)
        {

            fragment = new image3Fragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Mehandi 1";
        }
        else if (position == 1)
        {
            title = "Mehandi 2";
        }
        else if (position == 2)
        {
            title = "Mehandi 3";
        }else if (position == 3)
        {
            title = "Mehandi 4";
        }else if (position == 4)
        {
            title = "Mehandi 5";
        }else if (position == 5)
        {
            title = "Mehandi 6";
        }else if (position == 6)
        {
            title = "Mehandi 7";
        }else if (position == 7)
        {
            title = "Mehandi 8";
        }
        return title;
    }
}