package com.probation.myapplication.network.statistics

data class MagnitudeCount(
//    val days28: Days28,
//    val days365: Days365,
//    val days7: Days7
        val days365: Map<String,Int>,
        val days28: Map<String,Int>,
        val days7: Map<String,Int>
)