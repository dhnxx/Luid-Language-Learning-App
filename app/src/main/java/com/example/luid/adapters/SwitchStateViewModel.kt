package com.example.luid.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SwitchStateViewModel : ViewModel() {

    private val _switchState = MutableLiveData<String>().apply { value = "0" }

    fun setSwitchState(state: String) {
        _switchState.value = state
    }

    fun getSwitchState(): LiveData<String> {
        return _switchState
    }
}