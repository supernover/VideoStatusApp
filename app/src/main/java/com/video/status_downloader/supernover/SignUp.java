package com.video.status_downloader.supernover;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import com.video.status_downloader.supernover.Model.UserInformation;


public class SignUp extends AppCompatActivity {
    ProgressBar progressBar;
    private EditText idemial;
    private EditText idpass;
    private EditText idname;
    private EditText idmobile;
    private Button btnregister;
    private static final String user_dr = "users";
    private FirebaseDatabase userInfoDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    private  static final String TAG = "SignUpActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        idemial = (EditText) findViewById(R.id.idemail);
        idpass = (EditText) findViewById(R.id.idpass);
        // idmobile = (EditText) findViewById(R.id.idmobile);
        idname = (EditText) findViewById(R.id.idname);
        progressBar = (ProgressBar) findViewById(R.id.pro);
        progressBar.setVisibility(View.INVISIBLE);


        userInfoDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user_dr);
        currentUser = mAuth.getCurrentUser();


        btnregister = (Button) findViewById(R.id.idsignup);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = idname.getText().toString().trim();
                String email = idemial.getText().toString().trim();
                String password = idpass.getText().toString().trim();
                //   String mobile = idmobile.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                if (name.length() < 4){
                    TastyToast.makeText(SignUp.this, "Not Valid  User Name! minimum 4 later", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }else {

                    if (TextUtils.isEmpty(email)) {
                        //  Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        TastyToast.makeText(SignUp.this, "Enter email address!", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                        return;
                    }else {

                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {




                            if (TextUtils.isEmpty(password)) {
                                //Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                TastyToast.makeText(SignUp.this, "Enter password!", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                                return;
                            }else {

                                if (password.length()< 6){
                                    //   Toast.makeText(getApplicationContext(), "Not Valid  password! minimum 6 later", Toast.LENGTH_SHORT).show();
                                    TastyToast.makeText(SignUp.this, "Not Valid  password! minimum 6 later", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    return;

                                }else {

                                    authenticationListner();

                                }


                            }
                        }
                        else{
                            //Toast.makeText(getApplicationContext(), "invalid Email address", Toast.LENGTH_SHORT).show();
                            TastyToast.makeText(SignUp.this, "invalid Email address", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        }
                    }
                }







            }
        });
    }

    private void  authenticationListner(){

        final String user_name = idname.getText().toString();

        final String user_email = idemial.getText().toString();

        final String user_password = idpass.getText().toString();
        //  final String user_mobile = idmobile.getText().toString();


        if (TextUtils.isEmpty(user_email)  && (TextUtils.isEmpty(user_password))){
            Toast.makeText(this,"Fill The All Field", Toast.LENGTH_LONG).show();
        }
        else {

            mAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    UserInformation userInformation = new UserInformation();
                    userInformation.setEmail(user_email);
                    userInformation.setName(user_name);
                    //    userInformation.setNumber(user_mobile);

                    userInformation.setPassword(user_password);
                    //  userInformation.setRupees(0);
                    userInformation.setPoints(100);



                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        Toast.makeText(SignUp.this, "SUCCESS.",Toast.LENGTH_LONG).show();
                        Log.d("SUCCESS", "createUserWithEmail:success");
                        //  String user_id = mAuth.getCurrentUser().getUid();
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        databaseReference = FirebaseDatabase.getInstance().getReference("/users/"+user.getUid());
                        //   DatabaseReference  user_id_child =  databaseReference.child(user_id);
                        databaseReference.child("Wallet").child("points").setValue(100);
                        databaseReference.child("Wallet").child("invite").setValue(0);
                        databaseReference.child("Wallet").child("rs").setValue(0);
                        databaseReference.child("Wallet").child("noOfWithdraws").setValue(0);
                        databaseReference.child("email").setValue(user_email);
                        databaseReference.child("name").setValue(user_name);
                        //         databaseReference.child("number").setValue(user_mobile);
                        databaseReference.child("password").setValue(user_password);
                        databaseReference.child("referral_code").setValue(user_name.substring(0,4) + user_password.substring(0,2));
                        databaseReference.child("refered_by").setValue("empty");
                        // user_id_child.child("points").setValue(50);

                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(SignUp.this,LoginActivity.class));
                        finish();



                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUp.this, ""+task.getException(),Toast.LENGTH_LONG).show();

                    }



                    // ...
                }
            });





        }
    }
}

