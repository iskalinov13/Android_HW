package com.example.lasttwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ImageView welcomeLoginIcon;
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private Button loginRegButton;
    private TextView forgotPassword;
    private AppCompatCheckBox loginShowPasswordCheckbox;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ButterKnife.bind(this);

        welcomeLoginIcon =(ImageView)findViewById(R.id.welcome_login_icon);
        loginEmail = (EditText)findViewById(R.id.login_email);
        loginPassword = (EditText)findViewById(R.id.login_password);
        loginButton =(Button)findViewById(R.id.login_button);
        loginRegButton = (Button)findViewById(R.id.login_reg_button);
        forgotPassword = (TextView)findViewById(R.id.forgot_password);
        loginShowPasswordCheckbox = (AppCompatCheckBox)findViewById(R.id.login_show_password_checkbox);

        setAnimatedTranslatedView();
        showOrHidePasswordField();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setAnimatedTranslatedView() {
        TranslateAnim.setAnimation(welcomeLoginIcon, this, "top");
        TranslateAnim.setAnimation(loginEmail, this, "left");
        TranslateAnim.setAnimation(loginPassword, this, "right");
        TranslateAnim.setAnimation(loginShowPasswordCheckbox, this, "left");
        TranslateAnim.setAnimation(loginButton, this, "bottom");
        TranslateAnim.setAnimation(forgotPassword, this, "bottom");
        TranslateAnim.setAnimation(loginRegButton, this, "bottom");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null&& currentUser.isEmailVerified()) {
            sendToMainActivity();
        }
    }

    public void onViewClicked(View v) {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            //CircularProgressBar progressBar = new CircularProgressBar(this);
            //final AlertDialog alertDialog = progressBar.setCircularProgressBar();


            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user != null){
                        if (user.isEmailVerified()) {
                            if (task.isSuccessful()) {
                                sendToMainActivity();
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed."  , Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Verify your email or you haven't yet registered" , Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "You haven't yet registered" , Toast.LENGTH_LONG).show();
                    }

                }
            });
        } else {
            Toast.makeText(this, "Empty email and password", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void onRegViewClicked(View v) {
        startActivity(new Intent(this, RegistrationActivity.class));
        overridePendingTransition(0, 0);
    }


    private void showOrHidePasswordField() {
        loginShowPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }
}