package com.example.dolfinloginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AccountInfoActivity extends AppCompatActivity {

    TextView TVborder,TVborder2,TVborder3;
    ImageView IVBackBtn;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        
        TVborder = findViewById(R.id.TVborder);
        TVborder2 = findViewById(R.id.TVborder2);
        TVborder3 = findViewById(R.id.TVborder3);
        IVBackBtn = findViewById(R.id.IVBackBtn);
        
        showUserData();

        IVBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });
        
    }

    private void showUserData() {

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String phnNum = intent.getStringExtra("phoneNum");

        TVborder.setText(name);
        TVborder2.setText(email);
        TVborder3.setText(phnNum);
    }

    public void passUserData(){
        String userName = TVborder.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RegisterInfo");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userName);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nameFromDB = snapshot.child(userName).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userName).child("email").getValue(String.class);
                    String phnNumFromDB = snapshot.child(userName).child("phoneNum").getValue(String.class);

                    Intent intent = new Intent(AccountInfoActivity.this, ProfileActivity.class);

                    intent.putExtra("name",nameFromDB);
                    intent.putExtra("email",emailFromDB);
                    intent.putExtra("phoneNum",phnNumFromDB);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}