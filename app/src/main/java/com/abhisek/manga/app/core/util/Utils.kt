package com.abhisek.manga.app.core.util

import java.time.Instant
import java.time.ZoneId

fun epochToYear(epoch: Int): Int {
    return Instant.ofEpochSecond(epoch.toLong())
        .atZone(ZoneId.systemDefault())
        .year
}