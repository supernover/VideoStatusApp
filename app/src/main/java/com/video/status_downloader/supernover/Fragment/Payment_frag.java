package com.video.status_downloader.supernover.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.sdsmdg.tastytoast.TastyToast;
import com.video.status_downloader.supernover.Model.WithdrawRequest;
import com.video.status_downloader.supernover.R;

import java.text.DateFormat;
import java.util.Date;


public class Payment_frag extends Fragment {
    CardView button;
    private String email,name,number,password;
    private EditText etPaytmNumber,etAmount;
    private Button btngetpayment;
    private ImageView imageView;
    String usernameGT,eamilGT,numberGT;
    long balanceGT;
    String currentDateTime;
    private FirebaseDatabase database;
    private DatabaseReference userInfoDatabase,requestDatabase,userwalletRef,userdata;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference withdrawRequestsRef;
    String firebaseId;
    private int currentpoint,previouspoint;
    private TextView currentPoint;
    private FirebaseAuth.AuthStateListener stateListener;
    public Payment_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_payment, container, false);

        etAmount = (EditText) view.findViewById(R.id.idpoint);
        etPaytmNumber = (EditText) view.findViewById(R.id.idpaytm);
        btngetpayment = (Button) view.findViewById(R.id.idgetpay);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //    userWithdrawHistory = FirebaseDatabase.getInstance().getReference("/users/"+user.getUid()+"/WithdrawHistory/");
        withdrawRequestsRef = FirebaseDatabase.getInstance().getReference("/WithdrawRequests/").child("PAYTM");



        currentDateTime = DateFormat.getDateTimeInstance().format(new Date());



        userwalletRef = FirebaseDatabase.getInstance().getReference("/users/"+user.getUid()+"/Wallet/");
        userwalletRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                balanceGT = (long)dataSnapshot.child("points").getValue();
                //    walletrs = (long)dataSnapshot.child("rs").getValue();
                //    walletBalanceP.setText(String.valueOf(walletPoints));
                //   walletBalanceR.setText(String.valueOf(walletrs));
//                nWithdraws = (long) dataSnapshot.child("noOfWithdraws").getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        btngetpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paytmnumber = etPaytmNumber.getText().toString();
                String amount = etAmount.getText().toString();

                if (balanceGT >= 1000) {
                    if (!TextUtils.isEmpty(paytmnumber) && !TextUtils.isEmpty(amount)){
                        WithdrawRequest withdrawRequest = new WithdrawRequest();

                        withdrawRequest.setPhoneNumber(etPaytmNumber.getText().toString());
                        withdrawRequest.setAmount(Integer.parseInt(etAmount.getText().toString()));
                        userwalletRef.child("points").setValue(balanceGT - Integer.parseInt(etAmount.getText().toString()));
                        DatabaseReference refglobal = withdrawRequestsRef.push();

                        refglobal.setValue(withdrawRequest);

                        TastyToast.makeText(getContext(), "Your Withdrawal Request successfully sent...", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);



                    }
                    else {
                      //  Toast.makeText(getActivity(),"fILL aLL tHE fIELDS fIRST",Toast.LENGTH_SHORT).show();
                        TastyToast.makeText(getContext(), "Fill the Fields First", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                }
                else {
                  //  Toast.makeText(getActivity(),"Your Balance Less than 5000",Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getContext(), "your Balance Less than 1000", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }
        });

        return view;
    }

    private void saveStringToPreferences(Editable text) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("label", String.valueOf(text));
        editor.apply();
    }




}
