package be.LarsVinz.MealMap.Models

import android.graphics.Bitmap
import be.LarsVinz.MealMap.Models.DataClasses.Recipe

interface ImageRepository {

    fun saveImage(image : Bitmap, fileName : String)

    fun loadImage(fileName : String) : Bitmap

    fun deleteImageRecipe(fileName: String)
}