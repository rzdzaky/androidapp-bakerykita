package com.bake.bakery.ui

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bake.bakery.R
import com.bake.bakery.adapter.BakeryAdapter
import com.bake.bakery.data.BakeryHelper
import com.bake.bakery.data.CartHelper
import com.bake.bakery.databinding.ActivityMainBinding
import com.bake.bakery.databinding.DialogAddCartBinding
import com.bake.bakery.dummy.DataDummy
import com.bake.bakery.model.Bakery

class MainActivity : AppCompatActivity(), BakeryAdapter.ClickItemListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bakeryHelper: BakeryHelper
    private lateinit var cartHelper: CartHelper
    private lateinit var bakeryAdapter: BakeryAdapter
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bakeryHelper = BakeryHelper.getInstance(this)
        cartHelper = CartHelper.getInstance(this)
        bakeryAdapter = BakeryAdapter(this)
        pref = getSharedPreferences("auth", MODE_PRIVATE)

        binding.rvBakery.adapter = bakeryAdapter

        binding.btnBuy.setOnClickListener {
            if (cartIsEmpty()) {
                Toast.makeText(this, "Keranjang Anda masih kosong.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val i = Intent(this, NotaActivity::class.java)
            startActivity(i)
        }

        setToolbar()
        setMenuList()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setMenuList() {
        DataDummy.generateDummyBakery(this)

        val listCart = mutableListOf<Bakery>()
        val listBakery = mutableListOf<Bakery>()

        cartHelper.open()
        listCart.addAll(cartHelper.queryAll())
        cartHelper.open()

        bakeryHelper.open()
        listBakery.addAll(bakeryHelper.queryAll())
        bakeryHelper.close()

        val listBakeryIds = listBakery.map { it.id }
        for (c in listCart) {
            if (listBakeryIds.contains(c.id)) {
                listBakery.remove(listBakery.first { it.id == c.id })
                listBakery.add(c)
            }
        }

        bakeryAdapter.setData(listBakery)
    }

    private fun addToCart(bakery: Bakery) {
        cartHelper.open()
        if (cartHelper.queryById(bakery.id).isEmpty()) {
            cartHelper.insert(bakery)
        } else {
            if (bakery.countInCart > 0) {
                cartHelper.update(bakery.id, bakery)
            } else {
                cartHelper.deleteById(bakery.id)
            }
        }
        cartHelper.close()
    }

    private fun clearCart() {
        cartHelper.open()
        for (b in cartHelper.queryAll()) {
            cartHelper.deleteById(b.id)
        }
        cartHelper.close()
    }

    private fun cartIsEmpty(): Boolean {
        cartHelper.open()
        val isEmpty = cartHelper.queryAll().isNullOrEmpty()
        cartHelper.close()
        return isEmpty
    }

    private fun logout() {
        val editor = pref.edit()
        editor.putBoolean("logged_in", false)
        editor.apply()
        clearCart()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun dialogAddCart(bakery: Bakery) {
        val bindingDialog = DialogAddCartBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.apply {
            tvInfo.text = "Tambahkan '${bakery.name}' ke keranjang"
            edtQty.setText(bakery.countInCart.toString())
            btnAdd.setOnClickListener {
                bakery.countInCart =
                    if (edtQty.text.isNullOrEmpty()) 0 else edtQty.text.toString().toInt()
                bakeryAdapter.updateData(bakery)
                dialog.dismiss()
                addToCart(bakery)
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickCard(bakery: Bakery) {

    }

    override fun onClickCart(bakery: Bakery) {
        dialogAddCart(bakery)
    }
}