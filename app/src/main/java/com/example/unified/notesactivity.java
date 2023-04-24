package com.example.unified;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class notesactivity extends AppCompatActivity {
    FloatingActionButton Add_notebtn;
    RecyclerView my_NotesView;
    ImageButton menu_Button;
    NoteAdapter noteAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesactivity);
        Add_notebtn=findViewById(R.id.floatingactionbutton);
        Add_notebtn.setOnClickListener((v)->startActivity(new Intent(notesactivity.this,Addnotedetails.class)));
        my_NotesView=findViewById(R.id.mynotesview);
        menu_Button=findViewById(R.id.menubutton);
        menu_Button.setOnClickListener(v->Showmenu());
        setUpRecyclerView();


    }
    void Showmenu(){
        PopupMenu popupMenu=new PopupMenu(notesactivity.this,menu_Button);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuitem) {
                if (menuitem.getTitle()=="Logout"){
                    Sign_out();
                }
                return false;
            }
        });
    }
    void Sign_out(){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance() ;
       firebaseAuth.signOut();
       finish();
       startActivity(new Intent(notesactivity.this,Login.class));
    }
    void setUpRecyclerView(){
        Query query=Utility.getCollectionReferenceForNotes().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options=new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class).build();
       my_NotesView.setLayoutManager(new LinearLayoutManager( this));
       noteAdapter=new NoteAdapter(options,this);
       my_NotesView.setAdapter(noteAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}