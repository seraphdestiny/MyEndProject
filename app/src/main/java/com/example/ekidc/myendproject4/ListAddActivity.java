package com.example.ekidc.myendproject4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class ListAddActivity extends AppCompatActivity{
    private EditText editMyDate,editMyMonth, editMyPosition, editMyTime,editMyMinute,editMyMoney;
    private Button btnSave,btnCancel;
    RadioButton imageType;
    int radioButtonId;
    RadioGroup group;
    TextView tv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadd);
        tv = (TextView)this.findViewById(R.id.tvSex);
        group = (RadioGroup)this.findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                radioButtonId = arg0.getCheckedRadioButtonId();
                imageType = (RadioButton)findViewById(radioButtonId);
                tv.setText(imageType.getText().toString());
            }
        });

        editMyMonth = (EditText) findViewById(R.id.editMyMonth);
        editMyDate = (EditText) findViewById(R.id.editMyDate);
        editMyTime = (EditText) findViewById(R.id.editMyTime);
        editMyMinute = (EditText) findViewById(R.id.editMyMinute);
        editMyPosition = (EditText) findViewById(R.id.editMyPosition);
        editMyMoney = (EditText) findViewById(R.id.editMyMoney);

        editMyMonth.setText(getMonth());
        editMyDate.setText(getDay());
        editMyTime.setText(getHour());
        editMyMinute.setText(getMinute());
        editMyPosition.setText("無");
        editMyMoney.setText("0");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            editMyMonth.setText(bundle.getString("Month"));
            editMyDate.setText(bundle.getString("Date"));
            editMyTime.setText(bundle.getString("Time"));
            editMyMinute.setText(bundle.getString("Time_m"));
            editMyPosition.setText(bundle.getString("Position"));
            editMyMoney.setText(bundle.getString("Money"));
        }

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(inputCheck()==true) {
                    Intent i = new Intent();
                    i.putExtra("Month", editMyMonth.getText().toString());
                    i.putExtra("Date", editMyDate.getText().toString());
                    i.putExtra("Position", editMyPosition.getText().toString());
                    i.putExtra("Time", editMyTime.getText().toString());
                    i.putExtra("Time_m", editMyMinute.getText().toString());
                    i.putExtra("Money", editMyMoney.getText().toString());
                    i.putExtra("ImageNumber", Integer.toString(getImageTypeNumber()));
                    i.putExtra("Change", "List");
                    ListAddActivity.this.setResult(0, i);
                    ListAddActivity.this.finish();
                }
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder delAlertDialog = new AlertDialog.Builder(ListAddActivity.this);
                delAlertDialog.setTitle("回上頁將會清除目前作業資料，確定返回上一頁？");

                delAlertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        ListAddActivity.this.finish();
                    }
                });

                delAlertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                delAlertDialog.show();
            }
        });
    }

    public boolean inputCheck()
    {
        String blank="";
        if(editMyMonth.getText().toString()==null)
        {
            Toast.makeText(this, "輸入的月份格式錯誤", Toast.LENGTH_LONG).show();
            return false;
        }
        if(blank.equals(editMyMonth.getText().toString()))
        {
            Toast.makeText(this, "請輸入月份", Toast.LENGTH_LONG).show();
            return false;
        }
        int month = Integer.parseInt(editMyMonth.getText().toString());
        if(month > 12 || month < 0)
        {
            Toast.makeText(this, "輸入的月份格式錯誤", Toast.LENGTH_LONG).show();
            return false;
        }

        if(editMyDate.getText().toString()==null)
        {
            Toast.makeText(this, "輸入的日期格式錯誤", Toast.LENGTH_LONG).show();
            return false;
        }
        if(blank.equals(editMyDate.getText().toString()))
        {
            Toast.makeText(this, "請輸入日期", Toast.LENGTH_LONG).show();
            return false;
        }
        int date = Integer.parseInt(editMyDate.getText().toString());
        if(date > 28 || date < 0)
        {
            switch (month)
            {
                case 2:
                    if(date > 28){
                        Toast.makeText(this, "輸入的日期格式錯誤", Toast.LENGTH_LONG).show();
                        return false;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if(date > 30){
                        Toast.makeText(this, "輸入的日期格式錯誤", Toast.LENGTH_LONG).show();
                        return false;
                    }
                    break;
                default:
                    Toast.makeText(this, "輸入的日期格式錯誤", Toast.LENGTH_LONG).show();
                    return false;
            }

        }

        if(editMyTime.getText().toString()==null)
        {
            Toast.makeText(this, "輸入的小時格式錯誤", Toast.LENGTH_LONG).show();
            return false;
        }
        if(blank.equals(editMyTime.getText().toString()))
        {
            Toast.makeText(this, "請輸入小時", Toast.LENGTH_LONG).show();
            return false;
        }
        int hour = Integer.parseInt(editMyTime.getText().toString());
        if(hour >= 24 || hour < 0)
        {
            Toast.makeText(this, "輸入的小時格式錯誤", Toast.LENGTH_LONG).show();
            return false;
        }

        if(editMyMinute.getText().toString()==null)
        {
            Toast.makeText(this, "輸入的分鐘格式錯誤", Toast.LENGTH_LONG).show();
            return false;
        }
        if(blank.equals(editMyMinute.getText().toString()))
        {
            Toast.makeText(this, "請輸入分鐘", Toast.LENGTH_LONG).show();
            return false;
        }
        int min = Integer.parseInt(editMyMinute.getText().toString());
        if(min >= 60 || min < 0)
        {
            Toast.makeText(this, "輸入的分鐘格式錯誤", Toast.LENGTH_LONG).show();
            return false;
        }

        if(editMyPosition.getText().toString()==null)
        {
            Toast.makeText(this, "請輸入地點", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public int getImageTypeNumber()
    {
        if(imageType!=null){
            String type = imageType.getText().toString();
            if(type.equals("地點")==true){
                return 4;
            }
            else if(type.equals("飲食")==true){
                return 2;
            }
            else if(type.equals("交通")==true){
                return 5;
            }
            else if(type.equals("住所")==true){
                return 3;
            }
            else if(type.equals("商店")==true){
                return 6;
            }
        }
        return 4;
    }
    public String getMonth()
    {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        String s_month = Integer.toString(month);
        if(month < 10){
            s_month = "0"+s_month;}
        return s_month;
    }
    public String getDay()
    {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        String s_day = Integer.toString(day);
        if(day < 10){
            s_day = "0"+s_day;}
        return s_day;
    }
    public String getHour()
    {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String s_hour = Integer.toString(hour);
        if(hour < 10){
            s_hour = "0"+s_hour;}
        return s_hour;
    }
    public String getMinute()
    {
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        String s_minute = Integer.toString(minute);
        if(minute < 10){
            s_minute = "0"+s_minute;}
        return s_minute;
    }
}

