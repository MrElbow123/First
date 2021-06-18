package com.example.first;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity2 extends Activity {
    int scoreA=0;
    int scoreB=0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tyz);//指向布局文件
        //two_scorer1与two_scorer2皆可
    }
    private  void show(){
        TextView a=findViewById(R.id.show); //找到那个对应的标签 可以是TextView也可以是其他
        a.setText(String.valueOf(scoreA)); // 这就是更改显示文本内容而已
    }
    private  void showb(){
        TextView b=findViewById(R.id.showb);
        b.setText(String.valueOf(scoreB));
    }
    public  void add3(View v){
        scoreA+=3;
        show();
    }
    public   void add2(View v){
        scoreA+=2;
        show();
    }
    public   void add1(View v){
        scoreA+=1;
        show();
    }

    public   void addb3(View v){
        scoreB+=3;
        showb();
    }
    public  void addb2(View v){
        scoreB+=2;
        showb();
    }
    public   void addb1(View v){
        scoreB+=1;
        showb();
    }
    public   void reset(View v){
        scoreB=0;
        showb();
        scoreA=0;
        show();
    }
    public  void addg2(View v){
        scoreB+=2;
        showb();
    }

}
