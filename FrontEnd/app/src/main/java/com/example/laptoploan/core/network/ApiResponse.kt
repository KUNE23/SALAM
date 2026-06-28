package com.example.laptoploan.core.network

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?,
    val meta: MetaResponse? = null,
    val errors: Any? = null
)

data class MetaResponse(
    val page: Int,
    val limit: Int,
    val total: Int,
    val totalPages: Int
)
