package com.abhisek.manga.app.core.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun epochToYear(epoch: Int): Int {
    return Instant.ofEpochSecond(epoch.toLong())
        .atZone(ZoneId.systemDefault())
        .year
}

fun epochToReadableDate(epoch: Int): String {
    val instant = Instant.ofEpochSecond(epoch.toLong())
    val zonedDateTime = instant.atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return formatter.format(zonedDateTime)
}