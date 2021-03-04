package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tictactoe.adapter.all_players_adp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Find_players extends AppCompatActivity {

    private RecyclerView rv;
    private DatabaseReference fref;
    private List<String> all_users=new ArrayList<>();
    private List<String> backup_users=new ArrayList<>();
    private FirebaseAuth mAuth;
    private EditText search_et;
    private Button search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_players);

        mAuth=FirebaseAuth.getInstance();

        rv=findViewById(R.id.rv);
        fref= FirebaseDatabase.getInstance().getReference().child("all_users");
        final all_players_adp adp= new all_players_adp(this,all_users,2);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);



        search_btn=findViewById(R.id.button3);
        search_et=findViewById(R.id.editText7);


        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adp);


        fref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                String user=dataSnapshot.getValue(String.class);
                all_users.add(user);
                adp.notifyDataSetChanged();



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
