package com.igsolution.safeneighborhood.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dados(
    val url: String = " "
    ) :Parcelable

@Parcelize
data class Localizacao(
    val latLng: String
):Parcelable