package com.example.apinote.app.ui.viewmodels.parcelable

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.apinote.app.ui.navigation.ReceivingDataInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ReceivingDataViewModel @Inject constructor() : ViewModel() {

    private val _receivingData = mutableStateOf<ReceivingDataInfo?>(null)
    val receivingData: State<ReceivingDataInfo?> get() = _receivingData

    fun setReceivingData(data: ReceivingDataInfo) {
        _receivingData.value = data
    }

    fun formatAge(age: Int): String {
        return if (age > 0) "$age years old" else "Unknown age"
    }
}