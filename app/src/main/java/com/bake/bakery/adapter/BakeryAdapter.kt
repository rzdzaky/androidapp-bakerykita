package com.bake.bakery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bake.bakery.R
import com.bake.bakery.databinding.ItemBakeryBinding
import com.bake.bakery.model.Bakery

class BakeryAdapter(private val clickListener: ClickItemListener) :
    RecyclerView.Adapter<BakeryAdapter.BakeryViewHolder>() {

    private val listBakery = mutableListOf<Bakery>()

    interface ClickItemListener {
        fun onClickCard(bakery: Bakery)
        fun onClickCart(bakery: Bakery)
    }

    fun setData(list: List<Bakery>) {
        listBakery.clear()
        listBakery.addAll(list.sortedBy { it.price })
        notifyDataSetChanged()
    }

    fun updateData(bakery: Bakery) {
        var position = 0
        for ((i, b) in listBakery.withIndex()) {
            if (b.id == bakery.id) {
                position = i
                break
            }
        }
        listBakery.removeAt(position)
        listBakery.add(position, bakery)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BakeryViewHolder {
        val view = ItemBakeryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BakeryViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: BakeryViewHolder, position: Int) {
        holder.bind(listBakery[position])
    }

    override fun getItemCount(): Int = listBakery.size

    class BakeryViewHolder(
        private val binding: ItemBakeryBinding,
        private val clickListener: ClickItemListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bakery: Bakery) {
            binding.apply {
                tvName.text = bakery.name
                tvPrice.text = "@ Rp." + bakery.price
                tvCount.text = bakery.countInCart.toString()
                when (bakery.image) {
                    "r1" -> imgBakery.setImageResource(R.drawable.r1)
                    "r2" -> imgBakery.setImageResource(R.drawable.r2)
                    "r3" -> imgBakery.setImageResource(R.drawable.r3)
                    "r4" -> imgBakery.setImageResource(R.drawable.r4)
                    "r5" -> imgBakery.setImageResource(R.drawable.r5)
                    "r6" -> imgBakery.setImageResource(R.drawable.r6)
                }
                card.setOnClickListener { clickListener.onClickCard(bakery) }
                btnAddCart.setOnClickListener { clickListener.onClickCart(bakery) }
            }
        }
    }
}