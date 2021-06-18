package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Convert extends AppCompatActivity {

    EditText inp;
    float rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        TextView show = findViewById(R.id.show);
        show.setText("rmb");

        inp=findViewById(R.id.input_rmb);
        String str=inp.getText().toString();
        Log.i(" ","onClick:"+str);

        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(this::onClick);

        Intent intent = getIntent();
        rate = intent.getFloatExtra("rateOne", 0.0f);



    }

    public void onClick(View v) {
        Log.i(" ","onclick:111111111");
        Float m=Float.parseFloat(inp.getText().toString())*100/rate;
        TextView show =findViewById(R.id.show);
        show.setText(m+" ");
    }
    /*public void afterTextChanged(Editable editable) {
        System.out.println("改变后："+et.getText().toString());

    }*/


}