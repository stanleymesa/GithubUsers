package com.stanleymesa.core.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException

abstract class BaseJsonAdapter<T> : JsonAdapter<T>() {

    internal fun readObject(reader: JsonReader): Any? {
        return when (reader.peek()) {
            JsonReader.Token.BEGIN_ARRAY -> {
                val list = mutableListOf<Any?>()
                reader.beginArray()
                while (reader.hasNext()) {
                    list.add(readObject(reader)) // Recursive call to the helper method
                }
                reader.endArray()
                list
            }

            JsonReader.Token.BEGIN_OBJECT -> {
                val map = linkedMapOf<String, Any?>()
                reader.beginObject()
                while (reader.hasNext()) {
                    map[reader.nextName()] = readObject(reader) // Recursive call
                }
                reader.endObject()
                map
            }

            JsonReader.Token.STRING -> reader.nextString()
            JsonReader.Token.NUMBER -> reader.nextDouble()
            JsonReader.Token.BOOLEAN -> reader.nextBoolean()
            JsonReader.Token.NULL -> reader.nextNull()

            else -> throw IllegalStateException("Unexpected token: ${reader.peek()}")
        }
    }

    // The toJson method is required, but we'll leave it unimplemented
    // as the focus is on reading JSON.
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: T?) {
        throw UnsupportedOperationException("BaseJsonAdapter is only used for deserialization.")
    }
}