package com.ypc.accelerometer;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

public class currentPara extends AppCompatActivity implements SensorEventListener{
    private SensorManager sm;
    private Sensor sensor;
    private Button button;
    public LineGraphSeries<DataPoint> series1,series2,series3;
    TextView textX;
    TextView textY;
    TextView textZ;
    TextView linearX;
    TextView linearY;
    TextView linearZ;
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];
    final float alpha = 0.8f;
    int start=0;
    GraphView graph1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_para);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        button=(Button)findViewById(R.id.pause);
       /*  textX=(TextView)findViewById(R.id.axisX);
         textY=(TextView)findViewById(R.id.axisY);
         textZ=(TextView)findViewById(R.id.axisZ);*/
         linearX=(TextView)findViewById(R.id.linearaxisX);
         linearY=(TextView)findViewById(R.id.linearaxisY);
         linearZ=(TextView)findViewById(R.id.linearaxisZ);
        //sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);
        graph1=(GraphView)findViewById(R.id.view1);
        graph1.getViewport().setScalable(true);
        graph1.getViewport().setScrollable(true);
        graph1.getViewport().setScalableY(true);
        graph1.getViewport().setScrollableY(true);
        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setMinY(-10);
        graph1.getViewport().setMaxY(10);
        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMinX(200);
        graph1.getViewport().setMaxX(500);
        graph1.getGridLabelRenderer().setHorizontalAxisTitle("count");
       // graph1.getGridLabelRenderer().setVerticalAxisTitle("linear_X");
        series1=new LineGraphSeries<DataPoint>();
        series2=new LineGraphSeries<DataPoint>();
        series3=new LineGraphSeries<DataPoint>();
        series1.setColor(Color.BLUE);
        series1.setTitle("linear_X");
        series2.setColor(Color.GREEN);
        series2.setTitle("linear_Y");
        series3.setColor(Color.RED);
        series3.setTitle("linear_Z");
        graph1.addSeries(series1);
        graph1.addSeries(series2);
        graph1.addSeries(series3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.unregisterListener(currentPara.this);
            }
        });
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
       /* textX.setText(String.valueOf(event.values[0]));
        textY.setText(String.valueOf(event.values[1]));
        textZ.setText(String.valueOf(event.values[2]));*/

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        linearX.setText(String.valueOf(linear_acceleration[0]));
        linearY.setText(String.valueOf(linear_acceleration[1]));
        linearZ.setText(String.valueOf(linear_acceleration[2]));
        DataPoint t=new DataPoint(start++,linear_acceleration[0]);
        series1.appendData(t,true,500);
        series2.appendData(new DataPoint(start++,linear_acceleration[1]),true,500);
        series3.appendData(new DataPoint(start++,linear_acceleration[2]),true,500);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onResume(){
        super.onResume();
        sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onPause(){
        super.onPause();
        sm.unregisterListener(this);
    }
}
