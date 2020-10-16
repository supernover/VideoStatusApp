package com.video.status_downloader.supernover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joooonho.SelectableRoundedImageView;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.io.File;


public class ImagesStatusPrivew extends AppCompatActivity {

    SelectableRoundedImageView selectableRoundedImageView;
    ImageButton share,down;
    private DownloadManager dm;
    private long queueid;
    private File file;
    AdView adView;
    private InterstitialAd interstitialAd;
    String bannerid;
    String appid;
    String fullscreenad;
    private DatabaseReference mdataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagestatuspriveu);

        selectableRoundedImageView = (SelectableRoundedImageView) findViewById(R.id.statusimageView);
        share = (ImageButton) findViewById(R.id.img_btn_share);
        down = (ImageButton) findViewById(R.id.img_btn_download);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = hasCurrentVideo();
                if (check) {
                    shareToDefault();
                } else {

                    startDownload2();
                }

            }
        });




        mdataRef = FirebaseDatabase.getInstance().getReference("Admob");

        mdataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bannerid = String.valueOf(dataSnapshot.child("banner").getValue().toString());
                // String webView = dataSnapshot.getValue(String.class);
                appid = String.valueOf(dataSnapshot.child("appid").getValue().toString());
                MobileAds.initialize(ImagesStatusPrivew.this,
                        appid);
                fullscreenad = String.valueOf(dataSnapshot.child("interstial").getValue().toString());
                // String webView = dataSnapshot.getValue(String.class);
                interstitialAd = new InterstitialAd(ImagesStatusPrivew.this);
                interstitialAd.setAdUnitId(fullscreenad);
                interstitialAd.loadAd(new AdRequest.Builder().build());
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {

                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {


                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {

                        interstitialAd.loadAd(new AdRequest.Builder().build());

                    }
                });

                View vv = findViewById(R.id.idadmob);
                adView = new AdView(ImagesStatusPrivew.this);
                adView.setAdSize(AdSize.BANNER);
                ((RelativeLayout) vv).addView(adView);
                adView.setAdUnitId(bannerid);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bundle b = getIntent().getExtras();
        String imageurl = b.getString("image");

        Picasso.get().load(imageurl).into(selectableRoundedImageView);

       /**BrodcastReceiver receiver = new BrodcastReceiver(){

           @Override
           public void onReceive ( Context context, Intent intent) {

               String action = intent.getAction();
               if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){

                   DownloadManager.Query req_query = new DownloadManager.Query();
                   req_query.setFilterById(queueid);

                   Cursor c = dm.query(req_query);

                   if (c.moveToFirst()){

                       int colomIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);

                       if (DownloadManager.STATUS_SUCCESSFUL==c.getInt(colomIndex))
                       {
                           TastyToast.makeText(ImagesStatusPrivew.this, "Download Finished", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);


                       }
                   }
               }

           }

       };

        registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));**/
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @SuppressLint("NewApi")
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query req_query = new DownloadManager.Query();
                    req_query.setFilterById(queueid);
                    Cursor c = dm.query(req_query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {
                            Toast.makeText(getBaseContext(), "Download Finished", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));



        String TAG = "Permsission : ";
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return ;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return ;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return ;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String TAG = "Permsission : ";
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);

            File mFolder = new File(Environment.getExternalStorageDirectory(), "vid cash");
            if (!mFolder.exists()) {
                boolean b =  mFolder.mkdirs();
            }
        }
    }


    private void shareToDefault() {
        String type = "image/*";
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();

        }
        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        Uri uri = FileProvider.getUriForFile(ImagesStatusPrivew.this, getString(R.string.authority), file);
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        PackageManager packageManager = getPackageManager();
        if (share.resolveActivity(packageManager) != null) {
            startActivity(share);
            // Broadcast the Intent.
            startActivity(Intent.createChooser(share, "Share to"));

        } else Toast.makeText(this, R.string.try_later, Toast.LENGTH_SHORT).show();


    }

    private void startDownload() {
        Bundle b = getIntent().getExtras();
        String id = b.getString("image");
        Uri uri = Uri.parse(String.valueOf(id));

        if (interstitialAd.isLoaded()) {
            interstitialAd.show();

        }

        dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);


        Bundle bb = getIntent().getExtras();
        String vtex = bb.getString("title");
        //   File mydownload = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/vid cash");


        File dir = new File("storage/Supernover Design");
        try{
            if(dir.mkdir()) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory is not created");
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        File diro = new File(Environment.getExternalStorageDirectory().getPath() + "/Supernover Design/");
        diro.mkdirs();

        request.setDestinationInExternalPublicDir(String.valueOf(diro),vtex + ".jpg");

        // request.setDestinationInExternalFilesDir(Video_play.this, DIRECTORY_DOWNLOADS,vtex+".mp4");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        TastyToast.makeText(ImagesStatusPrivew.this, "Downloading start..", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        queueid = dm.enqueue(request);
    }

    private void startDownload2() {
        Bundle b = getIntent().getExtras();
        String id = b.getString("image");
        Uri uri = Uri.parse(String.valueOf(id));
        dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);


        Bundle bb = getIntent().getExtras();
        String vtex = bb.getString("title");
        //   File mydownload = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/vid cash");


        File dir = new File("storage/Video Status ");
        try{
            if(dir.mkdir()) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory is not created");
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        File diro = new File(Environment.getExternalStorageDirectory().getPath() + "/Video Status /");
        diro.mkdirs();

        request.setDestinationInExternalPublicDir(String.valueOf(diro),vtex + ".jpg");

        // request.setDestinationInExternalFilesDir(Video_play.this, DIRECTORY_DOWNLOADS,vtex+".mp4");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        TastyToast.makeText(ImagesStatusPrivew.this, "Downloading start..", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        queueid = dm.enqueue(request);
        shareToDefault();
    }

    private abstract class BrodcastReceiver extends BroadcastReceiver {
        public abstract void onReceive(Context context, Intent intent);
    }

    private boolean hasCurrentVideo() {
        // Create a path where we will place our video in the user's
        // public pictures directory and check if the file exists.  If
        // external storage is not currently mounted this will think the
        // picture doesn't exist.
        Bundle bb = getIntent().getExtras();
        String vtex = bb.getString("title");
        File path = Environment.getExternalStoragePublicDirectory(
                "/storage/emulated/0/Video Status ");
        file = new File(path, vtex + ".jpg");
        if(file.exists()){
          //  Toast.makeText(this, "cheee", Toast.LENGTH_SHORT).show();

        }

        //Do something
        else{
            Toast.makeText(this, "Downloading..", Toast.LENGTH_SHORT).show();

        }
        return file.exists();
    }



}
