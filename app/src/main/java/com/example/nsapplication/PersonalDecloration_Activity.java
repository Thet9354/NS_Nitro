package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class PersonalDecloration_Activity extends AppCompatActivity {

    private TextView txtView_personalMsg, txtView_GTKYsubheading, txtView_DateOfBirth;
    private EditText editTxt_fullName, editTxt_NIRC, editTxt_DD, editTxt_MM, editTxt_YY;
    private Button btn_complete;

//    Intent intent =  getIntent();
//    String username = intent.getStringExtra("editTxt_username");
//    String email = intent.getStringExtra("editTxt_email");
//    String mobileNumber = intent.getStringExtra("editTxt_mobileNo");
//    String password = intent.getStringExtra("editTxt_password");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_decloration);


        Intent intent =  getIntent();
        String username = intent.getStringExtra("editTxt_username");
        String email = intent.getStringExtra("editTxt_email");
        String mobileNumber = intent.getStringExtra("editTxt_mobileNo");
        String password = intent.getStringExtra("editTxt_password");


        initView(username);

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

            String DDInput = editTxt_DD.getText().toString().trim();
            String MMInput = editTxt_MM.getText().toString().trim();
            String YYInput = editTxt_YY.getText().toString().trim();
            String DOBInput = DDInput + MMInput + YYInput;
            try {
                userModel = new UserModel(-1, username, email, mobileNumber, password, editTxt_fullName.getText().toString(), editTxt_NIRC.getText().toString(), DOBInput);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Error creating account, please try again", Toast.LENGTH_SHORT).show();
                userModel = new UserModel(-1, "error", "error", "error", "error", "error", "error", "error");
            }

            UserDataBaseHelper dataBaseHelper = new UserDataBaseHelper(PersonalDecloration_Activity.this);
            boolean success = dataBaseHelper.addOneUser(userModel);


            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(PersonalDecloration_Activity.this, Main_MenuPage_Activity.class);
            startActivity(intent);
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

    private void initView(String username) {
        txtView_personalMsg = findViewById(R.id.txtView_personalMsg);
        txtView_GTKYsubheading = findViewById(R.id.txtView_GTKYsubheading);
        txtView_DateOfBirth = findViewById(R.id.txtView_DateOfBirth);

        editTxt_fullName = findViewById(R.id.editTxt_fullName);
        editTxt_NIRC = findViewById(R.id.editTxt_NIRC);
        editTxt_DD = findViewById(R.id.editTxt_DD);
        editTxt_MM = findViewById(R.id.editTxt_MM);
        editTxt_YY = findViewById(R.id.editTxt_YY);

        btn_complete = findViewById(R.id.btn_complete);

        txtView_personalMsg.setText("Hello!");
    }
}