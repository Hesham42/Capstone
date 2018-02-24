package com.example.hesham.moves.widget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hesham.moves.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActiviy extends AppCompatActivity {
    @BindView(R.id.NoteTitle)
    EditText editText;
    @BindView(R.id.Add)
    Button ADDButn;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    ChildEventListener mChildEventListner;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        try {
            String currentUserEmail = user.getEmail().replace(".", "_");
            mMessagesDatabaseReference =
                    mFirebaseDatabase.getReference().child("message/" + currentUserEmail);

        }catch (Exception e){
            Toast.makeText(this, R.string.check, Toast.LENGTH_SHORT).show();
        }
        mChildEventListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ModelNote noteModel = dataSnapshot.getValue(ModelNote.class);
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

        ADDButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().equals("") ) {
                    Toast.makeText(NoteActiviy.this, R.string.no_request, Toast.LENGTH_SHORT).show();
                } else {
                    String title = editText.getText().toString();
                    ModelNote noteModel = new ModelNote(title);
                    mMessagesDatabaseReference.push().setValue(noteModel);
                    Toast.makeText(NoteActiviy.this, R.string.Note_Added_Successfully, Toast.LENGTH_LONG).show();
                    editText.setText("");
                }
            }
        });

    }


    public void OnViewList(View view) {
        Intent intent = new Intent(this, ListeNote.class);
        startActivity(intent);
    }
}
