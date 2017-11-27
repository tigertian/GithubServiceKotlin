package com.example.kotlin.livedataroomretrofitkotlindemo.database

import android.arch.persistence.room.TypeConverter
import com.example.kotlin.livedataroomretrofitkotlindemo.utils.DateUtils
import java.util.*


/**
 * Created by tianlu on 2017/11/27.
 */
class DateTypeConverter {

    @TypeConverter
    fun toDate(value: String?): Date? {
        return if (value == null) null else DateUtils.parse(value, DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_Z)
    }

    @TypeConverter
    fun toString(value: Date?): String? {
        return if (value == null) null else DateUtils.format(value, DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_Z)
    }
}