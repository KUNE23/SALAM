package com.example.laptoploan.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.laptoploan.core.data.model.Borrowing
import com.example.laptoploan.core.data.model.BorrowingStatus
import com.example.laptoploan.core.data.model.Laptop
import com.example.laptoploan.core.data.model.LaptopStatus
import com.example.laptoploan.core.data.model.Notification
import com.example.laptoploan.core.data.model.User
import com.example.laptoploan.core.data.model.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppConverters {
    @TypeConverter fun toRole(value: String) = UserRole.valueOf(value)
    @TypeConverter fun fromRole(value: UserRole) = value.name
    @TypeConverter fun toLaptopStatus(value: String) = LaptopStatus.valueOf(value)
    @TypeConverter fun fromLaptopStatus(value: LaptopStatus) = value.name
    @TypeConverter fun toBorrowingStatus(value: String) = BorrowingStatus.valueOf(value)
    @TypeConverter fun fromBorrowingStatus(value: BorrowingStatus) = value.name
}

@Database(
    entities = [User::class, Laptop::class, Borrowing::class, Notification::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AppConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "salam.db")
                    .addCallback(seedCallback)
                    .build()
                    .also { instance = it }
            }
        }

        private val seedCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        database.appDao().seedInitialData()
                    }
                }
            }
        }
    }
}

suspend fun AppDao.seedInitialData() {
    if (laptopCount() > 0) return
    insertLaptops((1..8).map {
        Laptop(name = "Lenovo Thinkpad %02d".format(it), code = "LAPTOP%02d".format(it))
    })
    insertUsers(
        listOf(
            User(name = "Yoga Nugraha", emailOrPhone = "yoganugrahareal@gmail.com", password = "password", role = UserRole.SISWA, nisn = "10224148", className = "12 Desain Komunikasi Visual"),
            User(name = "Zidan Afriza", emailOrPhone = "zidan@example.com", password = "password", role = UserRole.SISWA),
            User(name = "Admin Sekolah", emailOrPhone = "admin@salam.local", password = "password", role = UserRole.ADMIN),
            User(name = "Petugas Sarpras", emailOrPhone = "sarpras@salam.local", password = "password", role = UserRole.SARPRAS)
        )
    )
}
