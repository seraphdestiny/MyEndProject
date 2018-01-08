package com.example.ekidc.myendproject4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;

public class MapsActivity extends FragmentActivity {
    int PLACE_PICKER_REQUEST = 1;
    private static final int REQUEST_PLACE_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("已選取: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                    Intent i = new Intent();
                    i.putExtra("Month", getMonth());
                    i.putExtra("Date", getDay());
                    i.putExtra("Time", getHour());
                    i.putExtra("Time_m", getMinute());
                    i.putExtra("Position", place.getName());
                    i.putExtra("Money", "0");
                    i.putExtra("ImageNumber", "4");
                    i.putExtra("Change", "Map");
                MapsActivity.this.setResult(0, i);
                MapsActivity.this.finish();
            }
        }
    }

    public String getMonth()
    {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        String s_month = Integer.toString(month);
        return s_month;
    }
    public String getDay()
    {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        String s_day = Integer.toString(day);
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
/*    public String getMonth()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM");
        return df.format(c.getTime());
    }
    public String getDay()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        return df.format(c.getTime());
    }
    public String getHour()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        return df.format(c.getTime());
    }
    public String getMinute()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("mm");
        return df.format(c.getTime());
    }*/
