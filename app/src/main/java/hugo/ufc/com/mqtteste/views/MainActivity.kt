package hugo.ufc.com.mqtteste.views

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import hugo.ufc.com.mqtteste.R
import hugo.ufc.com.mqtteste.fragments.AccelerometerSensorViewFragment
import hugo.ufc.com.mqtteste.fragments.GyroscopeSensorViewFragment
import hugo.ufc.com.mqtteste.fragments.LightSensorViewFragment
import hugo.ufc.com.mqtteste.utils.MQTT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mqtt: MQTT
    private var topic = "sensor/light"
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = fragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> {
                transaction.replace(R.id.container, LightSensorViewFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                transaction.replace(R.id.container, AccelerometerSensorViewFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                transaction.replace(R.id.container, GyroscopeSensorViewFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.container, LightSensorViewFragment())
        transaction.commit()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        val mqttConnectOptions = MqttConnectOptions()
//        mqtt = MQTT
//        mqtt.connect(mqttConnectOptions)
    }
}
