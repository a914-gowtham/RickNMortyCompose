package com.gowtham.ricknmorty.utils

fun String.formatToDate(): String {
    val startIndex = 0
    val endIndex = 3
    return this.substring(
        startIndex,
        endIndex
    ) + " " + this.substring(this.indexOfFirst { it.isDigit() })
}
