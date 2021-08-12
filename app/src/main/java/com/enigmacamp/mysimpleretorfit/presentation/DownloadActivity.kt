package com.enigmacamp.mysimpleretorfit.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.enigmacamp.mysimpleretorfit.R
import com.enigmacamp.mysimpleretorfit.data.remote.UnsplashRetrofitInstance
import com.enigmacamp.mysimpleretorfit.databinding.ActivityDownloadBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*

class DownloadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            dowloadButton.setOnClickListener {
                downloadImage()
            }
        }
    }

    private fun downloadImage() {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                UnsplashRetrofitInstance.unsplashApi.getImage("/photo-1461988320302-91bde64fc8e4?ixid=2yJhcHBfaWQiOjEyMDd9&fm=jpg&fit=crop&w=1080&q=80&fit=max")
            Log.d("Download", "downloadImage: ")
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val file = File(
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "${UUID.randomUUID()}.jpg"
                    )

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
            } else {
                Log.e("Download", "downloadImage: Error")
            }
        }
    }
}