package hugo.ufc.com.mqtteste.utils

import android.content.Context
import hugo.ufc.com.mqtteste.views.MainActivity

class CommonSharedPreferences(c: Context) {
    private val sharedPreferences = (c as MainActivity).getSharedPreferences("config",Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
}