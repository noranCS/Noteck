package com.example.noteckv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class PrimePage extends AppCompatActivity {
//XML : line 23 , supposed to be rowCount=3
    //row / column places 1-0 1-1 etc.

    String userName;
    private MeowBottomNavigation meowBottomNavigation;
    ImageView mathIV,csIV,chemistryIV,biologyIV,physicsIV,languagesIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_page);

        userName = getIntent().getStringExtra("userName");//get user name from intent from firebase


        mathIV = (ImageView) findViewById(R.id.mathIV);
        csIV = (ImageView) findViewById(R.id.csIV);
        chemistryIV = (ImageView) findViewById(R.id.chemistryIV);
        biologyIV = (ImageView) findViewById(R.id.biologyIV);
        physicsIV = (ImageView) findViewById(R.id.physicsIV);
        languagesIV = (ImageView) findViewById(R.id.languagesIV);

        meowBottomNavigation =findViewById(R.id.meow_bottom_navigation);


//        BadgeDrawable badgeDrawable1 = bottomNavigationView.getOrCreateBadge(R.id.menu_profile);
//        BadgeDrawable badgeDrawable2 = bottomNavigationView.getOrCreateBadge(R.id.menu_profile);
//        BadgeDrawable badgeDrawable3 = bottomNavigationView.getOrCreateBadge(R.id.menu_profile);
//        BadgeDrawable badgeDrawable4 = bottomNavigationView.getOrCreateBadge(R.id.menu_profile);
//        BadgeDrawable badgeDrawable5 = bottomNavigationView.getOrCreateBadge(R.id.menu_profile);
//
//        badgeDrawable1.isVisible();

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.profile));//set icons for bottom buttons
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.add_comment));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.topics));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.share_with));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.calendar));

        meowBottomNavigation.show(3,true);//show categories button

        languagesIV.setOnClickListener(new View.OnClickListener() {//set what each imageView do!
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimePage.this,LanguagesPage.class);
                startActivity(intent);
            }
        });


    }
}