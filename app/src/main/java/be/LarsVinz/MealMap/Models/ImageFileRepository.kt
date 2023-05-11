package be.LarsVinz.MealMap.Models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileNotFoundException

class ImageFileRepository(val context: Context) :  ImageRepository {

    override fun saveImage(image: Bitmap, fileName : String) {

        context.openFileOutput(fileName.lowercase(), Context.MODE_PRIVATE).use {

            image.compress(Bitmap.CompressFormat.JPEG, 80, it)
            it.flush()
        }
    }

    override fun loadImage(fileName: String): Bitmap? {

        try {
            context.openFileInput(fileName.lowercase()).use {

                return BitmapFactory.decodeStream(it)
            }
        }
        catch (ex : FileNotFoundException){
            return null
        }
    }

    override fun deleteRecipeImage(fileName: String) {

        context.deleteFile(fileName.lowercase())
    }
}