package com.example.noteckv1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class openingDesignActivity extends AppCompatActivity {
    private BlurView blurView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_design);

        blurView = findViewById(R.id.blurLayoutOpenning);
        blurBackground();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(openingDesignActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    private void blurBackground() {
        float radius = 10f; // 0 < radius < 25
        View decorV = getWindow().getDecorView();
        ViewGroup rootV = (ViewGroup) decorV.findViewById(android.R.id.content);
        Drawable windowBG = decorV.getBackground();

        blurView.setupWith(rootV)
                .setFrameClearDrawable(windowBG)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);
    }
}
