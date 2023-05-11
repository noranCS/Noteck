package com.example.noteckv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button signUpBtn, logInBtn;
    Intent intent;
    ImageView imageViewManLogo;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpBtn = findViewById(R.id.signUpFirstBtn);
        logInBtn = findViewById(R.id.logInFirstBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                intent = new Intent(MainActivity.this,SignUpPage.class);
                startActivityForResult(intent,0);
                finish();
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, LogInPage.class);
                startActivityForResult(intent,1);
                finish();
            }
        });
    }

    }
