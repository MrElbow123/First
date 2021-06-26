package com.example.first;

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirstListActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener{
        Handler handler;
        Float rate;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_first_list);

            handler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    //收到消息
                    if (msg.what == 6) {

                        Bundle bdl=(Bundle)msg.obj;
                        ArrayList<String> listOne=bdl.getStringArrayList("list");

                        //新建适配器Adapter
                        SimpleAdapter listItemsAdapter = new SimpleAdapter(FirstListActivity.this,(List)listOne,
                                R.layout.list_item,new String[]{"TITLE","Detail"},
                                new int[]{R.id.itemTitle,R.id.itemDetail});
                        setListAdapter(listItemsAdapter);
                    }
                    super.handleMessage(msg);
                }
            };
            getListView().setOnItemClickListener(this);

            //开启线程
            Thread t= new Thread(this);
            t.start();

        }

        @Override
        public void run() {
        Document doc=null;
        Bundle bundle=new Bundle();
        try{

            doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i("","run: title="+doc.title());

            ArrayList listItems=new ArrayList<HashMap<String,String>>();

            Elements tables=doc.getElementsByTag("table");
            Element table1=tables.get(1);

            Elements tds=table1.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=8){
                Log.i("","run"+tds.get(i).text()+">"+tds.get(i+5).text());
                HashMap<String,String> map= new HashMap<String,String>();
                map.put("TITLE",tds.get(i).text());
                map.put("Detail",tds.get(i+5).text());
                listItems.add(map);
            }
            bundle.putStringArrayList("list", listItems);

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
    }

    @Override//重写AdapterView.OnItemClickListener中的函数 达到点击跳转传值的目的
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object itemAtPosition=getListView().getItemAtPosition(position);
            HashMap<String,String> map=(HashMap<String,String>)itemAtPosition;
            rate= Float.parseFloat(map.get("Detail"));
            Log.i(" ",map.get("Detail"));

            Intent config = new Intent(this,Convert.class);
            config.putExtra("rateOne",rate);

            startActivityForResult(config,1);



    }


}