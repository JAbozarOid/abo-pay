package com.jabozaroid.abopay.core.common.util.dateconverter

import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created on 17,November,2024
 */

class PersianDateFormat {
    //number Format
    enum class PersianDateNumberCharacter {
        ENGLISH,
        FARSI
    }
    //variable
    /**
     * Key for convert Date to String
     */
    private val key = arrayOf(
        "a", "l", "j", "F", "Y", "H", "i", "s", "d", "g", "n", "m", "t", "w", "y",
        "z", "A",
        "L", "X", "C", "E", "P", "Q", "R"
    )

    //from version 1.3.3 pattern is public and has de
    private var pattern = "l j F Y H:i:s"
    private var numberCharacter = PersianDateNumberCharacter.ENGLISH

    /**
     * key_parse for convert String to PersianDate
     *
     *
     * yyyy = Year (1396) MM = month (02-12-...) dd = day (13-02-15-...) HH = Hour (13-02-15-...) mm =
     * minutes (13-02-15-...) ss = second (13-02-15-...)
     */
    private val key_parse = arrayOf("yyyy", "MM", "dd", "HH", "mm", "ss")

    /**
     * Constructor for create formatter with just pattern
     */
    constructor(pattern: String) {
        this.pattern = pattern
    }

    /**
     * Constructor for create pattern with number character format
     *
     * @param pattern         pattern use for format
     * @param numberCharacter character type can be PersianDateNumberCharacter.FARSI | PersianDateNumberCharacter.English
     */
    constructor(pattern: String, numberCharacter: PersianDateNumberCharacter) {
        this.pattern = pattern
        this.numberCharacter = numberCharacter
    }

    /**
     * Change pattern
     *
     * @param pattern change format pattern
     */
    fun setPattern(pattern: String) {
        this.pattern = pattern
    }

    /**
     * Change number character
     *
     * @param numberCharacter number character
     */
    fun setNumberCharacter(
        numberCharacter: PersianDateNumberCharacter
    ) {
        this.numberCharacter = numberCharacter
    }

    /**
     * Constructor without pattern
     */
    constructor()

    fun format(date: PersianDate): String {
        val year2 = if (("" + date.getShYear()).length == 2) {
            "" + date.getShYear()
        } else if (("" + date.getShYear()).length == 3) {
            ("" + date.getShYear()).substring(2, 3)
        } else {
            ("" + date.getShYear()).substring(2, 4)
        }
        val values = arrayOf(
            date.getShortTimeOfTheDay(),
            date.dayName(),
            "" + date.getShDay(),
            date.monthName(),
            "" + date.getShYear(),
            this.textNumberFilter("" + date.getHour()),
            this.textNumberFilter("" + date.getMinute()),
            this.textNumberFilter("" + date.getSecond()),
            this.textNumberFilter("" + date.getShDay()),
            "" + date.get12FormatHour(),
            "" + date.getShMonth(),
            this.textNumberFilter("" + date.getShMonth()),
            "" + date.getMonthDays(),
            "" + date.dayOfWeek(),
            year2, "" + date.getDayInYear(),
            date.getTimeOfTheDay(),
            (if (date.isLeap()) "1" else "0"),
            date.AfghanMonthName(),
            date.KurdishMonthName(),
            date.PashtoMonthName(),
            date.FinglishMonthName(),
            date.dayFinglishName(),
            date.dayEnglishName()
        )
        if (this.numberCharacter == PersianDateNumberCharacter.FARSI) {
            farsiCharacter(values)
        }
        return this.stringUtils(this.pattern, this.key, values)
    }

    /**
     * Parse Jallali date from String
     *
     * @param date    date in string
     * @param pattern pattern
     */
    /**
     * Parse Jallali date from String
     *
     * @param date date in string
     */
    @JvmOverloads
    @Throws(ParseException::class)
    fun parse(date: String, pattern: String = this.pattern): PersianDate {
        val jalaliDate = mutableListOf<Int>()
        jalaliDate.add(0)
        jalaliDate.add(0)
        jalaliDate.add(0)
        jalaliDate.add(0)
        jalaliDate.add(0)
        jalaliDate.add(0)


        for (i in key_parse.indices) {
            if ((pattern.contains(key_parse[i]))) {
                val start_temp = pattern.indexOf(key_parse[i])
                val end_temp = start_temp + key_parse[i].length
                val dateReplace = date.substring(start_temp, end_temp)
                if (dateReplace.matches("[-+]?\\d*\\.?\\d+".toRegex())) {
                    jalaliDate[i] = dateReplace.toInt()
                } else {
                    throw ParseException("Parse Exception", 10)
                }
            }
        }
        return PersianDate()
            .initJalaliDate(
                jalaliDate[0], jalaliDate[1], jalaliDate[2], jalaliDate[3],
                jalaliDate[4], jalaliDate[5]
            )
    }

    /**
     * Convert String Grg date to persian date object
     *
     * @param date    date String
     * @param pattern pattern
     * @return PersianDate object
     */
    /**
     * Convert String Grg date to persian date object
     *
     * @param date date in String
     * @return PersianDate object
     */
    @JvmOverloads
    @Throws(ParseException::class)
    fun parseGrg(date: String?, pattern: String? = this.pattern): PersianDate {
        val dateInGrg = SimpleDateFormat(pattern).parse(date)
        return PersianDate(dateInGrg.time)
    }

    /**
     * Replace String
     *
     * @param text   String
     * @param key    Looking for
     * @param values Replace with
     */
    private fun stringUtils(text: String, key: Array<String>, values: Array<String>): String {
        var text = text
        for (i in key.indices) {
            text = text.replace(key[i], values[i])
        }
        return text
    }

    /**
     * add zero to start
     *
     * @param date data
     * @return return string with 0 in start
     */
    private fun textNumberFilter(date: String): String {
        if (date.length < 2) {
            return "0$date"
        }
        return date
    }

    companion object {
        /**
         * Convert with charter type
         *
         * @param date                  date in PersianDate object
         * @param pattern               pattern
         * @param numberFormatCharacter number charter
         * @return return date
         */
        /**
         * Format date
         *
         * @param date    PersianDate object of date
         * @param pattern Pattern you want to show
         * @return date in pattern
         */
        @JvmOverloads
        fun format(
            date: PersianDate,
            pattern: String?,
            numberFormatCharacter: PersianDateNumberCharacter = PersianDateNumberCharacter.ENGLISH
        ): String {
            var pattern = pattern
            if (pattern == null) pattern = "l j F Y H:i:s"
            val key = arrayOf(
                "a", "l", "j", "F", "Y", "H", "i", "s", "d", "g", "n", "m", "t", "w", "y", "z",
                "A", "L", "X", "C", "E", "P", "Q", "R"
            )
            val year2 = if (("" + date.getShYear()).length == 2) {
                "" + date.getShYear()
            } else if (("" + date.getShYear()).length == 3) {
                ("" + date.getShYear()).substring(2, 3)
            } else {
                ("" + date.getShYear()).substring(2, 4)
            }
            val values = arrayOf(
                date.getShortTimeOfTheDay(),
                date.dayName(),
                "" + date.getShDay(),
                date.monthName(),
                "" + date.getShYear(),
                textNumberFilterStatic("" + date.getHour()),
                textNumberFilterStatic("" + date.getMinute()),
                textNumberFilterStatic("" + date.getSecond()),
                textNumberFilterStatic("" + date.getShDay()),
                "" + date.get12FormatHour(),
                "" + date.getShMonth(),
                textNumberFilterStatic("" + date.getShMonth()),
                "" + date.getMonthDays(),
                "" + date.dayOfWeek(),
                year2,
                "" + date.getDayInYear(),
                date.getTimeOfTheDay(),
                (if (date.isLeap()) "1" else "0"),
                date.AfghanMonthName(),
                date.KurdishMonthName(),
                date.PashtoMonthName(),
                date.FinglishMonthName(),
                date.dayFinglishName(),
                date.dayEnglishName()
            )
            if (numberFormatCharacter == PersianDateNumberCharacter.FARSI) {
                farsiCharacter(values)
            }
            for (i in key.indices) {
                pattern = pattern!!.replace(key[i], values[i])
            }
            return pattern!!
        }

        fun textNumberFilterStatic(date: String): String {
            if (date.length < 2) {
                return "0$date"
            }
            return date
        }

        /**
         * Convert English characters to Farsi characters
         *
         * @param values a string array of values
         * @return a converted string array
         */
        fun farsiCharacter(values: Array<String>): Array<String> {
            val persianChars = arrayOf("۰", "۱", "۲", "٣", "۴", "۵", "۶", "۷", "۸", "٩")
            val englishChars = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
            for (i in values.indices) {
                var tmpValue = values[i]
                for (j in persianChars.indices) {
                    tmpValue = tmpValue.replace(englishChars[j].toRegex(), persianChars[j])
                }
                values[i] = tmpValue
            }

            return values
        }
    }
}