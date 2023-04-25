package com.example.unified;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button notestemp,expensetemp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notestemp=findViewById(R.id.notesbuttontemp);
        notestemp.setOnClickListener(v->startActivity(new Intent(MainActivity.this,notesactivity.class)));



    }
}