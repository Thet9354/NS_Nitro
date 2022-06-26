package com.example.nsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class PersonalDecloration_Activity extends AppCompatActivity {

    private TextView txtView_personalMsg, txtView_GTKYsubheading, txtView_DateOfBirth;
    private EditText editTxt_fullName, editTxt_NIRC, editTxt_DD, editTxt_MM, editTxt_YY;
    private Button btn_complete;

    private Intent intent;

    private String username;
    private String email;
    private String mobileNumber;
    private String password;

    FirebaseDatabase database = FirebaseDatabase.getInstance("Enter your firebase link here");
    DatabaseReference databaseReference = database.getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_decloration);


        intent =  getIntent();

        initView();

        pageDirectories(username, email, mobileNumber, password);

    }

    private void pageDirectories(String username, String email, String mobileNumber, String password) {
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoValidation(username, email, mobileNumber, password);
            }
        });
    }

    private void infoValidation(String username, String email, String mobileNumber, String password) {
        validateFullName();
        validateNIRC();
        validateDOB();
        confirmInput();
        addData(username, email, mobileNumber, password);
    }

    private void addData(String username, String email, String mobileNumber, String password) {


        UserModel userModel;

        if (!confirmInput())
        {
            return;
        }
        else {

            String fullName = editTxt_fullName.getText().toString();
            String nircTxt = editTxt_NIRC.getText().toString();
            String DDInput = editTxt_DD.getText().toString().trim();
            String MMInput = editTxt_MM.getText().toString().trim();
            String YYInput = editTxt_YY.getText().toString().trim();
            String DOBInput = DDInput + MMInput + YYInput;

            /** sending data to SQLite database **/
            try {
                userModel = new UserModel(-1, username, email, mobileNumber, password, fullName, nircTxt, DOBInput);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Error creating account, please try again", Toast.LENGTH_SHORT).show();
                userModel = new UserModel(-1, "error", "error", "error", "error", "error", "error", "error");
            }

            UserDataBaseHelper dataBaseHelper = new UserDataBaseHelper(PersonalDecloration_Activity.this);
            boolean success = dataBaseHelper.addOneUser(userModel);


            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Checking if NIRC number is not registered before

                    if (snapshot.hasChild(nircTxt))
                    {
                        Toast.makeText(PersonalDecloration_Activity.this, "Account is already registered with existing NIRC", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalDecloration_Activity.this);
                        builder.setTitle("NS Nitro");
                        builder.setMessage("Hey there, it seems like there's an existing account with the same NIRC.");
                        builder.setNegativeButton("Register a new account", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(PersonalDecloration_Activity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.setPositiveButton("Log in to exisiting account", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(PersonalDecloration_Activity.this, LogInPage_Activity.class);
                                startActivity(intent);
                            }
                        });
                        builder.create().show();
                    }
                    else
                    {
                        // sending data to firebase Realtime Database
                        //we are using NIRC number as a unique identity of every user.
                        //so all the other details of the user comes under NIRC number
                        databaseReference.child("users").child(nircTxt).child("fullname").setValue(fullName);
                        databaseReference.child("users").child(nircTxt).child("email").setValue(email);
                        databaseReference.child("users").child(nircTxt).child("password").setValue(password);
                        databaseReference.child("users").child(nircTxt).child("mobile number").setValue(mobileNumber);
                        databaseReference.child("users").child(nircTxt).child("username").setValue(username);
                        databaseReference.child("users").child(nircTxt).child("date of birth").setValue(DOBInput);

                        Toast.makeText(PersonalDecloration_Activity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PersonalDecloration_Activity.this, Main_MenuPage_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private boolean confirmInput() {
        if (!validateFullName() | !validateNIRC() | !validateDOB())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean validateDOB() {

        // Defining our own Date Of Birth pattern
        final Pattern DOB_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{8,}" +                // at least 9 characters
                        "$");

        // Extract input from EditText
        String DDInput = editTxt_DD.getText().toString().trim();
        String MMInput = editTxt_MM.getText().toString().trim();
        String YYInput = editTxt_YY.getText().toString().trim();
        int YYIntInput = Integer.parseInt(YYInput);
        String DOBInput = DDInput + MMInput + YYInput;

        // if Date Of Birth matched pattern
        // it will display "Field can not be empty"
        if (DOBInput.isEmpty())
        {
            if (DDInput.isEmpty())
            {
                editTxt_DD.setError("Field can not be empty");
            }
            else if (MMInput.isEmpty())
            {
                editTxt_MM.setError("Field can not be empty");
            }
            else if (YYInput.isEmpty())
            {
                editTxt_YY.setError("Field can not be empty");
            }
            return false;
        }

        // if Date of Birth entered is underage
        // it will display "You do not meet the minimum age requirement
        else if (YYIntInput > 02)
        {
            editTxt_YY.setError("You do not meet the minimum age requirement");
            return false;
        }


        // if Date Of Birth matches to the pattern
        // it will display an error message "Date Of Birth in valid"
        else if (DOB_PATTERN.matcher(DOBInput).matches())
        {
            editTxt_YY.setError("Invalid Date of Birth");
            editTxt_MM.setError("Invalid Date of Birth");
            editTxt_DD.setError("Invalid Date of Birth");
            return false;
        }
        else
        {
            editTxt_YY.setError(null);
            editTxt_MM.setError(null);
            editTxt_DD.setError(null);
            return true;
        }


    }

    private boolean validateNIRC() {

        // Defining our own NIRC pattern
        final Pattern NIRC_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{6,}" +                // at least 9 characters
                        "$");

        // Extract input from EditText
        String NIRCInput = editTxt_NIRC.getText().toString().trim();

        if (NIRCInput.isEmpty())
        {
            editTxt_NIRC.setError("Field can not be empty");
            return false;
        }
        // if fullname matches the pattern
        // it will display an error message "Invalid Name"
        else if (NIRC_PATTERN.matcher(NIRCInput).matches())
        {
            editTxt_NIRC.setError("Invalid NIRC No.");
            return false;
        }
        else
        {
            editTxt_NIRC.setError(null);
            return true;
        }
    }

    private boolean validateFullName() {
        //Defining our own Full Name pattern
        final Pattern FULLNAME_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "$");

        // Extract input from EditText
        String fullnameInput = editTxt_fullName.getText().toString().trim();

        if (fullnameInput.isEmpty())
        {
            editTxt_fullName.setError("Field can not be empty");
            return false;
        }
        // if fullname matches the pattern
        // it will display an error message "Invalid Name"
        else if (FULLNAME_PATTERN.matcher(fullnameInput).matches())
        {
            editTxt_fullName.setError("Invalid Full Name");
            return false;
        }
        else
        {
            editTxt_fullName.setError(null);
            return true;
        }
    }

    private void initView() {
        txtView_personalMsg = findViewById(R.id.txtView_personalMsg);
        txtView_GTKYsubheading = findViewById(R.id.txtView_GTKYsubheading);
        txtView_DateOfBirth = findViewById(R.id.txtView_DateOfBirth);

        editTxt_fullName = findViewById(R.id.editTxt_fullName);
        editTxt_NIRC = findViewById(R.id.editTxt_NIRC);
        editTxt_DD = findViewById(R.id.editTxt_DD);
        editTxt_MM = findViewById(R.id.editTxt_MM);
        editTxt_YY = findViewById(R.id.editTxt_YY);

        username = intent.getStringExtra("editTxt_username");
        email = intent.getStringExtra("editTxt_email");
        mobileNumber = intent.getStringExtra("editTxt_mobileNo");
        password = intent.getStringExtra("editTxt_password");

        btn_complete = findViewById(R.id.btn_complete);

        txtView_personalMsg.setText("Welcome " + username);
    }
}