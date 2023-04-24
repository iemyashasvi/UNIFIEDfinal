package com.example.unified;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class  Addnotedetails extends AppCompatActivity {
    ImageButton Save_notes;
    EditText Note_Title,Note_Content;
    TextView pagetitle;
    String title,content,Docid;
    TextView delete_btn;
    boolean IsEditmode=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotedetails);
        Save_notes=findViewById(R.id.savenotebtn);
        Note_Title=findViewById(R.id.notestitle);
        Note_Content=findViewById(R.id.contenttext);
        pagetitle=findViewById(R.id.pagetitle);
        delete_btn=findViewById(R.id.deletebutton);
        // recieved data
        title=getIntent().getStringExtra("Title");
        content=getIntent().getStringExtra("Content");
        Docid=getIntent().getStringExtra("docId");
        if (Docid!=null && !Docid.isEmpty()){
            IsEditmode=true;
        }
        Save_notes.setOnClickListener(v->Savenotefunc());
        delete_btn.setOnClickListener(v->deletenotefromfirebase());
        if (IsEditmode){
            delete_btn.setVisibility(View.VISIBLE);
        pagetitle.setText("Edit Your Note");
        Note_Content.setText(content);
        Note_Title.setText(title);}

    }
    void deletenotefromfirebase(){
        DocumentReference documentReference;


            //update the note
            documentReference=Utility.getCollectionReferenceForNotes().document(Docid);


        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //notes deleted
                    Utility.ShowToast(Addnotedetails.this,"Note deleted successfullly");
                    finish();
                }else {
                    Utility.ShowToast(Addnotedetails.this,task.getException().getLocalizedMessage());
                }
            }
        });

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
        if (IsEditmode){

            //update the note
            documentReference=Utility.getCollectionReferenceForNotes().document(Docid);
        }
        else{
            documentReference=Utility.getCollectionReferenceForNotes().document();
        }

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