package com.example.laptoploan.data.remote.mapper

import com.example.laptoploan.core.data.model.Borrowing
import com.example.laptoploan.core.data.model.BorrowingStatus
import com.example.laptoploan.core.data.model.Laptop
import com.example.laptoploan.core.data.model.LaptopStatus
import com.example.laptoploan.core.data.model.Notification
import com.example.laptoploan.core.data.model.User
import com.example.laptoploan.core.data.model.UserRole
import com.example.laptoploan.data.remote.dto.LaptopDto
import com.example.laptoploan.data.remote.dto.LoanDto
import com.example.laptoploan.data.remote.dto.NotificationDto
import com.example.laptoploan.data.remote.dto.UserDto
import kotlin.math.absoluteValue

fun String.stableLongId(): Long = hashCode().toLong().absoluteValue

fun UserDto.toModel(): User = User(
    id = id.stableLongId(),
    name = name,
    emailOrPhone = email,
    password = "",
    role = when (role) {
        "SUPER_ADMIN", "ADMIN" -> UserRole.ADMIN
        "SARPRAS" -> UserRole.SARPRAS
        else -> UserRole.SISWA
    },
    nisn = nisn,
    className = listOfNotNull(className, major).joinToString(" ").ifBlank { className }
)

fun LaptopDto.toModel(): Laptop = Laptop(
    id = id.stableLongId(),
    name = "$brand $series",
    code = code,
    status = if (status == "BORROWED") LaptopStatus.BORROWED else LaptopStatus.AVAILABLE
)

fun LoanDto.toModel(userId: Long): Borrowing = Borrowing(
    id = id.stableLongId(),
    userId = userId,
    laptopId = (laptop?.code ?: id).stableLongId(),
    borrowedAt = 0L,
    returnDeadline = 0L,
    returnedAt = null,
    status = if (status == "RETURNED") BorrowingStatus.RETURNED else BorrowingStatus.BORROWED
)

fun NotificationDto.toModel(): Notification = Notification(
    id = id.stableLongId(),
    userId = userId?.stableLongId(),
    title = title,
    message = message,
    time = 0L,
    isRead = isRead
)
