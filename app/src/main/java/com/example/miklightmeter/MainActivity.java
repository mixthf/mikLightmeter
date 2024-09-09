package com.example.miklightmeter;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView lightIntensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightIntensity = findViewById(R.id.light_intensity);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            lightIntensity.setText("No Light Sensor Found!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            lightIntensity.setText("Light Intensity: " + lux + " lx");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something if sensor accuracy changes
    }
}
