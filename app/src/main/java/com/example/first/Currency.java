package com.example.first;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

public class Currency extends AppCompatActivity implements Runnable{
                                                    //一个一个方法根据界面动作决定顺序，而不是按代码中的顺序
    EditText rmb;
    TextView result;
    private float dollarRate;
    private float euroRate;
    private float wonRate;
    Handler handler;

    //① onCreate 主方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        rmb=findViewById(R.id.input_rmb);
        result = findViewById(R.id.result);

        //保存当前的myrate值 在修改之后运行
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarRate=sharedPreferences.getFloat("dollar-Rate",0.0f);
        euroRate=sharedPreferences.getFloat("euro-Rate",0.0f);
        wonRate=sharedPreferences.getFloat("won-Rate",0.0f);

        //开启子线程
        Thread t= new Thread(this);
        t.start();

        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                //收到消息
                if(msg.what==6){
                    Bundle bdl=(Bundle)msg.obj;
                    dollarRate=bdl.getFloat("dollar-Rate");
                    euroRate=bdl.getFloat("euro-Rate");
                    wonRate=bdl.getFloat("won-Rate");
                    Log.i("","dollar"+dollarRate);
                    Log.i("","euro"+euroRate);
                    Log.i("","wonRate"+wonRate);

                    Toast.makeText(Currency.this,"已经更新",Toast.LENGTH_SHORT).show();


                }
                super.handleMessage(msg);
            }
        };
    }


    //②为onclick属性为click的btn写方法
    public void click(View btn) {
        String str = rmb.getText().toString();
        if (str != null && str.length() > 0) {
            float r = 0.1f;
            if (btn.getId() == R.id.btn_dollar) {
                r = dollarRate;
            } else if (btn.getId() == R.id.btn_euro) {
                r = euroRate;
            } else {
                r = wonRate;
            }
            r = r * Float.parseFloat(str);
            result.setText(String.format("%.2f", r));

        } else {
            Toast.makeText(this, "输入金额", Toast.LENGTH_SHORT).show();
        }
    }


    //③为onclick属性为openConfig的btn写方法
        public void openConfig(View btn){
        //用于传递汇率 并打开配置页面
            Intent config = new Intent(this,ConfigActivity.class);
                                                                   //第一个参数是上下文（this） 第二个参数是目标页面
            config.putExtra("dollar_rate_key",dollarRate);  //存数据 给name
            config.putExtra("euro_rate_key",euroRate);
            config.putExtra("won_rate_key",wonRate);

            Log.i("TAG", "openOne:dollarRate="+ dollarRate);
            Log.i("TAG", "openOne:euroRate="+ euroRate);
            Log.i("TAG", "openOne:wonRate="+ wonRate);

            startActivityForResult(config,1);         //带回传机制的Intent

        }

        //④处理返回数据的方法
        protected void onActivityResult(int requestCode,int resultCode,Intent data){
            if(requestCode==1&&resultCode==2){
                Bundle bundle= data.getExtras();                  //bundle把数据做一个整合
                dollarRate=bundle.getFloat("f1",0.1f);  //更新数据
                euroRate=bundle.getFloat("f2",0.1f);
                wonRate=bundle.getFloat("f3",0.1f);

                SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE); //保存更新汇率

                SharedPreferences.Editor editor=sp.edit();
                editor.putFloat("dollar-Rate",dollarRate);
                editor.putFloat("euro-Rate",euroRate);
                editor.putFloat("won-Rate",wonRate);
                editor.apply();

                Log.i("TAG","onActivityResult:dollarRate="+dollarRate);
                Log.i("TAG","onActivityResult:euroRate="+euroRate);
                Log.i("TAG","onActivityResult:wonRate="+wonRate);
            }
            super.onActivityResult(requestCode,resultCode,data);

        }

    //⑤多线程的实现：前边这个类已经implement了runable 所以必须先实现run方法
    // ——>209行

    @Override
    public void run() {
        //URL url= null;
        /*try{
            url=new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http=(HttpURLConnection)url.openConnection();
            InputStream in=http.getInputStream();
            String html=inputStream2String(in,"gb2312");
            Log.i("run:html",html);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            Log.e("TAG", "run: "+ e.toString());
        }
        catch (IOException e){
            Log.e("TAG", "run: "+ e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "run: "+ e.toString());
        }*/
        Document doc=null;
        Bundle bundle=new Bundle();                             //bundle保存数据
        try{

            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i("","run: title="+doc.title());

            Elements tables=doc.getElementsByTag("table");
            Element table1=tables.get(0);

            Elements tds=table1.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){


                if(tds.get(i).text().equals("美元")){
                    //dollarRate=Float.parseFloat(tds.get(i+5).text());
                    bundle.putFloat("dollar-Rate",100/Float.parseFloat(tds.get(i+5).text()));
                }
                else if(tds.get(i).text().equals("欧元")){
                    //euroRate=Float.parseFloat(tds.get(i+5).text());
                    bundle.putFloat("euro-Rate",100/Float.parseFloat(tds.get(i+5).text()));
                }
                else if(tds.get(i).text().equals("韩元")){
                    //wonRate=Float.parseFloat(tds.get(i+5).text());
                    bundle.putFloat("won-Rate",100/Float.parseFloat(tds.get(i+5).text()));
                }
                Log.i("","run"+tds.get(i).text()+">"+tds.get(i+5).text());
            }

        }

        catch (IOException e){
            e.printStackTrace();
            Log.e("",e.toString());
        }
        catch (Exception e){
            Log.e("",e.toString());
        }

        //发送消息
        Message msg= handler.obtainMessage(6);
        msg.obj=bundle;
        handler.sendMessage(msg);
        //——>58行
    }

    /*public static String  inputStream2String  (InputStream  in , String encoding)  throws  IOException  {
        StringBuffer  out  =  new  StringBuffer();
        InputStreamReader inread = new InputStreamReader(in,encoding);

        char[]  b  =  new  char[4096];
        for  (int  n;  (n  =  inread.read(b))  !=  -1;)  {
            out.append(new  String(b,  0,  n));
        }

        return out.toString();
    }*/


}

