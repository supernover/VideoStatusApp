package com.video.status_downloader.supernover;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.video.status_downloader.supernover.Model.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {
    private DatabaseReference mdataRef;
    private long wallet;
    String slider1,slider2,slider3,slider4,slider5,slider6,slider7,slider8,slider9;
    String tslider1,tslider2,tslider3,tslider4,tslider5,tslider6,tslider7,tslider8,tslider9;
    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();



    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        mdataRef = FirebaseDatabase.getInstance().getReference("VideoSlider").child("VideoLink");
        mdataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                slider1 = String.valueOf(dataSnapshot.child("video1").getValue().toString());
                slider2 = String.valueOf(dataSnapshot.child("video2").getValue().toString());
                slider3 = String.valueOf(dataSnapshot.child("video3").getValue().toString());
                slider4 = String.valueOf(dataSnapshot.child("video4").getValue().toString());
                slider5 = String.valueOf(dataSnapshot.child("video5").getValue().toString());
                slider6 = String.valueOf(dataSnapshot.child("video6").getValue().toString());
                slider7 = String.valueOf(dataSnapshot.child("video7").getValue().toString());
                slider8 = String.valueOf(dataSnapshot.child("video8").getValue().toString());
                slider9 = String.valueOf(dataSnapshot.child("video9").getValue().toString());

                // uri.setText(webView);
                // webviewthis.loadUrl(webView);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mdataRef = FirebaseDatabase.getInstance().getReference("VideoSlider").child("VideoTitle");
        mdataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tslider1 = String.valueOf(dataSnapshot.child("title1").getValue().toString());
                tslider2 = String.valueOf(dataSnapshot.child("title2").getValue().toString());
                tslider3 = String.valueOf(dataSnapshot.child("title3").getValue().toString());
                tslider4 = String.valueOf(dataSnapshot.child("title4").getValue().toString());
                tslider5 = String.valueOf(dataSnapshot.child("title5").getValue().toString());
                tslider6 = String.valueOf(dataSnapshot.child("title6").getValue().toString());
                tslider7 = String.valueOf(dataSnapshot.child("title7").getValue().toString());
                tslider8 = String.valueOf(dataSnapshot.child("title8").getValue().toString());
                tslider9 = String.valueOf(dataSnapshot.child("title9").getValue().toString());

                // uri.setText(webView);
                // webviewthis.loadUrl(webView);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(context)
                .load(sliderItem.getImageUrl())
               // .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            private Object MainFragment;

            @Override
            public void onClick(View v) {


                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}
