package com.example.nsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView txtView_welcomeMsg, txtView_existingAcc, txtView_altConnections, txtView_altSignIn;
    private EditText editTxt_username, editTxt_mobileNo, editTxt_email, editTxt_password, editTxt_confirmPassword;
    private ImageView img_facebook, img_google, img_linkedin;
    private Button btn_register;

    private String mUsername;
    private String mEmail;
    private String mMobileNo;
    private String mPassword;

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        //--> Google Sign in Integration with Firebase
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);


        //--> Facebook Sign In Integration with Firebase
        loginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handlerFacebookAccessToken(loginResult.getAccessToken());

                        Intent intent = new Intent(MainActivity.this, Main_MenuPage_Activity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(MainActivity.this, "Registration cancelled", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        initView();

        pageDirectories();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    private void handlerFacebookAccessToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in successful, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Sign In successful", Toast.LENGTH_SHORT).show();

                            // Lead to the Main Page of the app
                            Intent intent = new Intent(MainActivity.this, Main_MenuPage_Activity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Sign In unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //--> For Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        //--> For Google
        if (requestCode==100)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(MainActivity.this, Main_MenuPage_Activity.class);
                startActivity(intent);
            }catch (ApiException e)
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void pageDirectories() {

        /** OnClickListener for btn_Register **/
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_validation();
            }
        });

        /** onClickListener for txtView_altSignIn **/
        txtView_altSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LogInPage_Activity.class);
                startActivity(intent);
            }
        });

        /** OnClickListener for img_facebook **/
        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login to facebook
                loginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile"));
            }
        });

        /** OnClickListener for img_google **/
        img_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }




    private void info_validation() {

            validateUsername();
            validatePhoneNumber();
            validateEmail();
            validatePassword();
            confirmInput();
            addData();

    }

    private void addData() {
        if (!confirmInput())
        {
            return;
        }
        else
        {

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

            Intent intent = new Intent(MainActivity.this, PersonalDecloration_Activity.class);
            intent.putExtra("editTxt_username", mUsername);
            intent.putExtra("editTxt_email", mEmail);
            intent.putExtra("editTxt_mobileNo", mMobileNo);
            intent.putExtra("editTxt_password", mPassword);
            startActivity(intent);
        }
    }

    private boolean validatePhoneNumber() {
        // defining our own mobile number pattern
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
            mMobileNo = phoneNumberInput;
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
            mUsername = usernameInput;
            return true;
        }
    }


    private boolean confirmInput() {
        if (!validateEmail() | !validatePassword() | !validateUsername() | !validatePhoneNumber())
        {
            return false;
        }
        else
        {
            return true;
        }
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
            mPassword = passwordInput;
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
            mEmail = emailInput;
            return true;
        }
    }


    private void initView() {
        txtView_welcomeMsg = findViewById(R.id.txtView_welcomeMsg);
        txtView_existingAcc = findViewById(R.id.txtView_existingAcc);
        txtView_altConnections = findViewById(R.id.txtView_altConnections);
        txtView_altSignIn = findViewById(R.id.txtView_altSignIn);

        editTxt_username = findViewById(R.id.editTxt_username);
        editTxt_mobileNo = findViewById(R.id.editTxt_mobileNo);
        editTxt_email = findViewById(R.id.editTxt_email);
        editTxt_password = findViewById(R.id.editTxt_password);
        editTxt_confirmPassword = findViewById(R.id.editTxt_confirmPassword);

        img_facebook = findViewById(R.id.img_facebook);
        img_google = findViewById(R.id.img_google);
        img_linkedin = findViewById(R.id.img_linkedin);


        btn_register = findViewById(R.id.btn_register);
    }


}