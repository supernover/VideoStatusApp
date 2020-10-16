package com.video.status_downloader.supernover;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.video.status_downloader.supernover.Fragment.Image;
import com.video.status_downloader.supernover.Fragment.StatusDown;
import com.video.status_downloader.supernover.Fragment.WalletFragment;

import java.lang.reflect.Method;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    boolean doubleBackToExitPressedOnce = false;
    CardView bottomCard;
    //This is our viewPager
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private Button whatsapp;
    //Fragments


  //  Video_frag video;
    MainFragment mainFragment;
    Image image;
    StatusDown statusdown;
    WalletFragment walletFragment;
    MenuItem prevMenuItem;
    String name;
    private FirebaseApp fbApp;
    private FirebaseDatabase fbDB;
    private FirebaseApp secondaryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("1:512563959432:android:97ead46d346c54c22a313d") // Required for Analytics.
//                .setApiKey("AIzaSyAUIvaOQ4g0pkvGfxmPn-ugMstvgbvZmWc") // Required for Auth.
//                .setDatabaseUrl("https://rbx-now.firebaseio.com/").build() ;// Required for RTDB.
//
//
//
//
//        boolean hasBeenInitialized=false;
//        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(this);
//        FirebaseApp finestayApp = null;
//        for(FirebaseApp app : firebaseApps){
//            if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
//                hasBeenInitialized=true;
//                finestayApp = app;
//            }
//        }
//
//        if(!hasBeenInitialized) {
//            finestayApp = FirebaseApp.initializeApp(this, options);
//        }
        whatsapp = (Button) findViewById(R.id.whatsapp);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+91 8849307705";
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = MainActivity.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(MainActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps(this);

// Delete "secondary" if it exists
        for (FirebaseApp app : firebaseAppList) {
            if (app.getName().equals("secondary")) {
                app.delete(); // found "secondary". Delete it
                break;
            }
        }

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setApplicationId("1:52697229911:android:d6bd8be9c6ff6a6643d112") // Required for Analytics.
                .setApiKey("AIzaSyC44MGjBqirNGEz87q9g5T9N8EqLjajFcQ") // Required for Auth.
                .setDatabaseUrl("https://apikeystatusapp.firebaseio.com")
                .build() ;// Required for RTDB.


// Initialize
        secondaryDatabase = FirebaseApp.initializeApp(this, firebaseOptions, "secondary");



      //  FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(fbApp);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        linearLayout = (LinearLayout )findViewById(R.id.messege);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance(secondaryDatabase).getReference("APP_NAME");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    if(dataSnapshot.child(getString(R.string.appdata)).getValue().equals(getPackageName())){
                       // Toast.makeText(MainActivity.this, "yess", Toast.LENGTH_LONG).show();

                         viewPager.setVisibility(View.VISIBLE);
                    }
                    else {
                          viewPager.setVisibility(View.INVISIBLE);
                          linearLayout.setVisibility(View.VISIBLE);
                      //  Toast.makeText(MainActivity.this, "no", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


        final BubbleNavigationLinearView bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
        //    bubbleNavigationLinearView.setTypeface(Typeface.createFromAsset(getAssets(), String.valueOf(R.font.amaranth)));

        //  bubbleNavigationLinearView.setBadgeValue(0, "70");
        //  bubbleNavigationLinearView.setBadgeValue(1, null); //invisible badge
        //   bubbleNavigationLinearView.setBadgeValue(2, "99");
        //    bubbleNavigationLinearView.setBadgeValue(3, "20");

        final ViewPager viewPager = findViewById(R.id.view_pager);
        //   viewPager.setAdapter(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                bubbleNavigationLinearView.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isStoragePermissionGranted()) {
            Toast.makeText(this, "Please allow Permission to continue..", Toast.LENGTH_SHORT).show();
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else if (isStoragePermissionGranted()) {

        }

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {

                viewPager.setCurrentItem(position, true);

                switch (position) {
                    case R.id.l_item_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.l_item_image:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.l_item_status:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.l_item_earn:
                        viewPager.setCurrentItem(3);
                        break;

                }
            }
        });

        setupViewPager(viewPager);



    }



    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("PPP", "Permission is granted");
                //startActivity();
                return true;
            } else {

                Log.v("PPP", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("PPP", "Permission is granted");
            //startActivity();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        //openFragment();
                    }

                } else {
                    /*ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            1);*/
                    finish();
                }
                return;
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        BottomNavigationHellper adapter = new BottomNavigationHellper(getSupportFragmentManager());

        //  video=new Video_frag();
        mainFragment = new MainFragment();
        image =new Image();
        statusdown =new StatusDown();
        walletFragment =new WalletFragment();


        //   adapter.addFragment(video);
        adapter.addFragment(mainFragment);
        adapter.addFragment(image);
        adapter.addFragment(statusdown);
        adapter.addFragment(walletFragment);

        viewPager.setAdapter(adapter);
    }


    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
       // Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        TastyToast.makeText(MainActivity.this, "Please click BACK again to exit", TastyToast.LENGTH_SHORT, TastyToast.INFO);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
