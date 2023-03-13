package com.example.noteckv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button signUpBtn, logInBtn;
    Intent intent;

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
                intent = new Intent(MainActivity.this,FirstPage.class);
                startActivityForResult(intent,0);
                finish();
            }
        });
//
//        logInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(MainActivity.this, FirstPage.class);
//                startActivityForResult(intent,1);
//                intent.putExtra("logIn",1);
//                finish();
//            }
//        });
//    }

    }
}