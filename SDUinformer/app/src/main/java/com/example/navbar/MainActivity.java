package com.example.navbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String tag;
    private GridLayout mainGird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mainGird = (GridLayout) findViewById(R.id.mainGrid);
        //setSingleEvent(mainGird);
        

        drawerLayout = (DrawerLayout) findViewById(R.id.mydrawerlayout);
        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.root_layout,new Report1())
                    .commit();
            navigationView.setCheckedItem(R.id.about);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        main_page main_page = new main_page();
        FragmentManager main_page_fragment = getSupportFragmentManager();
        tag="MAIN_FRAGMENT";
        main_page_fragment.beginTransaction()
                .replace(R.id.root_layout,main_page,tag)
                .commit();
    }



    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(actionBarDrawerToggle.onOptionsItemSelected(menuItem)){
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }




    public void clicklayout1(View v){
        Report1 report1 = new Report1();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.root_layout,report1,report1.getTag())
                .commit();
        System.out.println("It pressed!!!");


    }

    public void clicklayout2(View v){
        Report2 report2 = new Report2();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.root_layout,report2,report2.getTag())
                .commit();
        System.out.println("It pressed!!!");

    }

    public void clicklayout3(View v){
        Report3 report3 = new Report3();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.root_layout,report3,report3.getTag())
                .commit();
        System.out.println("It pressed!!!");

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.about:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root_layout,new main_page())
                        .commit();
                break;
            case R.id.academics:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root_layout,new Academics())
                        .commit();
                break;
            case R.id.inoffice:

                main_page myFragment = (main_page)getSupportFragmentManager().findFragmentByTag(tag);

                if (myFragment != null && myFragment.isVisible()) {
                    ScrollView s = (ScrollView) findViewById(R.id.scrollView);
                    s.scrollTo(0,8000);
                }
                else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.root_layout,new mainNew())
                            .commit();
                }
                break;

            case R.id.lifeatsdu:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root_layout,new Gallery())
                        .commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void conctactoptionsClicked(View v){
        switch (v.getId()) {
            case R.id.call:
                String number = "87007001098";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(intent);
                break;
            case R.id.facebook:
                String url = "www.facebook.com/sdukz";
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("https://" + url));
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(intent1);
                break;

            case R.id.twitter:
                String url1 = "twitter.com/sdukz";
                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("https://" + url1));
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(intent2);
                break;

            case R.id.instagram:
                String url2 = "www.instagram.com/sdukz/";
                Intent intent4 = new Intent(Intent.ACTION_VIEW);
                intent4.setData(Uri.parse("https://" + url2));
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(intent4);
                break;

        }
    }

    public void newsOnclick(View v){
        switch (v.getId()) {
            case R.id.lltnew1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root_layout,new New1())
                        .commit();
                break;
            case R.id.lltnew2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root_layout,new New2())
                        .commit();
                break;
            case R.id.lltnew3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root_layout,new New3())
                        .commit();
                break;

            case R.id.lltnew4:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.root_layout,new New4())
                        .commit();
                break;

        }
    }



}
