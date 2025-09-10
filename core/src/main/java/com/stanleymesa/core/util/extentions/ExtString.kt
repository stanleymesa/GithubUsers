package com.stanleymesa.core.util.extentions

import android.util.Patterns
import java.util.Locale

fun String.toLower() = this.lowercase(Locale.ENGLISH)

fun String.toUpper() = this.uppercase(Locale.ENGLISH)

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun String?.ifValueIs(value: String, execute: () -> String): String {
    if (this.equals(value)) {
        return execute.invoke()
    }
    return this.orEmpty()
}

fun String?.ifNull(execute: () -> String): String {
    return this ?: execute.invoke()
}

inline fun String?.ifNullOrEmpty(execute: () -> String) =
    if (this.isNullOrBlank()) execute.invoke() else this

val String.capitalizeWords: String
    get() = split(" ").joinToString(" ") { it ->
        it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }


val String.fileName: String
    get() = try {
        val fileName = this.split("/")
        fileName[fileName.lastIndex]
    } catch (e: Exception) {
        "-"
    }

val String.fileNameWithOutExtension: String
    get() = try {
        val fileName = this.split("/")
        fileName[fileName.lastIndex].split(".").first()
    } catch (e: Exception) {
        "-"
    }

val String.fileExtension: String
    get() = try {
        val arrNameExt = this.split(".")
        arrNameExt[arrNameExt.lastIndex].lowercase()
    } catch (e: Exception) {
        ""
    }

fun String.addArgument(argument: String, nullable: Boolean? = false): String {
    return if (nullable == true) if (this.contains("?")) "${this}$argument&={$argument}" else "${this}?$argument={$argument}" else "${this}/{$argument}"
}

fun String.addArgumentValue(argument: String, value: String, nullable: Boolean? = false): String {
    return if (nullable == true) if (this.contains("?")) "${this}$argument&=$value" else "${this}?$argument=$value" else "${this}/$value"
}