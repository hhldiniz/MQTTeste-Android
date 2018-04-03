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
import hugo.ufc.com.mqtteste.utils.MQTT
import kotlinx.android.synthetic.main.gyro_sensor_view_layout.*
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

class GyroscopeSensorViewFragment: Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscopeSensor: Sensor
    private val options = MqttConnectOptions()
    private lateinit var mqtt : MQTT
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        when(p0?.sensor?.type)
        {
            Sensor.TYPE_GYROSCOPE->
            {
                gyro_value.text = p0.values[0].toString()
                val msg = MqttMessage()
                msg.payload = p0.values[0].toString().toByteArray()
                mqtt.publishMsg(msg)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.gyro_sensor_view_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            mqtt = MQTT(activity.applicationContext, "sensor/gyroscope", "AndroidClient")
            mqtt.setMqttClient("tcp://m11.cloudmqtt.com:18446")
            options.userName = "yhdytvpm"
            options.password = "2nMy0rfV-hKE".toCharArray()
            mqtt.connect(options)
            sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this@GyroscopeSensorViewFragment, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }catch (e: IllegalStateException)
        {
            gyro_value.text = getString(R.string.sensor_unavailable)
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            sensorManager.unregisterListener(this@GyroscopeSensorViewFragment)
        }catch (e: RuntimeException)
        {
            gyro_value.text = getString(R.string.sensor_unavailable)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            sensorManager.unregisterListener(this@GyroscopeSensorViewFragment)
        }catch (e: RuntimeException)
        {
            gyro_value.text = getString(R.string.sensor_unavailable)
        }
    }
}