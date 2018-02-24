package com.example.hesham.moves.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.hesham.moves.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListeNote extends AppCompatActivity {
    @BindView(R.id.NoteList)
    ListView noteslist;

    List<ModelNote> noteModelNoteList;
    Adapter adapter;
    ChildEventListener mChildEventListner;
    private DatabaseReference mMessagesDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;
    // auth
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_note);
        ButterKnife.bind(this);
        noteModelNoteList =new ArrayList<>();
        adapter = new Adapter(this, R.layout.itemwidget, noteModelNoteList);
        noteslist.setAdapter(adapter);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
//        if (user == null)
//            return null;
        try {
            String currentUserEmail = user.getEmail().replace(".", "_");
            mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("message/" + currentUserEmail);

        }catch (Exception e) {
        }
        mChildEventListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ModelNote noteModelNote = dataSnapshot.getValue(ModelNote.class);
                noteModelNoteList.add(noteModelNote);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        try {
            mMessagesDatabaseReference.addChildEventListener(mChildEventListner);
        }catch (Exception e){

        }

    }


    public void OnClearAll(View view) {
        mMessagesDatabaseReference.removeValue();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }
}
