package com.omidavz.project.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

fun dataValidator(
    name: String,
    lastName: String,
    dateOfBirth: String,
    identityNumber: String): Boolean {
    return if (
        name.isEmpty() ||
        lastName.isEmpty() ||
        dateOfBirth.isEmpty() ||
        identityNumber.isEmpty()
    ) false
    else if (
        identityNumber.length != 10
    ) false
    else {
        true
    }


}