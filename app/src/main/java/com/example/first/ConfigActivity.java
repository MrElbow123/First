package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_config);


            Intent intent = getIntent();                                                    //获取参数值
            float dollar2 = intent.getFloatExtra("dollar_rate_key", 0.0f);
            float euro2 = intent.getFloatExtra("euro_rate_key", 0.0f);
            float won2 = intent.getFloatExtra("won_rate_key", 0.0f);

            EditText set1 = findViewById(R.id.id1);                                         //存进控件当中
            set1.setHint("" + dollar2);
            EditText set2 = findViewById(R.id.id2);
            set2.setHint("" + euro2);
            EditText set3 = findViewById(R.id.id3);
            set3.setHint("" + won2);



        }

            public void back(View v){
            try{
                EditText set1=findViewById(R.id.id1);                                   //定义EditText对象
                Float f1=Float.parseFloat(set1.getText().toString());
                EditText set2=findViewById(R.id.id2);
                Float f2=Float.parseFloat(set2.getText().toString());
                EditText set3=findViewById(R.id.id3);
                Float f3=Float.parseFloat(set3.getText().toString());

                Intent intent=getIntent();
                Bundle bdl=new Bundle();                                             //获取数据存入bundle
                bdl.putFloat("f1",f1);
                bdl.putFloat("f2",f2);
                bdl.putFloat("f3",f3);
                intent.putExtras(bdl);

                setResult(2,intent);                                      //回传
                finish();


            }
            catch (Exception e){
                Toast.makeText(this,"你这不行啊",Toast.LENGTH_SHORT).show();
            }

        }


    }

