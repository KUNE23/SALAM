package com.example.laptoploan.data.remote.dto

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String, val role: String = "SISWA")
data class RefreshRequest(val refreshToken: String)
data class UpdateProfileRequest(val name: String? = null, val nisn: String? = null, val className: String? = null, val major: String? = null)
data class CreateLoanRequest(val laptopCode: String)

data class AuthDataDto(
    val accessToken: String,
    val refreshToken: String?,
    val user: UserDto
)

data class TokenDto(
    val accessToken: String,
    val refreshToken: String?
)

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val status: String? = null,
    val nisn: String? = null,
    val className: String? = null,
    val major: String? = null,
    val avatarUrl: String? = null
)

data class LaptopDto(
    val id: String,
    val brand: String,
    val series: String,
    val code: String,
    val ram: String? = null,
    val storage: String? = null,
    val completeness: String? = null,
    val photoUrl: String? = null,
    val status: String
)

data class LoanDto(
    val id: String,
    val student: LoanStudentDto?,
    val laptop: LoanLaptopDto?,
    val status: String,
    val requestDate: String?,
    val startTime: String?,
    val returnTime: String?,
    val duration: String?
)

data class LoanStudentDto(val name: String, val email: String, val initial: String?)
data class LoanLaptopDto(val code: String, val name: String)

data class NotificationDto(
    val id: String,
    val userId: String?,
    val title: String,
    val message: String,
    val type: String,
    val isRead: Boolean,
    val createdAt: String?
)
