package hugo.ufc.com.mqtteste.utils

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL

class ServerConnection(private val url: URL, private val user: String, private val password: String): AsyncTask<String, Unit, Unit>() {
    private lateinit var connection: HttpURLConnection
    private lateinit var response: Any
    override fun doInBackground(vararg params: String?) {
        connection = url.openConnection() as HttpURLConnection
        val urlParams = "user=$user&password=$password"
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded")
        connection.setRequestProperty("Content-Length", urlParams.length.toString())
        connection.doOutput = true
        connection.connect()
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        response = connection.content
    }

    val getResponse = {
        response
    }
}