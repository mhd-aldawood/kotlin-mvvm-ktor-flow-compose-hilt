package com.example.kotlintest.screens.users

import android.util.Log
import com.example.kotlintest.BaseViewModel
import javax.inject.Inject

class TestViewModel @Inject constructor() :
BaseViewModel<TestState, TestEvents, TestAction>(
initialState = TestState()
){
    override fun handleAction(action: TestAction) {
        when(action){
            TestAction.Test -> {
                Log.i("","ViewModel Action")
                sendEvent(TestEvents.Send_Events)
            }

        }

    }
}

sealed class TestAction {
    data object Test : TestAction()
}

sealed class TestEvents {
    data object Send_Events : TestEvents()
}

data class TestState (val p:Int=0)

