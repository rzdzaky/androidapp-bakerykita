package com.bake.bakery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bake.bakery.databinding.ItemBakeryNotaBinding
import com.bake.bakery.model.Bakery

class BakeryNotaAdapter : RecyclerView.Adapter<BakeryNotaAdapter.BakeryViewHolder>() {

    private val listBakery = mutableListOf<Bakery>()

    fun setData(list: List<Bakery>) {
        listBakery.clear()
        listBakery.addAll(list)
        notifyDataSetChanged()
    }

    fun getSumPrice(): Int {
        val listSum = mutableListOf<Int>()
        for (bakery in listBakery) {
            listSum.add(bakery.price * bakery.countInCart)
        }
        return listSum.sum()
    }

    fun getCountInCart(): Int = listBakery.map { it.countInCart }.sum()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BakeryViewHolder {
        val view = ItemBakeryNotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BakeryViewHolder(view)
    }

    override fun onBindViewHolder(holder: BakeryViewHolder, position: Int) {
        holder.bind(listBakery[position])
    }

    override fun getItemCount(): Int = listBakery.size

    class BakeryViewHolder(
        private val binding: ItemBakeryNotaBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bakery: Bakery) {
            binding.apply {
                tvName.text = bakery.name
                tvPriceInfo.text = "${bakery.countInCart} x Rp.${bakery.price}"
                tvSum.text = "Rp.${bakery.countInCart * bakery.price}"
            }
        }
    }
}