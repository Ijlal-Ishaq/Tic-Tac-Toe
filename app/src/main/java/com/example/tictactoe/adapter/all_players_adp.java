package com.example.tictactoe.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoe.MainActivity;
import com.example.tictactoe.R;
import com.example.tictactoe.game;
import com.example.tictactoe.game_obj;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class all_players_adp extends RecyclerView.Adapter<all_players_adp.viewholder> {

    private LayoutInflater layoutInflater;
    private List<String> all_players;
    private DatabaseReference fref;
    private FirebaseAuth mAuth;
    private int no;
    private Context context;


    public all_players_adp(Context context,List<String> all_players,int no) {

        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.all_players = all_players;
        this.no=no;
        fref= FirebaseDatabase.getInstance().getReference().child("users");
        mAuth=FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView;

        if(no==1){
            mItemView = layoutInflater.inflate(R.layout.friends_play, parent, false);
        }
        else if(no==2){
        mItemView = layoutInflater.inflate(R.layout.players_list, parent, false);
        }
        else if(no==3){
            mItemView = layoutInflater.inflate(R.layout.friend_req, parent, false);
        }
        else if(no==4){
            mItemView = layoutInflater.inflate(R.layout.friends_play, parent, false);
        }
        else
            mItemView=layoutInflater.inflate(R.layout.players_list, parent, false);
        return new viewholder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {

        final String id= all_players.get(position);

            fref.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    holder.username.setText(dataSnapshot.child("username").getValue(String.class) + ":");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            if(no==2) {

                holder.request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (no == 2) {
                            fref.child(id).child("friends_req").child(mAuth.getUid()).setValue(mAuth.getUid()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    holder.request.setText("Sent");
                                }
                            });
                        }
                    }
                });

            }




            if(no==3){


                holder.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fref.child(mAuth.getUid()).child("friends").child(id).setValue(id);
                        fref.child(id).child("friends").child(mAuth.getUid()).setValue(mAuth.getUid());
                        fref.child(mAuth.getUid()).child("friends_req").child(id).removeValue();
                        all_players.remove(all_players.indexOf(id));
                        notifyDataSetChanged();
                        Toast.makeText(context,"Request Accepted",Toast.LENGTH_SHORT).show();
                    }
                });

                holder.reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fref.child(mAuth.getUid()).child("friends_req").child(id).removeValue();
                        all_players.remove(all_players.indexOf(id));
                        notifyDataSetChanged();
                        Toast.makeText(context,"Request Rejected",Toast.LENGTH_SHORT).show();
                    }
                });






            }


        if (no == 4) {

            fref.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String status=dataSnapshot.child("status").getValue(String.class);

                    if(status.equals("online")){
                        holder.status.setVisibility(View.INVISIBLE);
                        holder.play.setVisibility(View.VISIBLE);
                    }
                    else if(status.equals("offline")){
                        holder.play.setVisibility(View.INVISIBLE);
                        holder.status.setText("offline");
                        holder.status.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.play.setVisibility(View.INVISIBLE);
                        holder.status.setText("playing"); holder.status.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        if(no==1||no==4){


            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.play.setText("Waiting...");
                    fref.child(id).child("game_req").child("id").setValue(mAuth.getUid());
                    fref.child(id).child("game_req").child("reply").setValue("waiting");
                    fref.child(id).child("game_req").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("reply").getValue(String.class).equals("yes")){

                                game_obj obj= new game_obj(mAuth.getUid(),id);

                                Intent intent =new Intent(context , game.class);

                                intent.putExtra("game", (Parcelable) obj);


                                context.startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });


        }




    }

    @Override
    public int getItemCount() {
        return all_players.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView username;
        TextView status;
        Button  request;
        Button  play;
        Button accept;
        Button reject;




        public viewholder(View view) {

            super(view);
            this.username=view.findViewById(R.id.textView3);

            if(no==1) {
                this.play = view.findViewById(R.id.button6);
            }
            if(no==2) {
                this.request = view.findViewById(R.id.button6);

            }
            if(no==3){
                this.accept=view.findViewById(R.id.button4);
                this.reject=view.findViewById(R.id.button6);
            }
            if(no==4){
                this.play=view.findViewById(R.id.button6);
                this.play.setVisibility(View.INVISIBLE);
                this.status=view.findViewById(R.id.textv7);
                this.status.setVisibility(View.VISIBLE);
            }


        }
    }
}
