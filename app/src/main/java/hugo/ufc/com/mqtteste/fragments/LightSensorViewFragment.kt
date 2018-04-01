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
import kotlinx.android.synthetic.main.light_sensor_view_layout.*

class LightSensorViewFragment: Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        when(p0?.sensor?.type)
        {
            Sensor.TYPE_LIGHT->
            {
                light_value.text = p0.values[0].toString()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.light_sensor_view_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorManager.registerListener(this@LightSensorViewFragment, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this@LightSensorViewFragment)
    }

    override fun onDetach() {
        super.onDetach()
        sensorManager.unregisterListener(this@LightSensorViewFragment)
    }
}