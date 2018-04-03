package hugo.ufc.com.mqtteste.utils

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MQTT(c: Context, private val topic: String, clientId: String): MqttCallback, IMqttActionListener {
    private lateinit var mqttAndroidClient : MqttAndroidClient
    private var lastSensorMsg = MqttMessage()

    val setMqttClient = {uri: String->
        mqttAndroidClient = MqttAndroidClient(c,uri,clientId)
        mqttAndroidClient.setCallback(this@MQTT)
    }

    override fun messageArrived(topic: String?, message: MqttMessage) {

    }

    override fun connectionLost(cause: Throwable?) {

    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {

    }

    override fun onSuccess(asyncActionToken: IMqttToken?) {

    }

    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {

    }

    val setLastSensorMsg = { msg: MqttMessage ->
        lastSensorMsg = msg
    }

    val connect = { mqttOptions: MqttConnectOptions ->
        mqttAndroidClient.connect(mqttOptions,null, this)
    }

    val publishMsg = { message: MqttMessage->
        mqttAndroidClient.publish(topic, lastSensorMsg)
    }
}