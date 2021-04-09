package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//有三个id  show inp btn
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG="MainActivity";
    TextView show;
    EditText inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //父类方法
        setContentView(R.layout.today); //设置当前容器视图

        TextView show = findViewById(R.id.show);
                show.setText("温度转换！");

        inp=findViewById(R.id.inp);
        String str=inp.getText().toString();
        Log.i(TAG,"onClick:"+str);

        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG,"onclick:111111111");
        String [] str =inp.getText().toString().split(" ");
        Double m=Double.parseDouble(str[0])*1.8+32;
        TextView show =findViewById(R.id.show);
        show.setText(m+"℉");
    }
}