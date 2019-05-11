package com.example.lasttwitter;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class RegistrationActivity extends AppCompatActivity{

    private ImageView profileImageView;
    private EditText regUsername;
    private EditText regEmail;
    private EditText regPassword;
    private EditText confirmPassword;
    private Button regButton;
    private Button alreadyAccountButton;
    AppCompatCheckBox regShowPasswordCheckbox;

    private Uri profileImageUri = null;
    private Bitmap compressedImageBitmap;

    private static final int REQUEST_READ_EXTERNAL_PERMISSION_CODE = 4512;
    private boolean isChangedProfileImage = false;
    private String userId;

    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //ButterKnife.bind(this);
        profileImageView = (ImageView)findViewById(R.id.welcome_reg_icon);
        regUsername = (EditText)findViewById(R.id.reg_username);
        regEmail = (EditText)findViewById(R.id.reg_email);
        regPassword = (EditText)findViewById(R.id.reg_password);
        confirmPassword = (EditText)findViewById(R.id.confirm_password);
        regButton = (Button) findViewById(R.id.reg_button);
        alreadyAccountButton = (Button) findViewById(R.id.already_account_button);
        regShowPasswordCheckbox = (AppCompatCheckBox)findViewById(R.id.reg_show_password_checkbox);


        setAnimatedTranslatedView();
        showOrHidePasswordField();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference("users");

        if(firebaseAuth.getCurrentUser()!=null){
            userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        }


    }

    private void setAnimatedTranslatedView() {
        TranslateAnim.setAnimation(profileImageView, this, "top");
        TranslateAnim.setAnimation(regUsername, this, "right");
        TranslateAnim.setAnimation(regEmail, this, "left");
        TranslateAnim.setAnimation(regPassword, this, "right");
        TranslateAnim.setAnimation(confirmPassword, this, "left");
        TranslateAnim.setAnimation(regShowPasswordCheckbox, this, "right");
        TranslateAnim.setAnimation(regButton, this, "bottom");
        TranslateAnim.setAnimation(alreadyAccountButton, this, "bottom");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            //sendToMainActivity();
        }
    }

    public void onCreateAccountClicked(View v) {
        final String userName = regUsername.getText().toString();
        final String userEmail = regEmail.getText().toString();
        final String userPassword = regPassword.getText().toString();
        final String userConfirmPassword = confirmPassword.getText().toString();


        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userEmail)&& !TextUtils.isEmpty(userPassword)&& !TextUtils.isEmpty(userConfirmPassword)) {
            if(userPassword.equals(userConfirmPassword)){
                firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    String userID = database.push().getKey();
                                    UserInfo user = new UserInfo(userId,userName,userEmail);
                                    database.child(userID).setValue(user);


                                    FirebaseUser fireuser = firebaseAuth.getCurrentUser();
                                    fireuser.sendEmailVerification();
                                    sendToLoginActivity();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                // ...
                            }
                        });
            }
            else{
                Toast.makeText(this, "Password and confirm password mismatched", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "UserName  can not be empty", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    private void sendToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void onViewClickedHaveAnAccount(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    private void showOrHidePasswordField() {
        regShowPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    regPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    regPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
