package com.jabozaroid.abopay.core.common.util

import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.model.CardCommonModel
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_COLOR_KEY_DOWN
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_COLOR_KEY_UP
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_ICON_KEY_COLOR
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_ICON_KEY_WHITE
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_TITLE_KEY
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern


enum class ValidationState {

    VALID,
    INVALID,
    EMPTY,
    INVALID_LENGTH,
    INVALID_PATTERN,

}

class ValidationUtil {

    companion object Validate {

        val VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        val ARABIC_LETTERS = Regex("[\u0620-\u064A]")
        val ARABIC_EXT_LETTERS = Regex("[\u0670-\u06D5]")
        val SPACE = Regex("[\\s\u200b\u200c]")
        val REGEX_NAME_FA = Regex(
            "(" + ARABIC_LETTERS + "|" + ARABIC_EXT_LETTERS + ")" +
                    "(" + ARABIC_LETTERS + "|" + ARABIC_EXT_LETTERS + "|" + SPACE + ")*"
        )

        /**
         * Validate national code
         *
         * @param value national code
         * @return returns true if national code check sum is valid
         */
        fun nationalCode(value: String?): ValidationState {
            if (value.isNullOrEmpty()) return ValidationState.EMPTY
            if (value.length != 10) return ValidationState.INVALID_LENGTH
            val checkDigit = Character.getNumericValue(value[9])
            var sum = 0
            for (i in 0 until value.length - 1) {
                sum += (10 - i) * Character.getNumericValue(value[i])
            }
            val rem = sum % 11
            return if (rem < 2 && checkDigit == rem) ValidationState.VALID else if (checkDigit + rem == 11) ValidationState.VALID else ValidationState.INVALID
        }

        fun password(value: String?): ValidationState {
            val passwordRegex =
                Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
            if (value.isNullOrEmpty()) return ValidationState.EMPTY
            if (value.length != 8) return ValidationState.INVALID_LENGTH
            if (!passwordRegex.matches(value)) return ValidationState.INVALID_PATTERN
            return ValidationState.VALID
        }

        fun confimPassword(value: String?, match: String): ValidationState {
            if (value.isNullOrEmpty()) return ValidationState.EMPTY
            if (value.length != 8) return ValidationState.INVALID_LENGTH
            if (!value.contentEquals(match)) ValidationState.INVALID
            return ValidationState.VALID
        }

        fun mobile(mobile: String?): ValidationState {
            if (mobile.isNullOrEmpty()) return ValidationState.EMPTY
            if (mobile.length > 11) return ValidationState.INVALID_LENGTH
            if (!mobile.startsWith("09") || mobile.length != 11) return ValidationState.INVALID
            return ValidationState.VALID
        }

        fun phone(tel: String?, withPreCode: Boolean): ValidationState {
            if (tel == null) return ValidationState.EMPTY
            if (tel.isEmpty()) return ValidationState.EMPTY
            if (tel.startsWith("09")) return ValidationState.INVALID
            return if (tel.length != (if (withPreCode) 11 else 8)) ValidationState.INVALID_LENGTH else ValidationState.VALID
        }

        fun names(text: String?): ValidationState {
            if (text == null) return ValidationState.EMPTY
            if (text.isEmpty()) return ValidationState.EMPTY
            return if (!text.matches(REGEX_NAME_FA) || text.length < 3) ValidationState.INVALID else ValidationState.VALID
        }

        fun persianNames(text: String): ValidationState {
            if (text.isEmpty()) return ValidationState.EMPTY
            val trimText = text.trim { it <= ' ' }
            if (trimText.length < 3) return ValidationState.INVALID_LENGTH
            return if (!trimText.matches(REGEX_NAME_FA)) ValidationState.INVALID else ValidationState.VALID
        }

        fun email(email: String?): ValidationState? {
            if (email.isNullOrEmpty()) return ValidationState.EMPTY
            val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email)
            return if (matcher.find()) ValidationState.VALID else ValidationState.INVALID
        }

        fun isNotNullOrEmpty(str: String?): Boolean {
            return !str.isNullOrEmpty()
        }

        fun isResourceValid(resourceId: String?): Boolean {
            return !resourceId.isNullOrEmpty() && resourceId != "-1"
        }


        fun isNotNullOrEmpty(list: List<*>?): Boolean {
            return list != null && list.isNotEmpty()
        }

        fun isNullOrEmpty(list: List<*>?): Boolean {
            return list == null || list.isEmpty()
        }

        fun isNumberOnly(value: String?): Boolean {
            return value?.matches(Regex("\\d+")) ?: false
        }

        fun hasNumber(value: String?): Boolean {
            return value?.matches(Regex(".*\\d.*")) ?: false
        }

        fun isNotNullOrEmpty(data: Map<String?, String?>?): Boolean {
            return !data.isNullOrEmpty()
        }

        fun isUrl(str: String): Boolean {
            if (str.isEmpty()) return false
            val data = str.lowercase(Locale.getDefault())
            return data.contains("http") && data.contains("://")
        }

        fun isNotNullOrFalse(state: Boolean?): Boolean {
            return state != null && state
        }

        fun isNotNullOrZero(data: Long?): Boolean {
            return data != null || data != 0L
        }

        fun iban(iban: String): ValidationState? {
            val IBAN_MIN_SIZE = 15
            val IBAN_MAX_SIZE = 34
            val IBAN_MAX: Long = 999999999
            val IBAN_MODULUS: Long = 97
            val trimmed = iban.trim { it <= ' ' }
            if (trimmed.length < IBAN_MIN_SIZE || trimmed.length > IBAN_MAX_SIZE) {
                return ValidationState.INVALID_LENGTH
            }
            val reformat = trimmed.substring(4) + trimmed.substring(0, 4)
            var total: Long = 0
            for (i in 0 until reformat.length) {
                val charValue = Character.getNumericValue(reformat[i])
                if (charValue < 0 || charValue > 35) {
                    return ValidationState.INVALID
                }
                total = (if (charValue > 9) total * 100 else total * 10) + charValue
                if (total > IBAN_MAX) {
                    total %= IBAN_MODULUS
                }
            }
            return if (total % IBAN_MODULUS == 1L) ValidationState.VALID else ValidationState.INVALID
        }

        fun isProbablyArabic(s: String): Boolean {
            var i = 0
            while (i < s.length) {
                val c = s.codePointAt(i)
                if (c in 0x0600..0x06E0) return true
                i += Character.charCount(c)
            }
            return false
        }

        fun validationCardNumber(cardNumber: String, hasToken: Boolean = false): com.jabozaroid.abopay.core.common.model.CardCommonModel {
            var errorMessage: Int? = null
            var cardDetail: Map<String, Int>? = null

            cardDetail = if (cardNumber.length >= 6)
                CardUtil.getBankIconResId(cardNumber)
            else
                null

            errorMessage = if (cardNumber.isBlank()) {
                R.string.enter_card_number
            } else if (cardNumber.isNotBlank() && cardNumber.length < 17 && !hasToken && card(cardNumber) == ValidationState.INVALID) {
                R.string.invalid_card_number
            } else null


            return com.jabozaroid.abopay.core.common.model.CardCommonModel(
                cardNumber = cardNumber,
                cardIconColor = cardDetail?.get(BANK_ICON_KEY_COLOR),
                cardIconWhite = cardDetail?.get(BANK_ICON_KEY_WHITE),
                errorMessage = errorMessage,
                bankName = cardDetail?.get(BANK_TITLE_KEY),
                cardColorUp = cardDetail?.get(BANK_COLOR_KEY_UP),
                cardColorDown = cardDetail?.get(BANK_COLOR_KEY_DOWN)
            )

        }


        fun validationCardMonth(month: String): ValidationState {
            return if (month.isEmpty() || month.length < 2 || month.toInt() !in 1..12)
                ValidationState.INVALID
            else
                ValidationState.VALID
        }

        fun validationCardYear(year: String): ValidationState {
            return if (year.isEmpty() || year.length < 2)
                ValidationState.INVALID
            else
                ValidationState.VALID

        }

        fun validationCVV2(cvv2: String): ValidationState {
            return if (cvv2.isBlank())
                ValidationState.INVALID
            else
                ValidationState.VALID
        }

        fun validationOtp(otp: String): ValidationState {
            return if (otp.isBlank()) {
                ValidationState.INVALID
            } else {
                ValidationState.VALID
            }
        }

        fun card(pureCardNumber: String): ValidationState {
            try {
                val charArray = pureCardNumber.toCharArray()
                var avg = 0
                for (i in 0..15) {
                    val num = charArray[i].toString().toInt()
                    if (i % 2 == 1) avg += num
                    else if (i % 2 == 0) {
                        val crossedNum = num * 2
                        avg += if (crossedNum > 9) crossedNum - 9 else crossedNum
                    }
                }
                if (avg % 10 == 0) return ValidationState.VALID

                return ValidationState.INVALID
            } catch (ex: java.lang.Exception) {
                return ValidationState.INVALID
            }
        }
    }
}

