package hugo.ufc.com.mqtteste.fragments

import android.app.Fragment
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hugo.ufc.com.mqtteste.R
import hugo.ufc.com.mqtteste.views.MainActivity
import kotlinx.android.synthetic.main.config_fragment_view_layout.*

class ConfigViewFragment: Fragment(), View.OnClickListener {
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.save_cfg_btn->
            {

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.config_fragment_view_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        save_cfg_btn.setOnClickListener(this)
        activity as MainActivity
    }
}