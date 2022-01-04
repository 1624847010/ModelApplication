package com.ll.myapplication.ui.shape

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import com.google.android.material.shape.*
import com.ll.myapplication.R
import com.ll.myapplication.databinding.ActivityShapeBinding

class ShapeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShapeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 代码设置圆角、切角
        val shapeAppearanceModel1 = ShapeAppearanceModel.builder().apply {
            setTopLeftCorner(RoundedCornerTreatment())
            setTopLeftCornerSize(80f)
            setBottomRightCorner(RoundedCornerTreatment())
            setBottomRightCornerSize(80f)
            setTopRightCorner(CutCornerTreatment())
            setTopRightCornerSize(80f)
            setBottomLeftCorner(CutCornerTreatment())
            setBottomLeftCornerSize(80f)
        }.build()
        binding.img10.shapeAppearanceModel = shapeAppearanceModel1

        // 代码设置 角和边
        val shapeAppearanceModel2 = ShapeAppearanceModel.builder().apply {
            setAllCorners(RoundedCornerTreatment())
            setAllCornerSizes(50f)
            setAllEdges(TriangleEdgeTreatment(50f, false))
        }.build()
        val drawable2 = MaterialShapeDrawable(shapeAppearanceModel2).apply {
            setTint(ContextCompat.getColor(this@ShapeActivity, R.color.colorPrimary))
            paintStyle = Paint.Style.FILL_AND_STROKE
            strokeWidth = 50f
        }
        binding.text1.setTextColor(Color.WHITE)
        binding.text1.background = drawable2


        // 代码设置 聊天框效果
        val shapeAppearanceModel3 = ShapeAppearanceModel.builder().apply {
            setAllCorners(RoundedCornerTreatment())
            setAllCornerSizes(20f)
            setRightEdge(object : TriangleEdgeTreatment(20f, false) {
                // center 位置 ， interpolation 角的大小
                override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
                    super.getEdgePath(length, 35f, interpolation, shapePath)
                }
            })
        }.build()
        val drawable3 = MaterialShapeDrawable(shapeAppearanceModel3).apply {
            setTint(ContextCompat.getColor(this@ShapeActivity, R.color.colorPrimary))
            paintStyle = Paint.Style.FILL
        }
        (binding.text2.parent as ViewGroup).clipChildren = false
        binding.text2.setTextColor(Color.WHITE)
        binding.text2.background = drawable3
    }
}