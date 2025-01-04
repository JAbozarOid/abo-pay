package com.jabozaroid.abopay.core.common.util.dateconverter

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Objects
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Created on 17,November,2024
 */

class PersianDate {
    private var timeInMilliSecond: Long
    private var shYear = 0
    private var shMonth = 0
    private var shDay = 0
    private var grgYear = 0
    private var grgMonth = 0
    private var grgDay = 0
    private var hour = 0
    private var minute = 0
    private var second = 0
    private var locale: Locale = Locale.getDefault()

    private var dialect = Dialect.IRANIAN

    enum class Dialect {
        FINGLISH,
        AFGHAN,
        IRANIAN,
        KURDISH,
        PASHTO
    }

    constructor() {
        this.timeInMilliSecond = Date().time
        this.init()
    }

    constructor(timeInMilliSecond: Long) {
        this.timeInMilliSecond = timeInMilliSecond
        this.init()
    }

    constructor(date: Date) {
        this.timeInMilliSecond = date.time
        this.init()
    }

    override fun toString(): String {
        return PersianDateFormat.format(this, null)
    }

    private val dayNames = arrayOf(
        "شنبه", "یک‌شنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه",
        "جمعه"
    )
    private val dayFinglishNames = arrayOf(
        "Shanbe", "Yekshanbe", "Doshanbe", "Seshanbe",
        "Chaharshanbe", "Panjshanbe",
        "Jom'e"
    )
    private val dayEnglishNames = arrayOf(
        "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday",
        "Friday"
    )
    private val monthNames = arrayOf(
        "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    )
    private val FinglishMonthNames = arrayOf(
        "Farvardin", "Ordibehesht", "Khordad", "Tir",
        "Mordad", "Shahrivar", "Mehr",
        "Aban", "Azar", "Day", "Bahman", "Esfand"
    )
    private val AfghanMonthNames = arrayOf(
        "حمل", "ثور", "جوزا", "سرطان", "اسد", "سنبله", "میزان",
        "عقرب", "قوس", "جدی", "دلو", "حوت"
    )
    private val KurdishMonthNames = arrayOf(
        "جیژنان", "گولان", "زه ردان", "په رپه ر", "گه لاویژ",
        "نوخشان", "به ران", "خه زان", "ساران", "بفران", "به ندان", "رمشان"
    )
    private val PashtoMonthNames = arrayOf(
        "وری", "غويی", "غبرګولی", "چنګاښ", "زمری", "وږی",
        "تله", "لړم", "ليندۍ", "مرغومی", "سلواغه", "كب"
    )
    private val GrgMonthNames = arrayOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )


    fun getLocale(): Locale {
        return locale
    }

    fun setLocal(locale: Locale): PersianDate {
        this.locale = locale
        return this
    }

    fun getDialect(): Dialect {
        return dialect
    }

    fun setDialect(dialect: Dialect): PersianDate {
        this.dialect = dialect
        return this
    }

    fun getShYear(): Int {
        return shYear
    }

    /**
     * Set Shamsi Year
     *
     * @param shYear Shamsi Year as a Integer value grate than 0
     * @return PersianDate
     * @throws IllegalArgumentException Return Exception if Year is less than 1
     */
    @Throws(IllegalArgumentException::class)
    fun setShYear(shYear: Int): PersianDate {
        if (shYear < 1) {
            throw IllegalArgumentException("PersianDate Error: ##=> Year must be greater than 0")
        }
        this.shYear = shYear
        this.changeTime(true)
        return this
    }

    fun getShMonth(): Int {
        return shMonth
    }

    /**
     * Set Shamsi Month
     *
     * @param shMonth Shamsi Month as a Integer value between 1 and 12
     * @return PersianDate
     * @throws IllegalArgumentException Return Exception if Month is less than 1 or greater than 12
     */
    @Throws(IllegalArgumentException::class)
    fun setShMonth(shMonth: Int): PersianDate {
        if (shMonth < 1 || shMonth > 12) {
            throw IllegalArgumentException("PersianDate Error: ##=> Month must be between 1 and 12")
        }
        this.shMonth = shMonth
        this.changeTime(true)
        return this
    }

    fun getShDay(): Int {
        return shDay
    }

    /**
     * Set Shamsi Day
     *
     * @param shDay Shamsi Day as a Integer value between 1 and 29~31
     * @return PersianDate object
     * @throws IllegalArgumentException Return Exception if Day is less than 1 or greater than 29~31
     * (Depend on Month Length)
     */
    @Throws(IllegalArgumentException::class)
    fun setShDay(shDay: Int): PersianDate {
        if (shDay < 1 || shDay > 31) {
            throw IllegalArgumentException("PersianDate Error: ##=> Day must be between 1 and 29~31")
        }
        if (shDay > this.getMonthLength()) {
            throw IllegalArgumentException(
                "PersianDate Error: ##=> Day in the " + this.getMonthName() + " must be between 1 and "
                        + this.getMonthLength()
            )
        }
        this.shDay = shDay
        this.changeTime(true)
        return this
    }

    fun getGrgYear(): Int {
        return grgYear
    }

    /**
     * Set Gregorian Year
     *
     * @param grgYear Gregorian Year as a Integer value grate than 0
     * @return PersianDate
     * @throws IllegalArgumentException Return Exception if Year is less than 1
     */
    @Throws(IllegalArgumentException::class)
    fun setGrgYear(grgYear: Int): PersianDate {
        if (grgYear < 1) {
            throw IllegalArgumentException("PersianDate Error: ##=> Year must be greater than 0")
        }
        this.grgYear = grgYear
        changeTime(false)
        return this
    }

    fun getGrgMonth(): Int {
        return grgMonth
    }

    /**
     * set Gregorian Month
     *
     * @param grgMonth Gregorian Month as a Integer value between 1 and 12
     * @return PersianDate
     * @throws IllegalArgumentException Return Exception if Month is less than 1 or greater than 12
     */
    @Throws(IllegalArgumentException::class)
    fun setGrgMonth(grgMonth: Int): PersianDate {
        if (grgMonth < 1 || grgMonth > 12) {
            throw IllegalArgumentException("PersianDate Error: ##=> Month must be between 1 and 12")
        }
        this.grgMonth = grgMonth
        changeTime(false)
        return this
    }

    fun getGrgDay(): Int {
        return grgDay
    }

    /**
     * Set Gregorian Day
     *
     * @param grgDay Gregorian Day as a Integer value between 1 and 28~31
     * @return PersianDate object
     * @throws IllegalArgumentException Return Exception if Day is less than 1 or greater than 28~31
     * (Depend on Month Length)
     */
    @Throws(IllegalArgumentException::class)
    fun setGrgDay(grgDay: Int): PersianDate {
        if (grgDay < 1 || grgDay > 31) {
            throw IllegalArgumentException("PersianDate Error: ##=> Day must be between 1 and 28~31")
        }
        if (grgDay > this.getGrgMonthLength()) {
            throw IllegalArgumentException(
                ("PersianDate Error: ##=> Day in the " + this.getGrgMonthName() + " must be between 1 and "
                        + this.getGrgMonthLength())
            )
        }
        this.grgDay = grgDay
        changeTime(false)
        return this
    }

    fun getHour(): Int {
        return hour
    }

    @JvmOverloads
    fun get12FormatHour(hour: Int = this.hour): Int {
        return if (hour <= 12) hour else (hour - 12)
    }

    /**
     * Set Minute
     *
     * @param hour Hour as a integer number between 0 and 23
     * @return PersianDate object
     * @throws IllegalArgumentException Returns an IllegalArgumentException if hour is not between 0
     * and 23
     */
    @Throws(IllegalArgumentException::class)
    fun setHour(hour: Int): PersianDate {
        if (hour < 0 || hour > 23) {
            throw IllegalArgumentException("PersianDate Error: ##=> Hour must be between 0 and 23")
        }
        this.hour = hour
        changeTime(false)
        return this
    }

    fun getMinute(): Int {
        return minute
    }

    /**
     * Set Minute
     *
     * @param minute Minute as a integer number between 0 and 59
     * @return PersianDate object
     * @throws IllegalArgumentException Returns an IllegalArgumentException if second is not between 0
     * and 59
     */
    @Throws(IllegalArgumentException::class)
    fun setMinute(minute: Int): PersianDate {
        if (minute < 0 || minute > 59) {
            throw IllegalArgumentException("PersianDate Error: ##=> Minute must be between 0 and 59")
        }
        this.minute = minute
        changeTime(false)
        return this
    }

    fun getSecond(): Int {
        return second
    }

    /**
     * Set Second
     *
     * @param second Second as a integer number between 0 and 59
     * @return PersianDate object
     * @throws IllegalArgumentException Returns an IllegalArgumentException if second is not between 0
     * and 59
     */
    @Throws(IllegalArgumentException::class)
    fun setSecond(second: Int): PersianDate {
        if (second < 0 || second > 59) {
            throw IllegalArgumentException("PersianDate Error: ##=> Second must be between 0 and 59")
        }
        this.second = second
        changeTime(false)
        return this
    }

    /**
     * init with Grg data
     *
     * @param year Year in Grg
     * @param month Month in Grg
     * @param day day in Grg
     * @param hour hour
     * @param minute min
     * @param second second
     * @return PersianDate
     */
    /**
     * init without time
     *
     * @param year Year in Grg
     * @param month Month in Grg
     * @param day Day in Grg
     * @return persianDate
     */
    @JvmOverloads
    @Throws(IllegalArgumentException::class)
    fun initGrgDate(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0
    ): PersianDate {
        //check input parameter
        if (year < 1) {
            throw IllegalArgumentException("PersianDate Error: ##=> Year must be greater than 0")
        }
        if (month < 1 || month > 12) {
            throw IllegalArgumentException("PersianDate Error: ##=> Month must be between 1 and 12")
        }
        if (day < 1 || day > 31) {
            throw IllegalArgumentException("PersianDate Error: ##=> Day must be between 1 and 28~31")
        }
        if (hour < 0 || hour > 23) {
            throw IllegalArgumentException("PersianDate Error: ##=> Hour must be between 0 and 23")
        }
        if (minute < 0 || minute > 59) {
            throw IllegalArgumentException("PersianDate Error: ##=> Minute must be between 0 and 59")
        }
        if (second < 0 || second > 59) {
            throw IllegalArgumentException("PersianDate Error: ##=> Second must be between 0 and 59")
        }
        if (day > this.getGrgMonthLength(year, month)) {
            throw IllegalArgumentException(
                ("PersianDate Error: ##=> Day in the " + this.getGrgMonthName(month)
                        + " must be between 1 and "
                        + this.getGrgMonthLength(year, month))
            )
        }
        this.grgYear = year
        this.grgMonth = month
        this.grgDay = day
        this.hour = hour
        this.minute = minute
        this.second = second
        changeTime(false)
        return this
    }

    /**
     * initialize date from Jallali date
     *
     * @param year Year in jallali date
     * @param month Month in Jallali date
     * @param day day in Jallali date
     * @param hour Hour
     * @param minute Minute
     * @param second Second
     * @return PersianDate
     */
    /**
     * initialize date from Jallali date
     *
     * @param year Year in Jallali date
     * @param month Month in Jallali date
     * @param day day in Jallali date
     * @return PersianDate
     */
    @JvmOverloads
    @Throws(IllegalArgumentException::class)
    fun initJalaliDate(
        year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0,
        second: Int = 0
    ): PersianDate {
        //validate input parameters
        if (year < 1) {
            throw IllegalArgumentException("PersianDate Error: ##=> Year must be greater than 0")
        }
        if (month < 1 || month > 12) {
            throw IllegalArgumentException("PersianDate Error: ##=> Month must be between 1 and 12")
        }
        if (day < 1 || day > 31) {
            throw IllegalArgumentException("PersianDate Error: ##=> Day must be between 1 and 28~31")
        }
        if (hour < 0 || hour > 23) {
            throw IllegalArgumentException("PersianDate Error: ##=> Hour must be between 0 and 23")
        }
        if (minute < 0 || minute > 59) {
            throw IllegalArgumentException("PersianDate Error: ##=> Minute must be between 0 and 59")
        }
        if (second < 0 || second > 59) {
            throw IllegalArgumentException("PersianDate Error: ##=> Second must be between 0 and 59")
        }
        if (day > this.getMonthLength(year, month)) {
            throw IllegalArgumentException(
                ("PersianDate Error: ##=> Day in the " + this.monthName(month) + " must be between 1 and "
                        + this.getMonthLength(year, month))
            )
        }
        this.shYear = year
        this.shMonth = month
        this.shDay = day
        this.hour = hour
        this.minute = minute
        this.second = second
        this.changeTime(true)
        return this
    }

    /**
     * return time in long value
     *
     * @return Value of time in mile
     */
    fun getTime(): Long {
        return this.timeInMilliSecond
    }

    /**
     * Check Grg year is leap
     *
     * @param Year Year
     * @return boolean
     */
    @JvmOverloads
    fun grgIsLeap(Year: Int = this.grgYear): Boolean {
        if (Year % 4 == 0) {
            if (Year % 100 == 0) {
                return Year % 400 == 0
            }
            return true
        }
        return false
    }

    /**
     * Check year in Leap
     *
     * @return true or false
     */
    fun isLeap(): Boolean {
        return this.isLeap(this.shYear)
    }

    /**
     * Check custom year is leap
     *
     * @param year int year
     * @return true or false
     */
    fun isLeap(year: Int): Boolean {
        val referenceYear = 1375.0
        var startYear = 1375.0
        val yearRes = year - referenceYear
        //first of all make sure year is not multiplier of 1375
        if (yearRes == 0.0 || yearRes % 33 == 0.0) {
            return true //year is 1375 or 1375+-(i)*33
        }

        if (yearRes > 0) {
            if (yearRes > 33) {
                val numb = yearRes / 33
                startYear = referenceYear + (floor(numb) * 33)
            }
        } else {
            if (yearRes > -33) {
                startYear = referenceYear - 33
            } else {
                val numb = abs(yearRes / 33)
                startYear = referenceYear - (ceil(numb) * 33)
            }
        }
        val leapYears = doubleArrayOf(
            startYear, startYear + 4, startYear + 8, startYear + 12, startYear + 16,
            startYear + 20,
            startYear + 24, startYear + 28, startYear + 33
        )
        return (Arrays.binarySearch(leapYears, year.toDouble())) >= 0
    }

    /**
     * Author: JDF.SCR.IR =>> Download Full Version :  http://jdf.scr.ir/jdf License: GNU/LGPL _ Open
     * Source & Free :: Version: 2.80 : [2020=1399]
     */
    fun gregorian_to_jalali(gy: Int, gm: Int, gd: Int): IntArray {
        val out = intArrayOf(
            if ((gm > 2)) (gy + 1) else gy,
            0,
            0
        )
        run {
            val g_d_m: IntArray =
                intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
            out[2] =
                (355666 + (365 * gy) + ((out.get(
                    0
                ) + 3) / 4) - ((out.get(0) + 99) / 100)
                        ) + ((out.get(0) + 399) / 400) + gd + g_d_m.get(
                    gm - 1
                )
        }
        out[0] = -1595 + (33 * (out[2] / 12053))
        out[2] %= 12053
        out[0] += 4 * (out[2] / 1461)
        out[2] %= 1461
        if (out[2] > 365) {
            out[0] += ((out[2] - 1) / 365)
            out[2] = (out[2] - 1) % 365
        }
        if (out[2] < 186) {
            out[1] = 1 + (out[2] / 31)
            out[2] = 1 + (out[2] % 31)
        } else {
            out[1] = 7 + ((out[2] - 186) / 30)
            out[2] = 1 + ((out[2] - 186) % 30)
        }
        return out
    }

    /**
     * Author: JDF.SCR.IR =>> Download Full Version :  http://jdf.scr.ir/jdf License: GNU/LGPL _ Open
     * Source & Free :: Version: 2.80 : [2020=1399]
     */
    fun jalali_to_gregorian(jy: Int, jm: Int, jd: Int): IntArray {
        var jy = jy
        jy += 1595
        val out = intArrayOf(
            0,
            0,
            -355668 + (365 * jy) + ((jy / 33) * 8) + (((jy % 33) + 3) / 4) + jd + (if ((jm < 7)) (jm - 1) * 31 else ((jm - 7) * 30) + 186)
        )
        out[0] = 400 * (out[2] / 146097)
        out[2] %= 146097
        if (out[2] > 36524) {
            out[0] += 100 * (--out[2] / 36524)
            out[2] %= 36524
            if (out[2] >= 365) {
                out[2]++
            }
        }
        out[0] += 4 * (out[2] / 1461)
        out[2] %= 1461
        if (out[2] > 365) {
            out[0] += ((out[2] - 1) / 365)
            out[2] = (out[2] - 1) % 365
        }
        val sal_a = intArrayOf(
            0, 31, if (((out[0] % 4 == 0 && out[0] % 100 != 0) || (out[0] % 400 == 0))) 29 else 28,
            31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        )
        out[2]++
        while (out[1] < 13 && out[2] > sal_a[out[1]]) {
            out[2] -= sal_a[out[1]]
            out[1]++
        }
        return out
    }

    /**
     * Get day of week from PersianDate object
     *
     * @param date persianDate
     * @return int
     */
    /**
     * calc day of week
     *
     * @return int
     */
    @JvmOverloads
    fun dayOfWeek(date: PersianDate = this): Int {
        return this.dayOfWeek(date.toDate())
    }

    /**
     * Get day of week from Date object
     *
     * @param date Date
     * @return int
     */
    fun dayOfWeek(date: Date?): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        if (cal[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY) {
            return 0
        }
        return (cal[Calendar.DAY_OF_WEEK])
    }

    fun getWeek(date: PersianDate): ArrayList<PersianDate> {
        val currentWeek = ArrayList<PersianDate>()
        for (i in 0 until date.dayOfWeek()) {
            val dateTmp = PersianDate(date.timeInMilliSecond)
            currentWeek.add(dateTmp.subDays((date.dayOfWeek() - i)))
        }
        currentWeek.add(date)
        val threshold = (7 - currentWeek.size)
        for (j in 1..threshold) {
            val dateTmp = PersianDate(date.timeInMilliSecond)
            currentWeek.add(dateTmp.addDays(j))
        }
        return currentWeek
    }

    fun getWeek(): Array<PersianDate> {
        return getWeek(this).toTypedArray()
    }

    /**
     * Return list of month
     *
     * @param dialect dialect
     * @return month names
     */
    /**
     * Return list of month
     *
     * @return month names
     */
    @JvmOverloads
    fun monthList(dialect: Dialect? = Dialect.IRANIAN): Array<String> {
        when (dialect) {
            Dialect.FINGLISH -> return this.FinglishMonthNames
            Dialect.AFGHAN -> return this.AfghanMonthNames
            Dialect.KURDISH -> return this.KurdishMonthNames
            Dialect.PASHTO -> return this.PashtoMonthNames
            else -> return this.monthNames
        }
    }

    /**
     * Return month name
     *
     * @param month Month
     */
    fun monthName(month: Int, dialect: Dialect?): String {
        when (dialect) {
            Dialect.FINGLISH -> return FinglishMonthNames[month - 1]
            Dialect.AFGHAN -> return AfghanMonthNames[month - 1]
            Dialect.KURDISH -> return KurdishMonthNames[month - 1]
            Dialect.PASHTO -> return PashtoMonthNames[month - 1]
            else -> return monthNames[month - 1]
        }
    }

    /**
     * return month name
     *
     * @return string
     */
    /**
     * Get current month name in Persian
     */
    @JvmOverloads
    fun monthName(dialect: Dialect? = Dialect.IRANIAN): String {
        return monthName(this.getShMonth(), dialect)
    }

    fun monthName(shMonth: Int): String {
        return monthName(shMonth, this.getDialect())
    }

    /**
     * Get month name in Finglish
     */
    /**
     * Get current date Finglish month name
     */
    @JvmOverloads
    fun FinglishMonthName(month: Int = this.getShMonth()): String {
        return FinglishMonthNames[month - 1]
    }

    /**
     * Get month name in Afghan
     */
    /**
     * Get current date Afghan month name
     */
    @JvmOverloads
    fun AfghanMonthName(month: Int = this.getShMonth()): String {
        return AfghanMonthNames[month - 1]
    }

    /**
     * Get month name in Kurdish
     */
    /**
     * Get current date Kurdish month name
     */
    @JvmOverloads
    fun KurdishMonthName(month: Int = this.getShMonth()): String {
        return KurdishMonthNames[month - 1]
    }

    /**
     * Get month name in Pashto
     */
    /**
     * Get current date Pashto month name
     */
    @JvmOverloads
    fun PashtoMonthName(month: Int = this.getShMonth()): String {
        return PashtoMonthNames[month - 1]
    }

    /**
     * Get Day Name
     */
    /**
     * get day name
     */
    @JvmOverloads
    fun dayName(date: PersianDate = this): String {
        return dayNames[this.dayOfWeek(date)]
    }

    /**
     * Get Finglish Day Name
     */
    /**
     * Get Finglish Day Name
     */
    @JvmOverloads
    fun dayFinglishName(date: PersianDate = this): String {
        return dayFinglishNames[this.dayOfWeek(date)]
    }

    /**
     * Get English Day Name
     */
    /**
     * Get English Day Name
     */
    @JvmOverloads
    fun dayEnglishName(date: PersianDate = this): String {
        return dayEnglishNames[this.dayOfWeek(date)]
    }

    /**
     * Number days of month
     *
     * @return return days
     */
    fun getMonthDays(): Int {
        return this.getMonthDays(this.getShYear(), this.getShMonth())
    }

    /**
     * calc count of day in month
     */
    fun getMonthDays(Year: Int, month: Int): Int {
        if (month == 12 && !this.isLeap(Year)) {
            return 29
        }
        if (month <= 6) {
            return 31
        } else {
            return 30
        }
    }

    /**
     * calculate day in year
     */
    fun getDayInYear(): Int {
        return this.getDayInYear(this.getShMonth(), getShDay())
    }

    /**
     * Calc day of the year
     *
     * @param month Month
     * @param day Day
     */
    fun getDayInYear(month: Int, day: Int): Int {
        var day = day
        for (i in 1 until month) {
            if (i <= 6) {
                day += 31
            } else {
                day += 30
            }
        }
        return day
    }

    /**
     * Subtract date
     *
     * @param SubYear Number of subtraction years
     * @param SubMonth Number of subtraction months
     * @param SubDay Number of subtraction year days
     * @param SubHour Number of subtraction year Hours
     * @param SubMinute Number of subtraction year minutes
     * @param SubSecond Number of subtraction year seconds
     * @return this
     */
    /**
     * Subtract date
     *
     * @param SubYear Number of subtraction years
     * @param SubMonth Number of subtraction months
     * @param SubDay Number of subtraction year days
     * @return this
     */
    @JvmOverloads
    fun subDate(
        SubYear: Long, SubMonth: Long, SubDay: Long, SubHour: Long = 0, SubMinute: Long = 0,
        SubSecond: Long = 0
    ): PersianDate {
        val cal = Calendar.getInstance()
        //add day to cal
        cal.time = toDate()
        if (SubYear >= 1) {
            cal.add(Calendar.YEAR, -SubYear.toInt())
        }
        if (SubMonth >= 1) {
            cal.add(Calendar.MONTH, -SubMonth.toInt())
        }
        if (SubDay >= 1) {
            cal.add(Calendar.DAY_OF_MONTH, -SubDay.toInt())
        }
        if (SubHour >= 1) {
            cal.add(Calendar.HOUR_OF_DAY, -SubHour.toInt())
        }
        if (SubMinute >= 1) {
            cal.add(Calendar.MINUTE, -SubMinute.toInt())
        }
        if (SubSecond >= 1) {
            cal.add(Calendar.SECOND, -SubSecond.toInt())
        }
        this.timeInMilliSecond = cal.timeInMillis
        this.init()
        return this
    }

    /**
     * Subtract more than one year
     *
     * @param years: Number of subtraction years
     * @return this
     */
    fun subYears(years: Int): PersianDate {
        return this.subDate(years.toLong(), 0, 0)
    }

    /**
     * Subtract a year
     *
     * @return this
     */
    fun subYear(): PersianDate {
        return this.subYears(1)
    }

    /**
     * Subtract months
     *
     * @param months: Number of subtraction months
     * @return this
     */
    fun subMonths(months: Int): PersianDate {
        return this.subDate(0, months.toLong(), 0)
    }

    /**
     * Subtract a month
     *
     * @return this
     */
    fun subMonth(): PersianDate {
        return this.subMonths(1)
    }

    /**
     * Subtract days
     *
     * @param days: Number of subtraction days
     * @return this
     */
    fun subDays(days: Int): PersianDate {
        return this.subDate(0, 0, days.toLong())
    }

    /**
     * Subtract a day
     *
     * @return this
     */
    fun subDay(): PersianDate {
        return this.subDays(1)
    }

    /**
     * Subtract hours
     *
     * @param hours: Number of subtraction hours
     * @return this
     */
    fun subHours(hours: Int): PersianDate {
        return this.subDate(0, 0, 0, hours.toLong(), 0, 0)
    }

    /**
     * Subtract an hour
     *
     * @return this
     */
    fun subHour(): PersianDate {
        return this.subHours(1)
    }

    /**
     * Subtract minutes
     *
     * @param minutes: Number of subtraction minutes
     * @return this
     */
    fun subMinutes(minutes: Int): PersianDate {
        return this.subDate(0, 0, 0, 0, minutes.toLong(), 0)
    }

    /**
     * Subtract a minute
     *
     * @return this
     */
    fun subMinute(): PersianDate {
        return this.subMinutes(1)
    }

    /**
     * Subtract Seconds
     *
     * @param seconds: Number of subtraction seconds
     * @return this
     */
    fun subSeconds(seconds: Int): PersianDate {
        return this.subDate(0, 0, 0, 0, 0, seconds.toLong())
    }

    /**
     * Subtract a Second
     *
     * @return this
     */
    fun subSecond(): PersianDate {
        return this.subSeconds(1)
    }

    fun subWeeks(weeks: Int): PersianDate {
        return this.subDays(7 * weeks)
    }

    fun subWeek(): PersianDate {
        return this.subWeeks(1)
    }

    /**
     * add date
     *
     * @param AddYear Number of Year you want add
     * @param AddMonth Number of month you want add
     * @param AddDay Number of day you want add
     * @param AddHour Number of hour you want add
     * @param AddMinute Number of minute you want add
     * @param AddSecond Number of second you want add
     * @return new date
     */
    @Throws(IllegalArgumentException::class)
    fun addDate(
        AddYear: Long, AddMonth: Long, AddDay: Long, AddHour: Long, AddMinute: Long,
        AddSecond: Long
    ): PersianDate {
        val cal = Calendar.getInstance()
        //add day to cal
        cal.time = toDate()
        if (AddYear >= 1) {
            cal.add(Calendar.YEAR, AddYear.toInt())
        }
        if (AddMonth >= 1) {
            cal.add(Calendar.MONTH, AddMonth.toInt())
        }
        if (AddDay >= 1) {
            cal.add(Calendar.DAY_OF_MONTH, AddDay.toInt())
        }
        if (AddHour >= 1) {
            cal.add(Calendar.HOUR_OF_DAY, AddHour.toInt())
        }
        if (AddMinute >= 1) {
            cal.add(Calendar.MINUTE, AddMinute.toInt())
        }
        if (AddSecond >= 1) {
            cal.add(Calendar.SECOND, AddSecond.toInt())
        }

        this.timeInMilliSecond = cal.timeInMillis
        this.init()
        return this
    }

    /**
     * add to date
     *
     * @param year Number of Year you want add
     * @param month Number of month you want add
     * @param day Number of day you want add
     */
    fun addDate(year: Long, month: Long, day: Long): PersianDate {
        return this.addDate(year, month, day, 0, 0, 0)
    }

    fun addYear(): PersianDate {
        return this.addYears(1)
    }

    fun addYears(year: Int): PersianDate {
        return this.addDate(year.toLong(), 0, 0)
    }

    fun addMonth(): PersianDate {
        return this.addMonths(1)
    }

    fun addMonths(month: Int): PersianDate {
        return this.addDate(0, month.toLong(), 0)
    }

    fun addWeek(): PersianDate {
        return this.addWeeks(1)
    }

    fun addWeeks(week: Int): PersianDate {
        return this.addDays(7 * week)
    }

    fun addDay(): PersianDate {
        return this.addDays(1)
    }

    fun addDays(day: Int): PersianDate {
        return this.addDate(0, 0, day.toLong())
    }

    fun addHour(): PersianDate {
        return this.addHours(1)
    }

    fun addHours(hour: Int): PersianDate {
        return this.addDate(0, 0, 0, hour.toLong(), 0, 0)
    }

    fun addMinute(): PersianDate {
        return this.addMinutes(1)
    }

    fun addMinutes(minute: Int): PersianDate {
        return this.addDate(0, 0, 0, 0, minute.toLong(), 0)
    }

    fun addSecond(): PersianDate {
        return this.addSeconds(1)
    }

    fun addSeconds(second: Int): PersianDate {
        return this.addDate(0, 0, 0, 0, 0, second.toLong())
    }

    /**
     * Compare 2 date
     *
     * @param dateInput PersianDate type
     */
    fun after(dateInput: PersianDate): Boolean {
        return (this.timeInMilliSecond < dateInput.getTime())
    }

    /**
     * compare to data
     *
     * @param dateInput Input
     */
    fun before(dateInput: PersianDate): Boolean {
        return (!this.after(dateInput))
    }

    /**
     * Check date equals
     */
    fun equals(dateInput: PersianDate): Boolean {
        return ((this.timeInMilliSecond == dateInput.getTime()))
    }

    /**
     * compare two data
     *
     * @return 0 = equal,1=data1 > anotherDate,-1=data1 > anotherDate
     */
    fun compareTo(anotherDate: PersianDate): Int {
        return (timeInMilliSecond.compareTo(anotherDate.getTime()))
    }

    /**
     * Check if PersianDate is today or not
     *
     * @return boolean
     */
    fun isToday(): Boolean {
        return this.isToday(this)
    }

    /**
     * Check if given PersianDate is today or not
     *
     * @param date Date to check is today or not
     * @return boolean
     */
    fun isToday(date: PersianDate): Boolean {
        return date.getDayUntilToday() == 0L
    }

    /**
     * Return Day in different date
     */
    fun getDayUntilToday(): Long {
        return this.getDayUntilToday(PersianDate())
    }

    /**
     * Return different just day in compare 2 date
     *
     * @param date date for compare
     */
    fun getDayUntilToday(date: PersianDate): Long {
        val ret = this.untilToday(date)
        return ret[0]
    }

    /**
     * calculate different between 2 date
     *
     * @param date Date 1
     */
    /**
     * Calc different date until now
     */
    @JvmOverloads
    fun untilToday(date: PersianDate = PersianDate()): LongArray {
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        var different =
            abs((this.timeInMilliSecond - date.getTime()).toDouble()).toLong()

        val elapsedDays = different / daysInMilli
        different = different % daysInMilli

        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli
        val elapsedSeconds = different / secondsInMilli
        return longArrayOf(elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)
    }

    /**
     * convert PersianDate class to date
     */
    fun toDate(): Date {
        return Date(this.timeInMilliSecond)
    }


    /**
     * Get start of day
     */
    /**
     * Get Start of day
     */
    @JvmOverloads
    fun startOfDay(persianDate: PersianDate = this): PersianDate {
        persianDate.setHour(0).setMinute(0).setSecond(0)
        return persianDate
    }

    /**
     * Get end of day
     */
    /**
     * Get end of day
     */
    @JvmOverloads
    fun endOfDay(persianDate: PersianDate = this): PersianDate {
        persianDate.setHour(23).setMinute(59).setSecond(59)
        return persianDate
    }

    /**
     * Check midnight
     */
    fun isMidNight(persianDate: PersianDate): Boolean {
        return persianDate.isMidNight()
    }

    /**
     * Check is midNight
     */
    fun isMidNight(): Boolean {
        return (this.hour < 12)
    }

    /**
     * Get short name time of the day
     */
    fun getShortTimeOfTheDay(): String {
        return if ((this.isMidNight())) AM_SHORT_NAME else PM_SHORT_NAME
    }

    /**
     * Get short name time of the day
     */
    fun getShortTimeOfTheDay(persianDate: PersianDate): String {
        return if ((persianDate.isMidNight())) AM_SHORT_NAME else PM_SHORT_NAME
    }

    /**
     * Get time of the day
     */
    fun getTimeOfTheDay(): String {
        return if ((this.isMidNight())) AM_NAME else PM_NAME
    }

    /**
     * Get time of the day
     */
    fun getTimeOfTheDay(persianDate: PersianDate): String {
        return if ((persianDate.isMidNight())) AM_NAME else PM_NAME
    }

    fun getGrgMonthLength(grgYear: Int, grgMonth: Int): Int {
        val cal = Calendar.getInstance()
        //set year in cal object
        cal[Calendar.YEAR] = grgYear
        //set month in cal object
        cal[Calendar.MONTH] = grgMonth - 1
        return (cal.getActualMaximum(Calendar.DATE))
    }

    fun getGrgMonthLength(date: Date?): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return (cal.getActualMaximum(Calendar.DATE))
    }

    fun getGrgMonthLength(): Int {
        return this.getGrgMonthLength(this.toDate())
    }

    fun getGrgMonthName(grgMonth: Int): String {
        if (grgMonth < 1 || grgMonth > 12) {
            return ""
        }
        return GrgMonthNames[grgMonth - 1]
    }

    fun getGrgMonthName(): String {
        return this.getGrgMonthName(this.getGrgMonth())
    }

    /**
     * Get number of days in month
     *
     * @param year Jalali year
     * @param month Jalali month
     * @return number of days in month
     */
    fun getMonthLength(year: Int, month: Int): Int {
        if (month <= 6) {
            return 31
        } else if (month <= 11) {
            return 30
        } else {
            if (this.isLeap(year)) {
                return 30
            } else {
                return 29
            }
        }
    }

    /**
     * Get number of days in month
     *
     * @param persianDate persianDate object
     * @return number of days in month
     */
    fun getMonthLength(persianDate: PersianDate): Int {
        return this.getMonthLength(persianDate.getShYear(), persianDate.getShMonth())
    }

    /**
     * Get number of days in month
     *
     * @return number of days in month
     */
    fun getMonthLength(): Int {
        return this.getMonthLength(this)
    }

    fun getMonthName(): String {
        return this.getMonthName(this)
    }

    fun getMonthName(pDate: PersianDate, dialect: Dialect?): String {
        if (pDate.getShMonth() > pDate.monthList(dialect).size) {
            return ""
        }
        return pDate.monthList(dialect)[pDate.getShMonth() - 1]
    }

    fun getMonthName(dialect: Dialect?): String {
        if (this.getShMonth() > monthList(dialect).size) {
            return ""
        }
        return monthList(dialect)[getShMonth() - 1]
    }

    fun getMonthName(pDate: PersianDate): String {
        if (pDate.getShMonth() > pDate.monthList().size) {
            return ""
        }
        return pDate.monthList()[pDate.getShMonth() - 1]
    }

    //endregion
    /*----- Helper Function-----*/ //region Private functions
    /**
     * Helper function
     */
    private fun textNumberFilter(date: String): String {
        if (date.length < 2) {
            return "0$date"
        }
        return date
    }

    private fun init() {
        this.grgYear = SimpleDateFormat("yyyy", this.locale).format(this.timeInMilliSecond).toInt()
        this.grgMonth = SimpleDateFormat("MM", this.locale).format(this.timeInMilliSecond).toInt()
        this.grgDay = SimpleDateFormat("dd", this.locale).format(this.timeInMilliSecond).toInt()
        this.hour = SimpleDateFormat("HH", this.locale).format(this.timeInMilliSecond).toInt()
        this.minute = SimpleDateFormat("mm", this.locale).format(this.timeInMilliSecond).toInt()
        this.second = SimpleDateFormat("ss", this.locale).format(this.timeInMilliSecond).toInt()

        changeTime(false)
    }

    private fun changeTime(isJalaliChanged: Boolean) {
        if (isJalaliChanged) {
            this.TimeCalcFromJalali(
                this.shYear, this.shMonth, this.shDay, this.hour, this.minute,
                this.second
            )
        } else {
            this.TimeCalcFromGrg(
                this.grgYear, this.grgMonth, this.grgDay, this.hour, this.minute,
                this.second
            )
        }
    }

    private fun TimeCalcFromJalali(year: Int, month: Int, day: Int, hr: Int, min: Int, sec: Int) {
        val grgTimes =
            intArrayOf(0,  /*YEAR*/0,  /*MONTH*/0,  /*DAY*/0,  /*HOUR*/0,  /*MINUTE*/0 /*SECOND*/)
        val jalaliTimes = intArrayOf(
            year,  /*YEAR*/month,  /*MONTH*/day,  /*DAY*/hr,  /*HOUR*/min,  /*MINUTE*/sec /*SECOND*/
        )
        //convert timestamp to grg date
        val convertedTime = this.jalali_to_gregorian(year, month, day)
        grgTimes[0] = convertedTime[0]
        grgTimes[1] = convertedTime[1]
        grgTimes[2] = convertedTime[2]
        grgTimes[3] = hr
        grgTimes[4] = min
        grgTimes[5] = sec
        notify(grgTimes, jalaliTimes)
    }

    private fun TimeCalcFromGrg(year: Int, month: Int, day: Int, hr: Int, min: Int, sec: Int) {
        val grgTimes = intArrayOf(
            year,  /*YEAR*/month,  /*MONTH*/day,  /*DAY*/hr,  /*HOUR*/min,  /*MINUTE*/sec /*SECOND*/
        )
        val jalaliTimes =
            intArrayOf(0,  /*YEAR*/0,  /*MONTH*/0,  /*DAY*/0,  /*HOUR*/0,  /*MINUTE*/0 /*SECOND*/)
        val convertedTime = this.gregorian_to_jalali(year, month, day)
        jalaliTimes[0] = convertedTime[0]
        jalaliTimes[1] = convertedTime[1]
        jalaliTimes[2] = convertedTime[2]
        jalaliTimes[3] = hr
        jalaliTimes[4] = min
        jalaliTimes[5] = sec
        notify(grgTimes, jalaliTimes)
    }

    private fun updateTimeStamp() {
        try {
            this.timeInMilliSecond = Objects
                .requireNonNull(
                    SimpleDateFormat("dd/MM/yyyy HH:mm:ss", this.locale)
                        .parse(
                            ("" + this.grgDay + "/" + this.grgMonth + "/" + this.getGrgYear() + " " + this.hour
                                    + ":" + this.minute + ":" + this.second)
                        )
                )
                .time
        } catch (e: ParseException) {
            this.timeInMilliSecond = Date().time
        }
    }

    private fun notify(grg: IntArray, jalali: IntArray) {
        this.grgYear = grg[0]
        this.grgMonth = grg[1]
        this.grgDay = grg[2]
        this.shYear = jalali[0]
        this.shMonth = jalali[1]
        this.shDay = jalali[2]
        this.hour = jalali[3]
        this.minute = jalali[4]
        this.second = jalali[5]
        updateTimeStamp()
    } //endregion

    companion object {
        val FARVARDIN: Int = 1
        val ORDIBEHEST: Int = 2
        val KHORDAD: Int = 3
        val TIR: Int = 4
        val MORDAD: Int = 5
        val SHAHRIVAR: Int = 6
        val MEHR: Int = 7
        val ABAN: Int = 8
        val AZAR: Int = 9
        val DAY: Int = 10
        val BAHMAN: Int = 11
        val ESFAND: Int = 12
        val AM: Int = 1
        val PM: Int = 2
        val AM_SHORT_NAME: String = "ق.ظ"
        val PM_SHORT_NAME: String = "ب.ظ"
        val AM_NAME: String = "قبل از ظهر"
        val PM_NAME: String = "بعد از ظهر"

        /**
         * Check static is leap year for Jalali Date
         *
         * @param year Jalali year
         * @return true if year is leap
         */
        fun isJalaliLeap(year: Int): Boolean {
            return (PersianDate().isLeap(year))
        }

        /**
         * Check static is leap year for Grg Date
         *
         * @param year Year
         * @return boolean
         */
        fun isGrgLeap(year: Int): Boolean {
            return (PersianDate().grgIsLeap(year))
        }

        /**
         * Return today
         */
        fun today(): PersianDate {
            val persianDate = PersianDate()
            persianDate.setHour(0).setMinute(0).setSecond(0)
            return persianDate
        }

        /**
         * Get tomorrow
         */
        fun tomorrow(): PersianDate {
            val persianDate = PersianDate()
            persianDate.addDay()
            persianDate.setHour(0).setMinute(0).setSecond(0)
            return persianDate
        }
    }
}
