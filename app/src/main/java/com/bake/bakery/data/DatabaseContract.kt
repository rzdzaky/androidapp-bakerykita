package com.bake.bakery.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.bake.bakery.data.DatabaseContract.BakeryColumns.Companion.TABLE_BAKERY
import com.bake.bakery.data.DatabaseContract.CartColumns.Companion.TABLE_CART
import com.bake.bakery.data.DatabaseContract.UserColumns.Companion.TABLE_USER

internal class DatabaseContract {
    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_USER = "tb_user"
            const val USERNAME = "username"
            const val PASSWORD = "password"
            const val IS_ADMIN = "is_admin"
        }
    }

    internal class BakeryColumns : BaseColumns {
        companion object {
            const val TABLE_BAKERY = "tb_bakery"
            const val ID_ = "id_"
            const val NAME = "name"
            const val IMAGE = "image"
            const val PRICE = "price"
        }
    }
    
    internal class CartColumns : BaseColumns {
        companion object {
            const val TABLE_CART = "tb_cart"
            const val ID_ = "id_"
            const val NAME = "name"
            const val IMAGE = "image"
            const val PRICE = "price"
            const val COUNT = "count"
        }
    }

    internal class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            private const val DATABASE_NAME = "db_bakery"
            private const val DATABASE_VERSION = 1
            private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_USER" +
                    " (${UserColumns.USERNAME} TEXT PRIMARY KEY," +
                    " ${UserColumns.PASSWORD} TEXT NOT NULL," +
                    " ${UserColumns.IS_ADMIN} BOOLEAN NOT NULL);"
            private const val SQL_CREATE_TABLE_BAKERY = "CREATE TABLE $TABLE_BAKERY" +
                    " (${BakeryColumns.ID_} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${BakeryColumns.NAME} TEXT NOT NULL," +
                    " ${BakeryColumns.IMAGE} TEXT NOT NULL," +
                    " ${BakeryColumns.PRICE} INTEGER NOT NULL);"
            private const val SQL_CREATE_TABLE_CART = "CREATE TABLE $TABLE_CART" +
                    " (${CartColumns.ID_} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${CartColumns.NAME} TEXT NOT NULL," +
                    " ${CartColumns.IMAGE} TEXT NOT NULL," +
                    " ${CartColumns.PRICE} INTEGER NOT NULL," +
                    " ${CartColumns.COUNT} INTEGER NOT NULL);"
        }

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_TABLE_USER)
            db.execSQL(SQL_CREATE_TABLE_BAKERY)
            db.execSQL(SQL_CREATE_TABLE_CART)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USER;")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_BAKERY;")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_CART;")
            onCreate(db)
        }
    }
}