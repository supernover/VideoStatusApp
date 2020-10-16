package com.video.status_downloader.supernover.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;
import com.video.status_downloader.supernover.R;
import com.video.status_downloader.supernover.Video;
import com.video.status_downloader.supernover.Video_play;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class video1Fragment extends Fragment {
    private RotateLoading rotateLoading;
    private int STORAGE_PERMISSION_CODE = 1;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    AdView adView1;
    private boolean doubleBackToExitPressedOnce = false;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView ;
    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    Button btlove,sad,funny,punjabi,gujrati,english,festival,party,inspiration,dialogu,emotional,sufi,devotional,greetin;
    ProgressDialog progressDialog;
    TextView viewcount;
    private InterstitialAd interstitialAd;
    //   private  int currentrupee,previousrupees;

    private int currentpoint,previouspoint;
    private String name,email,number,password;
    private FirebaseDatabase database;
    private DatabaseReference ref,userInfoDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    TextView currentPoint;
    String firebaseId;
    private int adscount = 0;
    String bannerid;
    String appid;
    String fullscreenad;
    private DatabaseReference mdataRef;

    private FirebaseRecyclerAdapter<Video, video1Fragment.NewsViewHolder> mPeopleRVAdapter;
    public video1Fragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cat1, container, false);

        mdataRef = FirebaseDatabase.getInstance().getReference("Admob");

        mdataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bannerid = String.valueOf(dataSnapshot.child("banner").getValue().toString());
                // String webView = dataSnapshot.getValue(String.class);
                appid = String.valueOf(dataSnapshot.child("appid").getValue().toString());
                MobileAds.initialize(getContext(),
                        appid);
                fullscreenad = String.valueOf(dataSnapshot.child("interstial").getValue().toString());
                // String webView = dataSnapshot.getValue(String.class);
                interstitialAd = new InterstitialAd(getContext());
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




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
        rotateLoading.start();

        if(!isConnected(getActivity())) buildDialog(getActivity()).show();
        else {
            // Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_SHORT).show();

        }




        mDatabase = FirebaseDatabase.getInstance().getReference("Video Status").child("Populer");
        mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) view.findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference("Video Status").child("Populer");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
       // StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
       // mPeopleRV.setLayoutManager(mLayoutManager);
        mPeopleRV.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Video>().setQuery(personsQuery, Video.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Video, video1Fragment.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(final video1Fragment.NewsViewHolder holder, final int position, final Video model) {
                YoYo.with(Techniques.ZoomIn).playOn(holder.cardView);
                holder.setTitle(model.getTitle());

                //holder.setDesc(model.getDesc());
                holder.setImage(getContext(), model.getImage());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = model.getUrl();
                        Intent intent = new Intent(getContext(), Video_play.class);
                        intent.putExtra("id", url);
                        final String title = model.getTitle();
                        intent.putExtra("title",title);


                        startActivity(intent);




                        adscount = adscount +1 ;



                        if (adscount == 2){

                            if (interstitialAd.isLoaded()) {
                                interstitialAd.show();
                                adscount = 0;
                            }
                        }



                    }


                });




            }

            @Override
            public video1Fragment.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.video_model, parent, false);
                rotateLoading.stop();
                return new video1Fragment.NewsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
    //    mPeopleRVAdapter.stopListening();


    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        View mView;
        public NewsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            cardView = (CardView) itemView.findViewById(R.id.cardrow);
        }
        public void setTitle(String title){

        }

        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.get().load(image).into(post_image);
        }


    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        return builder;
    }


}
