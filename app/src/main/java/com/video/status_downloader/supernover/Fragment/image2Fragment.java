package com.video.status_downloader.supernover.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;
import com.video.status_downloader.supernover.ImagesStatusPrivew;
import com.video.status_downloader.supernover.R;
import com.video.status_downloader.supernover.Video;
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


public class image2Fragment extends Fragment {
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
    CountDownTimer counterTimer;
    private int currentpoint,previouspoint;
    private String name,email,number,password;
    private FirebaseDatabase database;
    private DatabaseReference ref,userInfoDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private TextView currentPoint;
    String firebaseId;
    private int adscount = 0;

    String bannerid;
    String appid;
    String fullscreenad;
    private DatabaseReference mdataRef;
    private FirebaseRecyclerAdapter<Video, image3Fragment.NewsViewHolder> mPeopleRVAdapter;
    public image2Fragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cat7, container, false);


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



        mDatabase = FirebaseDatabase.getInstance().getReference("Image Status").child("Love");
        mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) view.findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference("Image Status").child("Love");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mPeopleRV.setLayoutManager(mLayoutManager);
      //  mPeopleRV.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Video>().setQuery(personsQuery, Video.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Video, image3Fragment.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(final image3Fragment.NewsViewHolder holder, final int position, final Video model) {
                holder.setTitle(model.getTitle());
                //holder.setDesc(model.getDesc());
                holder.setImage(getContext(), model.getImage());
                YoYo.with(Techniques.ZoomIn).playOn(holder.cardView);
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String image = model.getImage();
                        Intent intent = new Intent(getContext(), ImagesStatusPrivew.class);
                        intent.putExtra("image", image);
                        final String title = model.getTitle();
                        intent.putExtra("title",title);

                        // final String durl = model.getDurl();
                        //  intent.putExtra("downlod", durl);


                        startActivity(intent);

                        adscount = adscount +1 ;



                        if (adscount == 3){

                            if (interstitialAd.isLoaded()) {
                                interstitialAd.show();
                                adscount = 0;
                            }
                        }

                    }


                });





            }

            @Override
            public image3Fragment.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.image_model, parent, false);
                rotateLoading.stop();
                return new image3Fragment.NewsViewHolder(view);
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
    //  private  void requestStoragePermission() {
    //     if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){

    //      new AlertDialog.Builder(getActivity())
    //             .setTitle("Permission needed")
    //            .setMessage("this permission is needed")
    //            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
    //               @Override
    //              public void onClick(DialogInterface dialog, int which) {
    //                 ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

    //            }
    //        })
    //        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
    //          @Override
    //          public void onClick(DialogInterface dialog, int which) {
    //            dialog.dismiss();
    //       }
    //   })
    //     .create().show();

    //  } else {
    //       ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    //   }
    //  }

    // @Override
    //  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //  if (requestCode == STORAGE_PERMISSION_CODE) {
    //     if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

    //       Toast.makeText(getActivity(),"permission GRANTED", Toast.LENGTH_LONG).show();
    //    }else  {
    //       Toast.makeText(getActivity(),"permission DENIED", Toast.LENGTH_SHORT).show();
    //   }
    // }
    // }

}
