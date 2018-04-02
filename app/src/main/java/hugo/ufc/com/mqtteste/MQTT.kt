package hugo.ufc.com.mqtteste

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MQTT(c: Context, mqttMsgView: View): MqttCallback, IMqttActionListener {
    private val mqttAndroidClient : MqttAndroidClient
    private val clientId : String = "mqtt android client"
    private val context = c
    private val msgView = mqttMsgView

    init {
        mqttAndroidClient = MqttAndroidClient(context,"iot.eclipse.org:1883",clientId)
        mqttAndroidClient.setCallback(this@MQTT)
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        context as Activity
        Snackbar.make(msgView,
                String.format(
                context.resources.getString(R.string.mqtt_msg_arrived),topic,message
                ),Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun connectionLost(cause: Throwable?) {

    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {

    }

    override fun onSuccess(asyncActionToken: IMqttToken?) {

    }

    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {

    }
    val connect = { mqttOptions: MqttConnectOptions ->
        mqttAndroidClient.connect(mqttOptions,context, this)
    }
}