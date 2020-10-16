package com.video.status_downloader.supernover;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private EditText idemail;
    private  EditText idPASS;
    private CheckBox rememberMe;
    private TextView forgatepass;

    private  Button btnlogin;
    private  Button btnsignUp;

    private SharedPreferences loginPref;
    private  SharedPreferences.Editor prefs;
    private  boolean check;
    private FirebaseAuth mAuth;
    private Button whatsapp;
    private String email;
    private String password;
    String name;
    private FirebaseApp secondaryDatabase;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(!isConnected(LoginActivity.this)) buildDialog(LoginActivity.this).show();
        else {
            // Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_SHORT).show();

        }

        idemail = (EditText) findViewById(R.id.idemail);
        idPASS = (EditText)  findViewById(R.id.idpass);

        btnlogin = (Button) findViewById(R.id.btnLoging);
        btnsignUp = (Button) findViewById(R.id.btnRegister);
        //  rememberMe = (CheckBox) findViewById(R.id.checkbox);
        forgatepass = (TextView) findViewById(R.id.forgate);
        whatsapp = (Button) findViewById(R.id.whatsapp);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+250789690247";
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = LoginActivity.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(LoginActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
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


        linearLayout = (LinearLayout)findViewById(R.id.messege);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance(secondaryDatabase).getReference("APP_NAME");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    if(dataSnapshot.child(getString(R.string.appdata)).getValue().equals(getPackageName())){
                       // Toast.makeText(LoginActivity.this, "yess", Toast.LENGTH_LONG).show();


                    }
                    else {

                        linearLayout.setVisibility(View.VISIBLE);
                       // Toast.makeText(LoginActivity.this, "no", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        mAuth = FirebaseAuth.getInstance();
        loginPref = getSharedPreferences("loginPref",MODE_PRIVATE);

        if (mAuth.getCurrentUser() != null) {
            // User is logged in
            startActivity(new Intent(LoginActivity.this,Splashscreen.class));
            finish();
        }
        // if(loginPref.getBoolean("logged",false)){
        //    startActivity(new Intent(LoginActivity.this,Splashscreen.class));
        //   finish();
        //  }
        prefs = loginPref.edit();
        check = loginPref.getBoolean("savelogin",false);
        if (check == true) {
            idemail.setText(loginPref.getString("email",""));
            idPASS.setText(loginPref.getString("password",""));
            rememberMe.setChecked(true);
        }


        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUp.class));
                finish();
            }
        });

        forgatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgatePass.class));
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = idemail.getText().toString();
                final String password = idPASS.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    //   Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(LoginActivity.this, "Enter email address!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    //  Toast.makeText(getApplicationContext(), "Enter  password!", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(LoginActivity.this, "Enter  password!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    return;
                }
                //    setRememberMeMethod();
                loginMethod();
                //  loginPref.edit().putBoolean("logged",true).apply();



            }
        });





    }



    private void loginMethod() {
        mAuth.signInWithEmailAndPassword(idemail.getText().toString(),idPASS.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(LoginActivity.this,Splashscreen.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //  Toast.makeText(LoginActivity.this,"Error" +e.getMessage(),Toast.LENGTH_LONG).show();
                TastyToast.makeText(LoginActivity.this, "Error"+e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });
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
