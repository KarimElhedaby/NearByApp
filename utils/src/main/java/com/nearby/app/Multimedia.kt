package com.nearby.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.nearby.app.Common.generateImageName
import com.nearby.app.Common.generateUniqueId
import java.io.*
import java.net.URLConnection
import java.util.regex.Pattern

object Multimedia {

    fun ByteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        val arrayInputStream = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(arrayInputStream)
    }

    //For encoding toString
    fun getStringImage(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }
    fun getBitmapImage(encodingString :String):Bitmap{
        val data: ByteArray = Base64.decode(encodingString, Base64.DEFAULT)
        val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
        return bmp
    }


    fun isVideoFile(path:String):Boolean{
        val mimeType= URLConnection.guessContentTypeFromName(path)
        return mimeType!=null && mimeType.startsWith("video")
    }

    fun isImageFile(path:String):Boolean{
        val mimeType= URLConnection.guessContentTypeFromName(path)
        return mimeType!=null && mimeType.startsWith("image")
    }

    fun requireFileSize(path: String,reqSize:Int):Boolean{
        val file=File(path)
        val fileSizeInBytes =file.length()
        val fileSizeInKB = fileSizeInBytes / 1024
        val fileSizeInMB = fileSizeInKB / 1024

        Log.e("","$fileSizeInMB  :  $reqSize")
        return fileSizeInMB <= reqSize
    }

    fun getVideoThumbnails(path: String): Bitmap? {
        return ThumbnailUtils.createVideoThumbnail(path,
            MediaStore.Images.Thumbnails.MINI_KIND)
    }

    fun checkYoutubeUrlValidation(youtubeUrl:String):Boolean{
        return Pattern.matches("^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+",youtubeUrl)
    }

    fun compressImage(file: File, requiredSize: Int = 60): File? {
        try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image

            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()


            // Find the correct scale value. It should be the power of 2.

            var scale = 1
            while (o.outWidth / scale / 2 >= requiredSize && o.outHeight / scale / 2 >= requiredSize) {
                scale *= 2
            }

            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)

            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            val temp = File.createTempFile("pre", "su")
            val outputStream = FileOutputStream(temp)

            selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            temp.deleteOnExit()

            // size of output
            //    Logy("size of image --" + temp.length() / 1024)

            return temp
        } catch (e: Exception) {
            return null
        }

    }

    fun getFileFromBitmap(bitmap: Bitmap?,context: Context): File {
        val filesDir=context.filesDir
        val imageFile=File(filesDir,"${generateImageName(generateUniqueId().toString())}.png")
        val os: OutputStream = FileOutputStream(imageFile)
        try {
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.flush()
            os.close()

        }   catch (e:Exception){
            Log.e("",e.message?:"")
        }


        return imageFile
    }

    fun generateDarkColorFromImage(context: Context,bitmap: Bitmap): Int {
      return  Palette.from(bitmap).generate().getMutedColor(ContextCompat.getColor(context,android.R.color.black))
    }


}