package com.stanleymesa.core.util.extentions

fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean.isFalse() = !this
fun Boolean?.isTrue() = this.orFalse()