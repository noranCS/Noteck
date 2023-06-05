package ButtonsActivities;



// import static activities IDS

import static TopicsActivities.PrimePage.addPicsID;
import static TopicsActivities.PrimePage.calendarID;
import static TopicsActivities.PrimePage.profileID;
import static TopicsActivities.PrimePage.shareWithTeachersID;
import static TopicsActivities.PrimePage.topicsID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import TopicsActivities.PrimePage;
import com.example.noteckv1.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileActivity extends Activity {
    private MeowBottomNavigation meowBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        meowBottomNavigation = findViewById(R.id.meow_bottom_navigation);//custom navigation bottom bar
        meowBottomNavigation.show(profileID,true);//show categories button

        meowBottomNavigation.add(new MeowBottomNavigation.Model(profileID,R.drawable.profile));//set icons for bottom buttons
        meowBottomNavigation.add(new MeowBottomNavigation.Model(addPicsID,R.drawable.add_pics));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(topicsID, R.drawable.topics));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(shareWithTeachersID, R.drawable.share_with_teachers));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(calendarID, R.drawable.calendar));

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if( model.getId() == topicsID ){
                    Intent intent = new Intent(ProfileActivity.this, PrimePage.class);
                    startActivity(intent);
                }else if( model.getId() == addPicsID ){
                    Intent intent = new Intent(ProfileActivity.this, AddPicActivity.class);
                    startActivity(intent);
                }else if( model.getId() == shareWithTeachersID ){
                    Intent intent = new Intent(ProfileActivity.this, ChatWithActivity.class);
                    startActivity(intent);
                }else if( model.getId() == calendarID ){
                    Intent intent = new Intent(ProfileActivity.this, CalendarActivity.class);
                    startActivity(intent);
                }
                return null;
            }
        });

    }
}