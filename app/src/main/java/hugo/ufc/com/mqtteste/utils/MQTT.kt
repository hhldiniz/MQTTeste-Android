package hugo.ufc.com.mqtteste.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import hugo.ufc.com.mqtteste.R
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MQTT(private val c: Context, private val topic: String, clientId: String): MqttCallback, IMqttActionListener {
    private lateinit var mqttAndroidClient : MqttAndroidClient
    val setMqttClient = {uri: String->
        mqttAndroidClient = MqttAndroidClient(c,uri,clientId)
        mqttAndroidClient.setCallback(this@MQTT)
    }

    override fun messageArrived(topic: String?, message: MqttMessage) {

    }

    override fun connectionLost(cause: Throwable?) {
        Log.e("mqtt_con_error", cause?.message)
        Toast.makeText(c, R.string.mqtt_connection_error, Toast.LENGTH_SHORT).show()
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {

    }

    override fun onSuccess(asyncActionToken: IMqttToken?) {
        Toast.makeText(c, R.string.mqtt_connected, Toast.LENGTH_LONG).show()
    }

    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
        Log.e("mqtt_con_error", exception?.message)
        Toast.makeText(c, R.string.mqtt_connection_error, Toast.LENGTH_SHORT).show()
    }

    val connect = { mqttOptions: MqttConnectOptions ->
        mqttAndroidClient.connect(mqttOptions,null, this)
    }

    val publishMsg = { message: MqttMessage->
            if (mqttAndroidClient.isConnected)
                mqttAndroidClient.publish(topic, message)
    }
}