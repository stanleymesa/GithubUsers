package com.stanleymesa.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    fun getFileData(uri: Uri, context: Context): FileData {
        val fileData = FileData()

        // Get the mime type using the content resolver
        fileData.mime = context.contentResolver.getType(uri)

        // Get the file size by querying the content resolver
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {

                // Retrieve the file name from the cursor
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    val displayName = it.getString(nameIndex)
                    if (!displayName.isNullOrEmpty()) {
                        fileData.name = displayName
                    }
                }

                val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
                if (sizeIndex != -1) {
                    fileData.size = it.getLong(sizeIndex)
                }

            }
        }

        // Set the uri of the file
        fileData.uri = uri

        return fileData
    }

    fun getFileDataFromCacheUri(uri: Uri, context: Context): FileData {
        val fileData = FileData()

        // Get the file path from the URI
        val filePath = uri.path

        // Set the file size
        val file = File(filePath.orEmpty())
        if (file.exists()) {
            fileData.size = file.length()
        }

        // Set the file name (parse it from the file path)
        val fileName = filePath?.substring(filePath.lastIndexOf("/") + 1)
        if (!fileName.isNullOrEmpty()) {
            fileData.name = fileName
        }

        // Set the URI
        fileData.uri = uri

        // Determine the MIME type based on the file extension (you may need to add more extensions)
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(filePath)
        if (!fileExtension.isNullOrEmpty()) {
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
            if (!mimeType.isNullOrEmpty()) {
                fileData.mime = mimeType
            }
        }

        return fileData
    }

    fun reduceImageFromUri(uri: Uri, context: Context): File? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap != null) {
                var compressQuality = 100
                var streamLength: Int
                val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
                var bmpPicByteArray: ByteArray?

                do {
                    val bmpStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
                    bmpPicByteArray = bmpStream.toByteArray()
                    streamLength = bmpPicByteArray.size
                    compressQuality -= 5
                } while (streamLength > 5 * 1024 * 1024)

                val outputStream = FileOutputStream(file)
                outputStream.write(bmpPicByteArray)
                outputStream.close()

                return file
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * @param context The context.
     * @param fileData The FileUtils.FileData.
     */
//    @SuppressLint("IntentReset")
//    fun openFile(fileData: FileData, context: Context) {
//        kotlin.runCatching {
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_VIEW
//                type = fileData.mime
//                data = fileData.uri
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                }
//            }
//
//            val shareIntent =
//                Intent.createChooser(sendIntent, context.getString(R.string.open_with))
//            context.startActivity(shareIntent)
//        }.onFailure {
//            Toast.makeText(
//                context,
//                it.message,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }

    /**
     * @param context The context.
     * @param fileData The FileUtils.FileData.
     */
//    fun openFromFile(fileData: FileData, context: Context) {
//        kotlin.runCatching {
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_VIEW
//                type = fileData.mime
//                data = FileProvider.getUriForFile(
//                    context,
//                    context.applicationContext.packageName + ".fileprovider",
//                    fileData.file!!
//                )
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                }
//            }
//
//            val shareIntent =
//                Intent.createChooser(sendIntent, context.getString(R.string.open_with))
//            context.startActivity(shareIntent)
//        }.onFailure {
//            it.printStackTrace()
//            Toast.makeText(
//                context,
//                "${it.message}, File name: ${fileData.name}",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }

    @Keep
    @kotlinx.parcelize.Parcelize
    data class FileData(
        @Json(name = "mime")
        var mime: @kotlinx.parcelize.RawValue String? = null,
        @Json(name = "uri")
        var uri: @kotlinx.parcelize.RawValue Uri? = null,
        @Json(name = "name")
        var name: @kotlinx.parcelize.RawValue String? = null,
        @Json(name = "size")
        var size: @kotlinx.parcelize.RawValue Long? = null,
        @Json(name = "file")
        var file: File? = null,
        @Json(name = "id")
        var id: Int = 0
    ) : Parcelable

}