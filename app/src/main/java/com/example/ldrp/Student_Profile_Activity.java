package com.example.ldrp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_Profile_Activity extends AppCompatActivity {
    TextView editp,uploaddoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        editp=findViewById(R.id.editp);
        uploaddoc=findViewById(R.id.uploaddoc);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        editp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_Profile_Activity.this,Student_EditProfile_Activity.class));
            }
        });
        uploaddoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String depart=snapshot.child("department").getValue(String.class);
                        String sem=snapshot.child("semester").getValue(String.class);
                        Intent intent = new Intent(Student_Profile_Activity.this, Student_Document_Activity.class);
                        intent.putExtra("department", depart);
                        intent.putExtra("semester", sem);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}