package com.example.notes;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Dialog view_or_create_dialog;
    Dialog delete_dialog;
    ArrayList<Note> notes;
    NoteAdapter adapter;
    DatabaseHelper databaseHelper;
    ImageView img_delete;

    private int x = 0;
    private int y = 0;
    private static int current_position=0;
    private boolean new_note_is_opened = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv= findViewById(R.id.listViewOfNotes);
        databaseHelper = new DatabaseHelper(this);
        notes = new ArrayList<>();
        notes = databaseHelper.getNotes();
        adapter = new NoteAdapter(this,notes);
        lv.setAdapter(adapter);


        delete_dialog = new Dialog(this);
        delete_dialog.setContentView(R.layout.delete_dialog);
        delete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        view_or_create_dialog = new Dialog(this);
        view_or_create_dialog.setContentView(R.layout.view_or_create_dialog);
        view_or_create_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        delete_dialog.findViewById(R.id.delete_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    System.out.println( notes.get(current_position).getContent()+" CurrentPos"+databaseHelper.numberOfRows());

                    databaseHelper.deleteNote(notes.get(current_position));
                    notes.remove(current_position);
                    current_position--;


                delete_dialog.cancel();
                onResume();
            }

        });

        delete_dialog.findViewById(R.id.delete_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_dialog.cancel();
                System.out.println(databaseHelper.numberOfRows());

            }

        });

        view_or_create_dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText  = (EditText)view_or_create_dialog.findViewById(R.id.dialog_notes_content);
                String date = new SimpleDateFormat("yyyy/MM/dd", Locale.CANADA.getDefault()).format(new Date());


                if(notes.size()!=0&&current_position<databaseHelper.numberOfRows()){
                    Note note = notes.get(current_position);
                    note.setNote(editText.getText().toString());
                    note.setColor(note.getColor());
                    note.setDate(note.getDate());
                    databaseHelper.updateNote(note);
                    notes.set(current_position,note);
                }
                else{
                    if(editText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Shoud not be empty!!!",Toast.LENGTH_SHORT).show();
                        current_position--;

                    }
                    else{
                        long id = databaseHelper.insertNote(editText.getText().toString(),"#FFFF90",date);
                        Note n = databaseHelper.getNote(id);
                        if(n!=null){
                            notes.add(n);
                        }
                    }

                }

                view_or_create_dialog.cancel();
                onResume();
            }

        });

        view_or_create_dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_or_create_dialog.cancel();
               onResume();

            }

        });




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x = adapter.getX();
                y = adapter.getY();
                System.out.println("POSITION"+x+y);
                current_position = position;


                if(x>910){
                    delete_dialog.show();

                }else if(x==0&&y==0){
                    final EditText editText  = (EditText)view_or_create_dialog.findViewById(R.id.dialog_notes_content);
                    Note note = notes.get(current_position);
                    editText.setText(note.getContent());
                    view_or_create_dialog.show();
                }
                else if(x<910&&x!=0){
                    System.out.println("I am here!!!");
                    Note note = notes.get(current_position);
                    String current_color = note.getColor();
                    String content = note.getContent();
                    String date = note.getDate();


                    System.out.println(current_color);
                    if(current_color.equals("#FFFF90")){
                        current_color = "#EF6945";
                    }
                    else if(current_color.equals("#EF6945")){
                        current_color = "#5CB68E";
                    }
                    else if(current_color.equals("#5CB68E")){
                        current_color = "#8D78B6";
                    }
                    else if(current_color.equals("#8D78B6")){
                        current_color = "#48A3D1";
                    }
                    else if(current_color.equals("#48A3D1")){
                        current_color = "#D07AA6";
                    }
                    else if(current_color.equals("#D07AA6")){
                        current_color = "#FFFF90";
                    }


                    note.setNote(content);
                    note.setColor(current_color);
                    note.setDate(date);
                    databaseHelper.updateNote(note);
                    notes.set(current_position,note);

                    onResume();


                }




            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addNote:
                EditText editText = (EditText) view_or_create_dialog.findViewById(R.id.dialog_notes_content);
                editText.setText("");
                System.out.println("Item is selected"+current_position);
                System.out.println(databaseHelper.numberOfRows());
                current_position = databaseHelper.numberOfRows();
                current_position++;
                view_or_create_dialog.show();

                break;
        }


        return  true;
    }


    protected void onResume() {
        super.onResume();
        notes = databaseHelper.getNotes();
        adapter.notifyDataSetChanged();
        adapter = new NoteAdapter(this,notes);
        lv.setAdapter(adapter);

        if(databaseHelper.numberOfRows()!=0){
            TextView tx = (TextView)findViewById(R.id.no_notes);
            tx.setText("");
            tx.setTextSize(0);

            LinearLayout lt = (LinearLayout)findViewById(R.id.activity_main_layout);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFF4"));
            lt.setBackground(colorDrawable);
        }
        else{
            TextView tx = (TextView)findViewById(R.id.no_notes);
            tx.setText("You don't have notes.");
            tx.setTextSize(18);

            LinearLayout lt = (LinearLayout)findViewById(R.id.activity_main_layout);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFB0"));
            lt.setBackground(colorDrawable);
        }

    }


}
