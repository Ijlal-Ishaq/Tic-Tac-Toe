package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class game extends AppCompatActivity {

    private TextView player1;
    private TextView player2;
    private TextView result_tv;
    private Button play_again;
    private DatabaseReference fref;
    private DatabaseReference fref1;
    private DatabaseReference fref2;
    private DatabaseReference fref3;
    private DatabaseReference fref7;
    private FirebaseAuth mAuth;
    private Button p1;
    private Button p2;
    private Button p3;
    private Button p4;
    private Button p5;
    private Button p6;
    private Button p7;
    private Button p8;
    private Button p9;
    private String sign;
    private String op_sign;
    private TextView turn_tv;
    private DatabaseReference fref4;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        player1=findViewById(R.id.textView19);
        player2=findViewById(R.id.textView20);
        result_tv=findViewById(R.id.textView21);
        play_again=findViewById(R.id.button16);


        turn_tv=findViewById(R.id.textView22);

        fref= FirebaseDatabase.getInstance().getReference().child("games");
        fref2=FirebaseDatabase.getInstance().getReference().child("users");
        fref3=FirebaseDatabase.getInstance().getReference().child("Online");
        fref7= FirebaseDatabase.getInstance().getReference().child("games");
        fref4=FirebaseDatabase.getInstance().getReference().child("users");


        final game_obj obj=getIntent().getParcelableExtra("game");
        fref1= FirebaseDatabase.getInstance().getReference().child("games").child(obj.getGame_id());

        fref4.child(obj.getPlayer1()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player1.setText(dataSnapshot.child("username").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fref4.child(obj.getPlayer2()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player2.setText(dataSnapshot.child("username").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mAuth=FirebaseAuth.getInstance();



        p1=findViewById(R.id.button7);
        p2=findViewById(R.id.button8);
        p3=findViewById(R.id.button9);
        p4=findViewById(R.id.button10);
        p5=findViewById(R.id.button11);
        p6=findViewById(R.id.button12);
        p7=findViewById(R.id.button13);
        p8=findViewById(R.id.button14);
        p9=findViewById(R.id.button15);


        if(mAuth.getUid().equals(obj.getPlayer1())){
            sign="O";
            op_sign="X";
        }
        else{
            sign="X";
            op_sign="O";
        }


        fref.child(obj.getGame_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final game_obj obj1=new game_obj(obj.getPlayer1(),obj.getPlayer2());



                final String turn =dataSnapshot.child("turn").getValue(String.class);
                obj1.setTurn(turn);

                if(!turn.equals("W")){
                    if(turn.equals("O"))turn_tv.setText(player1.getText()+"'s turn");
                    else turn_tv.setText(player2.getText()+"'s turn");
                }else{
                    turn_tv.setVisibility(View.INVISIBLE);
                }


                p1.setText(dataSnapshot.child("p_1").getValue(String.class));
                obj1.setP_1(p1.getText().toString());

                p2.setText(dataSnapshot.child("p_2").getValue(String.class));
                obj1.setP_2(p2.getText().toString());

                p3.setText(dataSnapshot.child("p_3").getValue(String.class));
                obj1.setP_3(p3.getText().toString());

                p4.setText(dataSnapshot.child("p_4").getValue(String.class));
                obj1.setP_4(p4.getText().toString());

                p5.setText(dataSnapshot.child("p_5").getValue(String.class));
                obj1.setP_5(p5.getText().toString());

                p6.setText(dataSnapshot.child("p_6").getValue(String.class));
                obj1.setP_6(p6.getText().toString());

                p7.setText(dataSnapshot.child("p_7").getValue(String.class));
                obj1.setP_7(p7.getText().toString());

                p8.setText(dataSnapshot.child("p_8").getValue(String.class));
                obj1.setP_8(p8.getText().toString());

                p9.setText(dataSnapshot.child("p_9").getValue(String.class));
                obj1.setP_9(p9.getText().toString());


                if(!dataSnapshot.child("win").getValue(String.class).equals("")){
                    int no=check_win(obj1);
                    win(no,obj1);
                }

                    if(p1.getText().equals(" ")){
                        p1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(turn.equals(sign)) {
                                    fref1.child("p_1").setValue(sign);obj1.setP_1(sign);
                                    int result = check_win(obj1);
                                    Log.i("result",String.valueOf(result));
                                    p1.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }

                            }
                        });
                    }
                    if(p2.getText().equals(" ")){
                        p2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(turn.equals(sign)) {
                                    fref1.child("p_2").setValue(sign);obj1.setP_2(sign);
                                    int result = check_win(obj1);
                                    p2.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }
                            }
                        });
                    }
                    if(p3.getText().equals(" ")){
                        p3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(turn.equals(sign)) {
                                    fref1.child("p_3").setValue(sign);obj1.setP_3(sign);
                                    int result = check_win(obj1);
                                    p3.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }
                            }
                        });
                    }
                    if(p4.getText().equals(" ")){
                        p4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(turn.equals(sign)) {
                                    fref1.child("p_4").setValue(sign);obj1.setP_4(sign);
                                    int result = check_win(obj1);
                                    p4.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }
                            }
                        });
                    }
                    if(p5.getText().equals(" ")){
                        p5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(turn.equals(sign)) {
                                    fref1.child("p_5").setValue(sign);obj1.setP_5(sign);
                                    int result = check_win(obj1);
                                    p5.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }
                            }
                        });
                    }
                    if(p6.getText().equals(" ")){
                        p6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(turn.equals(sign)) {
                                    fref1.child("p_6").setValue(sign);obj1.setP_6(sign);
                                    int result = check_win(obj1);
                                    p6.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }
                            }
                        });
                    }
                    if(p7.getText().equals(" ")){
                        p7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(turn.equals(sign)) {
                                    fref1.child("p_7").setValue(sign);obj1.setP_7(sign);
                                    int result = check_win(obj1);

                                    p7.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }
                            }
                        });
                    }
                    if(p8.getText().equals(" ")){
                        p8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(turn.equals(sign)) {
                                    fref1.child("p_8").setValue(sign);
                                    obj1.setP_8(sign);
                                    int result = check_win(obj1);
                                    p8.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }
                            }
                        });
                    }
                    if(p9.getText().equals(" ")){
                        p9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(turn.equals(sign)) {
                                    fref1.child("p_9").setValue(sign);
                                    obj1.setP_9(sign);
                                    int result = check_win(obj1);
                                    p9.setText(sign);

                                    if (result == 3) {
                                        fref1.child("turn").setValue(op_sign);
                                    } else {
                                        fref1.child("turn").setValue("W");
                                        win(result,obj1);
                                    }
                                }
                            }
                        });
                    }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fref.child(obj.getGame_id()).child("play_again").child(sign).setValue(sign);

                play_again.setText("Waiting");

                fref.child(obj.getGame_id()).child("play_again").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getChildrenCount()==2) {
                            game_obj obj5 = new game_obj(obj.getPlayer1(), obj.getPlayer2());

                            Intent intent = new Intent(game.this, game.class);

                            intent.putExtra("game", (Parcelable) obj5);
                            fref7.child(obj.getGame_id()).setValue(obj);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });










    }

    private void win(int result,game_obj obj) {


        DatabaseReference fref3 =FirebaseDatabase.getInstance().getReference().child("games").child(obj.getGame_id()).child("win");

        if(result==0){


            result_tv.setVisibility(View.VISIBLE);
            result_tv.setText(player1.getText()+" Win!");
            play_again.setVisibility(View.VISIBLE);
            fref3.setValue(obj.getPlayer1());

        }

        else if(result==1) {
            result_tv.setVisibility(View.VISIBLE);
            result_tv.setText(player2.getText()+" Win!");
            play_again.setVisibility(View.VISIBLE);
            fref3.setValue(obj.getPlayer2());
        }

        else if(result==2){
            result_tv.setVisibility(View.VISIBLE);
            result_tv.setText("Draw!");
            play_again.setVisibility(View.VISIBLE);
            fref3.setValue(obj.getPlayer1()+obj.getPlayer2());
        }




    }


    public int check_win(game_obj obj){

        if(((obj.getP_1().equals(obj.getP_2()))&&(obj.getP_2().equals(obj.getP_3()))&&(!obj.getP_3().equals(" "))&&(obj.getP_3().equals("O")))||
           ((obj.getP_4().equals(obj.getP_5()))&&(obj.getP_5().equals(obj.getP_6()))&&(!obj.getP_6().equals(" "))&&(obj.getP_6().equals("O")))||
           ((obj.getP_7().equals(obj.getP_8()))&&(obj.getP_8().equals(obj.getP_9()))&&(!obj.getP_9().equals(" "))&&(obj.getP_9().equals("O")))||
           ((obj.getP_1().equals(obj.getP_4()))&&(obj.getP_4().equals(obj.getP_7()))&&(!obj.getP_7().equals(" "))&&(obj.getP_7().equals("O")))||
           ((obj.getP_2().equals(obj.getP_5()))&&(obj.getP_5().equals(obj.getP_8()))&&(!obj.getP_8().equals(" "))&&(obj.getP_8().equals("O")))||
           ((obj.getP_3().equals(obj.getP_6()))&&(obj.getP_6().equals(obj.getP_9()))&&(!obj.getP_9().equals(" "))&&(obj.getP_9().equals("O")))||
           ((obj.getP_1().equals(obj.getP_5()))&&(obj.getP_5().equals(obj.getP_9()))&&(!obj.getP_9().equals(" "))&&(obj.getP_9().equals("O")))||
           ((obj.getP_3().equals(obj.getP_5()))&&(obj.getP_5().equals(obj.getP_7()))&&(!obj.getP_7().equals(" "))&&(obj.getP_7().equals("O"))))
        {
            return 0;
        }
        else if(((obj.getP_1().equals(obj.getP_2()))&&(obj.getP_2().equals(obj.getP_3()))&&(!obj.getP_3().equals(" "))&&(obj.getP_3().equals("X")))||
                ((obj.getP_4().equals(obj.getP_5()))&&(obj.getP_5().equals(obj.getP_6()))&&(!obj.getP_6().equals(" "))&&(obj.getP_6().equals("X")))||
                ((obj.getP_7().equals(obj.getP_8()))&&(obj.getP_8().equals(obj.getP_9()))&&(!obj.getP_9().equals(" "))&&(obj.getP_9().equals("X")))||
                ((obj.getP_1().equals(obj.getP_4()))&&(obj.getP_4().equals(obj.getP_7()))&&(!obj.getP_7().equals(" "))&&(obj.getP_7().equals("X")))||
                ((obj.getP_2().equals(obj.getP_5()))&&(obj.getP_5().equals(obj.getP_8()))&&(!obj.getP_8().equals(" "))&&(obj.getP_8().equals("X")))||
                ((obj.getP_3().equals(obj.getP_6()))&&(obj.getP_6().equals(obj.getP_9()))&&(!obj.getP_9().equals(" "))&&(obj.getP_9().equals("X")))||
                ((obj.getP_1().equals(obj.getP_5()))&&(obj.getP_5().equals(obj.getP_9()))&&(!obj.getP_9().equals(" "))&&(obj.getP_9().equals("X")))||
                ((obj.getP_3().equals(obj.getP_5()))&&(obj.getP_5().equals(obj.getP_7()))&&(!obj.getP_7().equals(" "))&&(obj.getP_7().equals("X"))))
        {
            return 1;
        }
        else if(!obj.getP_1().equals(" ")&&
                !obj.getP_2().equals(" ")&&
                !obj.getP_3().equals(" ")&&
                !obj.getP_4().equals(" ")&&
                !obj.getP_5().equals(" ")&&
                !obj.getP_6().equals(" ")&&
                !obj.getP_7().equals(" ")&&
                !obj.getP_8().equals(" ")&&
                !obj.getP_9().equals(" "))
        {
            return 2;
        }

        else
        {
            return 3;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        fref3.child(mAuth.getUid()).setValue(mAuth.getUid());
        fref2.child(mAuth.getUid()).child("status").setValue("playing");
    }
    @Override
    protected void onStop() {
        super.onStop();
        fref3.child(mAuth.getUid()).removeValue();
        fref2.child(mAuth.getUid()).child("status").setValue("offline");
    }
}
