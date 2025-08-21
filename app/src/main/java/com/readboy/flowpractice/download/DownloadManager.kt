package com.readboy.flowpractice.download

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

import com.readboy.flowpractice.utils.copyTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

object DownloadManager {
    fun download(url: String, file: File): Flow<DownloadStatus> {
        return flow {
            val request = Request.Builder()
                .url(url)
                .get()
                .build()
            val response = OkHttpClient().newBuilder().build().newCall(request).execute()
            if (response.isSuccessful){
                response.body()!!.let { body ->
                    val total = body.contentLength()
                    // 文件读写操作
                    file.outputStream().use { output ->
                        val input = body.byteStream()
                        var emitProgress = 0L
                        input.copyTo(output) { bytesCopied ->
                            val progress = bytesCopied * 100 / total
                            if (progress - emitProgress > 5) {
                                delay(100)
                                emit(DownloadStatus.Progress(progress.toInt()))
                                emitProgress = progress
                            }
                        }
                    }
                }
                emit(DownloadStatus.Done(file))
            } else {
                // 下载失败抛出异常,被下面的catch捕获并发射错误信息
                throw IOException("Download failed: $response")
            }
        }.catch {
            file.delete()
            emit(DownloadStatus.Error(it))
        }.flowOn(Dispatchers.IO)
    }
}