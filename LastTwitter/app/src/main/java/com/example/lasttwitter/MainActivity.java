package com.example.lasttwitter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;




public class MainActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private FloatingActionButton addPostFloatingActionButton, goToProfilePage;
    BottomNavigationView bottomNavigationView;

    ArrayList<Post> posts, users;
    ArrayList<String> username;
    PostAdapter adater;
    ListView lv;
    DatabaseReference databasePost, databaseUser;
    Dialog dialog;


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;

    public static String userName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        databasePost = FirebaseDatabase.getInstance().getReference("posts");
        databaseUser = FirebaseDatabase.getInstance().getReference("users");

        posts = new ArrayList<>();
        users = new ArrayList<>();
        username = new ArrayList<>();
        lv=findViewById(R.id.listView_post);
        //ButterKnife.bind(this);

        //fragment instance
        addPostFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_post_floating_action_button);
        goToProfilePage = (FloatingActionButton) findViewById(R.id.profile_page);


        setAnimatedTranslatedView();
        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.post_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            sentToLoginActivity();
        }


        addPostFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        goToProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToProfileActivity();
            }
        });

        dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText postContent = (EditText)dialog.findViewById(R.id.dialog_post_content);
                if(!TextUtils.isEmpty(postContent.getText().toString())){
                    addPost(postContent.getText().toString());
                }
                else{
                    Toast.makeText(MainActivity.this, "Post shoud not be empty", Toast.LENGTH_SHORT).show();
                }
                postContent.setText("");
                dialog.cancel();
            }

        });

        dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText postContent = (EditText)dialog.findViewById(R.id.dialog_post_content);
                postContent.getText().clear();
                dialog.cancel();
            }

        });


        //replaceFragment(homeFragment);
        //getBottomNavigationItemSelection();
    }

    private void addPost(String content) {

        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd-MM");
        String s = formatter.format(date);
        String postId = databasePost.push().getKey();

        System.out.println(userName+"Ozgermedi");
        String userName = "";
        for(int i = 0; user.getEmail().charAt(i)!='@';i++){
           userName = userName + user.getEmail().charAt(i);
        }
        Post post = new Post(postId, userName, s, content);

        databasePost.child(postId).setValue(post);

        Toast.makeText(getApplicationContext(), "Tweet was successful"+postId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databasePost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot p : dataSnapshot.getChildren()){

                    Post post = p.getValue(Post.class);
                    posts.add(post);

                }
                Collections.reverse(posts);
                adater=new PostAdapter(MainActivity.this,posts);
                lv.setAdapter(adater);
                adater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void getCurrentUserName(String uid) {
        databaseUser.child(String.format("users/%s/userName", uid))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // 3. Set the public variable in your class equal to value retrieved
                        //userName = dataSnapshot.getValue(String.class);
                        // 4a. EDIT: now that your fetch succeeded, you can trigger whatever else you need to run in your class that uses `yourNameVariable`, and you can be sure `yourNameVariable` is not null.
                        sayHiToMe();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
    }

    // (part of step 4a)
    public void sayHiToMe() {
        Log.d("", "hi there, " + userName);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout_menu:
                logout();

                return true;

            case R.id.action_account_setting_menu:
                sendToProfileActivity();
                return true;

            default:
                return false;
        }
    }

    private void sendToProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
        overridePendingTransition(0, 0);
    }

    private void logout() {
        firebaseAuth.signOut();
        sentToLoginActivity();
    }

    private void sentToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }


    public void onViewClickedAddPost(View v) {
        dialog.show();
    }







    private void setAnimatedTranslatedView() {
        TranslateAnim.setAnimation(addPostFloatingActionButton, this, "top");
        TranslateAnim.setAnimation(goToProfilePage, this, "bottom");
    }
}