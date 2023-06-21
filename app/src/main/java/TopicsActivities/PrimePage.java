package TopicsActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.noteckv1.R;

import ButtonsActivities.AddPicActivity;
import ButtonsActivities.CalendarActivity;
import ButtonsActivities.ChatWithActivity;
import ButtonsActivities.ProfileActivity;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PrimePage extends AppCompatActivity {
    //row / column places 1-0 1-1 etc.

    //Buttons Activities  Ids
    public final static int profileID = 1; // ProfileActivity
    public final static int addPicsID = 2; // AddPicActivity
    public final static int topicsID = 3; // primePage
    public final static int shareWithTeachersID = 4; // ChatWithActivity
    public final static int calendarID = 5; // CalendarActivity

    //to use for all categories activities
    public static String userName ;
     private TextView useNameTV;
    private MeowBottomNavigation meowBottomNavigation;
    ImageView mathIV,csIV,chemistryIV,biologyIV,physicsIV,languagesIV
            ,settingIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_page);

        userName = getIntent().getStringExtra("userName");//get user name from intent from firebase
        useNameTV = findViewById(R.id.textNameTopicsAc);
        useNameTV.setText(userName);

        mathIV = (ImageView) findViewById(R.id.mathIV);
        csIV = (ImageView) findViewById(R.id.csIV);
        chemistryIV = (ImageView) findViewById(R.id.chemistryIV);
        biologyIV = (ImageView) findViewById(R.id.biologyIV);
        physicsIV = (ImageView) findViewById(R.id.physicsIV);
        languagesIV = (ImageView) findViewById(R.id.languagesIV);
        settingIV = (ImageView) findViewById(R.id.setting_icon);

        meowBottomNavigation =findViewById(R.id.meow_bottom_navigation);//custom navigation bottom bar

        meowBottomNavigation.add(new MeowBottomNavigation.Model(profileID,R.drawable.profile));//set icons for bottom buttons
        meowBottomNavigation.add(new MeowBottomNavigation.Model(addPicsID,R.drawable.add_pics));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(topicsID, R.drawable.topics));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(shareWithTeachersID, R.drawable.share_with_teachers));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(calendarID, R.drawable.calendar));

        meowBottomNavigation.show(topicsID,true);//show categories button

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if( model.getId() == profileID ){
                    Intent intent = new Intent(PrimePage.this, ProfileActivity.class);
                    startActivity(intent);
                }else if( model.getId() == addPicsID ){
                    Intent intent = new Intent(PrimePage.this, AddPicActivity.class);
                    startActivity(intent);
                }else if( model.getId() == shareWithTeachersID ){
                    Intent intent = new Intent(PrimePage.this, ChatWithActivity.class);
                    startActivity(intent);
                }else if( model.getId() == calendarID ){
                    Intent intent = new Intent(PrimePage.this, CalendarActivity.class);
                    startActivity(intent);
                }
                return null;
            }
        });

                languagesIV.setOnClickListener(new View.OnClickListener() {//set what each imageView do!
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PrimePage.this, LanguagesPage.class);
                        intent.putExtra("userName",userName);
                        startActivity(intent);
                    }
                });

        mathIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimePage.this,MathSubjectsActivity.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        csIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimePage.this,CSActivity.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        chemistryIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimePage.this,ChemistryActivity.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        biologyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimePage.this,BiologyActivity.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        physicsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimePage.this,PhysicsActivity.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });


    }


}