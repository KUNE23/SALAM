package com.example.laptoploan.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.laptoploan.core.data.model.Borrowing
import com.example.laptoploan.core.data.model.BorrowingStatus
import com.example.laptoploan.core.data.model.Laptop
import com.example.laptoploan.core.data.model.LaptopStatus
import com.example.laptoploan.core.data.model.Notification
import com.example.laptoploan.core.data.model.User
import com.example.laptoploan.core.data.model.UserRole
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT COUNT(*) FROM laptops")
    suspend fun laptopCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaptops(laptops: List<Laptop>)

    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    fun observeUser(id: Long): Flow<User?>

    @Query("SELECT * FROM users WHERE emailOrPhone = :emailOrPhone AND password = :password AND role = :role LIMIT 1")
    suspend fun login(emailOrPhone: String, password: String, role: UserRole): User?

    @Query("SELECT * FROM users WHERE role = :role")
    fun observeUsersByRole(role: UserRole): Flow<List<User>>

    @Query("SELECT * FROM laptops ORDER BY code")
    fun observeLaptops(): Flow<List<Laptop>>

    @Query("SELECT * FROM laptops WHERE status = :status ORDER BY code")
    fun observeLaptopsByStatus(status: LaptopStatus): Flow<List<Laptop>>

    @Query("SELECT * FROM laptops WHERE UPPER(code) = UPPER(:code) LIMIT 1")
    suspend fun findLaptopByCode(code: String): Laptop?

    @Insert
    suspend fun insertLaptop(laptop: Laptop): Long

    @Update
    suspend fun updateLaptop(laptop: Laptop)

    @Delete
    suspend fun deleteLaptop(laptop: Laptop)

    @Insert
    suspend fun insertBorrowing(borrowing: Borrowing): Long

    @Update
    suspend fun updateBorrowing(borrowing: Borrowing)

    @Query("SELECT * FROM borrowings WHERE userId = :userId AND status = :status LIMIT 1")
    fun observeActiveBorrowing(userId: Long, status: BorrowingStatus = BorrowingStatus.BORROWED): Flow<Borrowing?>

    @Query("SELECT * FROM borrowings WHERE status = :status ORDER BY borrowedAt DESC")
    fun observeBorrowingsByStatus(status: BorrowingStatus = BorrowingStatus.BORROWED): Flow<List<Borrowing>>

    @Query("SELECT * FROM borrowings WHERE id = :id")
    suspend fun getBorrowing(id: Long): Borrowing?

    @Insert
    suspend fun insertNotification(notification: Notification): Long

    @Query("SELECT * FROM notifications WHERE userId IS NULL OR userId = :userId ORDER BY time DESC")
    fun observeNotifications(userId: Long): Flow<List<Notification>>

    @Transaction
    suspend fun borrowLaptop(userId: Long, laptop: Laptop, now: Long, deadline: Long) {
        updateLaptop(laptop.copy(status = LaptopStatus.BORROWED, borrowedByUserId = userId))
        insertBorrowing(Borrowing(userId = userId, laptopId = laptop.id, borrowedAt = now, returnDeadline = deadline))
        insertNotification(
            Notification(
                userId = userId,
                title = "Pengingat pengembalian",
                message = "Segera kumpulkan laptop atau kamu akan dikenakan sanksi!",
                time = now
            )
        )
    }

    @Transaction
    suspend fun returnBorrowing(borrowing: Borrowing) {
        val laptop = getLaptop(borrowing.laptopId) ?: return
        updateBorrowing(borrowing.copy(status = BorrowingStatus.RETURNED, returnedAt = System.currentTimeMillis()))
        updateLaptop(laptop.copy(status = LaptopStatus.AVAILABLE, borrowedByUserId = null))
    }

    @Query("SELECT * FROM laptops WHERE id = :id")
    suspend fun getLaptop(id: Long): Laptop?
}
