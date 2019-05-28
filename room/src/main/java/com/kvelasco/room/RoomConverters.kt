package com.kvelasco.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RoomConverters {
    companion object {
        // Type converters for list of Ints
        @TypeConverter
        @JvmStatic
        fun fromIntList(list: List<Int>?): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toIntList(jsonString: String?): List<Int> {
            val listType: Type = object : TypeToken<List<Int>>() {}.type
            return Gson().fromJson(jsonString, listType) ?: emptyList()
        }

        // Type converters for list of Strings
        @TypeConverter
        @JvmStatic
        fun fromStringList(list: List<String>?): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toStringList(jsonString: String?): List<String> {
            val listType: Type = object : TypeToken<List<String>>() {}.type
            return Gson().fromJson(jsonString, listType) ?: emptyList()
        }
    }
}