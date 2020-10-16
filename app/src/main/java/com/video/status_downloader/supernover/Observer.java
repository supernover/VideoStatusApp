package com.video.status_downloader.supernover;

import android.content.Context;

public interface Observer {
    void update(final String value, Context context);
}