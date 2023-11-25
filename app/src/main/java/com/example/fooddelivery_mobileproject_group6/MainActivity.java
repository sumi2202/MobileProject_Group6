package com.example.fooddelivery_mobileproject_group6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginBtn, registerBtn;
    EditText emailEDT, passEDT;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);           //Creates DBHandler object
        emailEDT = (EditText) findViewById(R.id.lemailET);      //Finds email edittext by id
        passEDT = (EditText) findViewById(R.id.lpassET);        //Finds password edittext by id

        /*Finds the login and register buttons by id*/
        loginBtn = (Button) findViewById(R.id.loginbtn);
        registerBtn = (Button) findViewById(R.id.lregisterbtn);

        /*onClickListener when the register button is clicked, which will take user to register page*/
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent intent = new Intent(MainActivity.this, registerpage.class);
                    startActivity(intent);
            }
        });

        /*onClickListener when the login button is clicked*/
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEDT.getText().toString();       //Gets the inputted text and puts it to string
                String password = passEDT.getText().toString();

                if(email.equals("") || password.equals("")){        //If there are any empty fields, a toast will display
                    Toast.makeText(MainActivity.this, "Please complete all fields.",Toast.LENGTH_SHORT).show();
                } else {                                              //Else, the inputted password is checked
                    Boolean checkpass = db.checkPassword(email, password);

                   if(checkpass == true){                               //If the password exists and is correct, a toast will display notifying user
                     Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), mainpage.class);    //Brings user to main page
                    startActivity(intent);
                     } else {                       //If the entered password does not match any values in the db, a toast will display
                     Toast.makeText(MainActivity.this, "Invalid values entered.", Toast.LENGTH_SHORT).show();
                     }
                }
            }
        });

    }
}