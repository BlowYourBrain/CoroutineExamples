package com.example.coroutinesexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.timer_example_fragment_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TimerExampleFragment : Fragment() {

    lateinit var scope: CoroutineScope
    lateinit var timerChannel: ReceiveChannel<Unit>

    val timerToggleChannel = Channel<Boolean>(Channel.CONFLATED)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.timer_example_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scope = MainScope()

        timerChannel = scope.createTimerAndAssignWithTextView()

        timerSwitch.isChecked = true
        timerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            timerToggleChannel.offer(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
    }

    fun CoroutineScope.createTimerAndAssignWithTextView(): ReceiveChannel<Unit> {
        return repeatableTimer(1000, timerToggleChannel).apply {
            var seconds = 0

            consumeAsFlow()
                .onEach { timer.text = "${seconds++}\nseconds" }
                .launchIn(scope)
        }
    }

}