package com.example.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutinesexample.fragments.routing.OnRoutingElementClickListener
import com.example.coroutinesexample.fragments.routing.RoutingElement
import com.example.coroutinesexample.fragments.routing.RoutingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        val fragment = RoutingFragment().apply {
            callback = object : OnRoutingElementClickListener {
                override fun onClick(routingElement: RoutingElement) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container2, routingElement.fragmentInstance.invoke())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container2, fragment)
            .commitAllowingStateLoss()
    }

}
