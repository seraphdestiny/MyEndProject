package com.example.ekidc.myendproject4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private Button set;
    RadioButton rb;
    private String timeSystem = "12小時制";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                int radioButtonId = arg0.getCheckedRadioButtonId();
                rb = (RadioButton)findViewById(radioButtonId);
            }
        });
        set = (Button) findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("Change", "Setting");
                //if(rb.getText().toString()!=null)
                    //timeSystem=(rb.getText().toString());
                i.putExtra("timeSwitch", timeSystem);
                SettingActivity.this.setResult(0, i);
                SettingActivity.this.finish();
            }
        });
    }
}