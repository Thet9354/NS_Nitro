package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingsPage_Activity extends AppCompatActivity {

    private ImageView btn_back, securityIcon, btn_Security, textSizeIcon, btn_textSIze, languageIcon, btn_language,
            feedbackIcon, btn_feedback, aboutUsIcon, btn_aboutUs, faqIcon, btn_faq, logOutIcon, btn_logOut;

    private RelativeLayout rel_security, rel_textSize, rel_language, rel_feedback, rel_aboutUs, rel_faq, rel_logOut;

    androidx.appcompat.widget.AppCompatButton btn_editProfile;

    private androidx.appcompat.widget.SwitchCompat nightMode_switch, notifications_Switch, privateAcc_switch;

    private TextView txtView_security, txtView_textSize, txtView_languages, txtView_feedback, txtView_aboutUs, txtView_faq, txtView_logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        initView();

        pageDirectories();
    }

    private void pageDirectories() {



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
            }
        });

        rel_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the security and privacy page
            }
        });

        txtView_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the security and privacy page
            }
        });

        btn_Security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the security and privacy page
            }
        });


        /** OnCLickListener for Text Size **/
        textSizeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Text Size page
            }
        });

        rel_textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Text Size page
            }
        });

        txtView_textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Text Size page
            }
        });

        btn_textSIze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the Text Size page
            }
        });


        /** OnCLickListener for Language **/
        languageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the language page
            }
        });

        rel_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the language page
            }
        });

        txtView_languages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the language page
            }
        });

        btn_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the language page
            }
        });


        /** OnCLickListener for Feedback **/
        feedbackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the feedback page
            }
        });

        rel_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the feedback page
            }
        });

        txtView_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the feedback page
            }
        });

        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the feedback page
            }
        });


        /** OnCLickListener for About Us **/
        aboutUsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the About Us page
            }
        });

        rel_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the About Us page
            }
        });

        txtView_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the About Us page
            }
        });

        btn_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the About Us page
            }
        });


        /** OnCLickListener for FAQs **/
        faqIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the FAQs page
            }
        });

        rel_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the FAQs page
            }
        });

        txtView_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the FAQs page
            }
        });

        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add the intent leading to the FAQs page
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
        faqIcon = findViewById(R.id.faqIcon);
        btn_faq = findViewById(R.id.btn_faq);
        logOutIcon = findViewById(R.id.logOutIcon);
        btn_logOut = findViewById(R.id.btn_logOut);
        btn_editProfile = findViewById(R.id.btn_editProfile);

        // RelativeLayout
        rel_security = findViewById(R.id.rel_security);
        rel_textSize = findViewById(R.id.rel_textSize);
        rel_language = findViewById(R.id.rel_language);
        rel_feedback = findViewById(R.id.rel_feedback);
        rel_aboutUs = findViewById(R.id.rel_aboutUs);
        rel_faq = findViewById(R.id.rel_faq);
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
        txtView_faq = findViewById(R.id.txtView_faq);
        txtView_logOut = findViewById(R.id.txtView_logOut);
    }
}