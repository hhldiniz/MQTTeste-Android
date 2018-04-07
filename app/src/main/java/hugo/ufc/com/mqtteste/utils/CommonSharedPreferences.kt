package hugo.ufc.com.mqtteste.utils

import android.app.Activity
import android.content.Context

class CommonSharedPreferences(c: Context) {
    private val sharedPreferences = (c as Activity).getSharedPreferences("config",Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    val putString = { key:String, value: String ->
        editor.putString(key, value)
        editor.commit()
    }
}