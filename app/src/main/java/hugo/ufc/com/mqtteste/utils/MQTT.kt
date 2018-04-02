package hugo.ufc.com.mqtteste.utils

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

object MQTT: MqttCallback, IMqttActionListener {
    private lateinit var mqttAndroidClient : MqttAndroidClient
    private val clientId : String = "mqtt android client"
    private var lastSensorMsg = MqttMessage()
    private var topic = ""

    val setMqttClient = {
        mqttAndroidClient = MqttAndroidClient(this@MQTT,"tcp://iot.eclipse.org:1883",clientId)
        mqttAndroidClient.setCallback(this@MQTT)
    }

    val setTopic = {newTopic: String->
        topic = newTopic
    }
    override fun messageArrived(topic: String?, message: MqttMessage?) {

    }

    override fun connectionLost(cause: Throwable?) {

    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {

    }

    override fun onSuccess(asyncActionToken: IMqttToken?) {

    }

    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {

    }

    val setLastSensorMsg = { msg: String ->
        lastSensorMsg.payload = msg.toByteArray()
    }

    val connect = { mqttOptions: MqttConnectOptions ->
        mqttAndroidClient.connect(mqttOptions,null, this)
        mqttAndroidClient.publish(topic, lastSensorMsg)
    }
}