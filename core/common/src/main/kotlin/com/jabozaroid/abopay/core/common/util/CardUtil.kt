package com.jabozaroid.abopay.core.common.util

import android.text.TextUtils
import com.jabozaroid.abopay.core.common.R

object CardUtil {
    const val BANK_TITLE_KEY: String = "BANK_TITLE_KEY"
    const val BANK_ICON_KEY_COLOR: String = "BANK_ICON_KEY_COLOR"
    const val BANK_ICON_KEY_WHITE: String = "BANK_ICON_KEY_WHITE"
    const val BANK_COLOR_KEY_UP: String = "BANK_COLOR_KEY_UP"
    const val BANK_COLOR_KEY_DOWN: String = "BANK_COLOR_KEY_DOWN"

    fun getPureCardNo(cardNo: String): String {
        if (TextUtils.isEmpty(cardNo)) return ""
        return cardNo.trim { it <= ' ' }.replace("-", "")
    }

    fun getBankIconResId(cardNo: String): Map<String, Int> {
        val resultMap: MutableMap<String, Int> = HashMap()
        if (TextUtils.isEmpty(cardNo)) {
            resultMap[BANK_TITLE_KEY] = R.string.other
            resultMap[BANK_ICON_KEY_COLOR] = R.drawable.ic_card_design_system
            resultMap[BANK_ICON_KEY_WHITE] = R.drawable.ic_card_design_system
            resultMap[BANK_COLOR_KEY_UP] = R.color.base
            resultMap[BANK_COLOR_KEY_DOWN] = R.color.base
            return resultMap
        }
        var cardBin = getPureCardNo(cardNo)
        if (cardBin.length >= 6) {
            cardBin = cardBin.substring(0, 6)
            when (cardBin) {
                "603799" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.melli_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_melli
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_melli
                    resultMap[BANK_COLOR_KEY_UP] = R.color.melli_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.melli_Down
                }

                "589210" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.sepah_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sepah
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sepah
                    resultMap[BANK_COLOR_KEY_UP] = R.color.melli_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.melli_Down
                }

                "627648", "207177" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.toseesaderat_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_tosesaderat
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_tosesaderat

                    resultMap[BANK_COLOR_KEY_UP] = R.color.tosee_saderat_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.tosee_saderat_down
                }

                "627961" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.sanat_madan_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sanatmaadan
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sanatmaadan
                    resultMap[BANK_COLOR_KEY_UP] = R.color.sanat_va_maadan_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.sanat_va_maadan_down
                }

                "603770", "639217" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.keshavarzi_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_keshavarzi
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_keshavarzi
                    resultMap[BANK_COLOR_KEY_UP] = R.color.keshavarzi_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.keshavarzi_down
                }

                "628023" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.maskan_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_maskan
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_maskan
                    resultMap[BANK_COLOR_KEY_UP] = R.color.maskan_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.maskan_down
                }

                "502908" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.tosee_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_taavon
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_taavon
                    resultMap[BANK_COLOR_KEY_UP] = R.color.tosee_taavon_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.tosee_taavon_down
                }

                "627760" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.post_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_postbank
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_postbank
                    resultMap[BANK_COLOR_KEY_UP] = R.color.post_bank_iran_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.post_bank_iran_down
                }

                "627412" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.en_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_eghtesad
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_eghtesad
                    resultMap[BANK_COLOR_KEY_UP] = R.color.eghtesad_novin_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.eghtesad_novin_down
                }

                "622106", "639194", "627884" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.parsian_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_parsian
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_parsian
                    resultMap[BANK_COLOR_KEY_UP] = R.color.parsian_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.parsian_down
                }

                "502229", "639347" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.pasargad_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_pasargad
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_pasargad
                    resultMap[BANK_COLOR_KEY_UP] = R.color.pasargad_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.pasargad_down
                }

                "627488", "502910" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.karafarin_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_karafarin
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_karafarin
                    resultMap[BANK_COLOR_KEY_UP] = R.color.karafarin_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.karafarin_down
                }

                "621986" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.saman_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_saman
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_saman
                    resultMap[BANK_COLOR_KEY_UP] = R.color.saman_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.saman_down
                }

                "639346" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.sina_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sina
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sina
                    resultMap[BANK_COLOR_KEY_UP] = R.color.sina_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.sina_down
                }

                "639607" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.sarmayeh_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sarmayeh
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sarmayeh
                    resultMap[BANK_COLOR_KEY_UP] = R.color.sarmayeh_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.sarmayeh_down
                }

                "502806", "504706" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.shahr_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_shahr
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_shahr
                    resultMap[BANK_COLOR_KEY_UP] = R.color.shahr_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.shahr_down
                }

                "502938" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.dey_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_dey
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_dey
                    resultMap[BANK_COLOR_KEY_UP] = R.color.dey_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.dey_down
                }

                "603769" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.saderat_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_saderat
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_saderat
                    resultMap[BANK_COLOR_KEY_UP] = R.color.saderat_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.saderat_down
                }

                "610433", "991975" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.mellat_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_mellat
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_mellat
                    resultMap[BANK_COLOR_KEY_UP] = R.color.melat_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.melat_down
                }

                "627353", "585983" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.tejarat_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_tejarat
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_tejarat
                    resultMap[BANK_COLOR_KEY_UP] = R.color.tejarat_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.tejarat_down
                }

                "589463" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.refah_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_refah
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_refah
                    resultMap[BANK_COLOR_KEY_UP] = R.color.refah_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.refah_down
                }

                "627381", "637381" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.ansaar_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sepah
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sepah
                    resultMap[BANK_COLOR_KEY_UP] = R.color.sepah_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.sepah_dwon
                }

                "636214" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.ayandeh_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_white_ayandeh
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_ayandeh
                    resultMap[BANK_COLOR_KEY_UP] = R.color.ayande_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.ayande_down
                }

                "639370" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.mehreeghtesad_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sepah
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sepah
                    resultMap[BANK_COLOR_KEY_UP] = R.color.sepah_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.sepah_dwon
                }

                "505785" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.iranzamin_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_iranzamin
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_iranzamin
                    resultMap[BANK_COLOR_KEY_UP] = R.color.iran_zamin_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.iran_zamin_down
                }

                "504172" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.resalat_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_resalat
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_resalat
                    resultMap[BANK_COLOR_KEY_UP] = R.color.resalat_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.resalat_down
                }

                "505416" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.gardeshgari_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_gardeshgari
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_gardeshgari
                    resultMap[BANK_COLOR_KEY_UP] = R.color.gareshgary_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.gareshgary_down
                }

                "636949" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.hekmatiranian_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sepah
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sepah
                    resultMap[BANK_COLOR_KEY_UP] = R.color.sepah_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.sepah_dwon
                }

                "606373" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.mehriran_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_mehr
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_mehr
                    resultMap[BANK_COLOR_KEY_UP] = R.color.mehr_iran_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.mehr_iran_down
                }

                "639599" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.ghavamin_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sepah
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sepah
                    resultMap[BANK_COLOR_KEY_UP] = R.color.sepah_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.sepah_dwon
                }

                "606256" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.melal_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_melal
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_melal
                    resultMap[BANK_COLOR_KEY_UP] = R.color.moasese_etebary_melal_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.moasese_etebary_melal_down
                }

                "585947" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.khavarmiane_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_middleeast
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_middleeast
                    resultMap[BANK_COLOR_KEY_UP] = R.color.khavar_miyane_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.khavar_miyane_down
                }

                "507677" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.noor_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_noor
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_noor
                    resultMap[BANK_COLOR_KEY_UP] = R.color.moasese_etebary_noor_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.moasese_etebary_noor_down
                }

                "505801" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.kosar_bank
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_sepah
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_sepah
                    resultMap[BANK_COLOR_KEY_UP] = R.color.sepah_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.sepah_dwon
                }

                "628157" -> {
                    resultMap[BANK_TITLE_KEY] = R.string.tosee_institute
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.card_icon_color_tosee
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.card_icon_white_tosee
                    resultMap[BANK_COLOR_KEY_UP] = R.color.moasese_etebary_tosee_up
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.moasese_etebary_tosee_down
                }

                else -> {
                    resultMap[BANK_TITLE_KEY] = R.string.other
                    resultMap[BANK_ICON_KEY_COLOR] = R.drawable.ic_card_design_system
                    resultMap[BANK_ICON_KEY_WHITE] = R.drawable.ic_card_design_system
                    resultMap[BANK_COLOR_KEY_UP] = R.color.base
                    resultMap[BANK_COLOR_KEY_DOWN] = R.color.base
                }
            }
        } else {
            resultMap[BANK_TITLE_KEY] = R.string.empty_string
            resultMap[BANK_ICON_KEY_COLOR] = R.drawable.ic_card_design_system
            resultMap[BANK_ICON_KEY_WHITE] = R.drawable.ic_card_design_system
            resultMap[BANK_COLOR_KEY_UP] = R.color.base
            resultMap[BANK_COLOR_KEY_DOWN] = R.color.base
        }
        return resultMap
    }

    fun getOtherBin(bin: String?): Array<String?>? {

        val filteredBins: Array<String?>? = when (bin) {
            "603799" -> arrayOf("603799")
            "589210" -> arrayOf("589210")
            "627648", "207177" -> arrayOf("627648", "207177")
            "627961" -> arrayOf("627961")
            "603770", "639217" -> arrayOf("603770", "639217")
            "628023" -> arrayOf("628023")
            "502908" -> arrayOf("502908")
            "627760" -> arrayOf("627760")
            "627412" -> arrayOf("627412")
            "622106", "639194", "627884" -> arrayOf("622106", "639194", "627884")
            "502229", "639347" -> arrayOf("502229", "639347")
            "627488", "502910" -> arrayOf("627488", "502910")
            "621986" -> arrayOf("621986")
            "639346" -> arrayOf("639346")
            "639607" -> arrayOf("639607")
            "502806", "504706" -> arrayOf("502806", "504706")
            "502938" -> arrayOf("502938")
            "603769" -> arrayOf("603769")
            "610433", "991975" -> arrayOf("610433", "991975")
            "627353", "585983" -> arrayOf("627353", "585983")
            "589463" -> arrayOf("589463")
            "627381", "637381" -> arrayOf("627381", "637381")
            "636214" -> arrayOf("636214")
            "639370" -> arrayOf("639370")
            "505785" -> arrayOf("505785")
            "504172" -> arrayOf("504172")
            "505416" -> arrayOf("505416")
            "636949" -> arrayOf("636949")
            "606373" -> arrayOf("606373")
            "639599" -> arrayOf("639599")
            "606256" -> arrayOf("606256")
            "585947" -> arrayOf("585947")
            "507677" -> arrayOf("507677")
            "505801" -> arrayOf("505801")
            "628157" -> arrayOf("628157")
            else -> null
        }
        return filteredBins
    }
}