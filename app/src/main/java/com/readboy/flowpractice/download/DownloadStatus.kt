package com.readboy.flowpractice.download

import java.io.File

sealed class DownloadStatus {
    object None : DownloadStatus()
    data class Progress(val progress: Int) : DownloadStatus()
    data class Done(val file: File) : DownloadStatus()
    data class Error(val error: Throwable) : DownloadStatus()
}