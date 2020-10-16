package com.video.status_downloader.supernover.Listener;

import com.video.status_downloader.supernover.Video;

import java.util.List;

public interface IFirebaseLoadDone {

    void  onFirebaseLoadSucses(List<Video>videoList);
    void   onFirebaseLoadFail (String message);
}
