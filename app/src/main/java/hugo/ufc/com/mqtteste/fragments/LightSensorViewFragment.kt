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
import kotlinx.android.synthetic.main.light_sensor_view_layout.*
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

class LightSensorViewFragment: Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor
    private val options = MqttConnectOptions()
    private lateinit var mqtt :MQTT
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        when(p0?.sensor?.type)
        {
            Sensor.TYPE_LIGHT->
            {
                light_value.text = p0.values[0].toString()
                val msg = MqttMessage()
                msg.payload = p0.values[0].toString().toByteArray()
                mqtt.publishMsg(msg)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.light_sensor_view_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            mqtt = MQTT(activity.applicationContext, "sensor/light", "AndroidClient")
            mqtt.setMqttClient("tcp://m11.cloudmqtt.com:18446")
            options.userName = "yhdytvpm"
            options.password = "2nMy0rfV-hKE".toCharArray()
            mqtt.connect(options)
            sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this@LightSensorViewFragment, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }catch (e: IllegalStateException)
        {
            light_value.text = resources.getString(R.string.sensor_unavailable)
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            sensorManager.unregisterListener(this@LightSensorViewFragment)
        }catch (e: RuntimeException)
        {
            light_value.text = resources.getString(R.string.sensor_unavailable)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            sensorManager.unregisterListener(this@LightSensorViewFragment)
        }catch (e: RuntimeException)
        {
            light_value.text = resources.getString(R.string.sensor_unavailable)
        }
    }
}