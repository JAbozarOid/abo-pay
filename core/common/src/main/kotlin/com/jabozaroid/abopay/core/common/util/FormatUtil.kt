package com.jabozaroid.abopay.core.common.util

import com.jabozaroid.abopay.core.common.model.CurrencyType
import org.spongycastle.util.encoders.Base64
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

/**
 *  Created on 8/3/2024
 **/
class FormatUtil {


    companion object {
        private val REMOVE_TAGS: Pattern = Pattern.compile("<.+?>")

        private val hexArray = "0123456789ABCDEF".toCharArray()
        private val pNumbers: CharArray =
            charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
        private val eNumbers: CharArray =
            charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

        fun byteArrayToHexString(data: ByteArray): String {
            val hexChars = CharArray(data.size * 2)
            for (j in data.indices) {
                val v = data[j].toInt() and 0xFF
                hexChars[j * 2] = hexArray[v ushr 4]
                hexChars[j * 2 + 1] = hexArray[v and 0x0F]
            }
            return String(hexChars)
        }
        fun base64ToHexString(data :String) : String {
            return byteArrayToHexString(Base64.decode(data))
        }

           fun base64ToByteArray (data : String): ByteArray {
              return Base64.decode(data)
           }

        fun byteArrayToAscii(data: ByteArray?): String {
            return String(data!!)
        }

        fun hexToByteArray(s: String): ByteArray {
            var s = s
            var len = s.length
            if (len % 2 != 0) {
                s = "0$s"
                len++
            }

            val data = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                data[i / 2] = ((s[i].digitToIntOrNull(16) ?: -1 shl 4)
                + s[i + 1].digitToIntOrNull(16)!! ?: -1).toByte()
                i += 2
            }

            return data
        }

        /**
         * Get masked PAN from plain PAN
         *
         * @param pan Plain PAN or dashed PAN
         * @return masked PAN in format xxxxxx******xxxx
         */
        fun getMaskedPan(pan: String?): String? {
            var pan = pan
            if (pan == null || pan.isEmpty() || pan.length < 16) return pan
            pan = pan.replace("-", "")
            return pan.substring(0, 6) + "******" + pan.substring(pan.length - 4)
        }

        /**
         * separate Pan
         *
         * @param pan Plain PAN or dashed PAN
         * @return masked PAN in format xxxx-xxxx-xxxx-xxxx
         */
        fun getSeparatePan(pan: String?): String {
            var pan = pan
            if (pan == null || pan == "") return ""
            pan = getPurePan(pan)
            return pan.substring(0, 4) + "-" + pan.substring(4, 8) + "-" + pan.substring(
                8,
                12
            ) + "-" + pan.substring(12)
        }

        /**
         * remove dashed from Pan
         *
         * @param dashedPan dashed PAN
         * @return
         */
        fun getPurePan(dashedPan: String): String {
            return dashedPan.toString().trim { it <= ' ' }.replace("-", "")
        }

        fun fromByteArray(data: ByteArray): String {
            val hexChars = CharArray(data.size * 2)
            for (j in data.indices) {
                val v = data[j].toInt() and 0xFF
                hexChars[j * 2] = hexArray[v ushr 4]
                hexChars[j * 2 + 1] = hexArray[v and 0x0F]
            }
            return String(hexChars)
        }

        fun fixNumber(number: String?): String {
            var replacedNumber = number
            try {
                if (replacedNumber.isNullOrEmpty()) return ""

                replacedNumber = replacePersianNumber(replacedNumber)
                replacedNumber = replacedNumber.replace("[^\\d.]".toRegex(), "")

                if (replacedNumber.isEmpty()) return ""

                if (ValidationUtil.isNumberOnly(replacedNumber)) return BigDecimal(replacedNumber).toString()

                val decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US)
                return DecimalFormat("###", decimalFormatSymbols).format(BigDecimal(replacedNumber))
            } catch (e: Exception) {
                return ""
            }
        }

        fun fixNumberInLong(number: String?): Long {
            return try {
                fixNumber(number).toLong()
            } catch (e: Exception) {
                -1L
            }
        }


        fun replacePersianNumber(number: String): String {
            var number = number
            if (number == null || number.isEmpty()) return number
            for (i in pNumbers.indices) {
                number = number.replace(pNumbers[i].toString().toRegex(), eNumbers[i].toString())
            }
            return number
        }


        fun toFormattedNumber(value: Long?): String {
            if (value == null) return ""
            if (value < 1000) return value.toString()
            val myFormat = NumberFormat.getInstance()
            myFormat.isGroupingUsed = true
            return myFormat.format(value)
        }

        fun toSeparatedAmount(input: String): String {
            if (input.isEmpty()) return ""
            return getDecimalFormat().format(BigDecimal(input))
        }

        fun toSeparatedAmount(input: BigDecimal?): String {
            return getDecimalFormat().format(input)
        }

        fun getDecimalFormat(): DecimalFormat {
            val decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US)
            return DecimalFormat("#,###", decimalFormatSymbols)
        }

        fun rightPad(input: String, length: Int, fill: String?): String {
            val pad = input.trim { it <= ' ' } + String.format("%" + length + "s", "").replace(
                " ",
                fill!!
            )
            return pad.substring(0, length)
        }

        fun leftPad(input: String, length: Int, fill: String?): String {
            val pad = String.format("%" + length + "s", "").replace(
                " ",
                fill!!
            ) + input.trim { it <= ' ' }
            return pad.substring(pad.length - length)
        }


        fun convertDigitsToPersianWords(input: Long): String? {
            val inputDigits = input.toString()
            var output = ""
            val persianWords = Array(3) {
                arrayOfNulls<String>(
                    10
                )
            }
            persianWords[0] = arrayOf("", "یک", "دو", "سه", "چهار", "پنج", "شش", "هفت", "هشت", "نه")
            persianWords[1] =
                arrayOf("", "", "بیست", "سی", "چهل", "پنجاه", "شصت", "هفتاد", "هشتاد", "نود")
            persianWords[2] = arrayOf(
                "",
                "یکصد",
                "دویست",
                "سیصد",
                "چهارصد",
                "پانصد",
                "ششصد",
                "هفتصد",
                "هشتصد",
                "نهصد"
            )


            val persianWords10_19 = arrayOfNulls<String>(10)
            persianWords10_19[0] = "ده"
            persianWords10_19[1] = "یازده"
            persianWords10_19[2] = "دوازده"
            persianWords10_19[3] = "سیزده"
            persianWords10_19[4] = "چهارده"
            persianWords10_19[5] = "پانزده"
            persianWords10_19[6] = "شانزده"
            persianWords10_19[7] = "هفده"
            persianWords10_19[8] = "هجده"
            persianWords10_19[9] = "نوزده"

            if (inputDigits.length > 9) { // میلیارد ها
                val upper = inputDigits.substring(0, inputDigits.length - 9).toInt()
                val lower = inputDigits.substring(inputDigits.length - 9).toInt()
                return (convertDigitsToPersianWords(upper.toLong())!!.trim { it <= ' ' }
                        + " میلیارد"
                        + (if (lower != 0) " و " else "")
                        + convertDigitsToPersianWords(lower.toLong())!!.trim { it <= ' ' })
            } else if (inputDigits.length > 6) { // میلیون ها
                val upper = inputDigits.substring(0, inputDigits.length - 6).toInt()
                val lower = inputDigits.substring(inputDigits.length - 6).toInt()
                return (convertDigitsToPersianWords(upper.toLong())!!.trim { it <= ' ' }
                        + " میلیون"
                        + (if (lower != 0) " و " else "")
                        + convertDigitsToPersianWords(lower.toLong())!!.trim { it <= ' ' })
            } else if (inputDigits.length > 3) { // هزارها
                val upper = inputDigits.substring(0, inputDigits.length - 3).toInt()
                val lower = inputDigits.substring(inputDigits.length - 3).toInt()
                return (convertDigitsToPersianWords(upper.toLong())!!.trim { it <= ' ' }
                        + " هزار"
                        + (if (lower != 0) " و " else "")
                        + convertDigitsToPersianWords(lower.toLong())!!.trim { it <= ' ' })
            } else {
                if (inputDigits.length == 3) {
                    var number = inputDigits.substring(0, 1).toInt()
                    output += persianWords[2][number]
                    number = inputDigits.substring(1).toInt()
                    return if (number != 0) {
                        output.trim { it <= ' ' } + " و " + convertDigitsToPersianWords(number.toLong())!!
                            .trim { it <= ' ' }
                    } else {
                        output.trim { it <= ' ' } + convertDigitsToPersianWords(number.toLong())!!
                            .trim { it <= ' ' }
                    }
                } else if (inputDigits.length == 2) {
                    var number = inputDigits.toInt()
                    if (number > 19) {
                        number = inputDigits.substring(0, 1).toInt()
                        output += persianWords[1][number]

                        number = inputDigits.substring(1).toInt()
                        return if (number != 0) {
                            output.trim { it <= ' ' } + " و " + convertDigitsToPersianWords(number.toLong())!!
                                .trim { it <= ' ' }
                        } else {
                            output.trim { it <= ' ' } + convertDigitsToPersianWords(number.toLong())!!
                                .trim { it <= ' ' }
                        }
                    } else if (number >= 10) {
                        return persianWords10_19[number - 10] + " "
                    } else {
                        return persianWords[0][number]
                    }
                } else {
                    return persianWords[0][inputDigits.toInt()] + " "
                }
            }
        }

        fun getPureAmount(value: String?): BigDecimal {
            var value = value
            if (value == null || value == "") return BigDecimal.ZERO
            val separator = getDecimalFormat().decimalFormatSymbols.groupingSeparator
            value = value.replace(separator.toString(), "")
                .replace("٬".toRegex(), "")
                .replace(",".toRegex(), "")
                .replace("،".toRegex(), "")
                .replace(" ".toRegex(), "")
                .replace(",".toRegex(), "")
            value = replacePersianNumber(value)
            value = value.replace("[^\\d.]".toRegex(), "")
            return try {
                BigDecimal(
                    BigDecimal(value.trim { it <= ' ' }).stripTrailingZeros().toPlainString()
                )
            } catch (e: Exception) {
                try {
                    BigDecimal(value.trim { it <= ' ' }.replace("\\.".toRegex(), ""))
                } catch (ex: Exception) {
                    BigDecimal.ZERO
                }
            }
        }

        fun getPureAmountKeepNegativeSign(value: String?): BigDecimal {
            var value = value
            if (value == null || value == "") return BigDecimal.ZERO
            val separator = getDecimalFormat().decimalFormatSymbols.groupingSeparator
            value = value.replace(separator.toString(), "")
                .replace("٬".toRegex(), "")
                .replace(",".toRegex(), "")
                .replace("،".toRegex(), "")
                .replace(" ".toRegex(), "")
            value = replacePersianNumber(value)
            return try {
                BigDecimal(value.trim { it <= ' ' })
            } catch (e: Exception) {
                BigDecimal.ZERO
            }
        }

        fun en2fa(input: String?): String? {
            if (input == null) {
                return null
            }
            return input
                .replace('0', '\u06F0')
                .replace('1', '\u06F1')
                .replace('2', '\u06F2')
                .replace('3', '\u06F3')
                .replace('4', '\u06F4')
                .replace('5', '\u06F5')
                .replace('6', '\u06F6')
                .replace('7', '\u06F7')
                .replace('8', '\u06F8')
                .replace('9', '\u06F9')
                .replace('\u0660', '\u06F0')
                .replace('\u0661', '\u06F1')
                .replace('\u0662', '\u06F2')
                .replace('\u0663', '\u06F3')
                .replace('\u0664', '\u06F4')
                .replace('\u0665', '\u06F5')
                .replace('\u0666', '\u06F6')
                .replace('\u0667', '\u06F7')
                .replace('\u0668', '\u06F8')
                .replace('\u0669', '\u06F9')
        }

        fun numberOnly(value: String?): String? {
            if (value == null) return null
            return value.replace("[^\\d]".toRegex(), "")
        }

        fun fa2en(input: String?): String? {
            if (input == null) {
                return null
            }
            return input
                .replace('\u06F0', '0')
                .replace('\u06F1', '1')
                .replace('\u06F2', '2')
                .replace('\u06F3', '3')
                .replace('\u06F4', '4')
                .replace('\u06F5', '5')
                .replace('\u06F6', '6')
                .replace('\u06F7', '7')
                .replace('\u06F8', '8')
                .replace('\u06F9', '9')
                .replace('\u0660', '0')
                .replace('\u0661', '1')
                .replace('\u0662', '2')
                .replace('\u0663', '3')
                .replace('\u0664', '4')
                .replace('\u0665', '5')
                .replace('\u0666', '6')
                .replace('\u0667', '7')
                .replace('\u0668', '8')
                .replace('\u0669', '9')
                .replace('۰', '0')
                .replace('۱', '1')
                .replace('۲', '2')
                .replace('۳', '3')
                .replace('۴', '4')
                .replace('۵', '5')
                .replace('۶', '6')
                .replace('۷', '7')
                .replace('۸', '8')
                .replace('۹', '9')
        }

        fun toIsoDateFormat(date: Date?): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            return sdf.format(date)
        }

        fun toDualTimeFormat(duration: Long): String {
            var seconds = (duration / 1000).toInt()
            val minutes = seconds / 60
            seconds %= 60
            return (String.format(Locale.ENGLISH, "%02d", minutes) + ":"
                    + String.format(Locale.ENGLISH, "%02d", seconds))
        }

        fun getPanId(pan: String?): String? {
            var repalcedPan = pan
            if (repalcedPan.isNullOrEmpty()) return ""
            repalcedPan = numberOnly(repalcedPan)
            val len = repalcedPan!!.length
            return if (len >= 10) {
                repalcedPan.substring(0, 6) + repalcedPan.substring(len - 4)
            } else repalcedPan
        }

        fun removeTags(str: String?): String? {
            val m = str?.let { REMOVE_TAGS.matcher(it) }
            return m?.replaceAll("")
        }


        fun isValidGUID(str: String?): Boolean {
            // Regex to check valid
            // GUID (Globally Unique Identifier)
            val regex = ("^[{]?[0-9a-fA-F]{8}"
                    + "-([0-9a-fA-F]{4}-)"
                    + "{3}[0-9a-fA-F]{12}[}]?$")

            // Compile the ReGex
            val p = Pattern.compile(regex)

            // If the string is empty
            // return false
            if (str == null) {
                return false
            }

            // Find match between given string
            // and regular expression
            // uSing Pattern.matcher()
            val m = p.matcher(str)

            // Return if the string
            // matched the ReGex
            return m.matches()
        }

        /**
         * Remove 0098 From Mobile Number
         *
         * @param mobile
         * @return
         */
        fun removeIranCodeFromMobileNumber(mobile: String?): String? {
            var mobile = mobile
            mobile = numberOnly(mobile)
            if (mobile!!.startsWith("98")) {
                mobile = mobile.replaceFirst("98".toRegex(), "0")
            }
            if (mobile.startsWith("0098")) {
                mobile = mobile.replaceFirst("0098".toRegex(), "0")
            }
            return mobile
        }

        fun convertAmount(amount: String, currencyType: com.jabozaroid.abopay.core.common.model.CurrencyType): String {
            return try {
                // checking currency type and converting the price to desired ones.
                if (amount.toInt() >= 10) {
                    if (currencyType == com.jabozaroid.abopay.core.common.model.CurrencyType.RIAL) {
                        convertDigitsToPersianWords(amount.toLong() / 10).plus(" تومان ") ?: ""
                    } else {
                        convertDigitsToPersianWords(amount.toLong() * 10) ?: ""
                    }
                } else {
                    ""
                }
            } catch (e: Exception) {
                ""
            }
        }

        fun getAmountWithVat(amount: String, vat: String): Long {
            var pureAmount: Long? = 0
            var pureVat: Long? = 0
            try {
                pureAmount = amount.toLongOrNull()
                pureVat = vat.toLongOrNull()
            } catch (e: Exception) {
                return 0
            }

            return if (pureAmount != null && pureVat != null) {
                pureAmount + (pureAmount * pureVat / 100)
            } else 0
        }

    }


}


