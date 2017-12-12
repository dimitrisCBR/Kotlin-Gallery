package cbr.com.opengallery.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File

/** Created by Dimitrios on 12/10/2017.*/
fun getAllImages(context: Context): List<File> {
    val results = mutableListOf<File>()
    results.addAll(getExternalStorageContent(context))
    results.addAll(getInternalStorageContent(context))
    return results
}

fun sortImagesByFolder(files: List<File>): Map<File, List<File>> {
    val resultMap = mutableMapOf<File, MutableList<File>>()
    for (file in files) {
        (!resultMap.containsKey(file.parentFile)).let { resultMap.put(file.parentFile, mutableListOf()) }
        resultMap[file.parentFile]?.add(file)
    }
    return resultMap.toMap()
}

fun getAllImagesUnderFolder(context: Context, file: File): List<File> = getImageFileFromUri(context, Uri.fromFile(file))

private fun getInternalStorageContent(context: Context): Collection<File> = getImageFileFromUri(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

private fun getExternalStorageContent(context: Context): Collection<File> = getImageFileFromUri(context, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

private fun getImageFileFromUri(context: Context, uri: Uri): List<File> {
    val cursor = context.contentResolver.query(uri,
            arrayOf(MediaStore.MediaColumns.DATA,
                    MediaStore.MediaColumns.DATE_ADDED,
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    MediaStore.MediaColumns.MIME_TYPE),
            null, null, null)
    
    val results = mutableListOf<File>()
    
    while (cursor.moveToNext()) {
        results.add(File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))))
    }
    
    cursor.close()
    
    return results
}