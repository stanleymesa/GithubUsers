package com.stanleymesa.core.util.extentions

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import com.stanleymesa.core.datastore.AppDataStoreKeys
import com.stanleymesa.core.enums.Themes
import okhttp3.internal.toHexString
import java.math.RoundingMode
import java.text.NumberFormat
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.math.roundToLong
import kotlin.random.Random
import androidx.core.graphics.toColorInt

/**
 * Created by fizhu on 22,May,2020
 * Hvyz.anbiya@gmail.com
 */

fun Any.toJsonPretty(): String = GsonBuilder().setPrettyPrinting().create().toJson(this)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppDataStoreKeys.APP_DATA_STORE_NAME)

fun <T> ArrayList<T>?.orArrayListEmpty(): ArrayList<T> = this ?: arrayListOf()

inline fun <reified T : Parcelable> Bundle.parcelize(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this.getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") this.getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelizeList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this.getParcelableArrayList(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") this.getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.parcelize(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this.getParcelableExtra(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") this.getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Intent.parcelizeList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this.getParcelableArrayListExtra(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") this.getParcelableArrayListExtra(key)
}

inline fun <reified T : java.io.Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this.getSerializable(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") this.getSerializable(key) as? T
}

inline fun <reified T : java.io.Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> this.getSerializableExtra(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") this.getSerializableExtra(key) as? T
}

fun Picture.asBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(
        this.width,
        this.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    canvas.drawPicture(this)
    return bitmap
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size = remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "shimmer")
    val startOffsetX = transition.animateFloat(
        initialValue = -2 * size.value.width.toFloat(),
        targetValue = 2 * size.value.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
        label = "shimmer"
    )

    val bgColor = MaterialTheme.colorScheme.background
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                bgColor.blendWithLuminance(0.1f),
                bgColor.blendWithLuminance(0.2f),
                bgColor.blendWithLuminance(0.1f),
            ),
            start = Offset(startOffsetX.value, 0f),
            end = Offset(
                startOffsetX.value + size.value.width.toFloat(),
                size.value.height.toFloat()
            )
        )
    )
        .onGloballyPositioned {
            size.value = it.size
        }
}

fun Color.blendWithLuminance(ratio: Float): Color {
    val isDark = this.luminance() < 0.5f
    return Color(
        ColorUtils.blendARGB(
            this.toArgb(),
            (if (isDark) Color.White else Color.Black).toArgb(),
            ratio
        )
    )
}

/**
 * Open given [url] to a browser.
 */
fun Context.openUrl(url: String) {
    kotlin.runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }.onFailure {
        it.printStackTrace()
        longToast("Cannot open the link!")
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}


fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}


fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
    permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

fun Context.getDrawableCompat(@DrawableRes drawableResId: Int) =
    ContextCompat.getDrawable(this, drawableResId)

fun Context.getColorCompat(@ColorRes colorResId: Int) =
    ContextCompat.getColor(this, colorResId)

fun Context.getColorStateListCompat(@ColorRes colorResId: Int) =
    ContextCompat.getColorStateList(this, colorResId)

// To get scroll offset
@OptIn(ExperimentalFoundationApi::class)
val PagerState.pageOffset: Float
    get() = this.currentPage + this.currentPageOffsetFraction


// To get scrolled offset from snap position
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

fun Float?.removeZeroOnDecimal(): String = kotlin.runCatching {
    val ceilNum = this?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)
    NumberFormat.getInstance().format(ceilNum).toString()
}.getOrElse {
    this.toString()
}

fun String?.removeZeroOnDecimalWithNoComma(): String = kotlin.runCatching {
    val ceilNum = this?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)
    NumberFormat.getInstance().format(ceilNum).toString().replace(",", "")
}.getOrElse {
    this.orEmpty()
}

fun String?.removeZeroOnDecimal(): String = kotlin.runCatching {
    val ceilNum = this?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)
    NumberFormat.getInstance().format(ceilNum).toString()
}.getOrElse {
    this.orEmpty()
}

fun String?.toFloatOrZero(): Float = kotlin.runCatching {
    this!!.toFloat()
}.getOrElse {
    0f
}

fun Float?.roundToLongWithHandler(): Long = kotlin.runCatching {
    if (this?.isNaN() == true) 0 else this?.roundToLong().orZero()
}.getOrElse {
    0
}

fun String?.toDoubleOrZero(): Double = kotlin.runCatching {
    this!!.toDouble()
}.getOrElse {
    0.0
}

fun String?.zeroIfBlankOrNull(): String = kotlin.runCatching {
    if (this == "") "0" else this ?: "0"
}.getOrElse {
    "0"
}

fun Context.copy(text: String, actionAfter: () -> Unit = {}) {
    val clipboard: ClipboardManager =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("soap", text)
    clipboard.setPrimaryClip(clip)
    actionAfter.invoke()
}

/**
 * Convert [Int] into [mm:ss]
 */
fun Int.toMinuteSeconds(): String =
    runCatching {
        val minute = this / 60
        val strMinute = if (minute < 10) "0$minute" else minute.toString()

        val second = this % 60
        val strSecond = if (second < 10) "0$second" else second.toString()

        "$strMinute:$strSecond"
    }.getOrElse { "" }

fun getRandomPastelColor(themes: Themes): Color =
    Color.hsl(
        alpha = 1f,
        hue = Random.nextDouble(0.0, 360.0).toFloat(),
        saturation = Random.nextDouble(0.25, 0.95).toFloat(),
        lightness = when (themes) {
            Themes.LIGHT, Themes.DYNAMIC_LIGHT -> Random.nextDouble(0.2, 0.4).toFloat()
            else -> Random.nextDouble(0.85, 0.95).toFloat()
        }
    )

fun String?.orStrip() = this.ifNullOrEmpty { "-" }

@Composable
fun String?.toColor() = kotlin.runCatching {
    val color = this ?: MaterialTheme.colorScheme.onBackground
        .toArgb()
        .toHexString()
    Color(
        "#$color".toColorInt()
    )
}.getOrElse {
    MaterialTheme.colorScheme.onBackground
}

@OptIn(ExperimentalEncodingApi::class)
fun String.encodeToBase64() = kotlin.io.encoding.Base64.encode(this.toByteArray())

@OptIn(ExperimentalEncodingApi::class)
fun String.decodeToString() = String(kotlin.io.encoding.Base64.decode(this.toByteArray()))

