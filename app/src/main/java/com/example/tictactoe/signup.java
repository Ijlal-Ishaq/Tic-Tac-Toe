package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText password;
    private Button signup;
    private FirebaseAuth mAuth;
    private DatabaseReference fref;
    private DatabaseReference fref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        email=findViewById(R.id.editText3);
        username=findViewById(R.id.editText5);
        password=findViewById(R.id.editText4);

        signup=findViewById(R.id.button2);

        mAuth=FirebaseAuth.getInstance();
        fref= FirebaseDatabase.getInstance().getReference().child("users");
        fref2=FirebaseDatabase.getInstance().getReference().child("all_users");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(!email.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("") &&
                   !username.getText().toString().trim().equals("")){
                    mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            user_obj obj=new user_obj(username.getText().toString().trim(),"",null,0,0,0);
                            String Key =authResult.getUser().getUid();
                            obj.setId(Key);
                            fref2.child(authResult.getUser().getUid()).setValue(Key);
                            fref.child(Key).setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(signup.this,MainActivity.class));
                                }
                            });

                        }
                    });
                }
            }
        });



    }
}
