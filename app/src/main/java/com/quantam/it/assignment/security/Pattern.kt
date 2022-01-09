package com.quantam.it.assignment.security

import java.util.regex.Pattern

fun passwordPattern(text: String): Boolean {
    val pattern = Pattern.compile(PASSWORD_PATTERN)
    val matcher = pattern.matcher(text)
    return matcher.matches()
}