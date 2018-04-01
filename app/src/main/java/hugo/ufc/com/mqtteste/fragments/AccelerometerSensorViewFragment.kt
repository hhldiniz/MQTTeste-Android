package hugo.ufc.com.mqtteste.fragments

import android.app.Fragment
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hugo.ufc.com.mqtteste.R
import kotlinx.android.synthetic.main.accelerometer_sensor_view_layout.*

class AccelerometerSensorViewFragment: Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerationSensor: Sensor
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        when(p0?.sensor?.type)
        {
            Sensor.TYPE_LINEAR_ACCELERATION->
            {
                acceleration_value.text = String.format(resources.getString(R.string.accel_values),
                        p0.values[0],
                        p0.values[1],
                        p0.values[2])
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.accelerometer_sensor_view_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
            sensorManager.registerListener(this@AccelerometerSensorViewFragment, accelerationSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }catch (e: IllegalStateException)
        {
            acceleration_value.text = resources.getString(R.string.sensor_unavailable)
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            sensorManager.unregisterListener(this@AccelerometerSensorViewFragment)
        }catch (e: RuntimeException)
        {
            acceleration_value.text = resources.getString(R.string.sensor_unavailable)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            sensorManager.unregisterListener(this@AccelerometerSensorViewFragment)
        }catch (e: RuntimeException)
        {
            acceleration_value.text = resources.getString(R.string.sensor_unavailable)
        }
    }
}