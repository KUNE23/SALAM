package com.example.laptoploan.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class UserRole { SISWA, ADMIN, SARPRAS }
enum class LaptopStatus { AVAILABLE, BORROWED }
enum class BorrowingStatus { BORROWED, RETURNED }

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val emailOrPhone: String,
    val password: String,
    val role: UserRole,
    val nisn: String? = null,
    val className: String? = null
)

@Entity(tableName = "laptops")
data class Laptop(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val code: String,
    val status: LaptopStatus = LaptopStatus.AVAILABLE,
    val borrowedByUserId: Long? = null
)

@Entity(tableName = "borrowings")
data class Borrowing(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val laptopId: Long,
    val borrowedAt: Long,
    val returnDeadline: Long,
    val returnedAt: Long? = null,
    val status: BorrowingStatus = BorrowingStatus.BORROWED
)

@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long? = null,
    val title: String,
    val message: String,
    val time: Long,
    val isRead: Boolean = false
)
