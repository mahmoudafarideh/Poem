package m.a.poem.storage

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import m.a.poem.R
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class BitmapSaver @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun savePhoto(bitmap: Bitmap) {

        val imagesDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            context.resources.getString(R.string.app_name)
        )
        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }
        val fileName =
            context.resources.getString(R.string.app_name) + ".png"

        val imageFile = File(imagesDir, fileName)

        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        notifyImageAdded(imageFile)
    }

    private fun notifyImageAdded(imageFile: File) {
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