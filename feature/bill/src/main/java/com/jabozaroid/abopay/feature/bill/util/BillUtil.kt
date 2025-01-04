package com.jabozaroid.abopay.feature.bill.util

import android.annotation.SuppressLint
import com.jabozaroid.abopay.core.common.util.FormatUtil
import com.jabozaroid.abopay.core.common.util.ValidationState
import com.jabozaroid.abopay.core.common.util.ValidationUtil
import com.jabozaroid.abopay.core.domain.model.bill.BillTypeResult


fun getAmount(pyaId: String): Long {
    var pyaId = pyaId
    pyaId = FormatUtil.leftPad(pyaId, 13, "0")
    var zero = ""
    val payLength = pyaId.length
    if (payLength < 13) {
        for (i in 0 until 13 - payLength) {
            zero += "0"
        }

        pyaId = zero + pyaId
    }
    pyaId = pyaId.substring(0, 8)
    val identity = pyaId.toLong() * 1000
    return identity
}

fun getBillType(billId: String?): Int {
    val leftPadBillId = FormatUtil.leftPad(billId!!, 13, "0")
    val type = leftPadBillId.substring(11, 12)
    return type.toInt()
}

@SuppressLint("SuspiciousIndentation")
fun getBillName(bills: List<BillTypeResult>, billType: Int): String? {
    var billParameterName: String? = ""
    bills.forEach {
        if (it.specResult?.type == billType) {
            billParameterName = it.parameterName ?: ""
            return@forEach
        }
    }
    return billParameterName
}

fun getBillIconThemeByTypeId(
    billTypesItems: List<BillTypeResult>,
    billType: Int,
    isDarkTheme: Boolean,
): String {
    if (ValidationUtil.isNotNullOrEmpty(billTypesItems)) {
        for (i in billTypesItems.indices) {
            billTypesItems[i].darkLogo?.let { darkLogo ->
                billTypesItems[i].lightLogo?.let { lightLogo ->
                    if (billTypesItems[i].id == billType) return if (isDarkTheme) darkLogo else lightLogo
                }
            }
        }
    }
    return ""
}

fun billId(billId: String?): ValidationState {

    return try {
        if (billId.isNullOrEmpty()) return ValidationState.EMPTY
        val leftPadBillId = FormatUtil.leftPad(billId, 13, "0")
        val billIdCheckSum = leftPadBillId.substring(12, 13)
        val originBillId = leftPadBillId.substring(0, 12)
        if (billIdCheckSum.matches(getBillCheckDigit1(originBillId).toRegex())) ValidationState.VALID else ValidationState.INVALID
    } catch (e: Exception) {
        ValidationState.INVALID
        throw e
    }

}

fun payId(payId: String?): ValidationState {
    return try {
        if (payId.isNullOrEmpty()) return ValidationState.EMPTY
        val leftPadPayId = FormatUtil.leftPad(payId, 13, "0")
        val payIdCheckSum = leftPadPayId.substring(11, 12)
        val originalPayId = leftPadPayId.substring(0, 11)
        if (payIdCheckSum.matches(getBillCheckDigit1(originalPayId).toRegex())) ValidationState.VALID else ValidationState.INVALID
    } catch (e: Exception) {
        ValidationState.INVALID
        throw e
    }
}

/**
 * check that billId and PayId Pair is correct
 *
 * @param billId
 * @param paymentId
 * @return
 * @throws IllegalArgumentException
 */
@Throws(IllegalArgumentException::class)
fun billIdAndPayId(billId: String?, paymentId: String?): ValidationState {
  return  try {
        if (billId.isNullOrEmpty() || paymentId.isNullOrEmpty()) return ValidationState.EMPTY
        val checkDigit2 = paymentId.substring(paymentId.length - 1).toInt()

        val bpStr = billId + paymentId
        val chars = StringBuffer(bpStr.substring(0, bpStr.length - 1)).reverse().toString()
            .toCharArray()

        val checksum: Int = calculateChecksumBy11(chars)

        require(checkDigit2 == checksum) { "invalid checkDigit1: is $checkDigit2 actually must be $checksum" }

        ValidationState.VALID
    } catch (e: Exception) {
        ValidationState.INVALID
        throw e
    }

}


@Throws(java.lang.IllegalArgumentException::class)
private fun calculateChecksumBy11(chars: CharArray): Int {
    var ratio = 2
    var sum = 0

    for (chr in chars) {
        val digit = chr.toString().toInt()
        sum += digit * ratio
        ratio++
        if (ratio > 7) ratio = 2
    }
    require(sum != 0) { "invalid checksum" }

    val rem = (sum % 11)
    if (rem < 2) return 0
    return 11 - rem
}

private fun getBillCheckDigit1(input: String): String {
    var input = input
    input = FormatUtil.leftPad(input, 12, "0")
    var sum = 0
    val seed = intArrayOf(2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7)
    for (i in 11 downTo 0) {
        sum += Character.getNumericValue(input[i]) * seed[11 - i]
    }
    var mode = sum % 11
    mode = if (((mode == 0) or (mode == 1))) {
        0
    } else {
        11 - mode
    }
    return mode.toString()
}
