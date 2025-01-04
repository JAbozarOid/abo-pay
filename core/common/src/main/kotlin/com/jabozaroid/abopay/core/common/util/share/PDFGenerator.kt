package com.jabozaroid.abopay.core.common.util.share

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Picture
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

/**
 * Created on 17,September,2024
 */

enum class SharingFileType {
    PDF, IMAGE, TEXT
}

fun generatePDF(context: Context, picture: Picture, pageWidth: Float, pageHeight: Float): File {
    val pdfDocument = PdfDocument()
    val paint = Paint()
    val myPageInfo: PdfDocument.PageInfo? =
        PdfDocument.PageInfo.Builder(pageWidth.roundToInt(), pageHeight.roundToInt(), 1).create()
    val myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

    val canvas: Canvas = myPage.canvas

    canvas.drawBitmap(createBitmapFromPicture(picture), 56F, 40F, paint)

    pdfDocument.finishPage(myPage)

    val file = File(
        context.cacheDir,
        "AboPayPaymentReceipt${System.currentTimeMillis()}.pdf"
    )

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "فایل pdf ساخته شد", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "خطا در ساخت فایل pdf", Toast.LENGTH_SHORT)
            .show()
    }
    pdfDocument.close()
    return file
}

fun generateImage(context: Context, picture: Picture, pageWidth: Float, pageHeight: Float): File {

    val sharingImage = createBitmapFromPicture(picture)

    val file = File(
        context.cacheDir,
        "AboPayPaymentReceipt${System.currentTimeMillis()}.jpg"
    )
    val fos = FileOutputStream(file)
    sharingImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
    fos.close()

    return file
}

private fun createBitmapFromPicture(picture: Picture): Bitmap {
    val bitmap = Bitmap.createBitmap(
        picture.width,
        picture.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    canvas.drawPicture(picture)
    return bitmap
}

fun shareReceipt(
    context: Activity,
    picture: Picture,
    pageWidth: Float,
    pageHeight: Float,
    sharingFileType: SharingFileType
) {
    val generatedFile: File? = when (sharingFileType) {
        SharingFileType.PDF -> generatePDF(context, picture, pageWidth, pageHeight)
        SharingFileType.IMAGE -> generateImage(context, picture, pageWidth, pageHeight)
        else -> null
    }

    if (generatedFile != null) {
        val sharingIntent = ShareCompat.IntentBuilder(context)
            .setStream(
                FileProvider.getUriForFile(
                    context,
                    context.packageName + ".fileProvider",
                    generatedFile
                )
            )
            .setType("*/JPEG")
            .intent
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"))
    }
}

fun shareReceipt(
    context: Activity,
    sharingData: String
) {
    val sharingIntent = ShareCompat.IntentBuilder(context)
        .setText(sharingData)
        .setType("text/plain")
        .intent
    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"))

}
