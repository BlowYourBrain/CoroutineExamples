package com.example.coroutinesexample.fragments.routing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coroutinesexample.R
import com.example.coroutinesexample.TimerExampleFragment
import kotlinx.android.synthetic.main.routing_fragment_layout.*

class RoutingFragment : Fragment() {

    var callback: OnRoutingElementClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.routing_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        routingRecycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = RoutingAdapter(routingList, object : OnRoutingElementClickListener {
                override fun onClick(routingElement: RoutingElement) {
                    callback?.onClick(routingElement)
                }
            })
            setHasFixedSize(true)
        }
    }

    val routingList: List<RoutingElement> = listOf(
        RoutingElement(
            R.drawable.ic_timer_black_24dp,
            "Timer"
        ) { TimerExampleFragment() }
    )
}