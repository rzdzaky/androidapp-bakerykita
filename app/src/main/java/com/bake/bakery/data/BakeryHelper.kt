package com.bake.bakery.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.bake.bakery.data.DatabaseContract.BakeryColumns.Companion.ID_
import com.bake.bakery.data.DatabaseContract.BakeryColumns.Companion.IMAGE
import com.bake.bakery.data.DatabaseContract.BakeryColumns.Companion.NAME
import com.bake.bakery.data.DatabaseContract.BakeryColumns.Companion.PRICE
import com.bake.bakery.data.DatabaseContract.BakeryColumns.Companion.TABLE_BAKERY
import com.bake.bakery.model.Bakery
import java.sql.SQLException

@Suppress("Unused")
class BakeryHelper(context: Context) {

    private var dataBaseHelper: DatabaseContract.DatabaseHelper =
        DatabaseContract.DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_BAKERY
        private var INSTANCE: BakeryHelper? = null

        fun getInstance(context: Context): BakeryHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: BakeryHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen) database.close()
    }

    fun queryAll(): MutableList<Bakery> {
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$PRICE ASC",
            null
        )

        val listBakery = mutableListOf<Bakery>()
        if (cursor.count <= 0) return listBakery
        if (cursor.moveToFirst()) {
            do {
                with(DatabaseContract.BakeryColumns) {
                    val id = cursor.getInt(cursor.getColumnIndex(ID_))
                    val name = cursor.getString(cursor.getColumnIndex(NAME))
                    val image = cursor.getString(cursor.getColumnIndex(IMAGE))
                    val price = cursor.getInt(cursor.getColumnIndex(PRICE))
                    listBakery.add(Bakery(id, name, image, price))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listBakery
    }

    fun queryById(id: Int): MutableList<Bakery> {
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            "$ID_ = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )

        val listBakery = mutableListOf<Bakery>()
        if (cursor.count <= 0) return listBakery
        if (cursor.moveToFirst()) {
            do {
                with(DatabaseContract.BakeryColumns) {
                    val id_ = cursor.getInt(cursor.getColumnIndex(ID_))
                    val name = cursor.getString(cursor.getColumnIndex(NAME))
                    val image = cursor.getString(cursor.getColumnIndex(IMAGE))
                    val price = cursor.getInt(cursor.getColumnIndex(PRICE))
                    listBakery.add(Bakery(id_, name, image, price))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listBakery
    }

    fun insert(bakery: Bakery): Long {
        val values = ContentValues()
        values.put(ID_, bakery.id)
        values.put(NAME, bakery.name)
        values.put(IMAGE, bakery.image)
        values.put(PRICE, bakery.price)
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: Int, bakery: Bakery): Int {
        val values = ContentValues()
        values.put(ID_, bakery.id)
        values.put(NAME, bakery.name)
        values.put(IMAGE, bakery.image)
        values.put(PRICE, bakery.price)
        return database.update(DATABASE_TABLE, values, "$ID_ = ?", arrayOf(id.toString()))
    }

    fun deleteById(id: Int): Int {
        return database.delete(DATABASE_TABLE, "$ID_ = '$id'", null)
    }
}