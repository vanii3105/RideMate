package com.example.ldrp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Faculty_Upload_Form_Activity extends AppCompatActivity {

    ImageView Uploadclose,addDoc;
    MaterialButton uploadDocbtn;
    TextInputEditText department,topic,semester;
    String name;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    String currentuser;
    static int n = 1;
//    RadioButton rsem1,rsem2,rsem3,rsem4,rsem5,rsem6,rsem7,rsem8;
//    String csem;
//    RadioGroup radioGroup;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_upload_form);
        Uploadclose=findViewById(R.id.Uploadclose);
        uploadDocbtn=findViewById(R.id.uploadDocbtn);
        department=findViewById(R.id.depart);
        topic=findViewById(R.id.topiceName);
        semester=findViewById(R.id.semester);
//        radioGroup=findViewById(R.id.radiogroup);
        addDoc=findViewById(R.id.addDoc);
//        rsem1=findViewById(R.id.rsem1);
//        rsem2=findViewById(R.id.rsem2);
//        rsem3=findViewById(R.id.rsem3);
//        rsem4=findViewById(R.id.rsem4);
//        rsem5=findViewById(R.id.rsem5);
//        rsem6=findViewById(R.id.rsem6);
//        rsem7=findViewById(R.id.rsem7);
//        rsem8=findViewById(R.id.rsem8);

        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("uploads");

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton = group.findViewById(checkedId);
//                csem= (String) radioButton.getText();
//            }
//        });

        Uploadclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(Faculty_Upload_Form_Activity.this);
                builder.setCancelable(false);
                builder.setTitle("Discard");
                builder.setMessage("You sure want to discard? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Faculty_Upload_Form_Activity.this,Faculty_Upload_Document.class));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog =builder.create();
                alertDialog.show();
            }
        });

        addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDF();
            }
        });

//        uploadDocbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Successfully Uploaded.",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(UploadNoteFormActivity.this,UploadActivity.class));
//            }
//        });
    }

    private void selectPDF() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uploadDocbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = data.getDataString().substring(data.getDataString().lastIndexOf("/")+1);

                    uploadPDFFirebase(data.getData());

                }
            });
        }
    }

    private void uploadPDFFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();
        String cu = currentuser+"/";
        StorageReference reference = storageReference.child("uploads/").child(cu).child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String Semester = semester.getText().toString();
                        String Department = department.getText().toString();
                        String Topic = topic.getText().toString();
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        putPDF putPDF = new putPDF(Semester,Department,Topic,uri.toString(),name);
                        saveMsg(putPDF,Semester,Department,Topic,uri.toString(),name);
//                        String count = Integer.toString(n);
//                        databaseReference.child().setValue(putPDF);
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(),"Successfully Uploaded.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Faculty_Upload_Form_Activity.this,Faculty_Upload_Document.class));

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("File Uploaded.."+(int) progress + "%");
                    }
                });
    }
    private void saveMsg( putPDF putPDF, String Semester, String Department,String Topic, String Url, String Name) {
        String count = Integer.toString(n);
        databaseReference.child(count).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
//        Toast.makeText(UploadNoteFormActivity.this, n, Toast.LENGTH_LONG).show();
                    n=n+1;
//                    Toast.makeText(UploadNoteFormActivity.this, n, Toast.LENGTH_LONG).show();
                    saveMsg(putPDF,Semester,Department,Topic,Url,Name);
                }
                else{
                    if (addDoc.getDrawable() != null){
//                        Toast.makeText(UploadNoteFormActivity.this, n, Toast.LENGTH_LONG).show();
                        databaseReference.child(count).setValue(putPDF);
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("name",Name);
                        m.put("semester",Semester);
                        m.put("department",Department);
                        m.put("topic",Topic);
                        m.put("purl",Url);
                        FirebaseDatabase.getInstance().getReference().child("documents").child(Department).child(Semester).child(count).updateChildren(m);
                        addDoc.setImageDrawable(null);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(Faculty_Upload_Form_Activity.this, Faculty_Upload_Document.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}

//    private void openAlertBox() {
//        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setTitle("Discard");
//        builder.setMessage("You sure want to discard ? ");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(UploadNoteFormActivity.this,HomePage.class));
//
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//            }
//        });
//        AlertDialog alertDialog =builder.create();
//        alertDialog.show();
//


