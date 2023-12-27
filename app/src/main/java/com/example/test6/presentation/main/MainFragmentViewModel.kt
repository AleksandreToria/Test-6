package com.example.test6.presentation.main

import androidx.lifecycle.ViewModel
import com.example.test6.data.Data
import com.example.test6.data.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor() : ViewModel() {

    private val _passcodeState = MutableStateFlow<String>("")
    val passcodeState: StateFlow<String> = _passcodeState.asStateFlow()

    fun handleButtonClick(clickedData: Data) {
        when (clickedData.type) {
            Type.NUMBER -> {
                if (_passcodeState.value.length < 4) {
                    _passcodeState.value += clickedData.number

                    if (_passcodeState.value.length == 4) {
                        checkPasscode()
                    }
                }
            }

            Type.DELETE -> {
                if (_passcodeState.value.isNotEmpty()) {
                    _passcodeState.value = _passcodeState.value.dropLast(1)
                }
            }

            else -> {
            }
        }
    }

    private fun checkPasscode() {
        val enteredPasscode = _passcodeState.value
        if (enteredPasscode == "0934") {
            _passcodeState.value = "Success"
        } else {
            _passcodeState.value = ""
        }
    }
}
