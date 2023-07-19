package com.example.ldrp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;

public class Student_EditProfile_Activity extends AppCompatActivity {
    private long pressed;
    EditText name, email, sem, rollno, depart, phone;
    CircularImageView profile;
    ImageView AccountSettingBackButton, changePhoto, editname, editemail, editsem, editquali, editdepart, editphone;
    Button save;
    String depart1, name1, email1, quali1, desig1, phone1;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile);

        AccountSettingBackButton = findViewById(R.id.AccountSettingBackButton);

        profile = findViewById(R.id.profile);
        changePhoto = findViewById(R.id.changePhoto);

        editname = findViewById(R.id.editname);
        name = findViewById(R.id.name);


        email = findViewById(R.id.email);
        editemail = findViewById(R.id.editemail);

        sem = findViewById(R.id.sem);
        editsem = findViewById(R.id.editsem);

        depart = findViewById(R.id.depart);
        editdepart = findViewById(R.id.editdepart);

        phone = findViewById(R.id.phone);
        editphone = findViewById(R.id.editphone);

        save = findViewById(R.id.save);

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sname = snapshot.child("name").getValue().toString();
                String semail = snapshot.child("email").getValue().toString();
                String sdesignation = snapshot.child("semester").getValue().toString();
//                String srollno = snapshot.child("enrollment").getValue().toString();
                String sdepartment = snapshot.child("department").getValue().toString();
                String sphone = snapshot.child("contact").getValue().toString();
                String surl = snapshot.child("purl").getValue().toString();

                name.setText(sname);
                email.setText(semail);
                sem.setText(sdesignation);
//                rollno.setText(srollno);
                depart.setText(sdepartment);
//                EditAccSettingPassword.setText(email);
                phone.setText(sphone);
                Glide.with(Student_EditProfile_Activity.this).load(surl).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Student_EditProfile_Activity.this);
                mydialog.setTitle("New Name?");

                final EditText nameInput = new EditText(Student_EditProfile_Activity.this);
                nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(nameInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name1 = nameInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("name",name1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });
//
        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Student_EditProfile_Activity.this);
                mydialog.setTitle("New Email Address?");

                final EditText emailInput = new EditText(Student_EditProfile_Activity.this);
                emailInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(emailInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        email1 = emailInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("email",email1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });
//
//
        editsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Student_EditProfile_Activity.this);
                mydialog.setTitle("New semester?");

                final EditText desigInput = new EditText(Student_EditProfile_Activity.this);
                desigInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(desigInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        desig1 = desigInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("designation",desig1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });
//        editquali.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder mydialog = new AlertDialog.Builder(Student_EditProfile_Activity.this);
//                mydialog.setTitle("New Qualification?");
//
//                final EditText qualiInput = new EditText(Student_EditProfile_Activity.this);
//                qualiInput.setInputType(InputType.TYPE_CLASS_TEXT);
//                mydialog.setView(qualiInput);
//
//                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        quali1 = qualiInput.getText().toString();
//                        HashMap<String,Object> m = new HashMap<String,Object>();
//                        m.put("qualification",quali1);
//                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
//                    }
//                });
//                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                mydialog.show();
//            }
//        });
        editdepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Student_EditProfile_Activity.this);
                mydialog.setTitle("New Department?");

                final EditText departInput = new EditText(Student_EditProfile_Activity.this);
                departInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(departInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        depart1 = departInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("department",depart1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });
        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Student_EditProfile_Activity.this);
                mydialog.setTitle("New Phone Number?");

                final EditText phoneInput = new EditText(Student_EditProfile_Activity.this);
                phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
                mydialog.setView(phoneInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phone1 = phoneInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("contact",phone1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });
//
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_EditProfile_Activity.this,Faculty_Profile_Activity.class));
            }
        });
//
//        AccountSettingBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Student_EditProfile_Activity.this,Faculty_Profile_Activity.class));
//            }
//        });
//
//    }
//
//    @Override
//    public void onBackPressed(){
//        if(pressed + 2000> System.currentTimeMillis()){
//            super.onBackPressed();
//            Intent i = new Intent(Student_EditProfile_Activity.this, Faculty_Profile_Activity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);
//            finish();
//        }
//        else {
//            Toast.makeText(Student_EditProfile_Activity.this, "Press Back again to go Back!", Toast.LENGTH_SHORT).show();
//        }
//        pressed = System.currentTimeMillis();
//    }

    }
}

//    editname.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            toggleEditableofEditText(EditProfileName);
////                EditProfileName.setFocusableInTouchMode(true);
////                EditProfileName.setSelection(EditProfileName.getText().length());
//        }
//    });
//    private void toggleEditableofEditText(EditText editProfileName) {
//        if(editProfileName.getKeyListener() != null){
//            editProfileName.setTag(editProfileName.getKeyListener());
//            editProfileName.setKeyListener(null);
//        }
//        else{
//            editProfileName.setKeyListener((KeyListener) editProfileName.getTag());
//            editProfileName.setFocusableInTouchMode(true);
//            editProfileName.setSelection(editProfileName.getText().length());
//        }
//    }
