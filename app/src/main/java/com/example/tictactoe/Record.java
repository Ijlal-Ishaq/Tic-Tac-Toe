package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Record extends AppCompatActivity {

    private TextView win;
    private TextView draw;
    private TextView loss;

    private DatabaseReference fref;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);



        win=findViewById(R.id.textView13);
        draw=findViewById(R.id.textView14);
        loss=findViewById(R.id.textView15);

        mAuth=FirebaseAuth.getInstance();

        fref= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());



        fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                win.setText("Win: "+dataSnapshot.child("win").getValue(Integer.class));
                draw.setText("Draw: "+dataSnapshot.child("draw").getValue(Integer.class));
                loss.setText("Loss: "+dataSnapshot.child("loss").getValue(Integer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
