package com.jabozaroid.abopay.core.common.util

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.FileProvider

/**
 *  Created on 8/29/2024 
 **/
class ShareUtil {



    companion object {


        fun createReceiptFileAndShare(view: View?, printable: Boolean, context: Context): Intent? {
            val screenView =
                if (printable) ScreenShotHelper.getScreenshotFromBuiltView(view!!) else ScreenShotHelper.getScreenShot(
                    view!!
                )
                screenView.let {
                    val saveFile =
                        ScreenShotHelper.getMainDirectoryName(context) //get the path to save screenshot
                    val file = ScreenShotHelper.store(
                        screenView,
                        "IVA_" + System.currentTimeMillis() / 1000 + ".jpg",
                        saveFile
                    ) //save the screenshot to selected path
                    val intent = Intent()
                    intent.setAction(Intent.ACTION_SEND)
                    val apkURI = FileProvider.getUriForFile(
                        context,
                        context
                            .packageName + ".provider", file
                    )
                    intent.setDataAndType(apkURI, "image/*")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intent.putExtra(Intent.EXTRA_STREAM, apkURI) //
                    return intent // pass uri here
                }

        }

    }
}