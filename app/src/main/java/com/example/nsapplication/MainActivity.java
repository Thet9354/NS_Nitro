package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView txtView_welcomeMsg, txtView_subheading, txtView_existingAcc, txtView_altConnections, txtView_altSignIn;
    private EditText editTxt_username, editTxt_mobileNo, editTxt_email, editTxt_password, editTxt_confirmPassword;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        pageDirectories();
    }

    private void pageDirectories() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_validation();
            }
        });
    }

    private void info_validation() {
        if (editTxt_username.equals("") || editTxt_mobileNo.toString().equals("") || editTxt_email.equals("") || editTxt_password.equals("") || editTxt_confirmPassword.equals(""))
        {
            Toast.makeText(this, "Please fill up all the require details", Toast.LENGTH_SHORT).show();
            info_validation();
        }
        else if (editTxt_password.toString().equals(editTxt_confirmPassword.toString()))
        {

        }
        else
        {
            validateUsername();
            validatePhoneNumber();
            validateEmail();
            validatePassword();
            confirmInput();
            addData();
        }
    }

    private void addData() {
        if (!confirmInput())
        {
            return;
        }
        else
        {
            Intent intent = new Intent(MainActivity.this, Personal_Info_Activity.class);
            startActivity(intent);
        }
    }

    private boolean validatePhoneNumber() {
        // defining our own password pattern
        final Pattern Phone_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{7,}" +                // at least 4 characters
                        "$");

        // Extract input from EditText
        String phoneNumberInput = editTxt_mobileNo.getText().toString().trim();

        if (phoneNumberInput.isEmpty())
        {
            editTxt_mobileNo.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.PHONE.matcher(phoneNumberInput).matches())
        {
            editTxt_mobileNo.setError("Please enter a valid mobile number");
            return false;
        }
        else
        {
            editTxt_mobileNo.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {

        // defining our own password pattern
        final Pattern PASSWORD_PATTERN2 =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{5,}" +                // at least 4 characters
                        "$");

        // Extract input from EditText
        String usernameInput = editTxt_username.getText().toString().trim();

        if (usernameInput.isEmpty())
        {
            editTxt_username.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!PASSWORD_PATTERN2.matcher(usernameInput).matches())
        {
            editTxt_username.setError("Please enter a valid username");
            return false;
        }
        else
        {
            editTxt_username.setError(null);
            return true;
        }
    }


    private boolean confirmInput() {
        if (!validateEmail() | !validatePassword() | !validateUsername() | !validatePhoneNumber())
        {
            return false;
        }

        // if the email and password matches, a toast message
        // with email and password is displayed
        String input ="Username: " + editTxt_username.getText().toString();
        input += "\n";
        input += "Mobile Number: " + editTxt_mobileNo.getText().toString();
        input += "\n";
        input += "Password: " + editTxt_password.getText().toString();
        input += "\n";
        input += "Email: " + editTxt_email.getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

        return true;
    }

    private boolean validatePassword() {

        // defining our own password pattern
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{4,}" +                // at least 4 characters
                        "$");

        String passwordInput = editTxt_password.getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty())
        {
            editTxt_password.setError("Field can not be empty");
            return false;
        }


        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches())
        {
            editTxt_password.setError("Password is too weak");
            return false;
        }
        else
        {
            editTxt_password.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        // Extract input from EditText
        String emailInput = editTxt_email.getText().toString().trim();

        if (emailInput.isEmpty())
        {
            editTxt_email.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            editTxt_email.setError("Please enter a valid email address");
            return false;
        }
        else
        {
            editTxt_email.setError(null);
            return true;
        }
    }


    private void initView() {
        txtView_welcomeMsg = findViewById(R.id.txtView_welcomeMsg);
        txtView_subheading = findViewById(R.id.txtView_subheading);
        txtView_existingAcc = findViewById(R.id.txtView_existingAcc);
        txtView_altConnections = findViewById(R.id.txtView_altConnections);
        txtView_altSignIn = findViewById(R.id.txtView_altSignIn);

        editTxt_username = findViewById(R.id.editTxt_username);
        editTxt_mobileNo = findViewById(R.id.editTxt_mobileNo);
        editTxt_email = findViewById(R.id.editTxt_email);
        editTxt_password = findViewById(R.id.editTxt_password);
        editTxt_confirmPassword = findViewById(R.id.editTxt_confirmPassword);

        btn_register = findViewById(R.id.btn_register);
    }


}