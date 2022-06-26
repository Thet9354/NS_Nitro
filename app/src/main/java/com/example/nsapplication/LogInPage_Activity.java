package com.example.nsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.regex.Pattern;

public class LogInPage_Activity extends AppCompatActivity {

    private EditText editTxt_SignInNIRC, editTxt_SignInMobileNo, editTxt_SignInpassword;
    private ImageView img_facebook, img_google, img_linkedin;
    private Button btn_signIn;

    private String NIRCInput;
    private String phoneNumberInput;
    private String passwordInput;

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;


    public static final String PROVIDER_ID = "firebase";
    private static final int RC_GOOGLE_SIGN_IN = 1000;
    private static final String TAG1 = "Facebook";

    FirebaseDatabase database = FirebaseDatabase.getInstance("enter your firebase link here");
    DatabaseReference databaseReference = database.getReference().child("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        //--> Google Sign in Integration with Firebase
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handlerFacebookAccessToken(loginResult.getAccessToken());
                        Intent intent = new Intent(LogInPage_Activity.this, Main_MenuPage_Activity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LogInPage_Activity.this, "Registration cancelled", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null)
        {
            Intent intent = new Intent(getApplicationContext(), Main_MenuPage_Activity.class);
            startActivity(intent);
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        // Passing the activity result back to the Facebook SDK
//        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
//
//        //--> For Google
//        if (requestCode==1000)
//        {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                task.getResult(ApiException.class);
//                Intent intent = new Intent(LogInPage_Activity.this, Main_MenuPage_Activity.class);
//                startActivity(intent);
//                finish();
//            }catch (ApiException e)
//            {
//                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        }


        if (requestCode == RC_GOOGLE_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            //--> For Google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(LogInPage_Activity.this, Main_MenuPage_Activity.class);
                startActivity(intent);
                finish();
            }catch (ApiException e)
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }else{
            //If not request code is RC_SIGN_IN it must be facebook
            // Passing the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
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
                            Toast.makeText(LogInPage_Activity.this, "Sign In successful", Toast.LENGTH_SHORT).show();

                            // Lead to the Main Page of the app
                            Intent intent = new Intent(LogInPage_Activity.this, Main_MenuPage_Activity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(LogInPage_Activity.this, "Sign In unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void pageDirectories() {
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoValidation();
            }
        });

        /** OnClickListener for img_facebook **/
        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LogInPage_Activity.this, Arrays.asList("public_profile"));
            }
        });

        /** OnClickListener for img_google **/
        img_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = gsc.getSignInIntent();
                startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
            }
        });
    }

    private void infoValidation() {
        validateNRIC();
        validateMobileNumber();
        validatePassword();
        addData();
    }

    private void addData() {
        if (!validateNRIC() | !validateMobileNumber() | !validatePassword())
        {
            return;
        }
        else
        {
            // Continue from here after lunch
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // checking if NRIC exist in firebase database
                    if (snapshot.hasChild(NIRCInput))
                    {
                        // NRIC exist in firebase database
                        // now getting password of user from firebase data and match if with user entered password and mobile number

                        final String getPassword = snapshot.child(NIRCInput).child("password").getValue(String.class);
                        final String getMobileNumber = snapshot.child(NIRCInput).child("mobile number").getValue(String.class);

                        if ((getPassword.equals(passwordInput)) && getMobileNumber.equals(phoneNumberInput))
                        {
                            // Open Main Menu Page for the user
                            Toast.makeText(LogInPage_Activity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogInPage_Activity.this, Main_MenuPage_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(LogInPage_Activity.this, "Log In unsuccessful, please check your password and mobile number", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(LogInPage_Activity.this, "Log In unsuccessful, please check your NRIC", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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

        passwordInput = editTxt_SignInpassword.getText().toString().trim();

        if (passwordInput.isEmpty())
        {
            editTxt_SignInpassword.setError("Field can not be empty");
            return false;
        }


        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches())
        {
            editTxt_SignInpassword.setError("Password is too weak");
            return false;
        }
        else
        {
            editTxt_SignInpassword.setError(null);
            return true;
        }
    }

    private boolean validateMobileNumber() {
        // defining our own mobile number pattern
        final Pattern Phone_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{7,}" +                // at least 4 characters
                        "$");

        phoneNumberInput = editTxt_SignInMobileNo.getText().toString().trim();

        if (phoneNumberInput.isEmpty())
        {
            editTxt_SignInMobileNo.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.PHONE.matcher(phoneNumberInput).matches())
        {
            editTxt_SignInMobileNo.setError("Please enter a valid mobile number");
            return false;
        }
        else
        {
            editTxt_SignInMobileNo.setError(null);
            return true;
        }
    }



    private boolean validateNRIC() {
        // Defining our own NIRC pattern
        final Pattern NIRC_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{6,}" +                // at least 9 characters
                        "$");

        // Extract input from EditText
        NIRCInput = editTxt_SignInNIRC.getText().toString().trim();

        if (NIRCInput.isEmpty())
        {
            editTxt_SignInNIRC.setError("Field can not be empty");
            return false;
        }
        // if fullname matches the pattern
        // it will display an error message "Invalid Name"
        else if (NIRC_PATTERN.matcher(NIRCInput).matches())
        {
            editTxt_SignInNIRC.setError("Invalid NIRC No.");
            return false;
        }
        else
        {
            editTxt_SignInNIRC.setError(null);
            return true;
        }
    }

    private void initView() {

        editTxt_SignInNIRC = findViewById(R.id.editTxt_SignInNIRC);
        editTxt_SignInMobileNo = findViewById(R.id.editTxt_SignInMobileNo);
        editTxt_SignInpassword = findViewById(R.id.editTxt_SignInpassword);

        img_facebook = findViewById(R.id.img_facebook);
        img_google = findViewById(R.id.img_google);
        img_linkedin = findViewById(R.id.img_linkedin);

        btn_signIn = findViewById(R.id.btn_signIn);
    }
}