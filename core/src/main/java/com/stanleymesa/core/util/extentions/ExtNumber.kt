package com.stanleymesa.core.util.extentions

import android.content.Context
import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.text.CompactDecimalFormat
import android.icu.text.MessageFormat
import android.icu.text.NumberFormat
import android.os.Build
import com.stanleymesa.core.R
import com.stanleymesa.core.datastore.AppDataStoreKeys
import com.stanleymesa.core.util.LocaleHelper
import com.stanleymesa.core.util.LocaleHelper.toLocaleEnum
import com.stanleymesa.core.util.LocaleHelper.toLocaleString
import com.stanleymesa.core.util.extentions.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow

fun Int.toWords(language: String = "en", country: String = "US"): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val formatter = MessageFormat(
            "{0,spellout,currency}",
            Locale(language, country)
        )
        formatter.format(arrayOf(this))
    } else {
        this.toString()
    }
}

fun Long.toRupiah(): String {
    val localeID = Locale("in", "ID")
    val numberFormat = java.text.NumberFormat.getCurrencyInstance(localeID)
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(this).toString()
}

fun Long.toCommaFormat(locale: Locale = Locale("in", "ID")): String {
    val numberFormat = NumberFormat.getInstance(locale)
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(this).toString()
}

fun Int?.orZero(): Int = this ?: 0
fun Double?.orZero(): Double = this ?: 0.0
fun Long?.orZero(): Long = this ?: 0
fun Int?.orStringEmpty(): Int = this ?: R.string.empty_string

fun Int.divideToPercent(divideTo: Int): Int {
    return if (divideTo == 0) 0
    else (this / divideTo.toFloat() * 100).toInt()
}

fun IntArray?.orEmpty(): IntArray = this ?: emptyArray<Int>().toIntArray()

fun Int?.orResourceStringEmpty(): Int = this ?: R.string.empty_string

fun Int?.orResourceStringWhiteSpace(): Int = this ?: R.string.empty_white_space


fun Long.shortNotation(context: Context): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        var locale: Locale?
        runBlocking {
            locale = Locale(
                context.dataStore.data.first()[AppDataStoreKeys.KEY_LANGUAGE]?.toLocaleEnum()
                    ?.toLocaleString() ?: LocaleHelper.AppLanguage.INDONESIA.toLocaleString()
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) NumberFormatter.with()
            .notation(Notation.compactShort())
            .locale(locale)
            .format(this)
            .toString()
        else CompactDecimalFormat
            .getInstance(locale, CompactDecimalFormat.CompactStyle.SHORT)
            .format(this)
    } else {
        val base = 1_000f
        if (this < base) return "$this"
        val ratio = ln(this.toDouble()) / ln(base)
        val exp = ratio.toInt()
        val format = if (ratio % 1 * 3 < 1) "%.1f%c" else "%.0f%c"
        @Suppress("SpellCheckingInspection")
        String.format(format, this / base.pow(exp), "kMGTPE"[exp - 1])
    }
}

fun Int.toCurrency(): String =
    java.text.NumberFormat.getNumberInstance(Locale("in", "ID")).format(this)

fun Int?.ifNull(execute: () -> Int): Int {
    return this ?: execute.invoke()
}

fun Float?.orZero() = this ?: 0f