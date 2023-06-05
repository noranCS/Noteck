package com.example.noteckv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import TopicsActivities.PrimePage;

public class LogInPage extends Activity {
    Button logInGoToPrime;
    EditText userNameET , userPasswordET;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        LinearLayout linearLayout = findViewById(R.id.login_pageId);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        sharedPreferences = getSharedPreferences("data.txt",MODE_PRIVATE);//upload to the "phone"/no need to sign in again    data.txt is a file to wrtite on it we need editor
        editor = sharedPreferences.edit();
        String name = sharedPreferences.getString("userName",null);
        if(name != null){
            Intent intent = new Intent(LogInPage.this, PrimePage.class);
            startActivity(intent);
        }


        logInGoToPrime = findViewById(R.id.logInGTP);
        userNameET = findViewById(R.id.userNameLI);
        userPasswordET = findViewById(R.id.userPasswordLI);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");//instance = object

        logInGoToPrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get info from editText
                String userNameST = userNameET.getText().toString();
                String userPasswordST = userPasswordET.getText().toString();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if( snapshot.child(userNameST).exists()){
                            User user = snapshot.child(userNameST).getValue(User.class);//check if loging in is OK
                            if( user.getUserPassword().equals(userPasswordST)){
                                Intent intent = new Intent(LogInPage.this,PrimePage.class);
                                //storage info by :
                                editor.putString("userName",user.getUserName());
                                editor.putString("userPassword",user.getUserPassword());
                                editor.commit();
                                startActivity(intent);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

      //  Intent intent = new Intent(LogInPage.this,SignUpPage.class);//go to SignUpPAge then come back to LogInPage
        //go to other class then come back starting from onActivityResult
      //  startActivityForResult(intent, SignUpPage.SignUp_REQUEST_CODE);//get code from signUpPage
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if( requestCode == SignUpPage.SignUp_REQUEST_CODE && resultCode == RESULT_OK ){//result_ok = -1
            //get info and use it from data.getExtra
        }
        logInGoToPrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get info from editText
                String userNameST = userNameET.getText().toString();
                String userPasswordST = userPasswordET.getText().toString();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if( snapshot.child(userNameST).exists()){
                            User user = snapshot.child(userNameST).getValue(User.class);//check if loging in is OK
                            if( user.getUserPassword().equals(userPasswordST)){
                                Intent intent = new Intent(LogInPage.this,PrimePage.class);
                                //storage info by :
                                editor.putString("userName",user.getUserName());
                                editor.putString("userPassword",user.getUserPassword());
                                editor.commit();
                                startActivity(intent);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}