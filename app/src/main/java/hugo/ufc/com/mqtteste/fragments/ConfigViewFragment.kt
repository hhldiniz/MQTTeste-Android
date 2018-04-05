package hugo.ufc.com.mqtteste.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hugo.ufc.com.mqtteste.R

class ConfigViewFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.config_fragment_view_layout, container, false)
    }
}