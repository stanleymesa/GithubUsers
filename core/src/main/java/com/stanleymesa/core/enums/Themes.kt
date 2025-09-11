package com.stanleymesa.core.enums

enum class Themes {
    LIGHT,
    DARK,
    DYNAMIC_LIGHT,
    DYNAMIC_DARK,
}

fun String.toThemeEnum() = when (this) {
    Themes.LIGHT.toString() -> Themes.LIGHT
    Themes.DARK.toString() -> Themes.DARK
    Themes.DYNAMIC_LIGHT.toString() -> Themes.DYNAMIC_LIGHT
    Themes.DYNAMIC_DARK.toString() -> Themes.DYNAMIC_DARK
    else -> Themes.LIGHT
}