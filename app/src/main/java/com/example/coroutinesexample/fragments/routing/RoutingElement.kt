package com.example.coroutinesexample.fragments.routing

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

data class RoutingElement(
    @DrawableRes
    val icon: Int,
    val name: String,
    val fragmentInstance: () -> Fragment
)