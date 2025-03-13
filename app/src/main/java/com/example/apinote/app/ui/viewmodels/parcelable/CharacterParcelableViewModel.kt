package com.example.apinote.app.ui.viewmodels.parcelable

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apinote.app.ui.navigation.ReceivingDataInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterParcelableViewModel @Inject constructor() : ViewModel() {

    private val _userData = mutableStateListOf("", "", "", "", "", "")
    val userData: List<String> get() = _userData

    val fieldLabels = listOf(
        "Name", "Surname", "Age", "Address", "Identity Document", "Email Address"
    )

    val keyboardTypes = listOf(
        KeyboardType.Text, KeyboardType.Text, KeyboardType.Number,
        KeyboardType.Text, KeyboardType.Text, KeyboardType.Email
    )

    fun updateField(index: Int, value: String) {
        _userData[index] = value
    }

    fun getReceivingDataInfo(): ReceivingDataInfo {
        return ReceivingDataInfo(
            name = _userData[0],
            surname = _userData[1],
            age = _userData[2].toIntOrNull() ?: 0,
            address = _userData[3],
            identityDocument = _userData[4],
            emailAddress = _userData[5]
        )
    }

    fun clearFields() {
        viewModelScope.launch {
            delay(300)
            _userData.replaceAll { "" }
        }
    }
}