package com.video.status_downloader.supernover;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class ImageSliderAdaptor extends SliderViewAdapter<ImageSliderAdaptor.SliderViewHolder> {

  Context context;
  int setTotalcount;
  DatabaseReference mdataRef;
    String slider1,slider2,tslider1,tslider4,tslider5,tslider6,tslider7,tslider8,tslider9,tslider2,tslider3;

  public  ImageSliderAdaptor (Context context,int setTotalcount){
      this.setTotalcount = setTotalcount;

      this.context = context;
  }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, parent,false);

        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SliderViewHolder viewHolder, final int position) {
        FirebaseDatabase.getInstance().getReference("VideoSlider").child("ImageLink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                switch (position){
                    case 0:
                        slider1 = String.valueOf(dataSnapshot.child("image1").getValue().toString());
                     Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                     break;

                    case 1:
                        slider1 = String.valueOf(dataSnapshot.child("image2").getValue().toString());
                        Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                        break;

                    case 2:
                        slider1 = String.valueOf(dataSnapshot.child("image3").getValue().toString());
                        Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                        break;

                    case 3:
                        slider1 = String.valueOf(dataSnapshot.child("image4").getValue().toString());
                        Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                        break;

                    case 4:
                        slider1 = String.valueOf(dataSnapshot.child("image5").getValue().toString());
                        Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                        break;

                    case 5:
                        slider1 = String.valueOf(dataSnapshot.child("image6").getValue().toString());
                        Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                        break;

                    case 6:
                        slider1 = String.valueOf(dataSnapshot.child("image7").getValue().toString());
                        Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                        break;

                    case 7:
                        slider1 = String.valueOf(dataSnapshot.child("image8").getValue().toString());
                        Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                        break;

                    case 8:
                        slider1 = String.valueOf(dataSnapshot.child("image9").getValue().toString());
                        Glide.with(viewHolder.itemView).load(slider1).into(viewHolder.sliderImageview);
                        break;

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            private Object MainFragment;

            @Override
            public void onClick(View v) {

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

                        //uri.setText(webView);
                        // webviewthis.loadUrl(webView);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                 FirebaseDatabase.getInstance().getReference("VideoSlider").child("VideoLink").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        switch (position) {
                            case 0:
                                slider2 = String.valueOf(dataSnapshot.child("video1").getValue().toString());
                                Intent intent = new Intent(context, Video_play.class);
                                intent.putExtra("id", slider2);
                                intent.putExtra("title", tslider1);
                                context.startActivity(intent);
                                break;

                            case 1:
                                slider2 = String.valueOf(dataSnapshot.child("video2").getValue().toString());
                                Intent intent2 = new Intent(context, Video_play.class);
                                intent2.putExtra("id", slider2);
                                intent2.putExtra("title", tslider2);
                                context.startActivity(intent2);
                                break;

                            case 2:
                                slider2 = String.valueOf(dataSnapshot.child("video3").getValue().toString());
                                Intent intent3 = new Intent(context, Video_play.class);
                                intent3.putExtra("id", slider2);
                                intent3.putExtra("title", tslider3);
                                context.startActivity(intent3);
                                break;

                            case 3:
                                slider2 = String.valueOf(dataSnapshot.child("video4").getValue().toString());
                                Intent intent4 = new Intent(context, Video_play.class);
                                intent4.putExtra("id", slider2);
                                intent4.putExtra("title", tslider4);
                                context.startActivity(intent4);
                                break;

                            case 4:
                                slider2 = String.valueOf(dataSnapshot.child("video5").getValue().toString());
                                Intent intent5 = new Intent(context, Video_play.class);
                                intent5.putExtra("id", slider2);
                                intent5.putExtra("title", tslider5);
                                context.startActivity(intent5);
                                break;

                            case 5:
                                slider2 = String.valueOf(dataSnapshot.child("video6").getValue().toString());
                                Intent intent6 = new Intent(context, Video_play.class);
                                intent6.putExtra("id", slider2);
                                intent6.putExtra("title", tslider6);
                                context.startActivity(intent6);
                                break;

                            case 6:
                                slider2 = String.valueOf(dataSnapshot.child("video7").getValue().toString());
                                Intent intent7 = new Intent(context, Video_play.class);
                                intent7.putExtra("id", slider2);
                                intent7.putExtra("title", tslider7);
                                context.startActivity(intent7);
                                break;

                            case 7:
                                slider2 = String.valueOf(dataSnapshot.child("video8").getValue().toString());
                                Intent intent8 = new Intent(context, Video_play.class);
                                intent8.putExtra("id", slider2);
                                intent8.putExtra("title", tslider8);
                                context.startActivity(intent8);
                                break;

                            case 8:
                                slider2 = String.valueOf(dataSnapshot.child("video9").getValue().toString());
                                Intent intent9 = new Intent(context, Video_play.class);
                                intent9.putExtra("id", slider2);
                                intent9.putExtra("title", tslider9);
                                context.startActivity(intent9);
                                break;

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }


        });
    }

    @Override
    public int getCount() {
        return setTotalcount;
    }

    class  SliderViewHolder extends SliderViewAdapter.ViewHolder {
      View itemView;
      ImageView sliderImageview;
        public SliderViewHolder(View itemView) {

            super(itemView);
            this.itemView = itemView;
            sliderImageview = itemView.findViewById(R.id.iv_auto_image_slider);

        }
    }
}
