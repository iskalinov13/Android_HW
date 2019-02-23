package com.example.studentlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Student> {
    ArrayList<Student> students;
    public CustomAdapter(Context context, ArrayList<Student> students) {
        super(context,R.layout.list_item);
        this.students = students;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_view = layoutInflater.inflate(R.layout.list_item,parent,false);
        TextView name = custom_view.findViewById(R.id.textView);
        TextView gpa = custom_view.findViewById(R.id.textView2);

        name.setText(students.get(position).getName());
        gpa.setText(students.get(position).getGpa());


        return custom_view;
    }

    @Override
    public int getCount() {
        return students.size();
    }
}
