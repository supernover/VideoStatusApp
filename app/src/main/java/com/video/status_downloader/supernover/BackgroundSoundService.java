package com.video.status_downloader.supernover;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jorgesys on 02/02/2015.
 */

/* Add declaration of this service into the AndroidManifest.xml inside application tag*/

public class BackgroundSoundService extends Service {

    private static final String TAG = "BackgroundSoundService";
    MediaPlayer mediaPlayer;

    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "onBind()" );
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            mediaPlayer.setDataSource("https://ypvnxx00-a.akamaihd.net/downloads/ringtones/files/mp3/lo-ring-tone-49371.mp3");
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true); // Set looping
            mediaPlayer.setVolume(100,100);
            Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onCreate() , service started...");

        }
        catch (Exception exception){

            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return Service.START_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        Log.i(TAG, "onUnBind()");
        return null;
    }

    public void onStop() {
        Log.i(TAG, "onStop()");
    }
    public void onPause() {
        Log.i(TAG, "onPause()");
    }
    @Override
    public void onDestroy() {
        mediaPlayer.release();
        Toast.makeText(this, "Service stopped...", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate() , service stopped...");
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory()");
    }
}