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
import android.widget.Toast
import hugo.ufc.com.mqtteste.R
import hugo.ufc.com.mqtteste.interfaces.SensorFragment
import hugo.ufc.com.mqtteste.utils.CommonSharedPreferences
import hugo.ufc.com.mqtteste.utils.MQTT
import hugo.ufc.com.mqtteste.utils.ServerConnection
import kotlinx.android.synthetic.main.light_sensor_view_layout.*
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.net.URL

class LightSensorViewFragment: Fragment(), SensorEventListener, SensorFragment {
    override fun startConfigs() {
        prefs.putString("username",resources.getString(R.string.default_mqtt_username))
        prefs.putString("password",resources.getString(R.string.default_mqtt_password))
        prefs.putString("port", resources.getString(R.string.default_mqtt_port))
        prefs.putString("server", resources.getString(R.string.default_mqtt_server))
        prefs.putInt("connection_method", R.id.radio_mqtt)
    }

    override fun startMqtt() {
        mqtt = MQTT(activity.applicationContext, "sensor/light", "AndroidClient")
        mqtt.setMqttClient("tcp://${prefs.getString("server")}:${prefs.getString("port")}")
        options.userName = prefs.getString("username")
        options.password = prefs.getString("password").toCharArray()
        mqtt.connect(options)
    }

    override fun startClientServer() {
        val url = URL("http://${prefs.getString("server")}")
        tcpServer = ServerConnection(url,prefs.getString("username"),prefs.getString("password"))
    }

    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor
    private val options = MqttConnectOptions()
    private lateinit var mqtt :MQTT
    private lateinit var prefs: CommonSharedPreferences
    private lateinit var tcpServer: ServerConnection
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
                try {
                    mqtt.publishMsg(msg)
                }catch (e: UninitializedPropertyAccessException)
                {
                    Toast.makeText(activity.baseContext, R.string.server_loading, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.light_sensor_view_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prefs = CommonSharedPreferences(activity)
        try {
            if(prefs.getInt("connection_method") == 0)
                startConfigs()
            if(prefs.getInt("connection_method") == R.id.radio_mqtt)
                startMqtt()
            else
                startClientServer()
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