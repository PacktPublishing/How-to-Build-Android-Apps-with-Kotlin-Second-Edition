package com.example.myrecyclerviewapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecyclerviewapp.model.CatUiModel

class CatsAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CatViewHolder>() {
    private val catsData = mutableListOf<CatUiModel>()

    fun setData(newCatsData: List<CatUiModel>) {
        catsData.clear()
        catsData.addAll(newCatsData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = layoutInflater.inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view, imageLoader, object : CatViewHolder.OnClickListener {
            override fun onClick(catData: CatUiModel) = onClickListener.onItemClick(catData)
        })
    }

    override fun getItemCount() = catsData.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bindData(catsData[position])
    }

    interface OnClickListener {
        fun onItemClick(catData: CatUiModel)
    }
}
