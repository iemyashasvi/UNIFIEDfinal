package com.example.unified;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.text.SimpleDateFormat;

public class Utility  {
    static void ShowToast(Context context,String message){
        Toast.makeText( context , message, Toast.LENGTH_SHORT).show();
    }
    static CollectionReference getCollectionReferenceForNotes(){
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
       return FirebaseFirestore.getInstance().collection("notes")
               .document(currentUser.getUid()).collection("my_notes");
    }
    static String timeStamptostring(Timestamp timestamp){
       return new SimpleDateFormat("MM/dd/yyyy ").format(timestamp.toDate());
    }
}
