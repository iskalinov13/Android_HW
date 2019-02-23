package com.example.studentlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Student> students;
    CustomAdapter adapter;;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        students = new ArrayList<>();
        students = databaseHelper.getStudents();
        adapter = new CustomAdapter(this,students);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(intent);
                break;
        }
        return  true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        students = databaseHelper.getStudents();
        adapter = new CustomAdapter(this,students);
        lv.setAdapter(adapter);
    }
}
