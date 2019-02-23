package com.example.notes;

public class Note {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_COLOR= "color";
    public static final String COLUMN_DATE = "date";

    private int id;
    private String note;
    private String color;
    private String date;



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CONTENT + " TEXT,"
                    +COLUMN_COLOR + " TEXT,"
                    + COLUMN_DATE + " TEXT"
                    + ")";

    public Note(){}
    public Note(int id, String note, String color, String date) {
        this.id = id;
        this.note = note;
        this.color = color;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return note;
    }

    public String getColor() {
        return color;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
