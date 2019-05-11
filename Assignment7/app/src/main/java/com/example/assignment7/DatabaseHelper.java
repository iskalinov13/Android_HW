package com.example.assignment7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "place7.db";
    public static final String DATABASE_TABLE = "place";
    public static final String ID= "_id";
    public static final String LOCATION_NAME = "_nam";
    public static final String LOCATION_DESCRIPTION= "_desc";
    public static final String LATITUDE = "_lat";
    public static final String LONGITUDE= "_long";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DATABASE_TABLE+"("+
                ID+" integer primary key autoincrement, "+LOCATION_NAME+" text not null, "+LOCATION_DESCRIPTION+" text not null, "+LATITUDE+" text not null, "+LONGITUDE+" text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+DATABASE_TABLE);
        onCreate(db);
    }
    public void insertPlace(Place place){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOCATION_NAME,place.getLocation_name());
        cv.put(LOCATION_DESCRIPTION,place.getLocation_description());
        cv.put(LATITUDE,place.getLatitude());
        cv.put(LONGITUDE,place.getLongitude());
        db.insert(DATABASE_TABLE,null,cv);
    }

    public ArrayList<Place> getPlaces(){
        ArrayList<Place> places = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String all_students_query = "select * from " + DATABASE_TABLE;
        Cursor c = db.rawQuery(all_students_query,null);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            places.add(new Place(c.getString(c.getColumnIndex(LOCATION_NAME)),c.getString(c.getColumnIndex(LOCATION_DESCRIPTION)),c.getString(c.getColumnIndex(LATITUDE)),c.getString(c.getColumnIndex(LONGITUDE))));
        }
        return  places;
    }


}
