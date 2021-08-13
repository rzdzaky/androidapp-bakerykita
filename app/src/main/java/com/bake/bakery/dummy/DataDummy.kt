package com.bake.bakery.dummy

import android.content.Context
import com.bake.bakery.data.BakeryHelper
import com.bake.bakery.data.UserHelper
import com.bake.bakery.model.Bakery
import com.bake.bakery.model.User

object DataDummy {
    fun generateDummyUser(context: Context) {
        val userDbHelper = UserHelper.getInstance(context)
        userDbHelper.open()
        userDbHelper.insert(User("bliga", "123", true))
        userDbHelper.insert(User("dzaki", "123"))
        userDbHelper.insert(User("nathan", "123"))
        userDbHelper.insert(User("salma", "123"))
        userDbHelper.insert(User("fitrah", "123"))
        userDbHelper.close()
    }

    fun generateDummyBakery(context: Context) {
        val bakeryHelper = BakeryHelper.getInstance(context)
        bakeryHelper.open()
        bakeryHelper.insert(Bakery(1, "Chocolate Pastry", "r1", 16000))
        bakeryHelper.insert(Bakery(2, "Sultana Pastry", "r2", 15000))
        bakeryHelper.insert(Bakery(3, "Tuna Pastry", "r3", 20000))
        bakeryHelper.insert(Bakery(4, "Beef Pastry", "r4", 25000))
        bakeryHelper.insert(Bakery(5, "Almond Turn Over", "r5", 28000))
        bakeryHelper.insert(Bakery(6, "Cheese Pastry", "r6", 36000))
        bakeryHelper.close()
    }
}