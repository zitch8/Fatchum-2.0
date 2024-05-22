package com.example.fatchum2

import android.os.Parcel
import android.os.Parcelable

// data input that will be passed to /search API
data class IngredientsInput(
    val ingredients: String
)

// data that will be passed to /recommend API
data class TagsInput(
    val tags: Array<Int>
)

// data that gets fetched from /search API
data class Recipe(
    val image_url: String,
    val recipe_name: String,
    val ingredients: List<String>,
    val protein: String,
    val fat: String,
    val calories: String,
    val sugar: String,
    val carbohydrates: String,
    val fiber: String,
    val instructions: List<String>,
    val tags: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image_url)
        parcel.writeString(recipe_name)
        parcel.writeStringList(ingredients)
        parcel.writeString(protein)
        parcel.writeString(fat)
        parcel.writeString(calories)
        parcel.writeString(sugar)
        parcel.writeString(carbohydrates)
        parcel.writeString(fiber)
        parcel.writeStringList(ingredients)
        parcel.writeString(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}

// data that gets fetched from /search API
data class Recommendation(
    val image_url: String,
    val recipe_name: String,
    val ingredients: List<String>,
    val protein: String,
    val fat: String,
    val calories: String,
    val sugar: String,
    val carbohydrates: String,
    val fiber: String,
    val instructions: String,
    val tags: String
)
