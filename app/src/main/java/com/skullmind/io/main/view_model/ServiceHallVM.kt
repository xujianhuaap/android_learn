package com.skullmind.io.main.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.skullmind.io.main.vo.ConfigItem
import com.skullmind.io.main.vo.RecommendItem
import com.skullmind.io.main.vo.getMenuData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServiceHallVM : ViewModel() {
    private val _viewState = MutableStateFlow(ServiceHallViewState())
    val viewState = _viewState.asStateFlow()

    private fun getPrimaryServiceListAndSearchHint() {
        CoroutineScope(Dispatchers.Default).launch {
            delay(100)
            _viewState.emit(viewState.value.copy(searchHint = "更多精彩，尽在南航官网", primaryServiceList = getMenuData().subList(0,5) ))
        }
    }

    fun reduce(event: Event) {
        when (event) {
            Event.Start -> {
                Log.d(ServiceHallVM::class.java.name, "Event Start")
                getPrimaryServiceListAndSearchHint()
            }

            Event.Dispose -> {
                Log.d(ServiceHallVM::class.java.name, "Event Dispose")
            }

            Event.Pause -> {
                Log.d(ServiceHallVM::class.java.name, "Event Pause")
            }

            Event.ClickSearch -> {
                Log.d(ServiceHallVM::class.java.name, "Event Pause")
            }

            is Event.ClickShrink -> {
                Log.d(ServiceHallVM::class.java.name, "Event ClickShrink")
                _viewState.value = viewState.value.copy(isShrink = !event.isShrink)
            }
            else -> Unit
        }
    }
}

data class ServiceHallViewState(
    val searchHint: String = "南航官网欢迎您",
    val primaryServiceList: List<ConfigItem> = listOf(),
    val serviceGroup: Map<String, List<ConfigItem>> = HashMap(),
    val isShrink: Boolean = false,
    val recommendServiceList: List<RecommendItem> = listOf()
)

sealed class Event {
    object Start : Event()
    object Pause : Event()
    object Dispose : Event()
    object ClickSearch : Event()
    data class ClickShrink(val isShrink: Boolean) : Event()
}