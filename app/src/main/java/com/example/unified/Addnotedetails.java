package com.example.unified;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class  Addnotedetails extends AppCompatActivity {
    ImageButton Save_notes;
    EditText Note_Title,Note_Content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotedetails);
        Save_notes=findViewById(R.id.savenotebtn);
        Note_Title=findViewById(R.id.notestitle);
        Note_Content=findViewById(R.id.contenttext);
        Save_notes.setOnClickListener(v->Savenotefunc());
    }
    void Savenotefunc(){
        String noteTitle=Note_Title.getText().toString();
        String noteContent  =Note_Content.getText().toString();
        if (noteTitle ==null || noteTitle.isEmpty()){
            Note_Title.setError("Title is empty");
            return;
        }
        if (noteContent ==null || noteContent.isEmpty()){
            Note_Content.setError("Content is empty");
            return;
        }
        Note note= new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());
        saveNoteToFirebase(note);
    }
    void saveNoteToFirebase(Note note){
        DocumentReference documentReference;
        documentReference=Utility.getCollectionReferenceForNotes().document();
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()){
                     //notes saved
                     Utility.ShowToast(Addnotedetails.this,"Note added successfullly");
                     finish();
                }else {
                     Utility.ShowToast(Addnotedetails.this,task.getException().getLocalizedMessage());
                 }
            }
        });
    }
}