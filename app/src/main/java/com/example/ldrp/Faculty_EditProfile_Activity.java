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

public class Faculty_EditProfile_Activity extends AppCompatActivity {
    private long pressed;
    EditText editname11, editemail11, editdesig11, editquali11, editdepart11, editphone11;
    CircularImageView profile;
    ImageView AccountSettingBackButton, changePhoto, editnameimg, editemailimg, editdesigimg, editqualiimg, editdepartimg, editphoneimg;
    Button save;
    String depart1, name1, email1, quali1, desig1, phone1;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_edit_profile);

        AccountSettingBackButton = findViewById(R.id.AccountSettingBackButton);

        profile = findViewById(R.id.profile11);
        changePhoto = findViewById(R.id.changePhotoimg);

        editname11 = findViewById(R.id.editname11);
        editnameimg = findViewById(R.id.editnameimg);

        editemail11 = findViewById(R.id.editemail11);
        editemailimg = findViewById(R.id.editemailimg);

        editdesig11 = findViewById(R.id.editdesig11);
        editdesigimg = findViewById(R.id.editdesigimg);

        editquali11 = findViewById(R.id.editquali11);
        editqualiimg = findViewById(R.id.editqualiimg);

        editdepartimg = findViewById(R.id.editdepartimg);
        editdepart11 = findViewById(R.id.editdepart11);

        editphone11 = findViewById(R.id.editphone11);
        editphoneimg = findViewById(R.id.editphoneimg);

        save = findViewById(R.id.save);

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sname = snapshot.child("name").getValue().toString();
                String semail = snapshot.child("email").getValue().toString();
                String sdesignation = snapshot.child("designation").getValue().toString();
                String squalification = snapshot.child("qualification").getValue().toString();
                String sdepartment = snapshot.child("department").getValue().toString();
                String sphone = snapshot.child("contact").getValue().toString();
                String surl = snapshot.child("purl").getValue().toString();

                editname11.setText(sname);
                editemail11.setText(semail);
                editdesig11.setText(sdesignation);
                editquali11.setText(squalification);
                editdepart11.setText(sdepartment);
//                EditAccSettingPassword.setText(email);
                editphone11.setText(sphone);
                Glide.with(Faculty_EditProfile_Activity.this).load(surl).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editnameimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Faculty_EditProfile_Activity.this);
                mydialog.setTitle("New Name?");

                final EditText nameInput = new EditText(Faculty_EditProfile_Activity.this);
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
        editemailimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Faculty_EditProfile_Activity.this);
                mydialog.setTitle("New Email Address?");

                final EditText emailInput = new EditText(Faculty_EditProfile_Activity.this);
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
        editdesigimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Faculty_EditProfile_Activity.this);
                mydialog.setTitle("New Designation?");

                final EditText desigInput = new EditText(Faculty_EditProfile_Activity.this);
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
        editqualiimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Faculty_EditProfile_Activity.this);
                mydialog.setTitle("New Qualification?");

                final EditText qualiInput = new EditText(Faculty_EditProfile_Activity.this);
                qualiInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(qualiInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quali1 = qualiInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("qualification",quali1);
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
        editdepartimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Faculty_EditProfile_Activity.this);
                mydialog.setTitle("New Department?");

                final EditText departInput = new EditText(Faculty_EditProfile_Activity.this);
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
        editphoneimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Faculty_EditProfile_Activity.this);
                mydialog.setTitle("New Phone Number?");

                final EditText phoneInput = new EditText(Faculty_EditProfile_Activity.this);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Faculty_EditProfile_Activity.this,Faculty_Profile_Activity.class));
            }
        });

        AccountSettingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Faculty_EditProfile_Activity.this,Faculty_Profile_Activity.class));
            }
        });

    }
//
    @Override
    public void onBackPressed(){
        if(pressed + 2000> System.currentTimeMillis()){
            super.onBackPressed();
            Intent i = new Intent(Faculty_EditProfile_Activity.this, Faculty_Profile_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(Faculty_EditProfile_Activity.this, "Press Back again to go Back!", Toast.LENGTH_SHORT).show();
        }
        pressed = System.currentTimeMillis();
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
