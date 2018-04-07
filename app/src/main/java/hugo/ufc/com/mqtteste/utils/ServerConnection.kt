package hugo.ufc.com.mqtteste.utils

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL

class ServerConnection(private val url: URL, private val user: String, private val password: String): AsyncTask<String, Unit, Unit>() {
    override fun doInBackground(vararg params: String?) {
        val connection = url.openConnection() as HttpURLConnection
        val urlParams = "user=$user&password=$password"
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded")
        connection.setRequestProperty("Content-Length", urlParams.length.toString())
        connection.doOutput = true
    }


}