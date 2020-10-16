package com.video.status_downloader.supernover;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.smarteist.autoimageslider.SliderView;
import com.video.status_downloader.supernover.Fragment.video1Fragment;
import com.video.status_downloader.supernover.Fragment.video2Fragment;
import com.video.status_downloader.supernover.Fragment.video3Fragment;
import com.video.status_downloader.supernover.Fragment.video4Fragment;
import com.video.status_downloader.supernover.Fragment.video5Fragment;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

     TabLayout tabLayout;
     ImageView imageView;
    SliderView sliderView;
    int Totalcount;
    TickerView currentPoint;
    private DatabaseReference walletRef,mdataRef;
    private long walletPoints;
    private DatabaseReference walletref;
    private long wallet;
    String slider1,slider2,slider3,slider4,slider5,slider6,slider7,slider8,slider9;
    private SliderAdapterExample adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
      //  TabLayout tabs = (TabLayout) view.findViewById(R.id.res);
      //  tabs.setupWithViewPager(viewPager);
        currentPoint = (TickerView) view.findViewById(R.id.userpointss);
        currentPoint.setCharacterLists(TickerUtils.provideNumberList());



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


        tabLayout = (TabLayout) view.findViewById(R.id.res);
        tabLayout.setupWithViewPager(viewPager);

        imageView = (ImageView) view.findViewById(R.id.idmenu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        sliderView = (SliderView) view.findViewById(R.id.imageSlider);

        FirebaseDatabase.getInstance().getReference("VideoSlider").child("ImageLink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long linkcounts = dataSnapshot.getChildrenCount();
                Totalcount = linkcounts.intValue();

                sliderView.setSliderAdapter(new ImageSliderAdaptor(getContext(),Totalcount));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("VideoSlider").child("VideoLink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long linkcounts = dataSnapshot.getChildrenCount();
                Totalcount = linkcounts.intValue();

                sliderView.setSliderAdapter(new ImageSliderAdaptor(getContext(),Totalcount));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("VideoSlider").child("VideoTitle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long linkcounts = dataSnapshot.getChildrenCount();
                Totalcount = linkcounts.intValue();

                sliderView.setSliderAdapter(new ImageSliderAdaptor(getContext(),Totalcount));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_TEXT, "My app name");

                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        String strShareMessage = "\nHey!  I Got new amazing App For Download Video and Image Status\n\n";
                        strShareMessage = strShareMessage + "https://play.google.com/store/apps/details?id=" + getString(R.string.packegname);

                        i.putExtra(Intent.EXTRA_TEXT, strShareMessage);

                        i.setPackage("com.whatsapp");
                        startActivity(i);

                        return true;
                    case R.id.item2:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacylink)));
                        startActivity(intent2);

                        return true;

                    case R.id.item3:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getString(R.string.packegname)));
                        startActivity(intent);
                        return true;
                    default:
                }
                return false;
            }
        });

    }




    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new video1Fragment(), "Populer");
      //  adapter.addFragment(new pubgFragment(), "PUBG");
        adapter.addFragment(new video2Fragment(), "Love");
        adapter.addFragment(new video3Fragment(), "Funny");
        adapter.addFragment(new video4Fragment(), "Sad");
        adapter.addFragment(new video5Fragment(), "Memes");
        viewPager.setAdapter(adapter);



    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
