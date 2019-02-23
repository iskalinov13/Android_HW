package com.example.studentlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "students.db";
    public static final String DATABASE_TABLE = "students";
    public static final String ID= "_id";
    public static final String NAME = "_name";
    public static final String GPA= "_gpa";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DATABASE_TABLE+"("+
                ID+" integer primary key autoincrement, "+NAME+" text not null, "+GPA+" text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+DATABASE_TABLE);
        onCreate(db);
    }
    public void insertStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME,student.getName());
        cv.put(GPA,student.getGpa());
        db.insert(DATABASE_TABLE,null,cv);
    }

    public ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String all_students_query = "select * from " + DATABASE_TABLE;
        Cursor c = db.rawQuery(all_students_query,null);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            students.add(new Student(c.getString(c.getColumnIndex(NAME)),c.getString(c.getColumnIndex(GPA))));
        }
        return  students;
    }


}
