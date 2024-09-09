package com.example.lightmeter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.miklightmeter.R

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var lightIntensity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lightIntensity = findViewById(R.id.light_intensity)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor == null) {
            lightIntensity.text = "No Light Sensor Found!"
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lux = event.values[0]
            lightIntensity.text = "Light Intensity: $lux lx"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do something if sensor accuracy changes
    }
}
