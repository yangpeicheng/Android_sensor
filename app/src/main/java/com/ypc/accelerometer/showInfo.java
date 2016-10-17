package com.ypc.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class showInfo extends AppCompatActivity {
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private SensorManager sm;
    private Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        text1.setText(sensor.getName());
        text2.setText(sensor.getVendor());
        text3.setText(String.valueOf(sensor.getResolution()));
    }
}
