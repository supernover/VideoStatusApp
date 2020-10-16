package com.video.status_downloader.supernover.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
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
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.video.status_downloader.supernover.How_it_works;
import com.video.status_downloader.supernover.R;
import com.video.status_downloader.supernover.Redeem;


public class WalletFragment extends Fragment {
    LinearLayout redeem,howto,rate,share,policy;
    private int currentpoint,previouspoint;
    private String name,email,number,password;
    private FirebaseDatabase database;
    private DatabaseReference ref,userInfoDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private TickerView currentPoint;
    String firebaseId;
    AdView adView1;
    private InterstitialAd interstitialAd;
    private DatabaseReference mdataRef;
    private TextView ucpbg,rbxpoint;
    private int ucpoints;
    private DatabaseReference walletRef;
    private long walletPoints;
    private DatabaseReference walletref;
    private long wallet;
    String bannerid;
    String appid;
    String fullscreenad;
    AdView adView;
    public WalletFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        currentPoint = (TickerView) view.findViewById(R.id.userpointss);
        currentPoint.setCharacterLists(TickerUtils.provideNumberList());

        redeem = (LinearLayout) view.findViewById(R.id.redeem);
        howto = (LinearLayout) view.findViewById(R.id.howto);
        rate = (LinearLayout) view.findViewById(R.id.rateus);
        share = (LinearLayout) view.findViewById(R.id.share);
        policy = (LinearLayout) view.findViewById(R.id.privacy);

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

                View vv = view.findViewById(R.id.idadmob);
                adView = new AdView(getContext());
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
                currentPoint.setText(String.valueOf(walletPoints ));

              //  ucpoints = Integer.parseInt(String.valueOf(walletPoints));

            //    ucpbg.setText(ucpoints/100 + "RS");
                //    walletBalanceRs.setText(String.valueOf(walletRs));
                //  progress.dismiss();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();

                }
                Intent intent = new Intent(getContext(), Redeem.class);
                startActivity(intent);
            }
        });

        howto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();

                }
                Intent intent = new Intent(getContext(), How_it_works.class);
                startActivity(intent);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();

                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getString(R.string.packegname)));
                startActivity(intent);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();

                }
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "My app name");

                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String strShareMessage = "\nHey!  I Got new amazing App For Download Video and Image Status\n\n";
                strShareMessage = strShareMessage + "https://play.google.com/store/apps/details?id=" + getString(R.string.packegname);

                i.putExtra(Intent.EXTRA_TEXT, strShareMessage);

                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();

                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cricproprivacy.blogspot.com/2019/03/privacy-policy.html?m=1"));
                startActivity(intent);

            }
        });


        return view;
    }

    private void savingnewdataFirebase() {
        walletref.setValue(wallet+currentpoint);

    }





}
