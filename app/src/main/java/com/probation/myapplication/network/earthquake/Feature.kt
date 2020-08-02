package com.probation.myapplication.network.earthquake

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)