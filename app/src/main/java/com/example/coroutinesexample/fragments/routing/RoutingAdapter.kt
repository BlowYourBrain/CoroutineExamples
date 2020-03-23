package com.example.coroutinesexample.fragments.routing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesexample.R
import kotlinx.android.synthetic.main.routing_recycler_element.view.*

class RoutingAdapter(
    val items: List<RoutingElement>,
    val callback: OnRoutingElementClickListener
) : RecyclerView.Adapter<RoutingAdapter.RoutingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routing_recycler_element, null, false)
        return RoutingViewHolder(view, callback)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RoutingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class RoutingViewHolder(private val view: View, private val callback: OnRoutingElementClickListener) :
        RecyclerView.ViewHolder(view) {
        fun bind(routingElement: RoutingElement) {
            view.setOnClickListener { callback.onClick(routingElement) }
            view.icon.setImageResource(routingElement.icon)
            view.name.text = routingElement.name
        }
    }

}


interface OnRoutingElementClickListener {
    fun onClick(routingElement: RoutingElement)
}