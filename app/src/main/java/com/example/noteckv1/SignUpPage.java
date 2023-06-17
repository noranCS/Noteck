package com.example.noteckv1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpPage extends Activity {

    //connected to firebase
    private DatabaseReference databaseReference;
    public static final int SignUp_REQUEST_CODE = 111;
            //

    Button confirmBtn;
    EditText userIdET, userNameET, userEmailET, userPasswordET, userPass2ET;
    RadioGroup radioGroup;
    RadioButton userStatus;
    String userNameST, userEmailST,userIdST, userPasswordST, userPass2ST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        //gradiant background
        RelativeLayout relativeLayout = findViewById(R.id.signup_pageId);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        confirmBtn = findViewById(R.id.confirmBtn);
        userIdET = findViewById(R.id.userID);
        userEmailET = findViewById(R.id.userEmail);
        userNameET = findViewById(R.id.s_userNameSU);
        userPasswordET = findViewById(R.id.s_userPasswordSU);
       // userStatus = findViewById(R.id.radioTeacher);
        radioGroup = findViewById(R.id.radioGroup);
        userPass2ET = findViewById(R.id.verifyPass);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //  String userStatusST = userStatus.getText().toString();
                    int idStatus = radioGroup.getCheckedRadioButtonId();
                    userNameST = userNameET.getText().toString();
                    userEmailST = userEmailET.getText().toString();
                    userPasswordST = userPasswordET.getText().toString();
                    userIdST = userIdET.getText().toString();
                    userPass2ST = userPass2ET.getText().toString();
                    String userStatus = "";//check status
                    if(idStatus == R.id.radioTeacher)
                        userStatus = "Teacher";
                    else userStatus = "Student";

                    // Log.d("Test",userStatus);

                    int userIdInt = Integer.parseInt(userIdET.getText().toString());
                if(checkName() && checkEmail() && checkPassword() && validTwoPasswords() && checkId()) {
                    User user = new User(userNameST,userPasswordST,userEmailST,userStatus,userIdInt);
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");//instance = object
                    writeToDatabase(user);
                    Intent intent = new Intent(SignUpPage.this, LogInPage.class);
                    startActivity(intent);
                }



                }
        });


    }
    //check if everything is applied

    private void writeToDatabase(User user){
//        if(checkName() && checkEmail() && checkPassword() && validTwoPasswords() && checkId()) {//upload to DATABASE
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(user.getUserName()).exists()) {//check to compare
                        Toast.makeText(SignUpPage.this, "user name exist", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child(user.getUserName()).setValue(user);
                        Intent i = new Intent();
                        i.putExtra("userName", user.getUserName());
                        i.putExtra("userPassword", user.getUserPassword());
                        i.putExtra("userId", user.getId());//send back to the first activity
                        setResult(RESULT_OK, i);
                        finish();//get back to login
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }


    private boolean checkName(){ //check everything // take all options in consider
        if(userNameST.isEmpty()){
            userNameET.setError("Field Cannot Be Empty!");
            return false;
        }else if(userNameST.length() >= 15) {
            userNameET.setError("UserName Too Long");
            return false;
        }else{
            userNameET.setError(null);//hide error
            userNameET.setEnabled(false);//remove space
            return true;
        }
    }
    private boolean checkPassword(){
        String passwordValid = "^" +
               //"(?=.*[0-9])" + //at least 1 digit
               //"(?=.*[a-z])" + //at least 1 lower case letter
               //"(?=.*[A-Z])" + //at least 1 upper case letter
               //"(?=.*[0-9])" + //at least 1 digit
                "(?=.*[a-zA-Z])" + //any letter
               // "(?=.*[@#$^&+=!])" + //at least 1 special character
                "(?=.*[?=\\S+$])" + //no white space
                ".{6,}" + //at least 6 characters
                "$";
        if(userPasswordST.isEmpty()){
            userPasswordET.setError("Field Cannot Be Empty!");
            return false;
        }else if(!userPasswordST.matches(passwordValid)){
            userPasswordET.setError("Password Too Weak");//recommended passwords
            return false;
        } else{
            userPasswordET.setError(null);
            return true;
        }
    }//check the validation
    private boolean validTwoPasswords(){
        if(userPass2ST.isEmpty()){
            userPass2ET.setError("Field Cannot Be Empty!");
            return false;
        }else if(!userPass2ST.matches(userPasswordST)) {
            userPass2ET.setError("The Verification Is Incorrect!");
            return false;
        }else return true;
    }//check the validation and verify your password

    private boolean checkEmail(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(userEmailST.isEmpty()){
            userEmailET.setError("Field Cannot Be Empty!");
            return false;
        }else if(!userEmailST.matches(emailPattern)){
            userEmailET.setError("Invalid Email Address,Please Use At Least 1 Upper " +
                    "Letter & Special Character");
            return false;
        }else{
            userEmailET.setError(null);
            return true;
        }
    }
    private boolean checkId(){
        if(userIdST.isEmpty()){
            userIdET.setError("Field Cannot Be Empty!");
            return false;
        }else{
            userIdET.setError(null);
            return true;
        }
    }

    public void checkButton(View view) {
    }


//    public void goToLogIn(View view) {
//        if(userPassword.getText().toString().isEmpty() || userPass2.getText().toString().isEmpty()){
//            //message
//            Toast.makeText(this, "Please Enter The Missing Form\"", Toast.LENGTH_SHORT).show();
//        }else if(userPassword.getText().toString().equals(userPass2.getText().toString())){
//              //message
//            Toast.makeText(this, "Your Vertify Password Is Wrong!", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            intent = new Intent(SignUpPage.this, LogInPage.class);
//            intent.putExtra("userId", userId.getText().toString());
//            intent.putExtra("userId", userId.getText().toString());
//            intent.putExtra("userEmail", userEmail.getText().toString());
//            intent.putExtra("userName", userName.getText().toString());
//            intent.putExtra("userPassword", userPassword.getText().toString());
//            intent.putExtra("userStatus", userStatus.getText().toString());
//        //   startActivityForResult(intent, 0);//
//          //  finish();
//        }
//    }

}