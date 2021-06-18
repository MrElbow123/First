package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ListView listView=findViewById(R.id.mylist);
        ProgressBar progressBar=findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);



        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                //收到消息
                if (msg.what == 6) {

                    Bundle bdl=(Bundle)msg.obj;
                    ArrayList<String> list2=bdl.getStringArrayList("list");
                    ListAdapter adapter=new ArrayAdapter<String>(
                            MainActivity3.this,
                            android.R.layout.simple_list_item_1,
                            list2);

                    listView.setAdapter(adapter);

                }
                super.handleMessage(msg);
            }
        };
        //开启线程
        Thread t= new Thread();
        t.start();

        }
    }
