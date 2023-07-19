package com.example.ldrp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Student_Register_Activity extends AppCompatActivity {


    ImageView d2_back_button,dedit,dprofile;
    Button dregisterbtn;
    TextInputEditText dname,daddress,dphonenumber,depart,sem,roleno;
    private Uri filepath;
    Bitmap bitmap;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        Toast.makeText(Student_Register_Activity.this, "aaaaa", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_student_register);
        dedit=findViewById(R.id.dedit);
        dprofile=findViewById(R.id.dprofile);
        dname=findViewById(R.id.dname);
        roleno=findViewById(R.id.roleno);
        daddress=findViewById(R.id.email);
        depart=findViewById(R.id.ddepart);
        sem=findViewById(R.id.dsem);
//        rsem1=findViewById(R.id.rsem1);
//        rsem2=findViewById(R.id.rsem2);
//        rsem3=findViewById(R.id.rsem3);
//        rsem4=findViewById(R.id.rsem4);
//        rsem5=findViewById(R.id.rsem5);
//        rsem6=findViewById(R.id.rsem6);
//        rsem7=findViewById(R.id.rsem7);
//        rsem8=findViewById(R.id.rsem8);
        dphonenumber=findViewById(R.id.dphonenumber);
        dregisterbtn=findViewById(R.id.dregisterbtn);
        d2_back_button=findViewById(R.id.d2_back_button);
        dregisterbtn=findViewById(R.id.dregisterbtn);
        Toast.makeText(Student_Register_Activity.this, "Saved!!", Toast.LENGTH_SHORT).show();

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        d2_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_Register_Activity.this, Decision_Activity.class));
            }
        });
        dedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Student_Register_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        dregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog p = new ProgressDialog(Student_Register_Activity.this);
                p.setTitle("Uploading...");
                p.show();
                if(filepath!=null)
                {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference uploader = storage.getReference("Image1"+new Random().nextInt(50)).child("picture/" + UUID.randomUUID().toString());
                    uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    {
                                        p.dismiss();
                                        Toast.makeText(Student_Register_Activity.this, "Saved!!", Toast.LENGTH_SHORT).show();
                                        HashMap<String,Object> m = new HashMap<String,Object>();
                                        m.put("name",dname.getText().toString());
                                        m.put("email",daddress.getText().toString());
                                        m.put("semester",sem.getText().toString());
                                        m.put("enrollment",roleno.getText().toString());
                                        m.put("department",depart.getText().toString());
                                        m.put("contact",dphonenumber.getText().toString());
                                        m.put("role","student");
                                        m.put("userid",currentuser.toString());
                                        m.put("purl",uri.toString());
                                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).setValue(m);
                                        FirebaseDatabase.getInstance().getReference().child("students").child(currentuser).setValue(m);

                                        startActivity(new Intent(Student_Register_Activity.this,Student_Home_Activity.class));
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Student_Register_Activity.this, "ERROR!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                dprofile.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}