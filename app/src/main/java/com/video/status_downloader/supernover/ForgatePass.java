package com.video.status_downloader.supernover;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sdsmdg.tastytoast.TastyToast;

public class ForgatePass extends AppCompatActivity {
    private EditText inputEmail;
    private CardView btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    TextView textView;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgate_pass);

        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (CardView) findViewById(R.id.btn_reset_password);
        textView = (TextView) findViewById(R.id.gotosignup);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        auth = FirebaseAuth.getInstance();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgatePass.this,SignUp.class);
                startActivity(intent);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                  //  Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(ForgatePass.this, "Enter your registered email id", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   // Toast.makeText(ForgatePass.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    TastyToast.makeText(ForgatePass.this, "We have sent you instructions to reset your password!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                } else {
                                 //   Toast.makeText(ForgatePass.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    TastyToast.makeText(ForgatePass.this, "Failed to send reset email!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ForgatePass.this,LoginActivity.class);
        startActivity(intent);
    }
}
