package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button btn_online_players;
    private Button btn_friends;
    private Button btn_find_players;
    private Button btn_record;
    private TextView username;
    private DatabaseReference fref;
    private DatabaseReference fref2;
    private DatabaseReference fref3;
    private DatabaseReference fref4;
    private FirebaseAuth mAuth;
    private TextView game_req_username;
    private TextView game_req;
    private Button play;
    private Button reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_online_players=findViewById(R.id.btn_online_players);
        btn_friends=findViewById(R.id.btn_friends);
        btn_find_players=findViewById(R.id.btn_find_players);
        btn_record=findViewById(R.id.btn_record);

        username=findViewById(R.id.textView4);

        game_req=findViewById(R.id.textView16);
        game_req_username=findViewById(R.id.textView3);
        play=findViewById(R.id.button5);
        reject=findViewById(R.id.button6);



        mAuth=FirebaseAuth.getInstance();
        fref= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());
        fref2=FirebaseDatabase.getInstance().getReference().child("Online");
        fref3= FirebaseDatabase.getInstance().getReference().child("users");
        fref4=FirebaseDatabase.getInstance().getReference().child("games");



        fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText("Username: "+dataSnapshot.child("username").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btn_online_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Online_players.class));
            }
        });

        btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Friends.class));
            }
        });

        btn_find_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Find_players.class));
            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Record.class));
            }
        });





        fref.child("game_req").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               final String req= dataSnapshot.child("id").getValue(String.class);
               try {
                   if (!req.equals("")) {
                       fref3.child(req).addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               game_req_username.setText(dataSnapshot.child("username").getValue(String.class) + ":");
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });
                       game_req_username.setVisibility(View.VISIBLE);
                       play.setVisibility(View.VISIBLE);
                       reject.setVisibility(View.VISIBLE);
                   }
               }catch (Exception e){

               }

               reject.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       fref.child("game_req").child("id").setValue("");
                       fref.child("game_req").child("reply").setValue("no");
                       game_req_username.setText("");
                       game_req_username.setVisibility(View.INVISIBLE);
                       play.setVisibility(View.INVISIBLE);
                       reject.setVisibility(View.INVISIBLE);
                   }
               });


               play.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       fref.child("game_req").child("id").setValue("");
                       fref.child("game_req").child("reply").setValue("yes");

                       game_obj obj= new game_obj(req,mAuth.getUid());

                       Intent intent =new Intent(MainActivity.this,game.class);

                       intent.putExtra("game", (Parcelable) obj);
                       fref4.child(obj.getGame_id()).setValue(obj);
                       startActivity(intent);


                   }
               });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        fref2.child(mAuth.getUid()).setValue(mAuth.getUid());
        fref3.child(mAuth.getUid()).child("status").setValue("online");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        fref2.child(mAuth.getUid()).setValue(mAuth.getUid());
        fref3.child(mAuth.getUid()).child("status").setValue("online");
    }

    @Override
    protected void onStop() {
        super.onStop();
        fref2.child(mAuth.getUid()).removeValue();
        fref3.child(mAuth.getUid()).child("status").setValue("offline");
    }

    @Override
    protected void onResume() {
        super.onResume();
        fref2.child(mAuth.getUid()).setValue(mAuth.getUid());
        fref3.child(mAuth.getUid()).child("status").setValue("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        fref2.child(mAuth.getUid()).removeValue();
        fref3.child(mAuth.getUid()).child("status").setValue("offline");
    }
}
