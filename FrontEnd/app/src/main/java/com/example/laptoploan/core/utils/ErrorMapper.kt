package com.example.laptoploan.core.utils

import retrofit2.Response
import java.io.IOException

object ErrorMapper {
    fun fromCode(code: Int, fallback: String?): String {
        return when (code) {
            400, 422 -> fallback ?: "Validasi gagal"
            401 -> "Session berakhir, silakan login kembali"
            403 -> "Akses ditolak"
            404 -> "Data tidak ditemukan"
            409 -> fallback ?: "Data sedang konflik"
            in 500..599 -> "Server sedang bermasalah"
            else -> fallback ?: "Terjadi kesalahan"
        }
    }

    fun fromThrowable(error: Throwable): String {
        return if (error is IOException) "Tidak dapat terhubung ke server" else error.message ?: "Terjadi kesalahan"
    }
}

fun <T> Response<T>.safeMessage(): String? = body()?.let {
    val messageField = it::class.java.declaredFields.firstOrNull { field -> field.name == "message" }
    messageField?.isAccessible = true
    messageField?.get(it) as? String
}
