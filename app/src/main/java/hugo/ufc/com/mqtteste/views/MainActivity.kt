package hugo.ufc.com.mqtteste.views

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import hugo.ufc.com.mqtteste.R
import hugo.ufc.com.mqtteste.fragments.AccelerometerSensorViewFragment
import hugo.ufc.com.mqtteste.fragments.GyroscopeSensorViewFragment
import hugo.ufc.com.mqtteste.fragments.LightSensorViewFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var lastFragment: Fragment? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = fragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> {
                lastFragment = LightSensorViewFragment()
                transaction.replace(R.id.container, lastFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                lastFragment = AccelerometerSensorViewFragment()
                transaction.replace(R.id.container, lastFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                lastFragment = GyroscopeSensorViewFragment()
                transaction.replace(R.id.container, lastFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = fragmentManager.beginTransaction()
        if(lastFragment == null) {
            lastFragment = LightSensorViewFragment()
            transaction.add(R.id.container, lastFragment)
        }else
            transaction.replace(R.id.container, lastFragment)
        transaction.commit()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        when(lastFragment)
        {
            is LightSensorViewFragment->
            {
                navigation.selectedItemId = R.id.navigation_home
            }
            is AccelerometerSensorViewFragment->
            {
                navigation.selectedItemId = R.id.navigation_dashboard
            }
            is GyroscopeSensorViewFragment->
            {
                navigation.selectedItemId = R.id.navigation_notifications
            }
        }
    }
}
