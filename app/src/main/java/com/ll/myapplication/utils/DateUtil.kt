package com.ll.myapplication.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {


    /** 设置时间格式化格式  */
    var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")


    /**
     * 获取今天日期
     * @param dateformat
     * @return
     */
    fun getToday(): String? {
        val calendar = Calendar.getInstance()
        return updateCalendar(calendar, 0)
    }

    /**
     * 获取昨天日期
     * @return
     */
    fun getYesterday(): String? {
        return updateCalendar(Calendar.getInstance(), -1)
    }

    /**
     * 获取明天日期
     * @return
     */
    fun getTomorrow(): String? {
        return updateCalendar(Calendar.getInstance(), 1)
    }

    /**
     * 获取本周一日期
     * @return
     */
    fun getMondayOfWeek(): String? {
        val calendar = Calendar.getInstance()
        return updateCalendar(calendar, getMondayAmount(calendar))
    }

    /**
     * 获取上周一日期
     * @return
     */
    fun getMondayOfLastWeek(): String? {
        val calendar = Calendar.getInstance()
        return updateCalendar(calendar, getMondayAmount(calendar) - 7)
    }

    /**
     * 获取下周一日期
     * @return
     */
    fun getMondayOfNextWeek(): String? {
        val calendar = Calendar.getInstance()
        return updateCalendar(calendar, getMondayAmount(calendar) + 7)
    }

    /**
     * 获取本周日日期
     * @return
     */
    fun getSundayOfWeek(): String? {
        val calendar = Calendar.getInstance()
        return updateCalendar(calendar, getSundayAmount(calendar))
    }

    /**
     * 获取上周日日期
     * @return
     */
    fun getSundayOfLastWeek(): String? {
        val calendar = Calendar.getInstance()
        return updateCalendar(calendar, getSundayAmount(calendar) - 7)
    }

    /**
     * 获取下周日日期
     * @return
     */
    fun getSundayOfNextWeek(): String? {
        val calendar = Calendar.getInstance()
        return updateCalendar(calendar, getSundayAmount(calendar) + 7)
    }

    /**
     * 获取当月第一天日期
     * @return
     */
    fun getFirstDayOfMonth(): String? {
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = 1 // 定位到1号
        return dateFormat.format(calendar.time)
    }

    /**
     * 获取上月第一天日期
     * @return
     */
    fun getFirstDayOfLastMonth(): String? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1) // 移动到上月
        calendar[Calendar.DAY_OF_MONTH] = 1 // 定位到1号
        return dateFormat.format(calendar.time)
    }

    /**
     * 获取下月第一天日期
     * @return
     */
    fun getFirstDayOfNextMonth(): String? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1) // 移动到下月
        calendar[Calendar.DAY_OF_MONTH] = 1 // 定位到1号
        return dateFormat.format(calendar.time)
    }

    /**
     * 获取当月最后一天日期
     * @return
     */
    fun getLastDayOfMonth(): String? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1) // 移动到下月
        calendar[Calendar.DAY_OF_MONTH] = 1 // 定位到1号
        return updateCalendar(calendar, -1) // 前移1天
    }

    /**
     * 获取上月最后一天日期
     * @return
     */
    fun getLastDayOfLastMonth(): String? {
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = 1 // 定位到1号
        return updateCalendar(calendar, -1) // 前移1天
    }

    /**
     * 获取下月最后一天日期
     * @return
     */
    fun getLastDayOfNextMonth(): String? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 2) // 移动2个月
        calendar[Calendar.DAY_OF_MONTH] = 1 // 定位到1号
        return updateCalendar(calendar, -1) // 前移1天
    }

    /**
     * 获取本周一的偏移量
     * @return
     */
    private fun getMondayAmount(calendar: Calendar): Int {
        val plus = calendar[Calendar.DAY_OF_WEEK] - 1
        return if (plus == 1) 0 else 1 - plus
    }

    /**
     * 获取本周日的偏移量
     * @return
     */
    private fun getSundayAmount(calendar: Calendar): Int {
        return getMondayAmount(calendar) + 6
    }

    /**
     * 按天更新日历
     * @param amount
     * @return
     */
    private fun updateCalendar(calendar: Calendar, amount: Int): String? {
        calendar.add(Calendar.DATE, amount)
        return dateFormat.format(calendar.time)
    }


    const val PATTERN_LONG_DAY = "yyyy-MM-dd"
    const val PATTERN_SHORT_DAY = "yyyyMMdd"

    const val PATTERN_LONG_MONTH = "yyyy-MM"
    const val PATTERN_SHORT_MONTH = "yyyyMM"

    const val PATTERN_LONG_SECOND = "yyyy-MM-dd HH:mm:ss"

    const val PATTERN_LONG_MILLISECOND = "yyyy-MM-dd HH:mm:ss:SSS"

    /**
     * 日期格式转化
     *
     * @param pattern
     * @param date
     * @return
     */
    fun parseDate(pattern: String?, date: String?): Date? {
        val sdf = SimpleDateFormat(pattern)
        var d: Date? = null
        try {
            d = sdf.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return d
    }

    /**
     * 日期运算
     *
     * @param date
     * @param calendarType
     * @param account
     * @return
     */
    fun mathDate(date: Date?, calendarType: Int, account: Int): Date? {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(calendarType, account)
        return calendar.time
    }

    /**
     * 获取加减年份后的日期 String
     *
     * @param pattern
     * @param date
     * @param year
     * @return
     */
    fun dateAddYears(
        pattern: String?,
        date: String?,
        year: Int
    ): String? {
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(mathDate(parseDate(pattern, date), Calendar.YEAR, year))
    }

    /**
     * 获取加减年份后的日期 Date
     *
     * @param date
     * @param year
     * @return
     */
    fun dateAddYears(date: Date?, year: Int): Date? {
        return mathDate(date, Calendar.YEAR, year)
    }

    /**
     * 获取加减月份后的日期 String
     *
     * @param pattern
     * @param date
     * @param month
     * @return
     */
    fun dateAddMonths(
        pattern: String?,
        date: String?,
        month: Int
    ): String? {
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(mathDate(parseDate(pattern, date), Calendar.MONTH, month))
    }

    /**
     * 获取加减月份后的日期 Date
     *
     * @param date
     * @param month
     * @return
     */
    fun dateAddMonths(date: Date?, month: Int): Date? {
        return mathDate(date, Calendar.MONTH, month)
    }

    /**
     * 获取加减天数后的日期 String
     *
     * @param pattern
     * @param date
     * @param day
     * @return
     */
    fun dateAddDays(pattern: String?, date: String?, day: Int): String? {
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(mathDate(parseDate(pattern, date), Calendar.DAY_OF_YEAR, day))
    }

    /**
     * 获取加减天数后的日期 Date
     *
     * @param date
     * @param day
     * @return
     */
    fun dateAddDays(date: Date?, day: Int): Date? {
        return mathDate(date, Calendar.DAY_OF_YEAR, day)
    }

    /**
     * 获取加减分钟后的日期 String
     *
     * @param pattern
     * @param date
     * @param minute
     * @return
     */
    fun dateAddMinutes(
        pattern: String?,
        date: String?,
        minute: Int
    ): String? {
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(mathDate(parseDate(pattern, date), Calendar.MINUTE, minute))
    }

    /**
     * 获取加减分钟后的日期 Date
     *
     * @param date
     * @param minute
     * @return
     */
    fun dateAddMinutes(date: Date?, minute: Int): Date? {
        return mathDate(date, Calendar.MINUTE, minute)
    }


    /**
     * 获取新年的第一天
     */
    fun getFirstDayOfNextYear(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, 1)  // 移动一年
        calendar[Calendar.DAY_OF_YEAR] = 1 //定位到1月1日
        return calendar.timeInMillis
    }


    fun getFormatDay(time: Long): String {
        val date = Date(time)
        return dateFormat.format(date)
    }

    //计算两个时间戳间隔多少天
    fun equation(startTime: Long, endTime: Long, repeat: Int = RepeatEnum.DAY.type): Int {
        var startTime = startTime
        var endTime = endTime

        when (repeat) {
            RepeatEnum.YEAR.type -> {
                while (startTime > endTime) {
                    val endDate = Calendar.getInstance()
                    endDate.timeInMillis = endTime
                    endDate.add(Calendar.YEAR, 1)
                    endTime = endDate.timeInMillis
                }
            }

            RepeatEnum.MONTH.type -> {
                while (startTime > endTime) {
                    val endDate = Calendar.getInstance()
                    endDate.timeInMillis = endTime
                    endDate.add(Calendar.MONTH, 1)
                    endTime = endDate.timeInMillis
                }
            }

            RepeatEnum.Week.type -> {
                while (startTime > endTime) {
                    val endDate = Calendar.getInstance()
                    endDate.timeInMillis = endTime
                    endDate.add(Calendar.DAY_OF_MONTH, 7)
                    endTime = endDate.timeInMillis
                }
            }
        }


        startTime = dateToStamp(stampToDate(startTime))
        endTime = dateToStamp(stampToDate(endTime))
        return ((endTime - startTime) / (1000 * 3600 * 24)).toInt()
    }

    /*
     * 将时间戳转换为时间
     */
    fun stampToDate(l: Long): String? {
        val res: String
        val simpleDateFormat = dateFormat
        val date = Date(l)
        res = simpleDateFormat.format(date)
        return res
    }

    /*
     * 将时间转换为时间戳
     */
    fun dateToStamp(s: String?): Long {
        val simpleDateFormat = dateFormat
        var date: Date? = null
        return try {
            date = simpleDateFormat.parse(s)
            date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            -1
        }
    }

    enum class RepeatEnum(var title: String, var type: Int) {
        NO("不重复", 0),
        DAY("日重复", 1),
        Week("周重复", 2),
        MONTH("月重复", 3),
        YEAR("年重复", 4)
    }
}