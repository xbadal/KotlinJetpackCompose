package com.badal.reciepeapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(

    @SerializedName("idCategory") var idCategory: String? = null,
    @SerializedName("strCategory") var strCategory: String? = null,
    @SerializedName("strCategoryThumb") var strCategoryThumb: String? = null,
    @SerializedName("strCategoryDescription") var strCategoryDescription: String? = null

) : Parcelable


data class CategoryResponseModel(

    @SerializedName("categories") var categories: ArrayList<CategoryModel> = arrayListOf()

)