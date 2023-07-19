package com.example.ldrp;

import static android.graphics.Color.RED;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Syllabus_List_Activity extends AppCompatActivity {
TextView t51,t61,t71,t81,t91,t101,t111;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_list);
        t51=findViewById(R.id.tv51);
        t61=findViewById(R.id.tv61);
        t71=findViewById(R.id.tv71);
        t81=findViewById(R.id.tv81);
        t91=findViewById(R.id.tv91);
        t101=findViewById(R.id.tv101);
        t111=findViewById(R.id.tv111);
        t51.setMovementMethod(LinkMovementMethod.getInstance());
        t51.setLinkTextColor(RED);

        t61.setMovementMethod(LinkMovementMethod.getInstance());
        t61.setLinkTextColor(RED);

        t71.setMovementMethod(LinkMovementMethod.getInstance());
        t71.setLinkTextColor(RED);

        t81.setMovementMethod(LinkMovementMethod.getInstance());
        t81.setLinkTextColor(RED);

        t91.setMovementMethod(LinkMovementMethod.getInstance());
        t91.setLinkTextColor(RED);

        t101.setMovementMethod(LinkMovementMethod.getInstance());
        t101.setLinkTextColor(RED);

        t111.setMovementMethod(LinkMovementMethod.getInstance());
        t111.setLinkTextColor(RED);


//        opdf=findViewById(R.id.opdf);
//        opdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Syllabus_List_Activity.this,Syllabus_Pdf_Activity.class));
//            }
//        });
    }
}