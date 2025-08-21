package com.readboy.flowpractice.utils

import java.io.InputStream
import java.io.OutputStream

inline fun InputStream.copyTo(output: OutputStream, bufferSize: Int = DEFAULT_BUFFER_SIZE, process: (bytesCopied: Long) -> Unit): Long {
    var bytesCopied: Long = 0
    val buffer = ByteArray(bufferSize)
    var bytes = read(buffer)
    while (bytes >= 0) {
        output.write(buffer, 0, bytes)
        bytesCopied += bytes.toLong()
        bytes = read(buffer)

        process(bytesCopied)
    }
    return bytesCopied
}