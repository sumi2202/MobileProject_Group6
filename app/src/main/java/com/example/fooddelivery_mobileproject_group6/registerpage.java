package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerpage extends AppCompatActivity {

    Button registerBtn, backBtn;
    EditText firstNameEDT, lastNameEDT, emailEDT, passEDT, repassEDT;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        db = new DBHandler(this);       //New DBHandler object

        /*Finds the register and back buttons by id*/
        registerBtn = (Button) findViewById(R.id.registerbtn);
        backBtn = (Button) findViewById(R.id.backbtn);

        /*Finds the edit texts by id*/
        firstNameEDT = (EditText) findViewById(R.id.fnameET);
        lastNameEDT = (EditText) findViewById(R.id.lnameET);
        emailEDT = (EditText) findViewById(R.id.emailET);
        passEDT = (EditText) findViewById(R.id.passET);
        repassEDT = (EditText) findViewById(R.id.retypepassET);

        /*onClickListener for the back button, when pressed, brings user back to MainActivity (login page)*/
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerpage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*onClickListener for register button*/
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Returns the inputted text into string*/
                String fname = firstNameEDT.getText().toString();
                String lname = lastNameEDT.getText().toString();
                String email = emailEDT.getText().toString();
                String password = passEDT.getText().toString();
                String repassword = repassEDT.getText().toString();

                //Checks if any of the fields are empty and displays a toast to inform the user
                if(fname.equals("") || lname.equals("") || email.equals("") || password.equals ("") || repassword.equals("")){
                    Toast.makeText(registerpage.this, "Please complete all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    if(password.equals(repassword)){        //Checks if the two inputted passwords match
                       Boolean confirmUser = db.checkEmail(email);      //Checks if the email exists
                       if (confirmUser == false){       //If email does not exist, insert the data into the db
                           Boolean insert = db.insertData(email, fname, lname, password);

                           if(insert == true){      //If the insert has been performed successfully, display a toast
                               Toast.makeText(registerpage.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(getApplicationContext(), MainActivity.class);     //Brings user back to MainActivity (login page)
                               startActivity(intent);
                           } else {                 //Else, display a toast informing the registration has failed
                               Toast.makeText(registerpage.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                           }
                       }    else {      //If the email already exists in the db, toast to prompt the user to log in
                           Toast.makeText(registerpage.this, "User already exists; please log in.", Toast.LENGTH_SHORT).show();
                       }
                    } else {            //If the two inputted passwords do not match, display a toast notifying the user
                        Toast.makeText(registerpage.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}