package com.example.unified;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {
    Context context;


    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.Titletextview.setText(note.Title);
        holder.Contenttextview.setText(note.Content);
        holder.timeTextView.setText(Utility.timeStamptostring(note.timestamp));
        holder.itemView.setOnClickListener((v)->{
                        Intent intent=new Intent(context,Addnotedetails.class);
                        intent.putExtra("Title",note.Title);
                        intent.putExtra("Content",note.Content);
                        String docId=this.getSnapshots().getSnapshot(position ).getId();
                        intent.putExtra("docId",docId);
                        context.startActivity(intent);
        }
                );


    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_recyclerview,parent,false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView Titletextview,Contenttextview,timeTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            Titletextview=itemView.findViewById(R.id.notetextview);
            Contenttextview=itemView.findViewById(R.id.contenttextview);
            timeTextView=itemView.findViewById(R.id.timetextview );

        }
    }
}
