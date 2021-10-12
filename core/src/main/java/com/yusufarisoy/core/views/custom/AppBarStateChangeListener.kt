package com.yusufarisoy.core.views.custom

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var currentState = State.EXPANDED

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, offset: Int) {
        when {
            offset == 0 -> setState(State.EXPANDED)
            abs(offset) >= appBarLayout?.totalScrollRange ?: 0 -> setState(State.COLLAPSED)
            else -> setState(State.IDLE)
        }
    }

    private fun setState(state: State) {
        if (currentState != state) {
            currentState = state
            onStateChanged(currentState)
        }
    }

    abstract fun onStateChanged(state: State)
}