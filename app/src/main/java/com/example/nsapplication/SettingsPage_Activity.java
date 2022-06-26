package com.example.nsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class SettingsPage_Activity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("Enter your link here");
    DatabaseReference databaseReference = database.getReference().child("users");

    private ImageView btn_back, securityIcon, btn_Security, textSizeIcon, btn_textSIze, languageIcon, btn_language,
            feedbackIcon, btn_feedback, aboutUsIcon, btn_aboutUs, additionalIcon, btn_additionalResources, logOutIcon, btn_logOut;

    private RelativeLayout rel_security, rel_textSize, rel_language, rel_feedback, rel_aboutUs, rel_additionalResources, rel_logOut;

    private LinearLayout linearLayout_NNP, linearLayout_STL, linearLayout_FAF, linearLayout_L;

    androidx.appcompat.widget.AppCompatButton btn_editProfile;

    private androidx.appcompat.widget.SwitchCompat nightMode_switch, notifications_Switch, privateAcc_switch;

    private TextView txtView_security, txtView_textSize, txtView_languages, txtView_feedback, txtView_aboutUs, txtView_additionalResources, txtView_logOut,
            txtView_nightMode, txtView_notification, txtView_privateAccount, txtView_settings, txtView_FullName;


    // OneSignal Cloud Messaging
    private static final String ONESIGNAL_APP_ID = "842b345f-b122-4ae4-9ec2-020e803cdcad";


    // Google stuff lolz
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    private FirebaseAuth mAuth;

    private LinearLayout parentView;
    private TextView themeTV, titleTV;

    private userSettingModel settingModel;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String selected;

    private String CHECKEDITEM = "checked_item";

    private String personName;
    private String personEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        //--> Google Sign in Integration with Firebase
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        settingModel = new userSettingModel();

        initView();

        loadSharedPreferences();

        pageDirectories();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null)
        {
            // Adjust later
            personName = account.getDisplayName();
            personEmail = account.getEmail();
            txtView_FullName.setText(personName);
        }
        else
            onResume();
    }

    private void pageDirectories() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /** OnCLickListener for Night Mode switch **/
        nightMode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    // When switch is on, change to dark mode
                    settingModel.setCustomTheme(userSettingModel.DARK_THEME);

                // When light mode on, need change color of everything in all of the other Activity page
                // Need to add alot more (color change of the text and design of this and other activity pages too)
                // try finish tmr
                else
                {
                    settingModel.setCustomTheme(userSettingModel.LIGHT_THEME);
                }
                editor = getSharedPreferences(userSettingModel.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(userSettingModel.CUSTOM_THEME, settingModel.getCustomTheme());
                editor.apply();
                updateView();

            }
        });

        /** OnCheckedChangeListener for notification switch **/
        notifications_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    oneSignalSetup();
                }
                else
                    onResume();
            }
        });

        /** onCheckedListener for **/
        privateAcc_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    Toast.makeText(SettingsPage_Activity.this, "Account has been set to private", Toast.LENGTH_LONG).show();
                    privateAcc_switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.purple_500));
                }
                else
                {
                    Toast.makeText(SettingsPage_Activity.this, "Account has been set to non private", Toast.LENGTH_LONG).show();
                    privateAcc_switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.gray));
                }
            }
        });

        /** OnClickListener for Edit Profile**/
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Edit Profile page
            }
        });

        /** OnCLickListener for Security & Privacy **/
        securityIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the security and privacy page
                startActivity(new Intent(SettingsPage_Activity.this, SecurityNPrivacy_Activity.class));
            }
        });

        rel_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the security and privacy page
                startActivity(new Intent(SettingsPage_Activity.this, SecurityNPrivacy_Activity.class));
            }
        });

        txtView_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the security and privacy page
                startActivity(new Intent(SettingsPage_Activity.this, SecurityNPrivacy_Activity.class));
            }
        });

        btn_Security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the security and privacy page
                startActivity(new Intent(SettingsPage_Activity.this, SecurityNPrivacy_Activity.class));
            }
        });


        /** OnCLickListener for Text Size **/
        textSizeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Text Size page
                startActivity(new Intent(getApplicationContext(), TextSize_Activity.class));
            }
        });

        rel_textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Text Size page
                startActivity(new Intent(getApplicationContext(), TextSize_Activity.class));
            }
        });

        txtView_textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Text Size page
                startActivity(new Intent(getApplicationContext(), TextSize_Activity.class));
            }
        });

        btn_textSIze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Text Size page
                startActivity(new Intent(getApplicationContext(), TextSize_Activity.class));
            }
        });


        /** OnCLickListener for Language **/
        languageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the language page
                startActivity(new Intent(getApplicationContext(), Language_Activity.class));
            }
        });

        rel_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the language page
                startActivity(new Intent(getApplicationContext(), Language_Activity.class));
            }
        });

        txtView_languages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the language page
                startActivity(new Intent(getApplicationContext(), Language_Activity.class));
            }
        });

        btn_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the language page
                startActivity(new Intent(getApplicationContext(), Language_Activity.class));
            }
        });


        /** OnCLickListener for Feedback **/
        feedbackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the feedback page
                startActivity(new Intent(getApplicationContext(), Feedback_Activity.class));
            }
        });

        rel_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the feedback page
                startActivity(new Intent(getApplicationContext(), Feedback_Activity.class));
            }
        });

        txtView_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the feedback page
                startActivity(new Intent(getApplicationContext(), Feedback_Activity.class));
            }
        });

        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the feedback page
                startActivity(new Intent(getApplicationContext(), Feedback_Activity.class));
            }
        });


        /** OnCLickListener for About Us **/
        aboutUsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the About Us page
                startActivity(new Intent(getApplicationContext(), AboutUs_Activity.class));
            }
        });

        rel_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the About Us page
                startActivity(new Intent(getApplicationContext(), AboutUs_Activity.class));
            }
        });

        txtView_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the About Us page
                startActivity(new Intent(getApplicationContext(), AboutUs_Activity.class));
            }
        });

        btn_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the About Us page
                startActivity(new Intent(getApplicationContext(), AboutUs_Activity.class));
            }
        });


        /** OnCLickListener for Additional resources **/
        additionalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdditionalResources_Activity.class));
            }
        });

        rel_additionalResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdditionalResources_Activity.class));
            }
        });

        txtView_additionalResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdditionalResources_Activity.class));
            }
        });

        btn_additionalResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdditionalResources_Activity.class));
            }
        });

        /** OnCLickListener for Log Out **/
        logOutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Log Out page
            }
        });

        rel_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Log Out page
            }
        });

        txtView_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Log Out page
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Log Out page
                googleSignOut();
                mAuth.signOut();
                startActivity(new Intent(SettingsPage_Activity.this, MainActivity.class));
                Toast.makeText(SettingsPage_Activity.this, "Successfully Logged out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void oneSignalSetup() {
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }

    private void updateView() {
        final Drawable purple_background = ContextCompat.getDrawable(this, R.drawable.purple_background);
        final Drawable white_standard_background = ContextCompat.getDrawable(this, R.drawable.white_standard_background);
        final Drawable round_back_white10_20 = ContextCompat.getDrawable(SettingsPage_Activity.this, R.drawable.round_back_white10_20);


        if (settingModel.getCustomTheme().equals(userSettingModel.DARK_THEME))
        {
            parentView.setBackground(purple_background);
            darkModeChanges();
            // Add if needed
        }
        else
        {
            parentView.setBackground(white_standard_background);
            lightModeChanges();
        }
    }

    private void darkModeChanges() {

        /** Text color changes for TextView **/
        txtView_nightMode.setTextColor(Color.WHITE);
        txtView_notification.setTextColor(Color.WHITE);
        txtView_privateAccount.setTextColor(Color.WHITE);
        txtView_security.setTextColor(Color.WHITE);
        txtView_textSize.setTextColor(Color.WHITE);
        txtView_languages.setTextColor(Color.WHITE);
        txtView_feedback.setTextColor(Color.WHITE);
        txtView_aboutUs.setTextColor(Color.WHITE);
        txtView_additionalResources.setTextColor(Color.WHITE);
        txtView_logOut.setTextColor(Color.WHITE);

        /** Src changes for ImageView **/
        btn_Security.setImageResource(R.drawable.right_arrow_icon);
        btn_textSIze.setImageResource(R.drawable.right_arrow_icon);
        btn_language.setImageResource(R.drawable.right_arrow_icon);
        btn_feedback.setImageResource(R.drawable.right_arrow_icon);
        btn_aboutUs.setImageResource(R.drawable.right_arrow_icon);
        btn_additionalResources.setImageResource(R.drawable.right_arrow_icon);
        btn_logOut.setImageResource(R.drawable.right_arrow_icon);

        /** Color changes for switches **/
        notifications_Switch.setThumbTintList(AppCompatResources.getColorStateList(this, R.color.teal_200));
//        notifications_Switch.setTrackTintList(AppCompatResources.getColorStateList(this, R.color.gray));

        privateAcc_switch.setThumbTintList(AppCompatResources.getColorStateList(this, R.color.purple));
//        privateAcc_switch.setTrackTintList(AppCompatResources.getColorStateList(this, R.color.gray));

        /** Background change for Linear Layouts**/
        linearLayout_NNP.setBackgroundResource(R.drawable.round_back_white10_20);
        linearLayout_STL.setBackgroundResource(R.drawable.round_back_white10_20);
        linearLayout_FAF.setBackgroundResource(R.drawable.round_back_white10_20);
        linearLayout_L.setBackgroundResource(R.drawable.round_back_white10_20);

        notifications_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    notifications_Switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.teal_200));
                }
                else
                    notifications_Switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.gray));
            }
        });

        privateAcc_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    privateAcc_switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.purple_500));
                else
                    privateAcc_switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.gray));

            }
        });
    }

    private void lightModeChanges() {

        /** Text color changes for TextView **/
        txtView_nightMode.setTextColor(Color.BLACK);
        txtView_notification.setTextColor(Color.BLACK);
        txtView_privateAccount.setTextColor(Color.BLACK);
        txtView_security.setTextColor(Color.BLACK);
        txtView_textSize.setTextColor(Color.BLACK);
        txtView_languages.setTextColor(Color.BLACK);
        txtView_feedback.setTextColor(Color.BLACK);
        txtView_aboutUs.setTextColor(Color.BLACK);
        txtView_additionalResources.setTextColor(Color.BLACK);
        txtView_logOut.setTextColor(Color.BLACK);

        /** Src changes for ImageView **/
        btn_Security.setImageResource(R.drawable.rigth_arrow_icon_black);
        btn_textSIze.setImageResource(R.drawable.rigth_arrow_icon_black);
        btn_language.setImageResource(R.drawable.rigth_arrow_icon_black);
        btn_feedback.setImageResource(R.drawable.rigth_arrow_icon_black);
        btn_aboutUs.setImageResource(R.drawable.rigth_arrow_icon_black);
        btn_additionalResources.setImageResource(R.drawable.rigth_arrow_icon_black);
        btn_logOut.setImageResource(R.drawable.rigth_arrow_icon_black);

        /** Color changes for switches **/
        notifications_Switch.setThumbTintList(AppCompatResources.getColorStateList(this, R.color.white));
        notifications_Switch.setTrackTintList(AppCompatResources.getColorStateList(this, R.color.gray));

        privateAcc_switch.setThumbTintList(AppCompatResources.getColorStateList(this, R.color.white));
        privateAcc_switch.setTrackTintList(AppCompatResources.getColorStateList(this, R.color.gray));

        /** Background change for Linear Layouts**/
        linearLayout_NNP.setBackgroundResource(R.drawable.round_back_black10_20);
        linearLayout_STL.setBackgroundResource(R.drawable.round_back_black10_20);
        linearLayout_FAF.setBackgroundResource(R.drawable.round_back_black10_20);
        linearLayout_L.setBackgroundResource(R.drawable.round_back_black10_20);

        notifications_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    notifications_Switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.teal_200));
                }
                else
                    notifications_Switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.gray));
            }
        });

        privateAcc_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    privateAcc_switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.purple_500));
                else
                    privateAcc_switch.setTrackTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.gray));

            }
        });
    }

    private void loadSharedPreferences() {
        sharedPreferences = getSharedPreferences(userSettingModel.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(userSettingModel.CUSTOM_THEME, userSettingModel.LIGHT_THEME);
        settingModel.setCustomTheme(theme);
        updateView();
    }

//    private void showDialog()
//    {
//        String[] themes = theme
//    }
//
//    private int getCheckedItem()
//    {
//        return sharedPreferences.getInt(CHECKEDITEM, 0);
//    }
//
//    private void setCheckedItem(int i)
//    {
//        editor.putInt(checkedItem, i);
//        editor.apply();
//
//    }

    private void googleSignOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void initView() {
        // ImageView
        btn_back = findViewById(R.id.btn_back);
        securityIcon = findViewById(R.id.securityIcon);
        btn_Security = findViewById(R.id.btn_Security);
        textSizeIcon = findViewById(R.id.textSizeIcon);
        btn_textSIze = findViewById(R.id.btn_textSIze);
        languageIcon = findViewById(R.id.languageIcon);
        btn_language = findViewById(R.id.btn_language);
        feedbackIcon = findViewById(R.id.feedbackIcon);
        btn_feedback = findViewById(R.id.btn_feedback);
        aboutUsIcon = findViewById(R.id.aboutUsIcon);
        btn_aboutUs = findViewById(R.id.btn_aboutUs);
        additionalIcon = findViewById(R.id.additionalIcon);
        btn_additionalResources = findViewById(R.id.btn_additionalResources);
        logOutIcon = findViewById(R.id.logOutIcon);
        btn_logOut = findViewById(R.id.btn_logOut);
        btn_editProfile = findViewById(R.id.btn_editProfile);

        // RelativeLayout
        rel_security = findViewById(R.id.rel_security);
        rel_textSize = findViewById(R.id.rel_textSize);
        rel_language = findViewById(R.id.rel_language);
        rel_feedback = findViewById(R.id.rel_feedback);
        rel_aboutUs = findViewById(R.id.rel_aboutUs);
        rel_additionalResources = findViewById(R.id.rel_additionalResources);
        rel_logOut = findViewById(R.id.rel_logOut);

        // androidx.appcompat.widget.SwitchCompat
        nightMode_switch = findViewById(R.id.nightMode_switch);
        notifications_Switch = findViewById(R.id.notifications_Switch);
        privateAcc_switch = findViewById(R.id.privateAcc_switch);

        // TextView
        txtView_security = findViewById(R.id.txtView_security);
        txtView_textSize = findViewById(R.id.txtView_textSize);
        txtView_languages = findViewById(R.id.txtView_languages);
        txtView_feedback = findViewById(R.id.txtView_feedback);
        txtView_aboutUs = findViewById(R.id.txtView_aboutUs);
        txtView_additionalResources = findViewById(R.id.txtView_additionalResources);
        txtView_logOut = findViewById(R.id.txtView_logOut);
        txtView_nightMode = findViewById(R.id.txtView_nightMode);
        txtView_notification = findViewById(R.id.txtView_notification);
        txtView_privateAccount = findViewById(R.id.txtView_privateAccount);
        txtView_settings = findViewById(R.id.txtView_settings);
        txtView_FullName = findViewById(R.id.txtView_FullName);


        linearLayout_NNP = findViewById(R.id.linearLayout_NNP);
        linearLayout_STL = findViewById(R.id.linearLayout_STL);
        linearLayout_FAF = findViewById(R.id.linearLayout_FAF);
        linearLayout_L = findViewById(R.id.linearLayout_L);



        parentView = findViewById(R.id.parentView);
    }
}