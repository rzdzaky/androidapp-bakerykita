package com.bake.bakery.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.bake.bakery.data.DatabaseContract.UserColumns.Companion.IS_ADMIN
import com.bake.bakery.data.DatabaseContract.UserColumns.Companion.PASSWORD
import com.bake.bakery.data.DatabaseContract.UserColumns.Companion.TABLE_USER
import com.bake.bakery.data.DatabaseContract.UserColumns.Companion.USERNAME
import com.bake.bakery.model.User
import java.sql.SQLException

@Suppress("Unused")
class UserHelper(context: Context) {

    private var dataBaseHelper: DatabaseContract.DatabaseHelper =
        DatabaseContract.DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_USER
        private var INSTANCE: UserHelper? = null

        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryByUsername(username: String): User? {
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            "$USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null,
            null
        )
        if (cursor.count <= 0) return null
        cursor.moveToFirst()
        val user = User(
            cursor.getString(cursor.getColumnIndex(USERNAME)),
            cursor.getString(cursor.getColumnIndex(PASSWORD)),
            cursor.getInt(cursor.getColumnIndex(IS_ADMIN)) > 0
        )
        cursor.close()
        return user
    }

    fun insert(user: User): Long {
        val values = ContentValues()
        values.put(USERNAME, user.username)
        values.put(PASSWORD, user.password)
        values.put(IS_ADMIN, user.isAdmin)
        return database.insert(DATABASE_TABLE, null, values)
    }
}