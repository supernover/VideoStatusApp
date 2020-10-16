package com.video.status_downloader.supernover;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.BubbleToggleView;
import com.google.android.gms.ads.AdView;
import com.smarteist.autoimageslider.SliderView;

public class How_it_works extends AppCompatActivity {
    AdView adView1;
    SliderView sliderView;
    private SliderAdapterExample adapter;
    BubbleToggleView bubbleToggleView;
    BubbleNavigationLinearView bubbleNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_works);



    }



}
