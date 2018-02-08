package com.example.hesham.moves.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class History extends AppCompatActivity {
    @BindView(R.id.Addbut)
    Button addBut;
    @BindView(R.id.Clearbut)
    Button clearBut;
    @BindView(R.id.Title)
    EditText Title;
    @BindView(R.id.Step)
    EditText Step;
    @BindView(R.id.noteList)
    ListView noteslist;

    NotesAdapter adapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;


    ChildEventListener mChildEventListner;


    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);




        List<NoteAttr> list_of_noteAttrs = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.list_item, list_of_noteAttrs);
        noteslist.setAdapter(adapter);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        try {
            String currentUserEmail = user.getEmail().replace(".", "_");
            mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("message/" + currentUserEmail);

        }catch (Exception e){
            Toast.makeText(this, R.string.check, Toast.LENGTH_SHORT).show();
        }

        mChildEventListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                NoteAttr noteAttr = dataSnapshot.getValue(NoteAttr.class);
                adapter.add(noteAttr);
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
            Toast.makeText(this, R.string.check, Toast.LENGTH_SHORT).show();
        }


        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Title.getText().toString().equals("") || Step.getText().toString().equals("")) {
                    Toast.makeText(History.this, R.string.no_request, Toast.LENGTH_SHORT).show();
                } else {

                    String title = Title.getText().toString();
                    String step = Step.getText().toString();
//




                    NoteAttr noteAttr = new NoteAttr(title, step);

                    mMessagesDatabaseReference.push().setValue(noteAttr);


                    Toast.makeText(History.this, R.string.Note_Added_Successfully, Toast.LENGTH_LONG).show();
                    Title.setText("");
                    Step.setText("");
                }
            }
        });

        clearBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessagesDatabaseReference.removeValue();
                adapter.clear();
                adapter.notifyDataSetChanged();

            }
        });
    }
}
