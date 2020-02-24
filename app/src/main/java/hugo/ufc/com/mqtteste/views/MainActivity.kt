package hugo.ufc.com.mqtteste.views

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import hugo.ufc.com.mqtteste.R
import hugo.ufc.com.mqtteste.fragments.AccelerometerSensorViewFragment
import hugo.ufc.com.mqtteste.fragments.ConfigViewFragment
import hugo.ufc.com.mqtteste.fragments.GyroscopeSensorViewFragment
import hugo.ufc.com.mqtteste.fragments.LightSensorViewFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var lastFragment: Fragment? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> {
                lastFragment = LightSensorViewFragment()
                transaction.replace(R.id.container, lastFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                lastFragment = AccelerometerSensorViewFragment()
                transaction.replace(R.id.container, lastFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                lastFragment = GyroscopeSensorViewFragment()
                transaction.replace(R.id.container, lastFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_config->
            {
                lastFragment = ConfigViewFragment()
                transaction.replace(R.id.container, lastFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()
        if(lastFragment == null) {
            lastFragment = LightSensorViewFragment()
            transaction.add(R.id.container, lastFragment!!)
        }else
            transaction.replace(R.id.container, lastFragment!!)
        transaction.commit()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        when(lastFragment)
        {
            is LightSensorViewFragment->
                navigation.selectedItemId = R.id.navigation_home
            is AccelerometerSensorViewFragment->
                navigation.selectedItemId = R.id.navigation_dashboard
            is GyroscopeSensorViewFragment->
                navigation.selectedItemId = R.id.navigation_notifications
            is ConfigViewFragment->
                navigation.selectedItemId = R.id.navigation_config
        }
    }
}
