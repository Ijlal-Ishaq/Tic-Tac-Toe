package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tictactoe.adapter.all_players_adp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {


    private RecyclerView rv1;
    private RecyclerView rv2;
    private List<String> friends_list=new ArrayList<>();
    private List<String> request_list=new ArrayList<>();
    private DatabaseReference fref1;
    private DatabaseReference fref2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);



        rv1=findViewById(R.id.rv1);
        rv2=findViewById(R.id.rv2);

        mAuth=FirebaseAuth.getInstance();
        fref2= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("friends");
        fref1= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("friends_req");

        final all_players_adp adp1=new all_players_adp(this,request_list,3);
        final all_players_adp adp2=new all_players_adp(this,friends_list,4);

        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this);

        rv1.setLayoutManager(linearLayoutManager1);
        rv2.setLayoutManager(linearLayoutManager2);

        rv1.setAdapter(adp1);
        rv2.setAdapter(adp2);





        fref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id=dataSnapshot.getValue(String.class);
                request_list.add(id);
                adp1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        fref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id=dataSnapshot.getValue(String.class);
                friends_list.add(id);
                adp2.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
