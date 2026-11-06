package com.example.twopayd2c.utils

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.widget.VideoView

class TextureVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : VideoView(context, attrs) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSPARENT)
    }
}