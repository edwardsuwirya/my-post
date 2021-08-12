package com.enigmacamp.mysimpleretorfit.util

import android.content.Context
import android.os.Environment
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.util.*

object SaveToStorage {
    fun save(context: Context, responseBody: ResponseBody?) {
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "${UUID.randomUUID()}.jpg"
        )
        responseBody?.let {
            val inputStream = it.byteStream()
            val buffer = ByteArray(8192)
            var length: Int
            val outputStream = FileOutputStream(file)
            while ((inputStream.read(buffer, 0, 8192).also { length = it }) > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        }

    }
}