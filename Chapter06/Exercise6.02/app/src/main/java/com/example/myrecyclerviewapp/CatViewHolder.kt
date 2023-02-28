package com.example.myrecyclerviewapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myrecyclerviewapp.model.CatBreed
import com.example.myrecyclerviewapp.model.CatUiModel
import com.example.myrecyclerviewapp.model.Gender

private const val FEMALE_SYMBOL = "\u2640"
private const val MALE_SYMBOL = "\u2642"
private const val UNKNOWN_SYMBOL = "?"

class CatViewHolder(
    containerView: View,
    private val imageLoader: ImageLoader
) : ViewHolder(containerView) {
    private val catBiographyView: TextView
        by lazy { containerView.findViewById(R.id.item_cat_biography) }
    private val catBreedView: TextView
        by lazy { containerView.findViewById(R.id.item_cat_breed) }
    private val catGenderView: TextView
        by lazy { containerView.findViewById(R.id.item_cat_gender) }
    private val catNameView: TextView
        by lazy { containerView.findViewById(R.id.item_cat_name) }
    private val catPhotoView: ImageView
        by lazy { containerView.findViewById(R.id.item_cat_photo) }

    fun bindData(catData: CatUiModel) {
        imageLoader.loadImage(catData.imageUrl, catPhotoView)
        catNameView.text = catData.name
        catBreedView.text = when (catData.breed) {
            CatBreed.AmericanCurl -> "American Curl"
            CatBreed.BalineseJavanese -> "Balinese-Javanese"
            CatBreed.ExoticShorthair -> "Exotic Shorthair"
        }
        catBiographyView.text = catData.biography
        catGenderView.text = when (catData.gender) {
            Gender.Female -> FEMALE_SYMBOL
            Gender.Male -> MALE_SYMBOL
            else -> UNKNOWN_SYMBOL
        }
    }
}
