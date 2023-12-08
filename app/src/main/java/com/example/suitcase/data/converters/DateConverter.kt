package com.example.suitcase.data.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime

open class DateConverter {

    @TypeConverter
    fun formTimestamp(value:String?): LocalDateTime? {
        return value?.let {
            LocalDateTime.parse(it)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date:LocalDateTime?):String?{
        return date?.toString()
    }
}