package com.example.apinote.app.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Menu


@Serializable
object List

@Serializable
data class Detail(val characterId: Int)


@Serializable
object Notes


@Serializable
object CharacterParcelable

@Serializable
data class ReceivingData(val receivingDataInfo: ReceivingDataInfo)

@Serializable
@Parcelize
data class ReceivingDataInfo(
    val name: String,
    val surname: String,
    val age: Int,
    val address: String,
    val identityDocument: String,
    val emailAddress: String
) : Parcelable
