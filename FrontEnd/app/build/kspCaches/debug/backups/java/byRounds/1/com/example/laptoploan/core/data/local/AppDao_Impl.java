package com.example.laptoploan.core.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.laptoploan.core.data.model.Borrowing;
import com.example.laptoploan.core.data.model.BorrowingStatus;
import com.example.laptoploan.core.data.model.Laptop;
import com.example.laptoploan.core.data.model.LaptopStatus;
import com.example.laptoploan.core.data.model.Notification;
import com.example.laptoploan.core.data.model.User;
import com.example.laptoploan.core.data.model.UserRole;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final AppConverters __appConverters = new AppConverters();

  private final EntityInsertionAdapter<Laptop> __insertionAdapterOfLaptop;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser_1;

  private final EntityInsertionAdapter<Laptop> __insertionAdapterOfLaptop_1;

  private final EntityInsertionAdapter<Borrowing> __insertionAdapterOfBorrowing;

  private final EntityInsertionAdapter<Notification> __insertionAdapterOfNotification;

  private final EntityDeletionOrUpdateAdapter<Laptop> __deletionAdapterOfLaptop;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<Laptop> __updateAdapterOfLaptop;

  private final EntityDeletionOrUpdateAdapter<Borrowing> __updateAdapterOfBorrowing;

  public AppDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`name`,`emailOrPhone`,`password`,`role`,`nisn`,`className`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getEmailOrPhone());
        statement.bindString(4, entity.getPassword());
        final String _tmp = __appConverters.fromRole(entity.getRole());
        statement.bindString(5, _tmp);
        if (entity.getNisn() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNisn());
        }
        if (entity.getClassName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getClassName());
        }
      }
    };
    this.__insertionAdapterOfLaptop = new EntityInsertionAdapter<Laptop>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `laptops` (`id`,`name`,`code`,`status`,`borrowedByUserId`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Laptop entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getCode());
        final String _tmp = __appConverters.fromLaptopStatus(entity.getStatus());
        statement.bindString(4, _tmp);
        if (entity.getBorrowedByUserId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getBorrowedByUserId());
        }
      }
    };
    this.__insertionAdapterOfUser_1 = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `users` (`id`,`name`,`emailOrPhone`,`password`,`role`,`nisn`,`className`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getEmailOrPhone());
        statement.bindString(4, entity.getPassword());
        final String _tmp = __appConverters.fromRole(entity.getRole());
        statement.bindString(5, _tmp);
        if (entity.getNisn() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNisn());
        }
        if (entity.getClassName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getClassName());
        }
      }
    };
    this.__insertionAdapterOfLaptop_1 = new EntityInsertionAdapter<Laptop>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `laptops` (`id`,`name`,`code`,`status`,`borrowedByUserId`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Laptop entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getCode());
        final String _tmp = __appConverters.fromLaptopStatus(entity.getStatus());
        statement.bindString(4, _tmp);
        if (entity.getBorrowedByUserId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getBorrowedByUserId());
        }
      }
    };
    this.__insertionAdapterOfBorrowing = new EntityInsertionAdapter<Borrowing>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `borrowings` (`id`,`userId`,`laptopId`,`borrowedAt`,`returnDeadline`,`returnedAt`,`status`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Borrowing entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getLaptopId());
        statement.bindLong(4, entity.getBorrowedAt());
        statement.bindLong(5, entity.getReturnDeadline());
        if (entity.getReturnedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getReturnedAt());
        }
        final String _tmp = __appConverters.fromBorrowingStatus(entity.getStatus());
        statement.bindString(7, _tmp);
      }
    };
    this.__insertionAdapterOfNotification = new EntityInsertionAdapter<Notification>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `notifications` (`id`,`userId`,`title`,`message`,`time`,`isRead`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Notification entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getUserId());
        }
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getMessage());
        statement.bindLong(5, entity.getTime());
        final int _tmp = entity.isRead() ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__deletionAdapterOfLaptop = new EntityDeletionOrUpdateAdapter<Laptop>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `laptops` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Laptop entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`name` = ?,`emailOrPhone` = ?,`password` = ?,`role` = ?,`nisn` = ?,`className` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getEmailOrPhone());
        statement.bindString(4, entity.getPassword());
        final String _tmp = __appConverters.fromRole(entity.getRole());
        statement.bindString(5, _tmp);
        if (entity.getNisn() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNisn());
        }
        if (entity.getClassName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getClassName());
        }
        statement.bindLong(8, entity.getId());
      }
    };
    this.__updateAdapterOfLaptop = new EntityDeletionOrUpdateAdapter<Laptop>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `laptops` SET `id` = ?,`name` = ?,`code` = ?,`status` = ?,`borrowedByUserId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Laptop entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getCode());
        final String _tmp = __appConverters.fromLaptopStatus(entity.getStatus());
        statement.bindString(4, _tmp);
        if (entity.getBorrowedByUserId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getBorrowedByUserId());
        }
        statement.bindLong(6, entity.getId());
      }
    };
    this.__updateAdapterOfBorrowing = new EntityDeletionOrUpdateAdapter<Borrowing>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `borrowings` SET `id` = ?,`userId` = ?,`laptopId` = ?,`borrowedAt` = ?,`returnDeadline` = ?,`returnedAt` = ?,`status` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Borrowing entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getLaptopId());
        statement.bindLong(4, entity.getBorrowedAt());
        statement.bindLong(5, entity.getReturnDeadline());
        if (entity.getReturnedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getReturnedAt());
        }
        final String _tmp = __appConverters.fromBorrowingStatus(entity.getStatus());
        statement.bindString(7, _tmp);
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insertUsers(final List<User> users, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(users);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertLaptops(final List<Laptop> laptops,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLaptop.insert(laptops);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfUser_1.insertAndReturnId(user);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertLaptop(final Laptop laptop, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfLaptop_1.insertAndReturnId(laptop);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertBorrowing(final Borrowing borrowing,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBorrowing.insertAndReturnId(borrowing);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertNotification(final Notification notification,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfNotification.insertAndReturnId(notification);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLaptop(final Laptop laptop, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfLaptop.handle(laptop);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLaptop(final Laptop laptop, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLaptop.handle(laptop);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBorrowing(final Borrowing borrowing,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBorrowing.handle(borrowing);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object borrowLaptop(final long userId, final Laptop laptop, final long now,
      final long deadline, final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> AppDao.DefaultImpls.borrowLaptop(AppDao_Impl.this, userId, laptop, now, deadline, __cont), $completion);
  }

  @Override
  public Object returnBorrowing(final Borrowing borrowing,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> AppDao.DefaultImpls.returnBorrowing(AppDao_Impl.this, borrowing, __cont), $completion);
  }

  @Override
  public Object laptopCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM laptops";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<User> observeUser(final long id) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmailOrPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "emailOrPhone");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfNisn = CursorUtil.getColumnIndexOrThrow(_cursor, "nisn");
          final int _cursorIndexOfClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "className");
          final User _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpEmailOrPhone;
            _tmpEmailOrPhone = _cursor.getString(_cursorIndexOfEmailOrPhone);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final UserRole _tmpRole;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfRole);
            _tmpRole = __appConverters.toRole(_tmp);
            final String _tmpNisn;
            if (_cursor.isNull(_cursorIndexOfNisn)) {
              _tmpNisn = null;
            } else {
              _tmpNisn = _cursor.getString(_cursorIndexOfNisn);
            }
            final String _tmpClassName;
            if (_cursor.isNull(_cursorIndexOfClassName)) {
              _tmpClassName = null;
            } else {
              _tmpClassName = _cursor.getString(_cursorIndexOfClassName);
            }
            _result = new User(_tmpId,_tmpName,_tmpEmailOrPhone,_tmpPassword,_tmpRole,_tmpNisn,_tmpClassName);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object login(final String emailOrPhone, final String password, final UserRole role,
      final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE emailOrPhone = ? AND password = ? AND role = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, emailOrPhone);
    _argIndex = 2;
    _statement.bindString(_argIndex, password);
    _argIndex = 3;
    final String _tmp = __appConverters.fromRole(role);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmailOrPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "emailOrPhone");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfNisn = CursorUtil.getColumnIndexOrThrow(_cursor, "nisn");
          final int _cursorIndexOfClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "className");
          final User _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpEmailOrPhone;
            _tmpEmailOrPhone = _cursor.getString(_cursorIndexOfEmailOrPhone);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final UserRole _tmpRole;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRole);
            _tmpRole = __appConverters.toRole(_tmp_1);
            final String _tmpNisn;
            if (_cursor.isNull(_cursorIndexOfNisn)) {
              _tmpNisn = null;
            } else {
              _tmpNisn = _cursor.getString(_cursorIndexOfNisn);
            }
            final String _tmpClassName;
            if (_cursor.isNull(_cursorIndexOfClassName)) {
              _tmpClassName = null;
            } else {
              _tmpClassName = _cursor.getString(_cursorIndexOfClassName);
            }
            _result = new User(_tmpId,_tmpName,_tmpEmailOrPhone,_tmpPassword,_tmpRole,_tmpNisn,_tmpClassName);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<User>> observeUsersByRole(final UserRole role) {
    final String _sql = "SELECT * FROM users WHERE role = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __appConverters.fromRole(role);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<List<User>>() {
      @Override
      @NonNull
      public List<User> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmailOrPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "emailOrPhone");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfNisn = CursorUtil.getColumnIndexOrThrow(_cursor, "nisn");
          final int _cursorIndexOfClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "className");
          final List<User> _result = new ArrayList<User>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final User _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpEmailOrPhone;
            _tmpEmailOrPhone = _cursor.getString(_cursorIndexOfEmailOrPhone);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final UserRole _tmpRole;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRole);
            _tmpRole = __appConverters.toRole(_tmp_1);
            final String _tmpNisn;
            if (_cursor.isNull(_cursorIndexOfNisn)) {
              _tmpNisn = null;
            } else {
              _tmpNisn = _cursor.getString(_cursorIndexOfNisn);
            }
            final String _tmpClassName;
            if (_cursor.isNull(_cursorIndexOfClassName)) {
              _tmpClassName = null;
            } else {
              _tmpClassName = _cursor.getString(_cursorIndexOfClassName);
            }
            _item = new User(_tmpId,_tmpName,_tmpEmailOrPhone,_tmpPassword,_tmpRole,_tmpNisn,_tmpClassName);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Laptop>> observeLaptops() {
    final String _sql = "SELECT * FROM laptops ORDER BY code";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"laptops"}, new Callable<List<Laptop>>() {
      @Override
      @NonNull
      public List<Laptop> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfBorrowedByUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowedByUserId");
          final List<Laptop> _result = new ArrayList<Laptop>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Laptop _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCode;
            _tmpCode = _cursor.getString(_cursorIndexOfCode);
            final LaptopStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __appConverters.toLaptopStatus(_tmp);
            final Long _tmpBorrowedByUserId;
            if (_cursor.isNull(_cursorIndexOfBorrowedByUserId)) {
              _tmpBorrowedByUserId = null;
            } else {
              _tmpBorrowedByUserId = _cursor.getLong(_cursorIndexOfBorrowedByUserId);
            }
            _item = new Laptop(_tmpId,_tmpName,_tmpCode,_tmpStatus,_tmpBorrowedByUserId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Laptop>> observeLaptopsByStatus(final LaptopStatus status) {
    final String _sql = "SELECT * FROM laptops WHERE status = ? ORDER BY code";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __appConverters.fromLaptopStatus(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"laptops"}, new Callable<List<Laptop>>() {
      @Override
      @NonNull
      public List<Laptop> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfBorrowedByUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowedByUserId");
          final List<Laptop> _result = new ArrayList<Laptop>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Laptop _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCode;
            _tmpCode = _cursor.getString(_cursorIndexOfCode);
            final LaptopStatus _tmpStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __appConverters.toLaptopStatus(_tmp_1);
            final Long _tmpBorrowedByUserId;
            if (_cursor.isNull(_cursorIndexOfBorrowedByUserId)) {
              _tmpBorrowedByUserId = null;
            } else {
              _tmpBorrowedByUserId = _cursor.getLong(_cursorIndexOfBorrowedByUserId);
            }
            _item = new Laptop(_tmpId,_tmpName,_tmpCode,_tmpStatus,_tmpBorrowedByUserId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object findLaptopByCode(final String code,
      final Continuation<? super Laptop> $completion) {
    final String _sql = "SELECT * FROM laptops WHERE UPPER(code) = UPPER(?) LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, code);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Laptop>() {
      @Override
      @Nullable
      public Laptop call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfBorrowedByUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowedByUserId");
          final Laptop _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCode;
            _tmpCode = _cursor.getString(_cursorIndexOfCode);
            final LaptopStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __appConverters.toLaptopStatus(_tmp);
            final Long _tmpBorrowedByUserId;
            if (_cursor.isNull(_cursorIndexOfBorrowedByUserId)) {
              _tmpBorrowedByUserId = null;
            } else {
              _tmpBorrowedByUserId = _cursor.getLong(_cursorIndexOfBorrowedByUserId);
            }
            _result = new Laptop(_tmpId,_tmpName,_tmpCode,_tmpStatus,_tmpBorrowedByUserId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Borrowing> observeActiveBorrowing(final long userId, final BorrowingStatus status) {
    final String _sql = "SELECT * FROM borrowings WHERE userId = ? AND status = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    final String _tmp = __appConverters.fromBorrowingStatus(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"borrowings"}, new Callable<Borrowing>() {
      @Override
      @Nullable
      public Borrowing call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfLaptopId = CursorUtil.getColumnIndexOrThrow(_cursor, "laptopId");
          final int _cursorIndexOfBorrowedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowedAt");
          final int _cursorIndexOfReturnDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "returnDeadline");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final Borrowing _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final long _tmpLaptopId;
            _tmpLaptopId = _cursor.getLong(_cursorIndexOfLaptopId);
            final long _tmpBorrowedAt;
            _tmpBorrowedAt = _cursor.getLong(_cursorIndexOfBorrowedAt);
            final long _tmpReturnDeadline;
            _tmpReturnDeadline = _cursor.getLong(_cursorIndexOfReturnDeadline);
            final Long _tmpReturnedAt;
            if (_cursor.isNull(_cursorIndexOfReturnedAt)) {
              _tmpReturnedAt = null;
            } else {
              _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            }
            final BorrowingStatus _tmpStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __appConverters.toBorrowingStatus(_tmp_1);
            _result = new Borrowing(_tmpId,_tmpUserId,_tmpLaptopId,_tmpBorrowedAt,_tmpReturnDeadline,_tmpReturnedAt,_tmpStatus);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Borrowing>> observeBorrowingsByStatus(final BorrowingStatus status) {
    final String _sql = "SELECT * FROM borrowings WHERE status = ? ORDER BY borrowedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __appConverters.fromBorrowingStatus(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"borrowings"}, new Callable<List<Borrowing>>() {
      @Override
      @NonNull
      public List<Borrowing> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfLaptopId = CursorUtil.getColumnIndexOrThrow(_cursor, "laptopId");
          final int _cursorIndexOfBorrowedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowedAt");
          final int _cursorIndexOfReturnDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "returnDeadline");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Borrowing> _result = new ArrayList<Borrowing>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Borrowing _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final long _tmpLaptopId;
            _tmpLaptopId = _cursor.getLong(_cursorIndexOfLaptopId);
            final long _tmpBorrowedAt;
            _tmpBorrowedAt = _cursor.getLong(_cursorIndexOfBorrowedAt);
            final long _tmpReturnDeadline;
            _tmpReturnDeadline = _cursor.getLong(_cursorIndexOfReturnDeadline);
            final Long _tmpReturnedAt;
            if (_cursor.isNull(_cursorIndexOfReturnedAt)) {
              _tmpReturnedAt = null;
            } else {
              _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            }
            final BorrowingStatus _tmpStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __appConverters.toBorrowingStatus(_tmp_1);
            _item = new Borrowing(_tmpId,_tmpUserId,_tmpLaptopId,_tmpBorrowedAt,_tmpReturnDeadline,_tmpReturnedAt,_tmpStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getBorrowing(final long id, final Continuation<? super Borrowing> $completion) {
    final String _sql = "SELECT * FROM borrowings WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Borrowing>() {
      @Override
      @Nullable
      public Borrowing call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfLaptopId = CursorUtil.getColumnIndexOrThrow(_cursor, "laptopId");
          final int _cursorIndexOfBorrowedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowedAt");
          final int _cursorIndexOfReturnDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "returnDeadline");
          final int _cursorIndexOfReturnedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "returnedAt");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final Borrowing _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final long _tmpLaptopId;
            _tmpLaptopId = _cursor.getLong(_cursorIndexOfLaptopId);
            final long _tmpBorrowedAt;
            _tmpBorrowedAt = _cursor.getLong(_cursorIndexOfBorrowedAt);
            final long _tmpReturnDeadline;
            _tmpReturnDeadline = _cursor.getLong(_cursorIndexOfReturnDeadline);
            final Long _tmpReturnedAt;
            if (_cursor.isNull(_cursorIndexOfReturnedAt)) {
              _tmpReturnedAt = null;
            } else {
              _tmpReturnedAt = _cursor.getLong(_cursorIndexOfReturnedAt);
            }
            final BorrowingStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __appConverters.toBorrowingStatus(_tmp);
            _result = new Borrowing(_tmpId,_tmpUserId,_tmpLaptopId,_tmpBorrowedAt,_tmpReturnDeadline,_tmpReturnedAt,_tmpStatus);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Notification>> observeNotifications(final long userId) {
    final String _sql = "SELECT * FROM notifications WHERE userId IS NULL OR userId = ? ORDER BY time DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notifications"}, new Callable<List<Notification>>() {
      @Override
      @NonNull
      public List<Notification> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final List<Notification> _result = new ArrayList<Notification>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Notification _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            }
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            final long _tmpTime;
            _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            final boolean _tmpIsRead;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp != 0;
            _item = new Notification(_tmpId,_tmpUserId,_tmpTitle,_tmpMessage,_tmpTime,_tmpIsRead);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLaptop(final long id, final Continuation<? super Laptop> $completion) {
    final String _sql = "SELECT * FROM laptops WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Laptop>() {
      @Override
      @Nullable
      public Laptop call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfBorrowedByUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "borrowedByUserId");
          final Laptop _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCode;
            _tmpCode = _cursor.getString(_cursorIndexOfCode);
            final LaptopStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __appConverters.toLaptopStatus(_tmp);
            final Long _tmpBorrowedByUserId;
            if (_cursor.isNull(_cursorIndexOfBorrowedByUserId)) {
              _tmpBorrowedByUserId = null;
            } else {
              _tmpBorrowedByUserId = _cursor.getLong(_cursorIndexOfBorrowedByUserId);
            }
            _result = new Laptop(_tmpId,_tmpName,_tmpCode,_tmpStatus,_tmpBorrowedByUserId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
