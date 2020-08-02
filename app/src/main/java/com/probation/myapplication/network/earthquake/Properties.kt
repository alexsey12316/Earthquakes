package com.probation.myapplication.network.earthquake

data class Properties(
    val depth: Double,
    val locality: String,
    val magnitude: Double,
    val mmi: Int,
    val publicID: String,
    val quality: String,
    val time: String
)