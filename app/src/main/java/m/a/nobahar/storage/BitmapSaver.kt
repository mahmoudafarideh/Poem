package m.a.nobahar.storage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import m.a.nobahar.R
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import javax.inject.Inject

class BitmapSaver @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @SuppressLint("SimpleDateFormat")
    fun savePhoto(bitmap: Bitmap) {

        val imagesDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            context.resources.getString(R.string.app_name)
        )
        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val fileName =
            context.resources.getString(R.string.app_name) + "_" + formatter.format(java.util.Date()) + ".png"

        val imageFile = File(imagesDir, fileName)

        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        runCatching {
            MediaScannerConnection.scanFile(
                context,
                arrayOf(imageFile.path),
                null,
                object : MediaScannerConnection.OnScanCompletedListener {
                    override fun onScanCompleted(p0: String?, p1: Uri?) {}
                }
            )
        }
    }
}