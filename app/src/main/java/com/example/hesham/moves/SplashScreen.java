package com.example.hesham.moves;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final int Splash_Time_Hs = 2000;
    private Handler mHandler;
    private Runnable mRunnable;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                FirebaseUser CrrentUser=mAuth.getCurrentUser();

                if (CrrentUser != null) {
                    Intent Home_intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(Home_intent);
                } else {
                    Intent Sign_intent = new Intent(SplashScreen.this, SignInAndSignUp.class);
                    startActivity(Sign_intent);

                }
                finish();
            }
        };

        mHandler.postDelayed(mRunnable, Splash_Time_Hs);
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                Intent Home_intent = new Intent(SplashScreen.this, SignInAndSignUp.class);
//                startActivity(Home_intent);
//                finish();
//            }
//        }, Splash_Time_Hs);

    }

}

