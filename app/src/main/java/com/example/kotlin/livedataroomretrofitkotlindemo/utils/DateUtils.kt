package com.example.kotlin.livedataroomretrofitkotlindemo.utils

/**
 * Created by tianlu on 2017/11/27.
 */
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private val YYYY_MM_DD_HH_MM_SS_SDF = object : ThreadLocal<DateFormat>() {

        override fun initialValue(): DateFormat {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        }
    }

    private val E_SDF = object : ThreadLocal<DateFormat>() {

        override fun initialValue(): DateFormat {
            return SimpleDateFormat("E", Locale.getDefault())
        }
    }

    private val YYYY_MM_DD_HH_MM_SDF = object : ThreadLocal<DateFormat>() {

        override fun initialValue(): DateFormat {
            return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        }
    }

    private val E_MM_SPRIT_DD_SDF = object : ThreadLocal<DateFormat>() {

        override fun initialValue(): DateFormat {
            return SimpleDateFormat("E  MM/dd", Locale.getDefault())
        }
    }

    private val MM_SPRIT_DD_SDF = object : ThreadLocal<DateFormat>() {

        override fun initialValue(): DateFormat {
            return SimpleDateFormat("MM/dd", Locale.getDefault())
        }
    }

    private val HH_MM_SDF = object : ThreadLocal<DateFormat>() {

        override fun initialValue(): DateFormat {
            return SimpleDateFormat("HH:mm", Locale.getDefault())
        }
    }

    private val YYYY_MM_DD_T_HH_MM_SS_SSSZ_SDF = object : ThreadLocal<DateFormat>() {

        override fun initialValue(): DateFormat {
            return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        }
    }

    private val YYYY_MM_DD_T_HH_MM_SS_Z_SDF = object : ThreadLocal<DateFormat>() {

        override fun initialValue(): DateFormat {
            return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        }
    }

    enum class DateFormatType {
        YYYY_MM_DD_HH_MM_SS, E, YYYY_MM_DD_HH_MM, E_MM_SPRIT_DD, HH_MM, MM_SPRIT_DD, YYYY_MM_DD_T_HH_MM_SS_SSSZ, YYYY_MM_DD_T_HH_MM_SS_Z
    }

    /**
     * parse string to date
     * @param dateStr
     * @param type: date format type
     * @return
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun parse(dateStr: String, type: DateFormatType): Date? {
        var result: Date? = null
        when (type) {
            DateUtils.DateFormatType.YYYY_MM_DD_HH_MM_SS -> result = YYYY_MM_DD_HH_MM_SS_SDF.get().parse(dateStr)
            DateUtils.DateFormatType.E -> result = E_SDF.get().parse(dateStr)
            DateUtils.DateFormatType.YYYY_MM_DD_HH_MM -> result = YYYY_MM_DD_HH_MM_SDF.get().parse(dateStr)
            DateUtils.DateFormatType.E_MM_SPRIT_DD -> result = E_MM_SPRIT_DD_SDF.get().parse(dateStr)
            DateUtils.DateFormatType.HH_MM -> result = HH_MM_SDF.get().parse(dateStr)
            DateUtils.DateFormatType.MM_SPRIT_DD -> result = MM_SPRIT_DD_SDF.get().parse(dateStr)
            DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_SSSZ -> result = YYYY_MM_DD_T_HH_MM_SS_SSSZ_SDF.get().parse(dateStr)
            DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_Z -> result = YYYY_MM_DD_T_HH_MM_SS_Z_SDF.get().parse(dateStr)
        }
        return result
    }

    /**
     * parse date to string
     * @param date
     * @param type: string format type
     * @return
     */
    fun format(date: Date, type: DateFormatType): String? {
        var result: String? = null
        when (type) {
            DateUtils.DateFormatType.YYYY_MM_DD_HH_MM_SS -> result = YYYY_MM_DD_HH_MM_SS_SDF.get().format(date)
            DateUtils.DateFormatType.E -> result = E_SDF.get().format(date)
            DateUtils.DateFormatType.YYYY_MM_DD_HH_MM -> result = YYYY_MM_DD_HH_MM_SDF.get().format(date)
            DateUtils.DateFormatType.E_MM_SPRIT_DD -> result = E_MM_SPRIT_DD_SDF.get().format(date)
            DateUtils.DateFormatType.HH_MM -> result = HH_MM_SDF.get().format(date)
            DateUtils.DateFormatType.MM_SPRIT_DD -> result = MM_SPRIT_DD_SDF.get().format(date)
            DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_SSSZ -> result = YYYY_MM_DD_T_HH_MM_SS_SSSZ_SDF.get().format(date)
            DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_Z -> result = YYYY_MM_DD_T_HH_MM_SS_Z_SDF.get().format(date)
        }
        return result
    }
}