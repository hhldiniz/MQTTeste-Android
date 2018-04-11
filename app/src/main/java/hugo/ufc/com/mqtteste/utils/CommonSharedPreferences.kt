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

    val putInt = {key: String, value: Int->
        editor.putInt(key, value)
        editor.commit()
    }

    val getString = {key: String->
        sharedPreferences.getString(key, "")
    }

    val getInt = { key: String->
        sharedPreferences.getInt(key, 0)
    }
}