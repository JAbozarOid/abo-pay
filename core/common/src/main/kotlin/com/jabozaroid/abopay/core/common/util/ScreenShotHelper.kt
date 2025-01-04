package com.jabozaroid.abopay.core.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileOutputStream

/**
 *  Created on 8/29/2024
 **/
object ScreenShotHelper {
    fun getScreenShot(view: View): Bitmap {
        val totalHeight = view.height
        val totalWidth = view.width
        val returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        bgDrawable?.draw(canvas)
        view.draw(canvas)
        return returnedBitmap
    }

    fun getMainDirectoryName(context: Context): File {
        //Here we will use getExternalFilesDir and inside that we will make our Demo folder
        //benefit of getExternalFilesDir is that whenever the app uninstalls the images will get deleted automatically.
        val mainDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IVA"
        )

        //If File is not present create directory
        if (!mainDir.exists()) {
            if (mainDir.mkdir()) Log.e(
                "Create Directory",
                "Main Directory Created : $mainDir"
            )
        }
        return mainDir
    }

    fun getScreenshotFromBuiltView(v: View): Bitmap {
        val specWidth = View.MeasureSpec.makeMeasureSpec(0,  /* any */View.MeasureSpec.UNSPECIFIED)
        v.measure(specWidth, specWidth)
        val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        v.draw(c)
        return b
    }

    fun store(bm: Bitmap, fileName: String?, saveFilePath: File): File {
        val dir = File(saveFilePath.absolutePath)
        if (!dir.exists()) dir.mkdirs()
        val file = File(saveFilePath.absolutePath, fileName)
        try {
            val fOut = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }
}