package com.ll.myapplication.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.annotation.IntRange
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import kotlin.math.roundToInt

/**
 * =====================================================
 * 作    者: Xiuz
 * 创建日期: 2021/1/12 15:10
 * 描    述:
 * =====================================================
 */
object BitmapUtil {

    fun bitmap2Bytes(bm: Bitmap?): ByteArray? {
        if (null == bm) return null
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        baos.use {
            return it.toByteArray()
        }
    }

    fun getBitmapFromBytes(
        @NonNull bytes: ByteArray?,
        @Nullable opts: BitmapFactory.Options?
    ): Bitmap? {
        if (null == bytes || bytes.isEmpty()) return null
        return if (opts != null) {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, opts)
        } else {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }


    fun getFromResid(
        @NonNull res: Resources, resid: Int,
        @Nullable opts: BitmapFactory.Options?
    ): Bitmap {
        return if (opts != null) {
            BitmapFactory.decodeResource(res, resid, opts)
        } else {
            BitmapFactory.decodeResource(res, resid)
        }
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        // 取 drawable 的颜色格式
        val config =
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(w, h, config)
        //建立对应 bitmap 的画布
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        // 把 drawable 内容画到画布中
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * Bitmap转Drawable
     */
    fun btimapToDrawable(context: Context, bitmap: Bitmap?): Drawable? {
        if (null == bitmap)return null
        return BitmapDrawable(context.resources, bitmap)
    }

    fun fileToBitmap(filePath: String?): Bitmap? {
        val options = BitmapFactory.Options()
        /**
         * 压缩长宽各为一半避免图片过大装载不了
         */
        options.inPurgeable = true
        options.inSampleSize = 2
        return BitmapFactory.decodeFile(filePath)
    }

    fun fileToBitmap(file: File?): Bitmap? {
        return try {
            BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 根据指定宽高缩放图片
     *
     * @param origin    需要缩放的图片
     * @param newWidth  新的宽
     * @param newHeight 新的高
     * @return 返回缩放后的图片
     */
    fun scaleBitmap(@NonNull origin: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val height = origin.height
        val width = origin.width
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        val bitmap = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
        origin.recycle()
        return bitmap
    }

    fun bitmapCopy(bitmap:Bitmap):Bitmap{
        val bit = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bit)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        return bit
    }


    fun gaussianBlur(
        context: Context?,
        source: Bitmap,
        @IntRange(from = 0, to = 25) radius: Int,
        scale: Float
    ): Bitmap? {
        if (radius == 0) return null
        Log.i("TAG", "origin size:" + source.width + "*" + source.height)
        val width = (source.width * scale).roundToInt()
        val height = (source.height * scale).roundToInt()
        val inputBmp = Bitmap.createScaledBitmap(source, width, height, false)
        val renderScript: RenderScript = RenderScript.create(context)
        Log.i("TAG", "scale size:" + inputBmp.width + "*" + inputBmp.height)

        // Allocate memory for Renderscript to work with
        val input: Allocation = Allocation.createFromBitmap(renderScript, inputBmp)
        val output: Allocation = Allocation.createTyped(renderScript, input.type)

        // Load up an instance of the specific script that we want to use.
        val scriptIntrinsicBlur: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(
            renderScript,
            Element.U8_4(renderScript)
        )
        scriptIntrinsicBlur.setInput(input)

        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius.toFloat())

        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output)

        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp)
        renderScript.destroy()
        return inputBmp
    }
}