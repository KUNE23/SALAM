package com.example.laptoploan.core.network

import com.example.laptoploan.data.remote.dto.AuthDataDto
import com.example.laptoploan.data.remote.dto.CreateLoanRequest
import com.example.laptoploan.data.remote.dto.LaptopDto
import com.example.laptoploan.data.remote.dto.LoanDto
import com.example.laptoploan.data.remote.dto.LoginRequest
import com.example.laptoploan.data.remote.dto.NotificationDto
import com.example.laptoploan.data.remote.dto.RefreshRequest
import com.example.laptoploan.data.remote.dto.RegisterRequest
import com.example.laptoploan.data.remote.dto.TokenDto
import com.example.laptoploan.data.remote.dto.UpdateProfileRequest
import com.example.laptoploan.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): Response<ApiResponse<AuthDataDto>>

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest): Response<ApiResponse<AuthDataDto>>

    @POST("auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): Response<ApiResponse<TokenDto>>

    @POST("auth/logout")
    suspend fun logout(): Response<ApiResponse<Unit>>

    @GET("auth/me")
    suspend fun me(): Response<ApiResponse<UserDto>>

    @GET("profile")
    suspend fun profile(): Response<ApiResponse<UserDto>>

    @PUT("profile")
    suspend fun updateProfile(@Body body: UpdateProfileRequest): Response<ApiResponse<UserDto>>

    @GET("laptops")
    suspend fun laptops(): Response<ApiResponse<List<LaptopDto>>>

    @GET("laptops/available")
    suspend fun availableLaptops(): Response<ApiResponse<List<LaptopDto>>>

    @GET("laptops/{id}")
    suspend fun laptopDetail(@Path("id") id: String): Response<ApiResponse<LaptopDto>>

    @GET("loans/my")
    suspend fun myLoans(): Response<ApiResponse<List<LoanDto>>>

    @POST("loans")
    suspend fun createLoan(@Body body: CreateLoanRequest): Response<ApiResponse<LoanDto>>

    @PATCH("loans/{id}/return")
    suspend fun returnLoan(@Path("id") id: String): Response<ApiResponse<LoanDto>>

    @GET("notifications")
    suspend fun notifications(): Response<ApiResponse<List<NotificationDto>>>

    @PATCH("notifications/{id}/read")
    suspend fun readNotification(@Path("id") id: String): Response<ApiResponse<NotificationDto>>

    @PATCH("notifications/read-all")
    suspend fun readAllNotifications(): Response<ApiResponse<Unit>>
}
