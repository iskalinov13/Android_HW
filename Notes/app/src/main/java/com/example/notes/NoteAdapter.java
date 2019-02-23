package com.example.notes;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> implements View.OnTouchListener {
    ArrayList<Note> notes;
    Dialog delete_dialog;
    DatabaseHelper databaseHelper;
    View view;
    int x = 0;
    int y = 0;
    int current_position;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context,R.layout.notes_list);
        this.notes = notes;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
         view = layoutInflater.inflate(R.layout.notes_list,parent,false);
        TextView date = view.findViewById(R.id.tv_date);
        TextView content = view.findViewById(R.id.tv_content);
        LinearLayout llt_content = view.findViewById(R.id.llt_content);

        LinearLayout llt_changerColor = view.findViewById(R.id.llt_changeColor);
        llt_changerColor.setOnTouchListener(this);
        //ColorDrawable color =( ColorDrawable) (view.findViewById(R.id.llt_content).getBackground());

        date.setText(notes.get(position).getDate());
        content.setText(notes.get(position).getContent());
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(notes.get(position).getColor()));
        llt_content.setBackground(colorDrawable);

        databaseHelper = new DatabaseHelper(getContext());






        return view;
    }

    @Override
    public int getCount() {
        return notes.size();
    }



    public int getX(){
        return this.x;
    }
    public int getY(){
        return  this.y;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.x = (int) event.getX();
        this.y = (int) event.getY();
        System.out.println(("OnTouchDemo X==" + String.valueOf(x) + " Y==" + String.valueOf(y)));
        return false;
    }


}
