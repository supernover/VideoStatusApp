package com.video.status_downloader.supernover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;


import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.luolc.emojirain.EmojiRainLayout;
import com.sdsmdg.tastytoast.TastyToast;
import com.victor.loading.rotate.RotateLoading;


public class Video_play extends AppCompatActivity {
    CardView cardView;
    TextView nameTxt;
    Button share,downbt;
    VideoView video;
    ImageView imageView,rupee,coin;
    private InterstitialAd interstitialAd;
    ProgressBar progressBar;
    ImageView mPlayButton;
    boolean isplaing;
    private DownloadManager dm;

    private long queueid;
    AdView adView;
    private int currentpoint,previouspoint;
    private String name,email,number,password;
    private FirebaseDatabase database;
    private DatabaseReference ref,userInfoDatabase;

    String firebaseId;
    MediaController mediaController;
    CountDownTimer countDownTimer;
    TextView mTextField;
    private RotateLoading rotateLoading;
    BrodcastReceiver receiver;
    private RewardedVideoAd mRewardedVideoAd;
    private static final String WHATSAPP_STATUSES_LOCATION = "/drawable/shareimage";
    private Uri imageUri;
    private Intent intent;
    private File file;
    private DatabaseReference walletRef;
    private long walletPoints;
    private DatabaseReference walletref;
    private long wallet;
    private EmojiRainLayout mContainer;
    String bannerid;
    String appid;
    String fullscreenad;
    private DatabaseReference mdataRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);



        mContainer = (EmojiRainLayout) findViewById(R.id.group_emoji_container);

        // add emoji sources
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);

        // set emojis per flow, default 6
        mContainer.setPer(15);

        // set total duration in milliseconds, default 8000
        mContainer.setDuration(2500);

        // set average drop duration in milliseconds, default 2400
        mContainer.setDropDuration(2400);

        // set drop frequency in milliseconds, default 500
        mContainer.setDropFrequency(500);



        mdataRef = FirebaseDatabase.getInstance().getReference("Admob");

        mdataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bannerid = String.valueOf(dataSnapshot.child("banner").getValue().toString());
                // String webView = dataSnapshot.getValue(String.class);
                appid = String.valueOf(dataSnapshot.child("appid").getValue().toString());
                MobileAds.initialize(Video_play.this,
                        appid);
                fullscreenad = String.valueOf(dataSnapshot.child("interstial").getValue().toString());
                // String webView = dataSnapshot.getValue(String.class);
                interstitialAd = new InterstitialAd(Video_play.this);
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
                adView = new AdView(Video_play.this);
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




      // downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

       downbt = (Button) findViewById(R.id.download);
       share = (Button) findViewById(R.id.share);
        rupee = (ImageView) findViewById(R.id.rupee);
        coin = (ImageView) findViewById(R.id.coinn);
        rupee.setVisibility(View.INVISIBLE);
       share.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

            //   Intent shareIntent = new Intent(Intent.ACTION_SEND);
           //    shareIntent.setType("text/plain");
           //    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
           //    String shareMessage= "\nHey I found Best App For Mehndi Design App Download It\n\n";
           //    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
           //    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            //   startActivity(Intent.createChooser(shareIntent, "choose one"));

               Boolean check = hasCurrentVideo();
               if (check) {
                   shareToDefault();
               } else {

                   startDownload();
               }


           }
       });


      /** BrodcastReceiver receiver = new BrodcastReceiver(){

           @Override
           public void onReceive ( Context context, Intent intent) {

               String action = intent.getAction();
               if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){

                   DownloadManager.Query req_query = new DownloadManager.Query();
                   req_query.setFilterById(queueid);

                   final Cursor c = dm.query(req_query);

                   if (c.moveToFirst()){

                       int colomIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);

                       if (DownloadManager.STATUS_SUCCESSFUL==c.getInt(colomIndex))
                       {
                           TastyToast.makeText(Video_play.this, "Download Finished", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);


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



        Bundle bb = getIntent().getExtras();
        String vtex = bb.getString("title");


        progressBar = (ProgressBar) findViewById(R.id.progressbar);


       // isplaing = false;

        video = (VideoView)findViewById(R.id.video_view);

        mediaController = new MediaController(this);

        progressBar.setVisibility(View.VISIBLE);





        Bundle b = getIntent().getExtras();
        String id = b.getString("id");

        video.setVideoURI(Uri.parse(id));
        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);
        video.start();



        if (video.isPlaying()){
           progressBar.setVisibility(View.INVISIBLE);


        }

        FirebaseUser userpoint = FirebaseAuth.getInstance().getCurrentUser();
        walletref = FirebaseDatabase.getInstance().getReference("/users/"+userpoint.getUid()+"/Wallet/points");
        walletref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wallet = (long)dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        walletRef = FirebaseDatabase.getInstance().getReference("/users/"+user.getUid()+"/Wallet");

        walletRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                walletPoints = (long)dataSnapshot.child("points").getValue();
                //  walletRs = (long)dataSnapshot.child("rs").getValue();
              //  currentPoint.setText(String.valueOf(walletPoints ));

                //  ucpoints = Integer.parseInt(String.valueOf(walletPoints));

                //    ucpbg.setText(ucpoints/100 + "RS");
                //    walletBalanceRs.setText(String.valueOf(walletRs));
                //  progress.dismiss();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final  MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.coindrop);


        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        progressBar.setVisibility(View.GONE);

                        mTextField = (TextView) findViewById(R.id.count);
                        new CountDownTimer(60000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                mTextField.setText("00:"+"" + millisUntilFinished / 1000);

                            }

                            public void onFinish() {
                                mTextField.setText("done!");
                                mTextField.setVisibility(View.INVISIBLE);
                                rupee.setVisibility(View.VISIBLE);
                                mContainer.startDropping();
                                TastyToast.makeText(Video_play.this, "tap on coin", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);


                                coin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (interstitialAd.isLoaded()) {

                                            interstitialAd.show();
                                        }
                                        coin.setEnabled(false);
                                        mediaPlayer.start();
                                        currentpoint =  + Integer.parseInt(getString(R.string.videopoints));
                                        savingnewdataFirebase();
                                        TastyToast.makeText(Video_play.this, getString(R.string.videopoints) +" coins added", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);


                                        // Toast.makeText(getActivity(), "3 point added", Toast.LENGTH_SHORT).show();

                                    }
                                });



                            }
                        }.start();
                        mp.start();

                    }
                });
            }
        });

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

    private void startDownload() {
        Bundle b = getIntent().getExtras();
        String id = b.getString("id");
        Uri uri = Uri.parse(String.valueOf(id));
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();

        }

        dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);


        Bundle bb = getIntent().getExtras();
        String vtex = bb.getString("title");
        //   File mydownload = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/vid cash");


        File dir = new File("storage/Video Status 2020");
        try{
            if(dir.mkdir()) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory is not created");
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        File diro = new File(Environment.getExternalStorageDirectory().getPath() + "/Video Status 2020/");
        diro.mkdirs();

        request.setDestinationInExternalPublicDir(String.valueOf(diro),vtex + ".mp4");

        // request.setDestinationInExternalFilesDir(Video_play.this, DIRECTORY_DOWNLOADS,vtex+".mp4");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        TastyToast.makeText(Video_play.this, "Downloading start..", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        queueid = dm.enqueue(request);

        BrodcastReceiver receiver = new BrodcastReceiver(){

            @Override
            public void onReceive ( Context context, Intent intent) {

                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){

                    DownloadManager.Query req_query = new DownloadManager.Query();
                    req_query.setFilterById(queueid);

                    final Cursor c = dm.query(req_query);

                    if (c.moveToFirst()){

                        int colomIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);

                        if (DownloadManager.STATUS_SUCCESSFUL==c.getInt(colomIndex))
                        {
                           // TastyToast.makeText(Video_play.this, "Download Finished", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                           shareToDefault();

                        }
                    }
                }

            }

        };
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
        String type = "video/*";
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();

        }
        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        Uri uri = FileProvider.getUriForFile(Video_play.this, getString(R.string.authority), file);
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


       public  void download_Click (View v){
           Bundle b = getIntent().getExtras();
           String id = b.getString("id");
           Uri uri = Uri.parse(String.valueOf(id));

           if (interstitialAd.isLoaded()) {
               interstitialAd.show();

           }
        dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);


           Bundle bb = getIntent().getExtras();
           String vtex = bb.getString("title");
        //   File mydownload = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/vid cash");


           File dir = new File("storage/Video Status 2020");
           try{
               if(dir.mkdir()) {
                   System.out.println("Directory created");
               } else {
                   System.out.println("Directory is not created");
               }
           }catch(Exception e){
               e.printStackTrace();
           }


           File diro = new File(Environment.getExternalStorageDirectory().getPath() + "/Video Status 2020/");
           diro.mkdirs();

           request.setDestinationInExternalPublicDir(String.valueOf(diro),vtex + ".mp4");

         // request.setDestinationInExternalFilesDir(Video_play.this, DIRECTORY_DOWNLOADS,vtex+".mp4");
           request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
           TastyToast.makeText(Video_play.this, "Downloading start..", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
           queueid = dm.enqueue(request);

       }


    private boolean hasCurrentVideo() {
        // Create a path where we will place our video in the user's
        // public pictures directory and check if the file exists.  If
        // external storage is not currently mounted this will think the
        // picture doesn't exist.
        Bundle bb = getIntent().getExtras();
        String vtex = bb.getString("title");
        File path = Environment.getExternalStoragePublicDirectory(
                "/storage/emulated/0/Video Status 2020");
        file = new File(path, vtex + ".mp4");
        if(file.exists()){
         //   Toast.makeText(this, "cheee", Toast.LENGTH_SHORT).show();

        }
//Do something
    else{
            Toast.makeText(this, "Downloading..", Toast.LENGTH_SHORT).show();

        }
        return file.exists();
    }







    private abstract class BrodcastReceiver extends BroadcastReceiver {
        public abstract void onReceive(Context context, Intent intent);
    }


    private void savingnewdataFirebase() {
        walletref.setValue(wallet+currentpoint);

    }

}
