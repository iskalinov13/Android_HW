package com.example.studentlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {
    EditText name,gpa;
    Button add;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        name = findViewById(R.id.editText);
        gpa= findViewById(R.id.editText2);
        add =findViewById(R.id.button);
        databaseHelper =new DatabaseHelper(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student =  new Student(name.getText().toString(),gpa.getText().toString());
                databaseHelper.insertStudent(student);
                finish();
            }
        });
    }
}
