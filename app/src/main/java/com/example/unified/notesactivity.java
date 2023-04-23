package com.example.unified;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class notesactivity extends AppCompatActivity {
    FloatingActionButton Add_notebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesactivity);
        Add_notebtn=findViewById(R.id.floatingactionbutton);
        Add_notebtn.setOnClickListener((v)->startActivity(new Intent(notesactivity.this,Addnotedetails.class)));
    }
}