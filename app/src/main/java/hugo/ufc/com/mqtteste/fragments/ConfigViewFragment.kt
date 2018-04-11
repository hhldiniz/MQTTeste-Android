package hugo.ufc.com.mqtteste.fragments

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hugo.ufc.com.mqtteste.R
import hugo.ufc.com.mqtteste.views.MainActivity
import kotlinx.android.synthetic.main.config_fragment_view_layout.*

class ConfigViewFragment: Fragment(), View.OnClickListener {
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onClick(p0: View?) {
        when(p0)
        {
            save_cfg_btn->
            {
                val username = user.text.toString()
                val password = pass.text.toString()
                val server = mqtt_server.text.toString()
                val port = mqtt_port.text.toString()
                val conMethod = connection_method.checkedRadioButtonId
                editor.putString("username",username)
                editor.putString("password",password)
                editor.putString("server",server)
                editor.putString("port", port)
                editor.putInt("connection_method", conMethod)
                editor.apply()
                Snackbar.make(config_fragment_root, R.string.save_conf_msg, Snackbar.LENGTH_SHORT).show()
            }
            reset_values_btn->
            {
                Toast.makeText(activity.baseContext, "clicou", Toast.LENGTH_SHORT).show()
                val conMethod = connection_method.checkedRadioButtonId
                when(conMethod)
                {
                    R.id.radio_mqtt->
                    {
                        user.setText(R.string.default_mqtt_username)
                        pass.setText(R.string.default_mqtt_password)
                        mqtt_server.setText(R.string.default_mqtt_server)
                        mqtt_port.setText(R.string.default_mqtt_port)
                    }
                    R.id.radio_client_server->
                    {
                        user.setText(R.string.default_client_server_username)
                        pass.setText(R.string.default_client_server_password)
                        mqtt_server.setText(R.string.default_client_server_server)
                        mqtt_port.setText(R.string.default_client_server_port)
                    }
                    else->
                            Toast.makeText(activity.baseContext, "WUT?", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.config_fragment_view_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        save_cfg_btn.setOnClickListener(this)
        reset_values_btn.setOnClickListener(this)
        val mainActivity = activity as MainActivity
        preferences = mainActivity.getSharedPreferences("config",Context.MODE_PRIVATE)
        editor = preferences.edit()
        editor.apply()
    }
}