package com.video.status_downloader.supernover.Adaptor;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mklimek.frameviedoview.FrameVideoView;
import com.video.status_downloader.supernover.R;
import com.video.status_downloader.supernover.Video;

import java.util.List;

public class MyAdapter extends PagerAdapter {
    VideoView video;
    FrameVideoView frameVideoView;
    MediaController mediaController;
    ProgressBar progressBar;
    Context context;
    List<Video> videoList;
    LayoutInflater inflater;

    public MyAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.view_pager_item,container,false);

        video = (VideoView) view.findViewById(R.id.video_view);


        video.setVideoURI(Uri.parse(videoList.get(position).getUrl()));
   //     video.setMediaController(mediaController);
    //    mediaController.setAnchorView(video);
        video.start();
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        if (video.isPlaying()){
            progressBar.setVisibility(View.INVISIBLE);

        }


        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        progressBar.setVisibility(View.GONE);





                    }
                });
            }
        });


        container.addView(view);
        return view;
    }


}
