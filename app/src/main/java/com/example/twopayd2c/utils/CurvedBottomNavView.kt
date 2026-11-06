package com.example.twopayd2c.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CurvedBottomNavView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val path = Path()

    init {
        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        val mainRadius = 60f          // main half-moon radius
        val smoothRadius = 12f        // small smooth curve radius
        val centerX = w / 2

        path.reset()

        // Start top-left
        path.moveTo(0f, 0f)

        // Line till smooth start (left)
        path.lineTo(centerX - mainRadius - smoothRadius, 0f)

        // LEFT smooth curve (small radius)
        path.quadTo(
            centerX - mainRadius,
            0f,
            centerX - mainRadius,
            smoothRadius
        )

        // MAIN half-moon arc
        path.arcTo(
            RectF(
                centerX - mainRadius,
                -mainRadius + smoothRadius,
                centerX + mainRadius,
                mainRadius + smoothRadius
            ),
            180f,
            -180f,
            false
        )

        // RIGHT smooth curve
        path.quadTo(
            centerX + mainRadius,
            0f,
            centerX + mainRadius + smoothRadius,
            0f
        )

        // Line till top-right
        path.lineTo(w, 0f)

        // Rectangle bottom
        path.lineTo(w, h)
        path.lineTo(0f, h)
        path.close()

        canvas.drawPath(path, paint)
    }


    }
