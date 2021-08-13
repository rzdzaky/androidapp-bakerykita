package com.bake.bakery.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bake.bakery.adapter.BakeryNotaAdapter
import com.bake.bakery.data.CartHelper
import com.bake.bakery.databinding.ActivityNotaBinding

class NotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotaBinding
    private lateinit var bakeryAdapter: BakeryNotaAdapter
    private lateinit var cartHelper: CartHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bakeryAdapter = BakeryNotaAdapter()
        cartHelper = CartHelper.getInstance(this)

        binding.rvBakery.adapter = bakeryAdapter

        setToolbar()
        setData()

    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setData() {
        cartHelper.open()
        bakeryAdapter.setData(cartHelper.queryAll())
        cartHelper.close()

        binding.tvCount.text = bakeryAdapter.getCountInCart().toString()
        binding.tvSumPrice.text = "Rp.${bakeryAdapter.getSumPrice()}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}