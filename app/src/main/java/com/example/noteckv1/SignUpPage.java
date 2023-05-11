package com.example.noteckv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        confirmBtn = findViewById(R.id.confirmBtn);
        userIdET = findViewById(R.id.userID);
        userEmailET = findViewById(R.id.userEmail);
        userNameET = findViewById(R.id.s_userNameSU);
        userPasswordET = findViewById(R.id.s_userPasswordSU);
        userStatus = findViewById(R.id.radioTeacher);
        radioGroup = findViewById(R.id.radioGroup);
        userPass2ET = findViewById(R.id.vertifyPass);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userNameSt = userNameET.toString();
                String userEmailSt = userEmailET.toString();
                String userPasswordST = userPasswordET.toString();
                String userStatusST = userStatus.toString();
                int useIdInt = Integer.parseInt(userIdET.toString());

                User user = new User(userNameSt,userPasswordST,userEmailSt,userStatusST,useIdInt);
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");//instance = object
                writeToDatabase(user);


//                int statusId = radioGroup.getCheckedRadioButtonId();
//                if(statusId == -1) {//no radioBTN is checked
//                    Message.message(getApplicationContext(),"Please Select A Status");
//                }else if(statusId != userStatus.getId() ){
//                    userStatus.setId(statusId);
//                }
//                Intent intent = new Intent(SignUpPage.this, PrimePage.class);
//                startActivity(intent);
            }
        });


    }

    private void writeToDatabase(User user){
        if(checkName() && checkEmail() && checkPassword() && checkId()) {//upload to DATABASE
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(user.getUserName()).exists()){//check to compare
                        Toast.makeText(SignUpPage.this, "user name exist", Toast.LENGTH_SHORT).show();
                    }else{
                        databaseReference.child(user.getUserName()).setValue(user);
                        Intent i = new Intent();
                        i.putExtra("userName",user.getUserName());
                        i.putExtra("userPassowrd",user.getUserPassword());
                        i.putExtra("userId",user.getId());//send back to the first activity
                        setResult(RESULT_OK,i);
                        finish();//get back to login
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    private boolean checkName(){ //check everything // take all options in consider
        return true;
    }
    private boolean checkPassword(){
        return true;
    }
    private boolean checkEmail(){
        return true;
    }
    private boolean checkId(){
        return true;
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