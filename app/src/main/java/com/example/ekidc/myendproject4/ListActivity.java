package com.example.ekidc.myendproject4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private Button btnJump, clear,btnmap;
    private int changedPosition = -1;
    private String change= " ",timeSystem = " ";

    List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
    Map<String, Object> item = new HashMap<String, Object>();
    private int[] image = {
            R.drawable.airplane, R.drawable.bus, R.drawable.food,R.drawable.hotel,
            R.drawable.position, R.drawable.railway,R.drawable.shop,
            R.drawable.start, R.drawable.view,R.drawable.wakeup,
    };

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(data!=null){
            change = data.getStringExtra("Change");
            if(change.equals("Map")==true||change.equals("List")==true)
            {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("month", data.getStringExtra("Month"));
                item.put("date", data.getStringExtra("Date"));
                item.put("time", data.getStringExtra("Time"));
                item.put("time_m", data.getStringExtra("Time_m"));
                item.put("position", data.getStringExtra("Position"));
                item.put("money", data.getStringExtra("Money"));
                int type = Integer.parseInt(data.getStringExtra("ImageNumber"));
                item.put("image", image[type]);
                switch (resultCode){
                    case 0:
                        if(changedPosition!=-1)
                        {
                            items.set(changedPosition,item);
                            changedPosition = -1;
                        }
                        else{
                            items.add(item);
                        }
                        simpleAdapter.notifyDataSetChanged();
                        break;
                    default:
                        simpleAdapter.notifyDataSetChanged();
                }
            }/*
            if(change.equals("Setting")==true)
            {
                timeSystem = data.getStringExtra("timeSwitch");
                if(timeSystem.equals("12小時制")==true){
                    for(int i = 1;i <= items.size(); i++)
                    {
                        String s_time = items.get(i).get("time").toString();

                        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
                       // Date date =sdf.parse(s_time);
                        Calendar calendar = Calendar.getInstance();
                       //calendar.setTime(date);

                        int i_time = Integer.parseInt(s_time);
                        if(i_time >= 12)
                            i_time -=12;
                        s_time = Integer.toString(i_time);
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("time",s_time);
                        item.put("image", R.drawable.arrow);
                        item.put("date", items.get(i).get("date"));
                        item.put("position",items.get(i).get("Position"));
                        item.put("money", items.get(i).get("Money"));
                        items.set(i,item);
                    }
                }
                else if(timeSystem.equals("24小時制")==true){
                    String s_time;
                    int i_time;
                    for(int i = 0;i < items.size(); i++)
                    {
                        s_time = items.get(i).get("time").toString();
                        i_time = Integer.valueOf(s_time);
                        if(i_time < 12)
                            i_time +=12;
                        s_time = Integer.toString(i_time);
                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put("time",s_time);
                        item.put("image", R.drawable.arrow);
                        item.put("date", items.get(i).get("date"));
                        item.put("position",items.get(i).get("Position"));
                        item.put("money", items.get(i).get("Money"));
                        items.set(i,item);
                    }
                }
            }*/
        }
        listView.setOnItemLongClickListener(onItemLongSel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listMyData);
        listView.setOnItemLongClickListener(onItemLongSel);
        listView.setOnItemClickListener(listViewOnItemClickListener);
        setInitialList();

        simpleAdapter = new SimpleAdapter(this, items, R.layout.item_layout
                ,new String[]{"image", "month", "date", "time", "time_m","position","money"},
                new int[]{R.id.image, R.id.month, R.id.date, R.id.time, R.id.time_m, R.id.position});
        listView.setAdapter(simpleAdapter);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(clearOnClickListener);

        btnJump = (Button) findViewById(R.id.btnJump);
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ListActivity.this, ListAddActivity.class);
                startActivityForResult(i, 0);
                simpleAdapter.notifyDataSetChanged();
            }
        });

        btnmap = (Button) findViewById(R.id.btnmap);
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ListActivity.this, MapsActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }


    private Button.OnClickListener clearOnClickListener
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
          AlertDialog.Builder delAlertDialog = new AlertDialog.Builder(ListActivity.this);
            delAlertDialog.setTitle("是否要全部清除");

            delAlertDialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    items.clear();
                    simpleAdapter.notifyDataSetChanged();
                }
            });

            delAlertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0, int arg1) {
                }
            });

            delAlertDialog.show();
        }
    };

    private AdapterView.OnItemLongClickListener onItemLongSel = new AdapterView.OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView parent, View view,final int position, long id){
            AlertDialog.Builder delAlertDialog = new AlertDialog.Builder(ListActivity.this);
            delAlertDialog.setTitle("是否要刪除此項目?");

            delAlertDialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    items.remove(position);
                    simpleAdapter.notifyDataSetChanged();
                }
            });

            delAlertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });

            delAlertDialog.show();
            return true;
        }
    };
    private ListView.OnItemClickListener listViewOnItemClickListener
            = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(ListActivity.this,ListAddActivity.class);
            intent.putExtra("Month", items.get(position).get("month").toString());
            intent.putExtra("Date", items.get(position).get("date").toString());
            intent.putExtra("Time", items.get(position).get("time").toString());
            intent.putExtra("Time_m",items.get(position).get("time_m").toString());
            if(items.get(position).get("position").toString()!= null){
                intent.putExtra("Position",items.get(position).get("position").toString());
            }
            intent.putExtra("Money", items.get(position).get("money").toString());
            //intent.putExtra("Money",items.get(position).get("money").toString());
            changedPosition = position;
            startActivityForResult(intent, 1);

        }
    };

    public void setInitialList()
    {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("image", image[7]);
            item.put("imageNunber", "7");
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MM");
            item.put("month", df.format(c.getTime()));
            df = new SimpleDateFormat("dd");
            item.put("date", df.format(c.getTime()));
            df = new SimpleDateFormat("HH");
            item.put("time", df.format(c.getTime()));
            df = new SimpleDateFormat("mm");
            item.put("time_m", df.format(c.getTime()));
            item.put("position", "家");
            item.put("money", "0");
            items.add(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0,0,Menu.NONE,"設定");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getGroupId() == 0)
        {
            Intent i = new Intent();
            i.setClass(ListActivity.this, SettingActivity.class);
            startActivityForResult(i, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intentHome = new Intent(Intent.ACTION_MAIN);
            intentHome.addCategory(Intent.CATEGORY_HOME);
            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
